package me.raino.listener;

import me.raino.Spork;
import me.raino.match.Match;
import me.raino.module.modules.SpawnModule;
import me.raino.spawn.SporkSpawn;
import me.raino.util.Config;

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
		
		for(String s : Config.MOTD.MOTD) player.sendMessage(s);
	}

}
