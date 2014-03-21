package io.sporkpgm.module.modules;

import io.sporkpgm.module.Module;
import io.sporkpgm.module.ModuleContainer;
import io.sporkpgm.module.ModuleInfo;
import io.sporkpgm.module.exceptions.ModuleLoadException;
import io.sporkpgm.region.Region;
import io.sporkpgm.region.RegionParser;
import io.sporkpgm.spawn.SporkSpawn;
import io.sporkpgm.team.SporkTeam;
import io.sporkpgm.util.XMLUtils;

import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;

import com.google.common.collect.Lists;

@ModuleInfo(module = SpawnModule.class, name = "SpawnModule", requires = { TeamModule.class, RegionModule.class })
public class SpawnModule extends Module {

	private SporkSpawn defaultSpawn;
	private List<SporkSpawn> spawns;

	public SpawnModule(SporkSpawn defaultSpawn, List<SporkSpawn> spawns) {
		this.defaultSpawn = defaultSpawn;
		this.spawns = spawns;
	}

	public void enable() {}

	public void disable() {}

	public static Module parse(ModuleContainer moduleContainer, Document doc) throws Exception {
		Element root = doc.getRootElement();

		SporkSpawn defaultSpawn = null;
		List<SporkSpawn> spawns = Lists.newArrayList();

		RegionParser parser = moduleContainer.getRegionContainer().getParser();
		TeamModule teamModule = (TeamModule) moduleContainer.getModule(TeamModule.class);

		for (Element el : XMLUtils.getElements(root, "spawns")) {
			String type = el.getName();

			if (type.equalsIgnoreCase("spawn")) {
				SporkTeam team = teamModule.getTeam(el.attributeValue("team"));
				if (team == null)
					throw new ModuleLoadException("Invalid team found for spawnmodule");
				Region region = parser.parse((Element) el.elements().get(0));
				float yaw = (float) XMLUtils.parseDouble(el.attributeValue("yaw"));
				spawns.add(new SporkSpawn(team, region, yaw));
			} else if (type.equalsIgnoreCase("default")) {
				Region region = parser.parse((Element) el.elements().get(0));
				float yaw = (float) XMLUtils.parseDouble(el.attributeValue("yaw"));
				defaultSpawn = new SporkSpawn(teamModule.getDefaultTeam(), region, yaw);
			}
		}
		return new SpawnModule(defaultSpawn, spawns);
	}
	
	public SporkSpawn getDefaultSpawn() {
		return defaultSpawn;
	}

	public List<SporkSpawn> getSpawns() {
		return spawns;
	}

	public SporkSpawn getSpawn(SporkTeam team) {
		if (defaultSpawn.getTeam() == team)
			return defaultSpawn;
		for (SporkSpawn s : getSpawns())
			if (s.getTeam() == team)
				return s;
		return null;
	}

}
