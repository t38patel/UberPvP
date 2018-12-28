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

public class KitTrooper implements CommandExecutor {
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) 
    {   	
		if (cmd.getName().equalsIgnoreCase("trooper")) 
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
					Manager.Kits.put(p.getName(), "Trooper");
					p.sendMessage(ChatColor.GREEN + "Equipped Trooper!");
					for (PotionEffect effect : p.getActivePotionEffects())
					p.removePotionEffect(effect.getType());
					inv.clear();
						
					p.getInventory().setHelmet(new ItemStack(Material.DIAMOND_HELMET));
				    p.getInventory().setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
					p.getInventory().setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
					p.getInventory().setBoots(new ItemStack(Material.DIAMOND_BOOTS));					
					ItemStack troopersword = new ItemStack(Material.IRON_SWORD, 1);
					troopersword.addEnchantment(Enchantment.DAMAGE_ALL, 1);
					p.getInventory().addItem(troopersword);
					p.getInventory().addItem(new ItemStack(Material.GOLDEN_APPLE, 5));
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
