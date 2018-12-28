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

public class KitShortbow implements CommandExecutor {
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) 
    {   	
		if (cmd.getName().equalsIgnoreCase("shortbow")) 
		{
			if (sender instanceof Player) 
			{
				Player p = (Player) sender;
				if (Manager.Kits.containsKey(p.getName())) 
				{
					p.sendMessage(ChatColor.RED + "You already have a kit!");
				}else if (Manager.isInPreGame.contains(p.getName()))
				{
					Inventory inv = p.getInventory();
					Manager.Kits.put(p.getName(), "Shortbow");
					p.sendMessage(ChatColor.GREEN + "Equipped Shortbow!");
					for (PotionEffect effect : p.getActivePotionEffects())
					p.removePotionEffect(effect.getType());
					inv.clear();
						
					p.getInventory().setHelmet(new ItemStack(Material.IRON_HELMET));
				    p.getInventory().setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
					p.getInventory().setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
					p.getInventory().setBoots(new ItemStack(Material.IRON_BOOTS));

					p.getInventory().addItem(new ItemStack(Material.IRON_SWORD));
					
					ItemStack shortBow = new ItemStack(Material.BOW);
					shortBow.addEnchantment(Enchantment.ARROW_DAMAGE, 1);
					shortBow.addEnchantment(Enchantment.ARROW_INFINITE, 1);
					p.getInventory().addItem(shortBow);
					
					p.getInventory().setItem(15, new ItemStack(Material.ARROW));
					p.getInventory().addItem(new ItemStack(Material.LAVA_BUCKET));
					p.getInventory().addItem(new ItemStack(Material.WATER_BUCKET));
					p.getInventory().addItem(new ItemStack(Material.WOOD, 64*2));
					p.getInventory().addItem(new ItemStack(Material.IRON_AXE));

				}else 
				{
					p.sendMessage(ChatColor.RED + "You can only select kits pre-game!");
				}
			}
		}
		return false;
	}

}
