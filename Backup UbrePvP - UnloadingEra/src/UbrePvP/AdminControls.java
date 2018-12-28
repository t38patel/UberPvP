package UbrePvP;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AdminControls implements CommandExecutor {
	
	World hub = Bukkit.getServer().getWorld("hub");
	World world = Bukkit.getServer().getWorld("world");
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if (cmd.getName().equalsIgnoreCase("tpworld")) {
			if (sender instanceof Player) {
		        Player p = (Player)sender;
		        if (p.getName().equalsIgnoreCase("____EL_UBRE____")) {
		        	p.teleport(new Location(world, -635, 74, -203));
		        	p.sendMessage(ChatColor.RED + "Teleported to 'world' successfully.");
		        }
			}
		}else if (cmd.getName().equalsIgnoreCase("tphub")) {
			if (sender instanceof Player) {
		        Player p = (Player)sender;
		        if (p.getName().equalsIgnoreCase("____EL_UBRE____")) {
		        	p.teleport(new Location(hub, -109, 134, -157));
		        	p.sendMessage(ChatColor.RED + "Teleported to 'hub' successfully.");
		        }
			}
		}
		
		return false;
	}

}
