package me.raino.region.regions;

import java.util.Arrays;
import java.util.List;

import me.raino.region.Region;

import org.bukkit.util.Vector;

import com.google.common.collect.Lists;

public class UnionRegion implements Region {

	private List<Region> subRegions = Lists.newArrayList();

	public UnionRegion(List<Region> subRegions) {
		this.subRegions = subRegions;
	}

	@Override
	public boolean contains(Vector point) {
		for (Region r : this.subRegions)
			if (r.contains(point))
				return true;
		return false;
	}

	public List<Region> getSubRegions() {
		return subRegions;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("UnionRegion{regions=[");
		sb.append(Arrays.toString(subRegions.toArray()));
		sb.append("]}");
		return sb.toString();
	}

}
