package me.raino.listener;

import me.raino.base.SporkPlayer;
import me.raino.event.BlockTransformEvent;
import me.raino.event.MatchCycleEvent;
import me.raino.match.Match;
import me.raino.module.modules.SpawnModule;
import me.raino.spawn.SporkSpawn;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class SporkListener implements Listener {

	@EventHandler
	public void event(MatchCycleEvent event) {
		Match match = event.getNewMatch();
		SporkSpawn spawn = ((SpawnModule) match.getMap().getModule(SpawnModule.class)).getDefaultSpawn();
		for (SporkPlayer p : event.getOldMatch().getPlayers()) {
			match.join(p);
			p.getPlayer().teleport(spawn.getSpawn(match));
		}
	}
	
	@EventHandler
	public void event(BlockTransformEvent event) {
		if(!event.hasPlayer()) return;
		SporkPlayer player = SporkPlayer.getPlayer(event.getPlayer());
		event.setCancelled(!player.canInteract());
	}
	
}
