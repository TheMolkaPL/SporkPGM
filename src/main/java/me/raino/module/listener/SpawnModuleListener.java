package me.raino.module.listener;

import me.raino.base.SporkPlayer;
import me.raino.module.modules.SpawnModule;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

public class SpawnModuleListener implements Listener {

	private SpawnModule module;

	public SpawnModuleListener(SpawnModule module) {
		this.module = module;
	}

	@EventHandler
	public void event(PlayerRespawnEvent event) {
		Player player = event.getPlayer();
		event.setRespawnLocation(handleSpawn(player));
	}

	private Location handleSpawn(Player player) {
		SporkPlayer spork = SporkPlayer.getPlayer(player);
		return module.getSpawn(spork.getTeam()).getSpawn(spork.getMatch());
	}

}
