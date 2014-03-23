package me.raino.base;

import java.util.List;

import me.raino.match.Match;
import me.raino.team.SporkTeam;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

import com.google.common.collect.Lists;

public class SporkPlayer {

	private static final List<SporkPlayer> players = Lists.newArrayList();

	private Match match;
	private SporkTeam team;

	private Player player;

	public SporkPlayer(Player player) {
		this.player = player;
		
		players.add(this);
	}

	public void reset() {
		Player player = getPlayer();
		player.setHealth(player.getMaxHealth());
		player.setFoodLevel(20);
		player.getInventory().clear();
		player.getInventory().setArmorContents(null);
		player.setSneaking(false);
		player.setSprinting(false);
		player.setExhaustion(0.0F);
		player.setFallDistance(0.0F);
		player.setFireTicks(0);
		player.setLevel(0);
		player.setExp(0.0F);
		player.setSaturation(5.0F);
		player.setBedSpawnLocation(null);
		player.closeInventory();
		for (PotionEffect effect : player.getActivePotionEffects())
			player.removePotionEffect(effect.getType());
	}
	
	public void update() {
		
	}
	
	public boolean canInteract() {
		return getTeam().isParticipating();
	}
	
	public void sendMessage(String message) {
		getPlayer().sendMessage(message);
	}

	public String getColoredName() {
		return getTeam().getColor() + getPlayer().getName();
	}

	public void setTeam(SporkTeam team) {
		this.team = team;
		this.match = team.getMatch();

		String name = getColoredName();
		if (name.length() > 16)
			name = name.substring(0, 15);
		getPlayer().setPlayerListName(name);
	}

	public Match getMatch() {
		return match;
	}

	public SporkTeam getTeam() {
		return team;
	}

	public Player getPlayer() {
		return player;
	}

	public String getName() {
		return getPlayer().getName();
	}

	public void remove() {
		players.remove(this);
	}
	
	public static List<SporkPlayer> getPlayers() {
		return players;
	}

	public static SporkPlayer getPlayer(String player) {
		for (SporkPlayer sp : getPlayers())
			if (sp.getName().equalsIgnoreCase(player))
				return sp;
		return null;
	}

	public static SporkPlayer getPlayer(Player player) {
		return getPlayer(player.getName());
	}

}
