package io.sporkpgm.spawn;

import io.sporkpgm.match.Match;
import io.sporkpgm.region.Region;
import io.sporkpgm.region.regions.BlockRegion;
import io.sporkpgm.region.regions.CuboidRegion;
import io.sporkpgm.region.regions.CylinderRegion;
import io.sporkpgm.team.SporkTeam;

import org.bukkit.Location;
import org.bukkit.util.Vector;

public class SporkSpawn {

	private SporkTeam team;
	private Region region;
	private float yaw;

	public SporkSpawn(SporkTeam team, Region region, float yaw) {
		this.team = team;
		this.region = region;
		this.yaw = yaw;
	}

	public SporkTeam getTeam() {
		return team;
	}

	public Location getSpawn(Match match) {
		Vector loc = match.getWorld().getSpawnLocation().toVector();
		if (region instanceof CuboidRegion)
			loc = ((CuboidRegion) region).getRandom();
		else if (region instanceof CylinderRegion)
			loc = ((CylinderRegion) region).getRandom();
		else if(region instanceof BlockRegion)
			loc = ((BlockRegion) region).getVector();
		Location l = loc.toLocation(match.getWorld(), yaw, 0);
		return l;
	}

}
