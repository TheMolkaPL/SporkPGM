package me.raino.match;

import java.io.File;
import java.io.IOException;
import java.util.List;

import me.raino.event.MatchCycleEvent;
import me.raino.map.MapManager;
import me.raino.map.SporkMap;
import me.raino.rotation.Rotation;
import me.raino.util.Config;
import me.raino.util.EventUtils;
import me.raino.util.Log;

import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.World;

import com.google.common.collect.Lists;

public class MatchManager {

	private MapManager mapManager;
	private List<SporkMap> rotation;

	private int matchId = 0;

	private SporkMap next;

	private List<Match> matches = Lists.newArrayList();

	public MatchManager(MapManager mapManager, Rotation rotation) {
		this.mapManager = mapManager;
		this.rotation = rotation.getRotation();
	}

	private int index = -1;

	public List<SporkMap> getRotation() {
		return rotation;
	}

	public SporkMap getCurrentMap() {
		return getCurrentMatch().getMap();
	}

	public SporkMap getNextMap() {
		if (next != null)
			return next;
		int temp = index + 1;
		if (temp >= rotation.size())
			temp = 0;
		return rotation.get(temp);
	}

	public void increment() {
		if (next != null) {
			next = null;
			return;
		}
		index++;
		if (index >= rotation.size())
			index = 0;
	}

	public SporkMap getNextAndIncrement() {
		SporkMap map = getNextMap();
		increment();
		return map;
	}

	public SporkMap getPrevious() {
		int temp = index - 1;
		if (temp < 0)
			temp = rotation.size() - 1;
		return rotation.get(temp);
	}

	public void setNext(SporkMap map) {
		this.next = map;
	}

	public Match getCurrentMatch() {
		return matches.iterator().next();
	}

	public World getCurrentWorld() {
		return getCurrentMatch().getWorld();
	}

	public Match cycle() {
		return cycle(getCurrentMatch());
	}
	
	public Match cycle(Match oldMatch) {
		Match newMatch = createMatch(getNextAndIncrement(), Config.Match.PREFIX + ++matchId);
		
		if(oldMatch != null) {
			MatchCycleEvent mce = new MatchCycleEvent(oldMatch, newMatch);
			EventUtils.callEvent(mce);
			deleteMatch(oldMatch);
		}
		
		return newMatch;
	}
	
	public Match createMatch(SporkMap map, String name) {
		File dest = new File(name);
		if (dest.exists()) {
			try {
				FileUtils.deleteDirectory(dest);
			} catch (IOException e) {}
		}
		try {
			this.mapManager.copyMap(map, dest);
		} catch (IOException e) {
			Log.log(e);
		}
		World world = this.mapManager.createMap(name);
		Match match = new Match(map, world);
		matches.add(match);
		Log.info("Loaded match '" + name + "' playing " + map.getName() + ".");
		return match;
	}
	
	public void deleteMatch(Match match) {
		File dir = match.getWorld().getWorldFolder();
		Bukkit.unloadWorld(match.getWorld(), true);
		try {
			FileUtils.forceDelete(dir);
		} catch (IOException e) {
			Log.log(e);
		}
		matches.remove(match);
		Log.info("Disabled match '" + match.getWorld().getName() + "'.");
	}

}
