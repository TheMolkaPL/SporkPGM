package me.raino.listener;

import me.raino.Spork;
import me.raino.map.SporkMap;
import me.raino.match.Match;
import me.raino.util.Char;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

public class ServerListener implements Listener {

	@EventHandler
	public void event(ServerListPingEvent event) {
		Match match = Spork.get().getMatchManager().getCurrentMatch();
		SporkMap map = match.getMap();
		event.setMotd(ChatColor.AQUA + "Current map " + ChatColor.YELLOW + Char.RAQUO + " " + getStatusColor(match) + map.getName());
	}

	private ChatColor getStatusColor(Match match) {
		switch (match.getState()) {
		case RUNNING:
			return ChatColor.GOLD;
		case ENDED:
			return ChatColor.RED;
		default:
			return ChatColor.GRAY;
		}
	}
}
