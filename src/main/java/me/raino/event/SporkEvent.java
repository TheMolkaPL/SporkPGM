package me.raino.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class SporkEvent extends Event implements Cancellable {

	private static HandlerList handlers = new HandlerList();

	private String cancelMessage;

	private Event parent;
	private Player player;

	public SporkEvent(Event parent, Player player) {
		this.parent = parent;
		this.player = player;
	}

	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}

	public boolean isCancelled() {
		if (parent instanceof Cancellable)
			return ((Cancellable) parent).isCancelled();
		return false;
	}

	public void setCancelled(boolean cancelled) {
		if (parent instanceof Cancellable)
			((Cancellable) parent).setCancelled(cancelled);
	}

	public void setCancelled(boolean cancelled, String message) {
		setCancelled(cancelled);
		this.cancelMessage = message;
	}

	public String getMessage() {
		return cancelMessage;
	}

	public boolean hasMessage() {
		return getMessage() != null;
	}

	public Event getParent() {
		return parent;
	}
	
	public boolean hasParent() {
		return getParent() != null;
	}

	public Player getPlayer() {
		return player;
	}

	public boolean hasPlayer() {
		return getPlayer() != null;
	}

}
