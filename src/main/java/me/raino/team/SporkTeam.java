package me.raino.team;

import java.util.List;

import me.raino.base.SporkPlayer;
import me.raino.match.Match;

import org.bukkit.ChatColor;

import com.google.common.collect.Lists;

public class SporkTeam {

	private Match match;

	private String name;
	private ChatColor color;
	private int max;

	private TeamType type;

	public SporkTeam(String name, ChatColor color, int max, TeamType type) {
		this.name = name;
		this.color = color;
		this.max = max;
		this.type = type;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public ChatColor getColor() {
		return color;
	}

	public int getMax() {
		return max;
	}

	public TeamType getType() {
		return type;
	}

	public String getColoredName() {
		return getColor() + getName();
	}

	public Match getMatch() {
		return match;
	}

	public boolean isParticipating() {
		return getType() == TeamType.PARTICIPATING && getMatch().isRunning();
	}

	public boolean isObserving() {
		return getType() == TeamType.OBSERVING || !getMatch().isRunning();
	}

	public SporkTeam update(Match match) {
		this.match = match;
		return this;
	}

	public void broadcast(String message) {
		for (SporkPlayer sp : getPlayers())
			sp.sendMessage(message);
	}

	public boolean isFull() {
		return getSize() >= getMax();
	}

	public int getSize() {
		return getPlayers().size();
	}

	public List<SporkPlayer> getPlayers() {
		List<SporkPlayer> players = Lists.newArrayList();
		for (SporkPlayer sp : SporkPlayer.getPlayers())
			if (sp.getTeam() == this)
				players.add(sp);
		return players;
	}

	public String toString() {
		return getName();
	}

	public enum TeamType {
		OBSERVING, PARTICIPATING;
	}

}