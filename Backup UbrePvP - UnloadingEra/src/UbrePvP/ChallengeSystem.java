package UbrePvP;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ChallengeSystem implements CommandExecutor {
	
public static HashMap<String, String> Request = new HashMap<String, String>();
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		
	    if (cmd.getName().equalsIgnoreCase("challenge")) {
	    	
	      if ((sender instanceof Player)) {
	    	  
	        final Player p = (Player)sender;
	        
	        if (Manager.isInGame.contains(p.getName())) {
	          p.sendMessage(ChatColor.RED + "You are already in a game.");
	        }
	        else if (args.length == 0) {
	          p.sendMessage(ChatColor.RED + "Please specify which player you would like to challenge.");
	        }
	        else {
	          @SuppressWarnings("deprecation")
			  Player target = Bukkit.getPlayerExact(args[0]);
	          if (target != null) {
	            if ((target.getName().equals(p.getName())) && (target != null)) {
	              p.sendMessage(ChatColor.RED + "You cannot challenge yourself.");
	            }
	            else if ((Manager.isInGame.contains(target.getName())) || (Manager.isInPreGame.contains(target.getName()))) {
	              p.sendMessage(ChatColor.RED + "The person who you would like to challenge is already in a game!");
	            }
	            else if (Request.containsKey(p.getName())) {
	              if (((String)Request.get(p.getName())).equals(target.getName())) {
	                p.sendMessage(ChatColor.RED + "You have already requested to challenge them!");
	              }
	            }
	            else {
	              target.sendMessage(ChatColor.DARK_GREEN + p.getName() + ChatColor.GOLD + " would like to challenge you!");
	              target.sendMessage(ChatColor.GOLD + "Type: " + ChatColor.GREEN + ChatColor.BOLD + "/ACCEPT " + ChatColor.GOLD + "or " + 
	                ChatColor.DARK_RED + ChatColor.BOLD + "/DENY " + ChatColor.GOLD + "then the requestor's name!");
	              p.sendMessage(ChatColor.GREEN + "Request sent to " + target.getName());
	              Request.put(p.getName(), target.getName());
	              Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.UbrePvP, new Runnable()
	              {
	                public void run() {
	                  if (ChallengeSystem.Request.containsKey(p.getName())) {
	                    ChallengeSystem.Request.remove(p.getName());
	                  }
	                }
	              }, 400L);
	            }
	          }
	          else {
	            p.sendMessage(ChatColor.RED + "'" + args[0] + "' cannot be found.");
	          }
	        }
	      }
	    }
	    else if (cmd.getName().equalsIgnoreCase("accept")) {
	    	
	      if ((sender instanceof Player)) {
	        final Player p = (Player)sender;
	        if (Manager.isInGame.contains(p.getName())) {
	          p.sendMessage(ChatColor.RED + "You are already in a game.");
	        }
	        else if (args.length == 0) {
	          p.sendMessage(ChatColor.RED + "Please specify which player you would like to accept.");
	        }
	        else {
	          @SuppressWarnings("deprecation")
			final Player target = Bukkit.getPlayerExact(args[0]);
	          if (target == null) {
	            p.sendMessage(ChatColor.RED + "'" + args[0] + "' cannot be found.");
	          } 
	          else if ((Manager.isInGame.contains(target.getName())) || (Manager.isInPreGame.contains(target.getName()))) {
	            p.sendMessage(ChatColor.RED + "The person who challenged you is already in a game!");
	          } 
	          else if (Request.containsKey(target.getName())) {
	            if (((String)Request.get(target.getName())).equals(p.getName())) {
	              Bukkit.getServer().broadcastMessage(ChatColor.GREEN + "=-=-=-=-=-=-=-=-=-=-=-=-=-");
	              Bukkit.getServer().broadcastMessage(ChatColor.GREEN + target.getName() + 
	                ChatColor.GOLD + " has challenged " + ChatColor.GREEN + p.getName() + 
	                ChatColor.GOLD + "!");
	              
	  			  World world = Bukkit.getServer().getWorld("world");
		          Bukkit.getServer().createWorld(new WorldCreator("world"));

	              p.teleport(new Location(world, -635, 74, -203));	              
	              target.teleport(new Location(world, -563, 74, -256));
	              	              
	              Request.remove(target.getName());
	              Manager.isInPreGame.add(p.getName());
	              Manager.isInPreGame.add(target.getName());
	              
	              ItemStack kitSelector = new ItemStack(Material.FEATHER);
	              ItemMeta kitselectormeta = kitSelector.getItemMeta();
	              kitselectormeta.setDisplayName(ChatColor.DARK_GREEN + "UHC Kit Selector");
	              kitSelector.setItemMeta(kitselectormeta);
	              
	              target.getInventory().setItem(4, kitSelector);
	              p.getInventory().setItem(4, kitSelector);
	              
	              target.sendMessage(ChatColor.GOLD + "Game starting in 15 seconds!");
	              p.sendMessage(ChatColor.GOLD + "Game starting in 15 seconds!");
	              Bukkit.getServer().broadcastMessage(ChatColor.GREEN + "=-=-=-=-=-=-=-=-=-=-=-=-=-");
	              
	              Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.UbrePvP, new Runnable() {
	                public void run() {
	                  if (!Manager.Kits.containsKey(target.getName())) {
	                    Bukkit.dispatchCommand(target, "trooper");
	                  }
	                  if (!Manager.Kits.containsKey(p.getName())) {
	                    Bukkit.dispatchCommand(p, "trooper");
	                  }
	                  if (Manager.isInPreGame.contains(target.getName())) {
	                    Manager.isInPreGame.remove(target.getName());
	                    Manager.isInGame.add(target.getName());
	                  }
	                  if (Manager.isInPreGame.contains(p.getName())) {
	                    Manager.isInPreGame.remove(p.getName());
	                    Manager.isInGame.add(p.getName());
	                  }
	                }
	              }, 300L);
	            }
	          }
	          else {
	            p.sendMessage(ChatColor.RED + "That player did not challenge you.");
	          }
	        }
	      }
	    }
	    else if (cmd.getName().equalsIgnoreCase("deny")) {
	      if ((sender instanceof Player)) {
	        Player p = (Player)sender;
	        if (Manager.isInGame.contains(p.getName())) {
	          p.sendMessage(ChatColor.RED + "You cannot do this while in a game.");
	        }
	        else if (args.length == 0) {
	          p.sendMessage(ChatColor.RED + "Please specify which player you would like to deny.");
	        }
	        else {
	          @SuppressWarnings("deprecation")
			  Player target = Bukkit.getPlayerExact(args[0]);
	          if (target == null) {
	            p.sendMessage(ChatColor.RED + "'" + args[0] + "' cannot be found.");
	          }
	          else if ((Manager.isInGame.contains(target.getName())) || (Manager.isInPreGame.contains(target.getName()))) {
	            p.sendMessage(ChatColor.RED + "(The person who you denied is in a game now.)");
	          }
	          else if (!Request.containsKey(target.getName())) {
	            p.sendMessage(ChatColor.RED + "This person hasn't sent you a request.");
	          }
	          else {
	            target.sendMessage(ChatColor.RED + p.getName() + ChatColor.DARK_RED + " has denied your request.");
	            Request.remove(target.getName());
	            p.sendMessage(ChatColor.GREEN + "You have denied this person.");
	          }
	        }
	      }
	    }
	    return false;
	  }

}
