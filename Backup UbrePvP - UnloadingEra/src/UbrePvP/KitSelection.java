package UbrePvP;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class KitSelection implements Listener {
	
	private void openGUI(Player player) {
		Inventory inv = Bukkit.createInventory(null, 54, ChatColor.DARK_RED + "" + ChatColor.BOLD + "         Kit Selector");
	    
		//// Kits start below
		ItemStack trooper = new ItemStack(Material.IRON_SWORD);
		trooper.addEnchantment(Enchantment.DAMAGE_ALL, 1);
		ItemMeta trooperMeta = trooper.getItemMeta();
		trooperMeta.setLore(Arrays.asList(ChatColor.AQUA + "Probably the most balanced kit. Trooper ",
				  ChatColor.AQUA + "is an average kit with sustain.")); 			  
		trooperMeta.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD + ChatColor.UNDERLINE + "Trooper"); 
		trooper.setItemMeta(trooperMeta);
		/////////////////////////////////////////////////////////////////////////////////////////////////////
		ItemStack shortbow = new ItemStack(Material.BOW);
		shortbow.addEnchantment(Enchantment.ARROW_DAMAGE, 1);
		ItemMeta shortbowMeta = shortbow.getItemMeta();
		shortbowMeta.setLore(Arrays.asList(ChatColor.AQUA + "An archer kit designed for precise ",
				  ChatColor.AQUA + "damage. ")); 			  
		shortbowMeta.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD + ChatColor.UNDERLINE + "Shortbow"); 
		shortbow.setItemMeta(shortbowMeta);
		/////////////////////////////////////////////////////////////////////////////////////////////////////
		ItemStack sniper = new ItemStack(Material.ARROW);
		sniper.addUnsafeEnchantment(Enchantment.ARROW_DAMAGE, 1);
		ItemMeta sniperMeta = sniper.getItemMeta();
		sniperMeta.setLore(Arrays.asList(ChatColor.AQUA + "Wreak havoc with shortbows' guru. ",
				  ChatColor.AQUA + "(Insane damage.) ")); 			  
		sniperMeta.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD + ChatColor.UNDERLINE + "Sniper"); 
		sniper.setItemMeta(sniperMeta);
		/////////////////////////////////////////////////////////////////////////////////////////////////////
		ItemStack axeman = new ItemStack(Material.DIAMOND_AXE);
	    ItemMeta axemanMeta = axeman.getItemMeta();
	    axemanMeta.setLore(Arrays.asList(new String[] { ChatColor.AQUA + "Chop not only through wood easily, ", 
	      ChatColor.AQUA + "but also flesh. " }));
	    axemanMeta.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD + ChatColor.UNDERLINE + "Axeman");
	    axeman.setItemMeta(axemanMeta);
		/////////////////////////////////////////////////////////////////////////////////////////////////////
	    ItemStack switcher = new ItemStack(Material.SNOW_BALL);
	    ItemMeta switcherMeta = switcher.getItemMeta();
	    switcherMeta.setLore(Arrays.asList(new String[] { ChatColor.AQUA + "Throw snowballs that switch your ", 
	      ChatColor.AQUA + "position with whoever you hit." }));
	    switcherMeta.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD + ChatColor.UNDERLINE + "Switcher");
	    switcher.setItemMeta(switcherMeta);
		/////////////////////////////////////////////////////////////////////////////////////////////////////
	    ItemStack pyro = new ItemStack(Material.FLINT_AND_STEEL);
	    ItemMeta pyroMeta = pyro.getItemMeta();
	    pyroMeta.setLore(Arrays.asList(new String[] { ChatColor.AQUA + "Go wild with pyro because ", 
	      ChatColor.AQUA + "it is a master of fire." }));
	    pyroMeta.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD + ChatColor.UNDERLINE + "Pyro");
	    pyro.setItemMeta(pyroMeta);
		/////////////////////////////////////////////////////////////////////////////////////////////////////
	    ItemStack stomper = new ItemStack(Material.IRON_BOOTS);
	    ItemMeta stomperMeta = stomper.getItemMeta();
	    stomperMeta.setLore(Arrays.asList(new String[] { ChatColor.AQUA + "Stomp down on enemies and transfer ", 
	      ChatColor.AQUA + "your fall damage to them!" }));
	    stomperMeta.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD + ChatColor.UNDERLINE + "Stomper");
	    stomper.setItemMeta(stomperMeta);
		/////////////////////////////////////////////////////////////////////////////////////////////////////
	    ItemStack hybrid = new ItemStack(Material.APPLE);
	    ItemMeta hybridMeta = hybrid.getItemMeta();
	    hybridMeta.setLore(Arrays.asList(new String[] { ChatColor.AQUA + "A great kit for players who appreciate ", 
	      ChatColor.AQUA + "the mixture of melee and ranged elements. " }));
	    hybridMeta.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD + ChatColor.UNDERLINE + "Hybrid");
	    hybrid.setItemMeta(hybridMeta);
		/////////////////////////////////////////////////////////////////////////////////////////////////////
	    ItemStack launcher = new ItemStack(Material.SLIME_BLOCK);
	    ItemMeta launcherMeta = launcher.getItemMeta();
	    launcherMeta.setLore(Arrays.asList(new String[] { ChatColor.AQUA + "Place blocks that will launch ", 
	      ChatColor.AQUA + "players into the sky when stepped on." }));
	    launcherMeta.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD + ChatColor.UNDERLINE + "Launcher");
	    launcher.setItemMeta(launcherMeta);
		/////////////////////////////////////////////////////////////////////////////////////////////////////

		
		inv.setItem(0, trooper);
		inv.setItem(1, shortbow);
		inv.setItem(2, sniper);
		inv.setItem(3, axeman);
		inv.setItem(4, switcher);
		inv.setItem(5, pyro);
		inv.setItem(6, stomper);
		inv.setItem(7, hybrid);
		inv.setItem(8, launcher);

		player.openInventory(inv);
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		if (!ChatColor.stripColor(event.getInventory().getName()).equalsIgnoreCase("         Kit Selector"))
			return;
		Player player = (Player) event.getWhoClicked();
		event.setCancelled(true);
		
		if ((event.getCurrentItem() == null) || (event.getCurrentItem().getType() == Material.AIR) || (!event.getCurrentItem().hasItemMeta()))
	    {
	        player.closeInventory();
	        return;
	    }
		
		switch (event.getCurrentItem().getType()) 
		{
		case IRON_SWORD:
			Bukkit.dispatchCommand(player, "trooper"); 
			player.closeInventory();
			break;
		case BOW:
			Bukkit.dispatchCommand(player, "shortbow"); 
			player.closeInventory();
			break;
		case ARROW:
			Bukkit.dispatchCommand(player, "sniper"); 
			player.closeInventory();
			break;
		case DIAMOND_AXE:
			Bukkit.dispatchCommand(player, "axeman"); 
			player.closeInventory();
			break;
		case SNOW_BALL:
			Bukkit.dispatchCommand(player, "switcher"); 
			player.closeInventory();
			break;
		case FLINT_AND_STEEL:
			Bukkit.dispatchCommand(player, "pyro"); 
			player.closeInventory();
			break;
		case IRON_BOOTS:
			Bukkit.dispatchCommand(player, "stomper"); 
			player.closeInventory();
			break;
		case STONE:
			Bukkit.dispatchCommand(player, "hybrid"); 
			player.closeInventory();
			break;
		default:
			break;
		}
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		Action a = event.getAction();
		ItemStack is = event.getItem();
		
		if ((a == Action.PHYSICAL) || (is == null) || (is.getType() == Material.AIR)) 
		{
			return;
	    }
		if (is.getType() == Material.FEATHER)
	    {
	        openGUI(event.getPlayer());
	    }
	    else if (is.getType() == Material.PAPER)
	    {
	        event.getPlayer().sendMessage(ChatColor.DARK_AQUA + "In UbrePvP, players must fight and kill ");
	        event.getPlayer().sendMessage(ChatColor.DARK_AQUA + "others in a 1v1 match. They are equipped ");
	        event.getPlayer().sendMessage(ChatColor.DARK_AQUA + "with useful items such as water and lava ");
	        event.getPlayer().sendMessage(ChatColor.DARK_AQUA + "buckets, blocks to build, an axe, etc. ");
	        event.getPlayer().sendMessage(ChatColor.DARK_AQUA + "They can also modify the terrain! There is ");
	        event.getPlayer().sendMessage(ChatColor.DARK_AQUA + "room for tons of skill and creativity! Enjoy! ");
	        event.getPlayer().sendMessage(ChatColor.AQUA + "Type /challenge <player name> to request to challenge them!");
	    }
	}

}
