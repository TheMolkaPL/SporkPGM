package me.raino.rotation;

import java.io.File;
import java.util.List;

import me.raino.Spork;
import me.raino.map.MapManager;
import me.raino.map.SporkMap;
import me.raino.util.Config;
import me.raino.util.Log;

import org.apache.commons.io.FileUtils;

import com.google.common.collect.Lists;

public class Rotation {

	private MapManager mapManager;

	private List<SporkMap> rotation = Lists.newArrayList();

	public Rotation(MapManager mapManager, File rotation) {
		this.mapManager = mapManager;

		this.rotation = loadRotation(rotation);
	}

	public List<SporkMap> getRotation() {
		return rotation;
	}

	private List<SporkMap> loadRotation(File rotation) {
		List<String> raw = Lists.newArrayList();

		try {
			raw = FileUtils.readLines(new File(Spork.get().getDataFolder(), Config.Rotation.ROTATION));
		} catch (Exception e) {
			Log.log(e);
		}

		List<SporkMap> maps = Lists.newArrayList();

		for (String s : raw) {
			SporkMap map = mapManager.getMap(s);
			if (map != null)
				maps.add(map);
		}

		return maps;
	}

}
