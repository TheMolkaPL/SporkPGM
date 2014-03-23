package me.raino.listener;

import me.raino.Spork;
import me.raino.base.SporkPlayer;
import me.raino.match.Match;
import me.raino.module.modules.SpawnModule;
import me.raino.spawn.SporkSpawn;
import me.raino.util.Config;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.sk89q.minecraft.util.commands.ChatColor;

public class PlayerListener implements Listener {

	@EventHandler
	public void event(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		SporkPlayer spork = new SporkPlayer(player);
		Match match = Spork.get().getMatchManager().getCurrentMatch();

		match.join(spork);

		SporkSpawn spawn = ((SpawnModule) match.getMap().getModule(SpawnModule.class)).getDefaultSpawn();

		player.teleport(spawn.getSpawn(match));

		for (String s : Config.MOTD.MOTD)
			player.sendMessage(s);

		event.setJoinMessage(spork.getColoredName() + ChatColor.YELLOW + " joined the game.");
	}

	@EventHandler
	public void event(PlayerKickEvent event) {
		handleQuit(event.getPlayer());
	}

	@EventHandler
	public void event(PlayerQuitEvent event) {
		handleQuit(event.getPlayer());
	}

	private void handleQuit(Player player) {

	}

}
