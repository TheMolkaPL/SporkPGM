package io.sporkpgm.region.regions;

import io.sporkpgm.region.Region;
import io.sporkpgm.util.RandomUtils;

import org.bukkit.util.Vector;

public class CuboidRegion implements Region {

	private Vector min;
	private Vector max;

	public CuboidRegion(Vector min, Vector max) {
		this.max = Vector.getMaximum(min, max);
		this.min = Vector.getMinimum(min, max);
	}

	@Override
	public boolean contains(Vector point) {
		return point.isInAABB(min, max);
	}

	public Vector max() {
		return max;
	}

	public Vector min() {
		return min;
	}

	public Vector getRandom() {
		int randomX = RandomUtils.getRandom(min.getBlockX(), max.getBlockX());
		int randomY = RandomUtils.getRandom(min.getBlockY(), max.getBlockY());
		int randomZ = RandomUtils.getRandom(min.getBlockZ(), max.getBlockZ());
		return new Vector(randomX, randomY, randomZ);
	}

	public String toString() {
		return "Region{type=CuboidRegion,min=" + min.toString() + ",max=" + max.toString() + '}';
	}

}
