package me.raino.match;

import java.util.List;

import me.raino.base.SporkPlayer;
import me.raino.map.SporkMap;
import me.raino.module.ModuleContainer;
import me.raino.module.modules.TeamModule;
import me.raino.team.SporkTeam;
import me.raino.util.Config;

import org.bukkit.Bukkit;
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
	private SporkTeam defaultTeam;

	public Match(SporkMap map, World world) {
		this.map = map;
		this.world = world;

		this.moduleContainer = getMap().getModuleContainer();

		this.teams = ((TeamModule) getMap().getModule(TeamModule.class)).getAllTeams();
		this.defaultTeam = getTeam(Config.Team.DEFAULT_NAME);
	}

	public boolean setState(MatchState state) {
		if (this.state == null || this.state.next() == state) {
			this.state = state;
			if (state == MatchState.RUNNING)
				this.started = Instant.now();
			else if (state == MatchState.ENDED)
				this.ended = Instant.now();
			return true;
		}
		return false;
	}

	public void start() {
		if (!setState(MatchState.RUNNING))
			return;
		moduleContainer.enableModules();

		Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + "# # # # # # # # # # # # # # # # ");
		Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + "# # " + ChatColor.GOLD + "The match has started!" + ChatColor.DARK_PURPLE + " # #");
		Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + "# # # # # # # # # # # # # # # #");
	}

	public void end(SporkTeam winner) {
		if (!setState(MatchState.ENDED))
			return;
		Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + "# # # # # # # # # # # #");
		Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + "# # " + ChatColor.GOLD + "   Game Over!   " + ChatColor.DARK_PURPLE + " # #");
		if (winner != null)
			Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + "# # " + winner.getColoredName() + " wins!" + ChatColor.DARK_PURPLE + " # #");
		Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + "# # # # # # # # # # # #");
	}

	public Duration getLenght() {
		if (started == null)
			return null;
		Instant ended = this.ended;
		if (ended == null)
			ended = Instant.now();
		return new Duration(started, ended);
	}

	public boolean isRunning() {
		return state == MatchState.RUNNING;
	}

	public boolean isFinished() {
		return state == MatchState.ENDED || state == MatchState.CYCLING;
	}

	public void join(SporkPlayer player) {
		player.setTeam(getDefaultTeam());
		player.reset();
	}

	public void leave(SporkPlayer player) {

	}

	public SporkTeam getTeam(String search) {
		for (SporkTeam st : getTeams())
			if (st.getName().equalsIgnoreCase(search))
				return st;
		return null;
	}

	public SporkTeam getDefaultTeam() {
		return defaultTeam;
	}

	public List<SporkTeam> getTeams() {
		return teams;
	}

	public List<SporkPlayer> getPlayers() {
		List<SporkPlayer> players = Lists.newArrayList();
		for (SporkPlayer sp : SporkPlayer.getPlayers())
			if (sp.getMatch() == this)
				players.add(sp);
		return players;
	}

	public SporkMap getMap() {
		return map;
	}

	public World getWorld() {
		return world;
	}

}
