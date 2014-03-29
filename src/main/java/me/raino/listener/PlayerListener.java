package me.raino.listener;

import me.raino.Spork;
import me.raino.base.SporkPlayer;
import me.raino.event.PlayerDealPlayerDamageEvent;
import me.raino.match.Match;
import me.raino.module.modules.SpawnModule;
import me.raino.spawn.SporkSpawn;
import me.raino.util.Config;
import me.raino.util.EventUtils;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import com.sk89q.minecraft.util.commands.ChatColor;

public class PlayerListener implements Listener {

	@EventHandler
	public void event(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		SporkPlayer spork = new SporkPlayer(player);
		Match match = Spork.get().getMatchManager().getCurrentMatch();

		match.join(spork);
		spork.update();

		SporkSpawn spawn = ((SpawnModule) match.getMap().getModule(SpawnModule.class)).getDefaultSpawn();

		player.teleport(spawn.getSpawn(match));

		for (String s : Config.MOTD.MOTD)
			player.sendMessage(s);

		event.setJoinMessage(null);
		match.broadcast(spork.getColoredName() + ChatColor.YELLOW + " joined the game.");
	}

	@EventHandler
	public void event(PlayerKickEvent event) {
		handleQuit(event.getPlayer());
		event.setLeaveMessage(null);
	}

	@EventHandler
	public void event(PlayerQuitEvent event) {
		handleQuit(event.getPlayer());
		event.setQuitMessage(null);
	}

	@EventHandler
	public void event(AsyncPlayerChatEvent event) {
		SporkPlayer player = SporkPlayer.getPlayer(event.getPlayer());
		player.getTeam().broadcast(player.getTeam().getColor() + "[Team] " + player.getName() + ChatColor.WHITE + ": " + event.getMessage());
		event.setCancelled(true);
	}

	@EventHandler
	public void event(PlayerMoveEvent event) {
		if (!(event.getTo().getBlockY() < -30))
			return;
		Player player = event.getPlayer();
		SporkPlayer spork = SporkPlayer.getPlayer(player);
		if (spork.canInteract())
			return;

		Match match = Spork.get().getMatchManager().getCurrentMatch();
		SporkSpawn spawn = ((SpawnModule) match.getMap().getModule(SpawnModule.class)).getDefaultSpawn();

		player.teleport(spawn.getSpawn(match));
	}

	@EventHandler
	public void event(PlayerRespawnEvent event) {
		event.getPlayer().setArrowsStuck(0);
	}

	@EventHandler
	public void event(EntityDamageByEntityEvent event) {
		Player player = getPlayer(event.getDamager());
		if (player != null) {
			SporkPlayer spork = SporkPlayer.getPlayer(player);
			if (spork.canInteract()) {
				if (event.getEntity() instanceof Player) {
					PlayerDealPlayerDamageEvent pdpde = new PlayerDealPlayerDamageEvent(event, player, (Player) event.getEntity());
					EventUtils.callEvent(pdpde);
				}
			} else {
				event.setCancelled(true);
			}
		}
	}

	private Player getPlayer(Entity ent) {
		if (ent instanceof Player)
			return (Player) ent;
		else if (ent instanceof Projectile && ((Projectile) ent).getShooter() instanceof Player)
			return (Player) ((Projectile) ent).getShooter();
		return null;
	}

	private void handleQuit(Player player) {
		SporkPlayer spork = SporkPlayer.getPlayer(player);
		spork.getMatch().broadcast(spork.getColoredName() + ChatColor.YELLOW + " left the game.");
		spork.remove();
	}

}
