package me.raino.base;

import me.raino.match.Match;
import me.raino.team.SporkTeam;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

public class SporkPlayer {

	private Match match;
	private SporkTeam team;

	private Player player;

	public SporkPlayer(Player player) {
		this.player = player;
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

	public void sendMessage(String message) {
		getPlayer().sendMessage(message);
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

}
