package UbrePvP;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.WorldCreator;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;

public class Manager implements Listener {
	
	
    public static HashMap<String, String> Kits = new HashMap<String, String>();
    public static List<String> isInGame = new ArrayList<String>();
    public static List<String> isInPreGame = new ArrayList<String>();
    public static HashMap<String, Long> cooldown = new HashMap<String, Long>();
    
	World hub = Bukkit.getServer().getWorld("hub");
	World world = Bukkit.getServer().getWorld("world");

	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void blockbreak(BlockBreakEvent blockbreak) {	    
	    Player breaker = blockbreak.getPlayer();
	    if (!(isInPreGame.contains(breaker.getName()))) {
	    	if (blockbreak.getBlock().getType() == Material.GLASS || blockbreak.getBlock().getType() == Material.BEDROCK) {
		    	blockbreak.setCancelled(true);
		    }else if (blockbreak.getBlock().getType() == Material.WOOD) {
			    breaker.getInventory().addItem(new ItemStack(Material.WOOD));
			    breaker.updateInventory();
		    }	   
	    }
	}
	
	@EventHandler
	public void weather(WeatherChangeEvent event) {
		World w = Bukkit.getWorld("world");
		if (w.hasStorm()) {
			w.setStorm(false);
		}else if (w.isThundering()) {
			w.setThundering(false);
		}
	}
	
	@EventHandler
	public void foodchange(FoodLevelChangeEvent foodchange) {
		foodchange.setCancelled(true);		
	}

	@EventHandler
	public void blockplace(BlockPlaceEvent blockplace) {
	    if (isInPreGame.contains(blockplace.getPlayer().getName())) {
	        blockplace.setCancelled(true);
	    }
	}
	
	@EventHandler
	public void dropitem(PlayerDropItemEvent dropItem) {
	    dropItem.setCancelled(true);
	}
	
	@EventHandler
	public void bloodpickup(PlayerPickupItemEvent pickItem) {
		pickItem.setCancelled(true);
	}
	
	@EventHandler
	public void playerMove(PlayerMoveEvent event) {
	    if (isInPreGame.contains(event.getPlayer().getName())) {
	        event.setCancelled(true);
	        Player mover = event.getPlayer();
	        mover.teleport(event.getFrom());
	    }
	}
	
	@EventHandler
	public void noDmg(EntityDamageByEntityEvent event) {
		if ((!(Kits.containsKey(event.getEntity().getName()))) || (!(Kits.containsKey(event.getDamager().getName())))) {
			event.setCancelled(true);
		}
	}
	
    @EventHandler
    public void onEntityDamage(PlayerTeleportEvent event) {
        Player player = event.getPlayer();
 
        if (event.getCause() == TeleportCause.ENDER_PEARL) {
            event.setCancelled(true);
 
            player.teleport(event.getTo());
        }
    }
	
    @EventHandler
    public void playerShoot(EntityShootBowEvent event) {
        if ((event.getEntity() instanceof Player)) {
            Player shooter = (Player)event.getEntity();
            if (isInPreGame.contains(shooter.getName())) {
                event.setCancelled(true);
            }
            if (event.getForce() <= 0.38D) {
                event.setCancelled(true);
                shooter.sendMessage(ChatColor.RED + "Bow spamming is not allowed.");
            }
        }
    }
	
	@EventHandler
	public void respawn(PlayerRespawnEvent respawnEvent) {			
		Player respawnedPlayer = respawnEvent.getPlayer();
		
		for (PotionEffect effect : respawnedPlayer.getActivePotionEffects()) {
			respawnedPlayer.removePotionEffect(effect.getType());
		}
		
		respawnedPlayer.getInventory().setHelmet(null);
		respawnedPlayer.getInventory().setChestplate(null);
		respawnedPlayer.getInventory().setLeggings(null);
		respawnedPlayer.getInventory().setBoots(null);
		respawnedPlayer.setMaxHealth(20);
		respawnedPlayer.setFoodLevel(20);
		
		ItemStack rules = new ItemStack(Material.PAPER);
		ItemMeta rulesMeta = rules.getItemMeta();
		rulesMeta.setDisplayName(ChatColor.DARK_GREEN + "Rules");
		rules.setItemMeta(rulesMeta);
		
		respawnedPlayer.getInventory().setItem(4, rules);
	}
	
	@EventHandler
	public void onDeath(PlayerDeathEvent event) {
		Player player = event.getEntity();
		Player killer = event.getEntity().getKiller();
		
		player.getInventory().clear();
		if (isInGame.contains(player.getName())) {
			isInGame.remove(player.getName());
		}
		if (Kits.containsKey(player.getName())) {
			Kits.remove(player.getName());
		}		
		if (isInGame.contains(killer.getName())) {
			Bukkit.getServer().broadcastMessage(ChatColor.GREEN + killer.getName() + ChatColor.GOLD
					+ " has defeated " + ChatColor.RED + player.getName() + ChatColor.GOLD + "!");	
			killer.getInventory().clear();
			killer.getInventory().setArmorContents(null);
			for (PotionEffect effect : killer.getActivePotionEffects())
			    killer.removePotionEffect(effect.getType());
			
			killer.teleport(new Location(hub, -109, 134, -157));
			isInGame.remove(killer.getName());		
		}		

		if (Kits.containsKey(killer.getName())) {
			Kits.remove(killer.getName());
		}
		
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.UbrePvP, new Runnable() {
          public void run() {
      		rollback("world");
          }
        }, 30L);
	}
	
	@EventHandler
	public void leaver(PlayerQuitEvent leaveEvent) {
		Player leaver = leaveEvent.getPlayer();
		leaver.getInventory().clear();
		
		if (Kits.containsKey(leaver.getName())) {
			Kits.remove(leaver.getName());
		}
		
		for (PotionEffect effect : leaver.getActivePotionEffects()) {
		    leaver.removePotionEffect(effect.getType());
		}
		
		if (isInGame.contains(leaver.getName())) {
			isInGame.remove(leaver.getName());
		}
		if (isInPreGame.contains(leaver.getName())) {
			isInPreGame.remove(leaver.getName());
		}
		if (ChallengeSystem.Request.containsKey(leaver.getName())) {
			ChallengeSystem.Request.remove(leaver.getName());
		}		
		if (isInGame.size() == 1 || isInPreGame.size() == 1) {
		    for (Player p : Bukkit.getOnlinePlayers()) {
		        if (isInPreGame.contains(p.getName()) || isInGame.contains(p.getName())) {
		            p.teleport(new Location(hub, -109, 134, -157));
		            p.getInventory().clear();
		            for (PotionEffect effect : p.getActivePotionEffects()) {
		                p.removePotionEffect(effect.getType());
		            }
		            ItemStack rules = new ItemStack(Material.PAPER);
		            ItemMeta rulesMeta = rules.getItemMeta();
		            rulesMeta.setDisplayName(ChatColor.DARK_GREEN + "UHC Rules");
		            rules.setItemMeta((ItemMeta)rulesMeta);
		          
		            p.getInventory().setItem(4, rules);
		          
		            if (isInPreGame.contains(p.getName())) {
			            isInPreGame.remove(p.getName());
		            }
		            if (isInGame.contains(p.getName())) {
			            isInGame.remove(p.getName());
		            }
		            if (Kits.containsKey(p.getName())) {
			            Kits.remove(p.getName());
		            }
		        }
		    }
		}
		
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.UbrePvP, new Runnable() {
          public void run() {
      		rollback("world");
          }
        }, 30L);
	}
	
	@EventHandler
	public void join(PlayerJoinEvent joinEvent) {
		Player joiner = joinEvent.getPlayer();
		World hub = Bukkit.getServer().getWorld("hub");
		joiner.teleport(new Location(hub, -109, 134, -157));
		
		joiner.sendMessage(ChatColor.GREEN + "===== Welcome to UbrePvP! =====");
		joiner.sendMessage(ChatColor.GREEN + "Report any bugs you find to Ubre please!");
		joiner.sendMessage(ChatColor.GREEN + "Enjoy!");
		
		joiner.getInventory().setHelmet(null);
		joiner.getInventory().setChestplate(null);
		joiner.getInventory().setLeggings(null);
		joiner.getInventory().setBoots(null);
		joiner.getInventory().clear();
		joiner.setMaxHealth(20);
		joiner.setFoodLevel(20);
		
		ItemStack rules = new ItemStack(Material.PAPER);
		ItemMeta rulesMeta = rules.getItemMeta();
		rulesMeta.setDisplayName(ChatColor.DARK_GREEN + "UHC Rules");
		rules.setItemMeta(rulesMeta);
		
		joiner.getInventory().setItem(4, rules);
	}

	public static void unloadMap(String mapname){
        if(Bukkit.getServer().unloadWorld(Bukkit.getServer().getWorld(mapname), false)) {
            Bukkit.getLogger().info("Successfully unloaded " + mapname);
        }
        else {
            Bukkit.getLogger().severe("COULD NOT UNLOAD " + mapname);
        }
    }
	
    public static void loadMap(String mapname){
        World w = Bukkit.getServer().createWorld(new WorldCreator(mapname));
        w.setAutoSave(false);
    }
 
    public static void rollback(String mapname){
        unloadMap(mapname);
        loadMap(mapname);
    }	

}
