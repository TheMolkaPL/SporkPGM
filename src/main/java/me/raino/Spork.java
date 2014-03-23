package me.raino;

import java.io.File;
import java.util.Collection;

import me.raino.commands.AdminCommands;
import me.raino.commands.UserCommands;
import me.raino.listener.BlockListener;
import me.raino.listener.ObserverListener;
import me.raino.listener.PlayerListener;
import me.raino.listener.SporkListener;
import me.raino.map.MapLoader;
import me.raino.map.MapManager;
import me.raino.map.SporkMap;
import me.raino.match.MatchManager;
import me.raino.module.ModuleRegistry;
import me.raino.module.exceptions.ModuleLoadException;
import me.raino.module.modules.InfoModule;
import me.raino.module.modules.RegionModule;
import me.raino.module.modules.SpawnModule;
import me.raino.module.modules.TeamModule;
import me.raino.rotation.Rotation;
import me.raino.util.Config;
import me.raino.util.Log;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.joda.time.Duration;
import org.joda.time.Instant;

import com.sk89q.bukkit.util.CommandsManagerRegistration;
import com.sk89q.minecraft.util.commands.CommandException;
import com.sk89q.minecraft.util.commands.CommandPermissionsException;
import com.sk89q.minecraft.util.commands.CommandUsageException;
import com.sk89q.minecraft.util.commands.CommandsManager;
import com.sk89q.minecraft.util.commands.MissingNestedCommandException;
import com.sk89q.minecraft.util.commands.WrappedCommandException;

public class Spork extends JavaPlugin {

	private static Spork spork;

	private CommandsManager<CommandSender> commands;

	private MapManager mapManager;
	private MatchManager matchManager;

	public void onDisable() {
		this.matchManager.deleteMatch(this.matchManager.getCurrentMatch());

		spork = null;
	}

	public void onEnable() {
		Instant start = Instant.now();
		spork = this;

		Config.init();
		Log.setDebugging(Config.General.DEBUG);

		Log.info("Loading modules...");
		this.registerModules();

		Log.info("Loading maps...");
		MapLoader loader = new MapLoader();
		File mapDir = new File(Config.Map.DIRECTORY);
		Collection<SporkMap> maps = loader.loadMaps(mapDir);
		if (maps.size() < 1) {
			Log.warning("I need at least 1 (one) map in order to function!");
			Log.warning("Disabling...");
			this.getServer().getPluginManager().disablePlugin(this);
			return;
		}
		Log.debug("Loaded " + maps.size() + " map" + (maps.size() != 1 ? "s" : "") + " in total!");
		this.mapManager = new MapManager(maps);

		Log.info("Loading rotation...");
		File rotationFile = new File(this.getDataFolder(), Config.Rotation.ROTATION);
		Rotation rotation = new Rotation(mapManager, rotationFile);
		this.matchManager = new MatchManager(mapManager, rotation);
		if (rotation.getRotation().size() < 1) {
			Log.warning("I need at least 1 (one) map in rotation in order to function!");
			Log.warning("Disabling...");
			this.getServer().getPluginManager().disablePlugin(this);
			return;
		}
		Log.debug("Loaded " + rotation.getRotation().size() + " map" + (rotation.getRotation().size() != 1 ? "s" : "") + " in rotation!");

		Log.info("Loading commands...");
		this.registerCommands();

		Log.info("Loading listeners...");
		this.registerListeners();

		Log.info("Successfully enabled in " + new Duration(start, Instant.now()).getMillis() + "ms!");

		Log.info("Starting the match cycle...");
		this.matchManager.cycle(null);
	}

	private void registerCommands() {
		this.commands = new CommandsManager<CommandSender>() {
			public boolean hasPermission(CommandSender sender, String perm) {
				return sender.hasPermission(perm);
			}
		};
		CommandsManagerRegistration cmr = new CommandsManagerRegistration(this, this.commands);
		cmr.register(UserCommands.class);
		cmr.register(AdminCommands.class);
	}

	private void registerModules() {
		try {
			ModuleRegistry.register(InfoModule.class);
			ModuleRegistry.register(TeamModule.class);
			ModuleRegistry.register(RegionModule.class);
			ModuleRegistry.register(SpawnModule.class);
		} catch (ModuleLoadException e) {
			Log.log(e);
		}
	}

	private void registerListeners() {
		PluginManager pm = this.getServer().getPluginManager();
		pm.registerEvents(new PlayerListener(), this);
		pm.registerEvents(new BlockListener(), this);
		pm.registerEvents(new SporkListener(), this);
		pm.registerEvents(new ObserverListener(), this);
	}

	public MapManager getMapManager() {
		return mapManager;
	}

	public MatchManager getMatchManager() {
		return matchManager;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		try {
			this.commands.execute(cmd.getName(), args, sender, sender);
		} catch (CommandPermissionsException e) {
			sender.sendMessage(ChatColor.RED + "You don't have permission.");
		} catch (MissingNestedCommandException e) {
			sender.sendMessage(ChatColor.RED + e.getUsage());
		} catch (CommandUsageException e) {
			sender.sendMessage(ChatColor.RED + e.getMessage());
			sender.sendMessage(ChatColor.RED + e.getUsage());
		} catch (WrappedCommandException e) {
			if (e.getCause() instanceof NumberFormatException) {
				sender.sendMessage(ChatColor.RED + "Number expected, string received instead.");
			} else {
				sender.sendMessage(ChatColor.RED + "An error has occurred. See console.");
				e.printStackTrace();
			}
		} catch (CommandException e) {
			sender.sendMessage(ChatColor.RED + e.getMessage());
		}
		return true;
	}

	public static Spork get() {
		return spork;
	}

}
