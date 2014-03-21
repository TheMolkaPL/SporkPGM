package io.sporkpgm.region.regions;

import io.sporkpgm.region.Region;

import java.util.Arrays;
import java.util.List;

import org.bukkit.util.Vector;

public class IntersectRegion implements Region {

	private List<Region> regions;

	public IntersectRegion(List<Region> regions) {
		this.regions = regions;
	}

	@Override
	public boolean contains(Vector point) {
		for (Region r : this.regions)
			if (!r.contains(point))
				return false;
		return true;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("IntersectRegion{regions=[");
		sb.append(Arrays.toString(this.regions.toArray()));
		sb.append("]}");
		return sb.toString();
	}

}
