package me.raino.util;

import me.raino.Spork;
import me.raino.map.SporkMap;
import me.raino.team.SporkTeam;

import org.bukkit.command.CommandException;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandUtils {

	public static Player getPlayer(CommandSender sender) {
		if (!(sender instanceof Player))
			throw new CommandException("You must be a player to execute this command!");
		return (Player) sender;
	}

	public static SporkMap getMap(String query) {
		SporkMap map = StringUtils.fuzzySearch(Spork.get().getMapManager().getMaps(), query);
		if (map == null)
			throw new CommandException("No maps matched query!");
		return map;
	}

	public static SporkTeam getTeam(String query) {
		SporkTeam team = StringUtils.fuzzySearch(Spork.get().getMatchManager().getCurrentMatch().getTeams(), query);
		if (team == null)
			throw new CommandException("No teams matched query!");
		return team;
	}

}
