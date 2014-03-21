package io.sporkpgm.listener;

import io.sporkpgm.Spork;
import io.sporkpgm.match.Match;
import io.sporkpgm.module.modules.SpawnModule;
import io.sporkpgm.spawn.SporkSpawn;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerListener implements Listener {

	@EventHandler
	public void event(PlayerJoinEvent event) {
		Player player = event.getPlayer();

		Match match = Spork.get().getMatchManager().getCurrentMatch();
		SporkSpawn spawn = ((SpawnModule) match.getMap().getModule(SpawnModule.class)).getDefaultSpawn();

		player.teleport(spawn.getSpawn(match));
	}

}
