package UbrePvP;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

public class KitStomper implements CommandExecutor, Listener {
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
	  {
	    if (cmd.getName().equalsIgnoreCase("stomper")) {
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
	          Manager.Kits.put(p.getName(), "Stomper");
	          p.sendMessage(ChatColor.GREEN + "Equipped Stomper!");
	          for (PotionEffect effect : p.getActivePotionEffects()) {
	            p.removePotionEffect(effect.getType());
	          }
	          inv.clear();
	          
	          p.getInventory().setHelmet(new ItemStack(Material.DIAMOND_HELMET));
	          p.getInventory().setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
	          p.getInventory().setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
	          p.getInventory().setBoots(new ItemStack(Material.DIAMOND_BOOTS));
	          
	          p.getInventory().addItem(new ItemStack(Material.DIAMOND_SWORD));
	          p.getInventory().addItem(new ItemStack(Material.GOLDEN_APPLE, 2));
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
	  public void stomp(EntityDamageEvent event)
	  {
	    if ((event.getEntity() instanceof Player)) {
	      if (event.getCause() == EntityDamageEvent.DamageCause.FALL)
	      {
	        Player stomper = (Player)event.getEntity();
	        if (Manager.isInGame.contains(stomper.getName())) {
	          if ((Manager.Kits.get(stomper.getName())).equals("Stomper"))
	          {
	            for (Entity ent : stomper.getNearbyEntities(3.0D, 3.0D, 3.0D)) {
	              if ((ent instanceof LivingEntity)) {
	                ((LivingEntity)ent).damage(event.getDamage());
	              }
	            }
	            event.setCancelled(true);
	            stomper.damage(event.getDamage() / 3.0D);
	          }
	        }
	      }
	    }
	  }

}
