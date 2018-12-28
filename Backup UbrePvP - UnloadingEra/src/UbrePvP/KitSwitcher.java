package UbrePvP;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

public class KitSwitcher implements CommandExecutor, Listener {
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
	  {
	    if (cmd.getName().equalsIgnoreCase("switcher")) {
	      if ((sender instanceof Player))
	      {
	        Player p = (Player)sender;
	        if (Manager.Kits.containsKey(p.getName()))
	        {
	          p.sendMessage(ChatColor.RED + "You already have a kit!");
	        }
	        else if (Manager.isInPreGame.contains(p.getName()))
	        {
	          Inventory inv = p.getInventory();
	          Manager.Kits.put(p.getName(), "Switcher");
	          p.sendMessage(ChatColor.GREEN + "Equipped Switcher!");
	          for (PotionEffect effect : p.getActivePotionEffects()) {
	            p.removePotionEffect(effect.getType());
	          }
	          inv.clear();
	          
	          p.getInventory().setHelmet(new ItemStack(Material.LEATHER_HELMET));
	          p.getInventory().setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
	          p.getInventory().setLeggings(new ItemStack(Material.LEATHER_LEGGINGS));
	          p.getInventory().setBoots(new ItemStack(Material.DIAMOND_BOOTS));
	          
	          p.getInventory().addItem(new ItemStack(Material.DIAMOND_SWORD));
	          p.getInventory().addItem(new ItemStack(Material.SNOW_BALL, 5));
	          p.getInventory().addItem(new ItemStack(Material.GOLDEN_APPLE, 1));
	          p.getInventory().addItem(new ItemStack(Material.LAVA_BUCKET));
	          p.getInventory().addItem(new ItemStack(Material.WATER_BUCKET));
	          p.getInventory().addItem(new ItemStack(Material.IRON_AXE));
	          p.getInventory().addItem(new ItemStack(Material.WOOD, 256));
	        }
	        else
	        {
	          p.sendMessage(ChatColor.RED + "You can only select kits pre-game!");
	        }
	      }
	    }
	    return false;
	  }
	  
	  @EventHandler
	  public void switcherBall(EntityDamageByEntityEvent event)
	  {
	      if ((event.getEntity() instanceof Player))
	      {
	          Player hurt = (Player)event.getEntity();
	          if (Manager.isInGame.contains(hurt.getName())) 
	          {
	              if ((event.getDamager() instanceof Snowball))
	              {
	                  Snowball snowball = (Snowball)event.getDamager();
	                  Player thrower = (Player)snowball.getShooter();
	                  if (Manager.isInGame.contains(thrower.getName()))
	                  {
	                      Location throwersLoc = thrower.getLocation();
	                      Location hurtsLoc = hurt.getLocation();
	            
	                      thrower.teleport(hurtsLoc);
	                      hurt.teleport(throwersLoc);
	                      thrower.sendMessage(ChatColor.AQUA + "You switched positions with " + ChatColor.BOLD + hurt.getName() + "!");
	                      hurt.sendMessage(ChatColor.AQUA + "You were switched by " + ChatColor.BOLD + thrower.getName() + "!");
	                  }
	              }
	          }
	      }
	  }
	  
	  @EventHandler
	  public void switcherThrow(PlayerInteractEvent event)
	  {
	      if (Manager.Kits.containsKey(event.getPlayer().getName())) 
	      {
	          if ((Manager.Kits.get(event.getPlayer().getName())).equals("Switcher"))
	          {
	              Player switcher = event.getPlayer();
	              if (switcher.getItemInHand().getType() == Material.SNOW_BALL)
	              {
	                  int cooldownTime = 10;
	                  if (Manager.cooldown.containsKey(switcher.getName()))
	                  {
	                      long timeLeft = (Manager.cooldown.get(switcher.getName())).longValue() / 1000L + cooldownTime - System.currentTimeMillis() / 1000L;
	                      if (timeLeft > 0L)
	                      {
	                          event.setCancelled(true);
	                          switcher.sendMessage(ChatColor.RED + "Switcher is still on cooldown for " + timeLeft + " seconds!");
	                          return;
	                      }
	                      Manager.cooldown.remove(switcher.getName());
	                  }
	                  Manager.cooldown.put(switcher.getName(), Long.valueOf(System.currentTimeMillis()));
	              }
	          }
	      }
	  }

}
