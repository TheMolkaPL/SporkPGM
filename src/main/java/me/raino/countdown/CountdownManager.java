package me.raino.countdown;

import java.util.List;

import me.raino.Spork;
import me.raino.map.SporkMap;
import me.raino.match.Match;
import me.raino.match.MatchManager;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import com.google.common.collect.Lists;

public class CountdownManager {

	private static List<BukkitTask> tasks = Lists.newArrayList();

	public static void cycle(final int seconds) {
		cancelAll();
		tasks.add(new BukkitRunnable() {
			MatchManager matchManager = Spork.get().getMatchManager();
			SporkMap next = matchManager.getNextMap();
			int sec = seconds;

			public void run() {
				if (sec == 0) {
					Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + "Now playing " + next.getDescription());
					matchManager.cycle();
					cancel();
				} else {
					if (sec % 5 == 0 || sec < 5)
						Bukkit.broadcastMessage(ChatColor.DARK_AQUA + "Cycling to " + ChatColor.AQUA + next.getName() + ChatColor.DARK_AQUA + " in " + ChatColor.DARK_RED + sec + ChatColor.DARK_AQUA + " second" + (sec == 1 ? "!" : "s!"));
				}
				sec--;
			}
		}.runTaskTimer(Spork.get(), 0L, 20L));
	}
	
	public static void start(final Match match, final int seconds) {
		cancelAll();
		tasks.add(new BukkitRunnable() {
			int sec = seconds;

			public void run() {
				if (sec == 0) {
					match.start();
					cancel();
				} else {
					if (sec % 5 == 0 || sec < 5)
						Bukkit.broadcastMessage(ChatColor.GREEN + "Match starting in " + ChatColor.DARK_RED + sec + ChatColor.GREEN + " second" + (sec == 1 ? "!" : "s!"));
				}
				sec--;
			}
		}.runTaskTimer(Spork.get(), 0L, 20L));
	}

	public static void cancelAll() {
		for (BukkitTask bt : tasks)
			bt.cancel();
		tasks.clear();
	}

}
