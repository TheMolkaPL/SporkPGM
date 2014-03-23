package me.raino.listener;

import me.raino.base.SporkPlayer;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;

public class ObserverListener implements Listener {

	@EventHandler
	public void event(PlayerDropItemEvent event) {
		SporkPlayer player = SporkPlayer.getPlayer(event.getPlayer());
		if(!player.canInteract()) event.getItemDrop().remove();
	}
	
	@EventHandler
	public void event(PlayerPickupItemEvent event) {
		SporkPlayer player = SporkPlayer.getPlayer(event.getPlayer());
		event.setCancelled(!player.canInteract());
	}
	
	@EventHandler
	public void event(PlayerInteractEvent event) {
		SporkPlayer player = SporkPlayer.getPlayer(event.getPlayer());
		event.setCancelled(!player.canInteract());
	}
	
	@EventHandler
	public void event(PlayerInteractEntityEvent event) {
		SporkPlayer player = SporkPlayer.getPlayer(event.getPlayer());
		event.setCancelled(!player.canInteract());
	}
	
	@EventHandler
	public void event(PlayerItemConsumeEvent event) {
		SporkPlayer player = SporkPlayer.getPlayer(event.getPlayer());
		event.setCancelled(!player.canInteract());
	}
	
}
