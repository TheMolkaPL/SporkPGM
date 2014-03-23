package me.raino.spawn;

import me.raino.match.Match;
import me.raino.region.Region;
import me.raino.region.regions.BlockRegion;
import me.raino.region.regions.CuboidRegion;
import me.raino.region.regions.CylinderRegion;
import me.raino.team.SporkTeam;

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
