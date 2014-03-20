package io.sporkpgm.map;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;

import tc.oc.commons.bukkit.util.NullChunkGenerator;
import tc.oc.commons.core.util.ContextStore;

public class MapManager extends ContextStore<SporkMap> {

	public MapManager(Collection<SporkMap> maps) {
		for (SporkMap map : maps)
			add(map.getName(), map);
	}

	public Collection<SporkMap> getMaps() {
		return store.values();
	}

	public SporkMap getMap(String map) {
		return store.get(map);
	}
	
	public void copyMap(SporkMap map, File dest) throws IOException {
		File src = map.getFolder();
		File level = new File(src, "level.dat");
		File region = new File(src, "region");
		File data = new File(src, "data");
		dest.mkdir();
		FileUtils.copyFile(level, new File(dest, "level.dat"));
		FileUtils.copyDirectory(region, new File(dest, "region"));
		if (data.exists()) FileUtils.copyDirectory(data, new File(dest, "data"));
	}
	
	public World createMap(String name) {
		World world = Bukkit.createWorld(new WorldCreator(name).generator(new NullChunkGenerator()));
		world.setAutoSave(false);
		world.setSpawnFlags(false, false);
		return world;
	}

}
