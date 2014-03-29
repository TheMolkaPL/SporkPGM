package me.raino.match;

import java.util.List;

import me.raino.base.SporkPlayer;
import me.raino.event.MatchEndEvent;
import me.raino.event.MatchStartEvent;
import me.raino.map.SporkMap;
import me.raino.module.ModuleContainer;
import me.raino.module.modules.TeamModule;
import me.raino.team.SporkTeam;
import me.raino.team.SporkTeam.TeamType;
import me.raino.util.Config;
import me.raino.util.EventUtils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.scoreboard.Scoreboard;
import org.joda.time.Duration;
import org.joda.time.Instant;

import com.google.common.collect.Lists;

public class Match {

	private SporkMap map;
	private World world;

	private Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();

	private ModuleContainer moduleContainer;

	private Instant started;
	private Instant ended;

	private MatchState state;

	private List<SporkTeam> teams = Lists.newArrayList();
	private SporkTeam defaultTeam;

	public Match(SporkMap map, World world) {
		this.map = map;
		this.world = world;

		setState(MatchState.WAITING);
		
		this.moduleContainer = getMap().getModuleContainer();

		for (SporkTeam sp : ((TeamModule) getMap().getModule(TeamModule.class)).getAllTeams())
			this.teams.add(sp.update(this));
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
		EventUtils.callEvent(new MatchStartEvent(this));

		moduleContainer.enableModules();

		broadcast(ChatColor.DARK_PURPLE + "# # # # # # # # # # # # # # # # ");
		broadcast(ChatColor.DARK_PURPLE + "# # " + ChatColor.GOLD + "The match has started!" + ChatColor.DARK_PURPLE + " # #");
		broadcast(ChatColor.DARK_PURPLE + "# # # # # # # # # # # # # # # #");
	}

	public void end(SporkTeam winner) {
		if (!setState(MatchState.ENDED))
			return;
		EventUtils.callEvent(new MatchEndEvent(this));

		moduleContainer.disableModules();

		broadcast(ChatColor.DARK_PURPLE + "# # # # # # # # # # # #");
		broadcast(ChatColor.DARK_PURPLE + "# # " + ChatColor.GOLD + "   Game Over!   " + ChatColor.DARK_PURPLE + " # #");
		if (winner != null)
			broadcast(ChatColor.DARK_PURPLE + "# # " + winner.getColoredName() + " wins!" + ChatColor.DARK_PURPLE + " # #");
		broadcast(ChatColor.DARK_PURPLE + "# # # # # # # # # # # #");
	}

	public Duration getLenght() {
		if (started == null)
			return null;
		Instant ended = this.ended;
		if (ended == null)
			ended = Instant.now();
		return new Duration(started, ended);
	}

	public MatchState getState() {
		return state;
	}

	public boolean isRunning() {
		return getState() == MatchState.RUNNING;
	}

	public boolean isFinished() {
		return getState() == MatchState.ENDED || getState() == MatchState.CYCLING;
	}

	public void join(SporkPlayer player) {
		player.setTeam(getDefaultTeam());
		player.reset();
	}

	public void leave(SporkPlayer player) {

	}

	public void broadcast(String message) {
		for (SporkPlayer sp : getPlayers())
			sp.sendMessage(message);
	}

	public Scoreboard getScoreboard() {
		return scoreboard;
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

	public List<SporkTeam> getParticipatingTeams() {
		List<SporkTeam> teams = Lists.newArrayList();
		for (SporkTeam st : getTeams())
			if (st.getType() == TeamType.PARTICIPATING)
				teams.add(st);
		return teams;
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
