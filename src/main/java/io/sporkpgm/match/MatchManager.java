package io.sporkpgm.match;

import io.sporkpgm.map.MapManager;
import io.sporkpgm.map.SporkMap;
import io.sporkpgm.rotation.Rotation;
import io.sporkpgm.util.Config;
import io.sporkpgm.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.List;

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
		return rotation.get(index);
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
	
	public Match cycle(Match old) {
		Match newMatch = createMatch(getNextAndIncrement(), Config.Match.PREFIX + ++matchId);
		
		if(old != null) {
			deleteMatch(old);
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
