package UbrePvP;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.util.Vector;

public class KitLauncher implements CommandExecutor, Listener {
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
	  {
	    if (cmd.getName().equalsIgnoreCase("launcher")) {
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
	          Manager.Kits.put(p.getName(), "Launcher");
	          p.sendMessage(ChatColor.GREEN + "Equipped Launcher!");
	          for (PotionEffect effect : p.getActivePotionEffects()) {
	            p.removePotionEffect(effect.getType());
	          }
	          inv.clear();
	          
	          p.getInventory().setHelmet(new ItemStack(Material.IRON_HELMET));
	          p.getInventory().setChestplate(new ItemStack(Material.CHAINMAIL_CHESTPLATE));
	          p.getInventory().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
	          
	          ItemStack launcherBoots = new ItemStack(Material.DIAMOND_BOOTS);
	          launcherBoots.addEnchantment(Enchantment.PROTECTION_FALL, 3);
	          p.getInventory().setBoots(launcherBoots);
	          
	          p.getInventory().addItem(new ItemStack(Material.IRON_SWORD));
	          p.getInventory().addItem(new ItemStack(Material.SLIME_BLOCK, 4));
	          p.getInventory().addItem(new ItemStack(Material.GOLDEN_APPLE, 3));
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
	  public void launcher(PlayerMoveEvent event)
	  {
	    Player mover = event.getPlayer();
	    if ((Manager.isInGame.contains(mover.getName())) && (Manager.Kits.containsKey(mover.getName())))
	    {
	      Block blockUnderMover = mover.getLocation().subtract(0.0D, 1.0D, 0.0D).getBlock();
	      if (blockUnderMover.getType().equals(Material.SLIME_BLOCK)) {
	        mover.setVelocity(new Vector(0, 1, 0).multiply(3.5D));
	      }
	    }
	  }

}
