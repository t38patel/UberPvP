package UbrePvP;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

public class KitPyro implements CommandExecutor {
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
	{
	    if (cmd.getName().equalsIgnoreCase("pyro")) 
	    {
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
	                Manager.Kits.put(p.getName(), "Pyro");
	                p.sendMessage(ChatColor.GREEN + "Equipped Pyro!");
	                for (PotionEffect effect : p.getActivePotionEffects()) {
	                p.removePotionEffect(effect.getType());
	                }
	                inv.clear();
	          
	                p.getInventory().setHelmet(new ItemStack(Material.GOLD_HELMET));
	                p.getInventory().setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
	                p.getInventory().setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
	                p.getInventory().setBoots(new ItemStack(Material.GOLD_BOOTS));
	          
	                ItemStack pyroSword = new ItemStack(Material.IRON_SWORD, 1);
	                pyroSword.addEnchantment(Enchantment.FIRE_ASPECT, 2);
	                p.getInventory().addItem(new ItemStack[] { pyroSword });
	          
	                ItemStack pyroBow = new ItemStack(Material.BOW);
	                pyroBow.addEnchantment(Enchantment.ARROW_FIRE, 1);
	                pyroBow.addEnchantment(Enchantment.ARROW_INFINITE, 1);
	                p.getInventory().addItem(new ItemStack[] { pyroBow });
	          
	                p.getInventory().addItem(new ItemStack[] { new ItemStack(Material.GOLDEN_APPLE, 2) });
	                p.getInventory().addItem(new ItemStack[] { new ItemStack(Material.LAVA_BUCKET, 2) });
	                p.getInventory().addItem(new ItemStack[] { new ItemStack(Material.IRON_AXE) });
	                p.getInventory().addItem(new ItemStack[] { new ItemStack(Material.WOOD, 256) });
	                p.getInventory().setItem(35, new ItemStack(Material.ARROW));
	            }
	            else
	            {
	                p.sendMessage(ChatColor.RED + "You can only select kits pre-game!");
	            }
	        }
	    }
	    return false;
	}

}
