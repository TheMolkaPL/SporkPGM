package io.sporkpgm.match;

import io.sporkpgm.base.User;
import io.sporkpgm.map.SporkMap;
import io.sporkpgm.module.ModuleContainer;
import io.sporkpgm.team.SporkTeam;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.World;
import org.joda.time.Duration;
import org.joda.time.Instant;

import com.google.common.collect.Lists;

public class Match {

	private SporkMap map;
	private World world;

	private ModuleContainer moduleContainer;

	private Instant started;
	private Instant ended;

	private MatchState state;

	private List<SporkTeam> teams = Lists.newArrayList();

	private List<User> players = Lists.newArrayList();

	public Match(SporkMap map, World world) {
		this.map = map;
		this.world = world;

		this.moduleContainer = getMap().getModuleContainer();
	}

	public boolean setState(MatchState state) {
		if (this.state == null || this.state.next() == state) {
			this.state = state;
			if(state == MatchState.RUNNING) this.started = Instant.now();
			else if(state == MatchState.ENDED) this.ended = Instant.now();
			return true;
		}
		return false;
	}

	public void start() {
		if (!setState(MatchState.RUNNING))
			return;
		moduleContainer.enableModules();

		broadcast(ChatColor.DARK_PURPLE + "# # # # # # # # # # # # # # # # ");
		broadcast(ChatColor.DARK_PURPLE + "# # " + ChatColor.GOLD + "The match has started!" + ChatColor.DARK_PURPLE + " # #");
		broadcast(ChatColor.DARK_PURPLE + "# # # # # # # # # # # # # # # #");
	}

	public void end(SporkTeam winner) {
		if (!setState(MatchState.ENDED))
			return;
		broadcast(ChatColor.DARK_PURPLE + "# # # # # # # # # # # #");
		broadcast(ChatColor.DARK_PURPLE + "# # " + ChatColor.GOLD + "   Game Over!   " + ChatColor.DARK_PURPLE + " # #");
		if (winner != null)
			broadcast(ChatColor.DARK_PURPLE + "# # " + winner.getColoredName() + " wins!" + ChatColor.DARK_PURPLE + " # #");
		broadcast(ChatColor.DARK_PURPLE + "# # # # # # # # # # # #");
	}
	
	public Duration getLenght() {
		if(started == null) return null;
		Instant ended = this.ended;
		if(ended == null) ended = Instant.now();
		return new Duration(started, ended);
	}

	public void broadcast(String message) {
		for (User u : getPlayers())
			u.sendMessage(message);
	}

	public List<User> getPlayers() {
		return players;
	}

	public SporkMap getMap() {
		return map;
	}

	public World getWorld() {
		return world;
	}

}
