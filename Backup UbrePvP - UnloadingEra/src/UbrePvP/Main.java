package UbrePvP;

import org.bukkit.Bukkit;
import org.bukkit.WorldCreator;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {
	
	public static Plugin UbrePvP;
	
	@Override
	public void onEnable() {				 
		UbrePvP = this;	
		
    	Bukkit.getServer().createWorld(new WorldCreator("world"));
    	Bukkit.getServer().createWorld(new WorldCreator("hub"));
		
		Manager manager = new Manager();
		KitSelection kitSelection = new KitSelection();
		KitSwitcher switcher = new KitSwitcher();
		KitStomper stomper = new KitStomper();
		KitLauncher launcher = new KitLauncher();
		
		// ALL EVENt HANDLERS 
		Bukkit.getServer().getPluginManager().registerEvents(manager, this);
		Bukkit.getServer().getPluginManager().registerEvents(kitSelection, this);
		Bukkit.getServer().getPluginManager().registerEvents(switcher, this);
		Bukkit.getServer().getPluginManager().registerEvents(stomper, this);
		Bukkit.getServer().getPluginManager().registerEvents(launcher, this);
		Bukkit.getServer().getPluginManager().registerEvents(this, this);
		
		// ALL COMMANDS, AND WHICH CLASS THEY FROM
		getCommand("challenge").setExecutor(new ChallengeSystem());
		getCommand("accept").setExecutor(new ChallengeSystem());
		getCommand("deny").setExecutor(new ChallengeSystem());
		getCommand("tpworld").setExecutor(new AdminControls());
		getCommand("tphub").setExecutor(new AdminControls());
		
		getCommand("trooper").setExecutor(new KitTrooper());
		getCommand("shortbow").setExecutor(new KitShortbow());
		getCommand("axeman").setExecutor(new KitAxeman());
		getCommand("sniper").setExecutor(new KitSniper());
		getCommand("switcher").setExecutor(new KitSwitcher());
		getCommand("pyro").setExecutor(new KitPyro());
		getCommand("stomper").setExecutor(new KitStomper());
		getCommand("hybrid").setExecutor(new KitHybrid());
		getCommand("launcher").setExecutor(new KitLauncher());
	}	

	public void onDisable() {		
		UbrePvP = null;		
	}

}
