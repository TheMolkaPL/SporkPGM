package io.sporkpgm.map;

import io.sporkpgm.module.modules.TeamModule;
import io.sporkpgm.team.SporkTeam;
import io.sporkpgm.util.Config;
import io.sporkpgm.util.Log;

import java.io.File;
import java.util.Collection;

import com.google.common.collect.Lists;

public class MapLoader {

	public Collection<SporkMap> loadMaps(File dir) {
		Collection<SporkMap> maps = Lists.newArrayList();

		for (File f : dir.listFiles()) {
			if (!valid(f))
				continue;
			SporkMap map = new SporkMap(f);
			Log.debug("Loaded map " + map.getName() + " v" + map.getInfo().getVersion() + ".");
			maps.add(map);

			for (SporkTeam t : ((TeamModule) map.getModule(TeamModule.class)).getAllTeams()) {
				Log.debug(t.getColoredName());
			}
		}
		return maps;
	}

	private boolean valid(File file) {
		return new File(file, "level.dat").isFile() && new File(file, "region").isDirectory() && new File(file, Config.Map.CONFIG).isFile();
	}

}
