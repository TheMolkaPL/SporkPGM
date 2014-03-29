package me.raino.event;

import me.raino.base.SporkPlayer;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;

public class PlayerDealPlayerDamageEvent extends SporkEvent {

	private Player victim;
	
	public PlayerDealPlayerDamageEvent(Event parent, Player player, Player victim) {
		super(parent, player);
		this.victim = victim;
	}
	
	public Player getVictim() {
		return victim;
	}
	
	public SporkPlayer getSporkVictim() {
		return SporkPlayer.getPlayer(getVictim());
	}

}
