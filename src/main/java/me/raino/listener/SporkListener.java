package me.raino.listener;

import java.util.List;

import me.raino.Spork;
import me.raino.base.SporkPlayer;
import me.raino.event.BlockTransformEvent;
import me.raino.event.MatchCycleEvent;
import me.raino.event.MatchEndEvent;
import me.raino.event.MatchStartEvent;
import me.raino.event.PlayerDealPlayerDamageEvent;
import me.raino.match.Match;
import me.raino.module.modules.SpawnModule;
import me.raino.spawn.SporkSpawn;
import me.raino.team.SporkTeam;

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
	public void event(MatchStartEvent event) {
		Match match = event.getMatch();
		List<SporkTeam> teams = match.getParticipatingTeams();
		SpawnModule spawnModule = ((SpawnModule) match.getMap().getModule(SpawnModule.class));
		for(SporkTeam st : teams) {
			SporkSpawn spawn = spawnModule.getSpawn(st);
			for(SporkPlayer sp : st.getPlayers()) {
				sp.update();
				sp.getPlayer().teleport(spawn.getSpawn(match));
			}
		}
	}
	
	@EventHandler
	public void event(MatchEndEvent event) {
		Match match = event.getMatch();
		for (SporkPlayer sp : match.getPlayers()) sp.update();
	}

	@EventHandler
	public void event(BlockTransformEvent event) {
		if (!Spork.get().getMatchManager().getCurrentMatch().isRunning()) {
			event.setCancelled(true);
			return;
		} else if (event.hasPlayer()) {
			SporkPlayer player = SporkPlayer.getPlayer(event.getPlayer());
			event.setCancelled(!player.canInteract());
		}
	}

	@EventHandler
	public void event(PlayerDealPlayerDamageEvent event) {
		SporkTeam playerTeam = event.getSporkPlayer().getTeam();
		SporkTeam victimTeam = event.getSporkVictim().getTeam();
		if (playerTeam == victimTeam)
			event.setCancelled(true);
	}

}
