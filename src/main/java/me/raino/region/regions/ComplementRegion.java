package me.raino.region.regions;

import java.util.List;

import me.raino.region.Region;

import org.bukkit.util.Vector;

public class ComplementRegion implements Region {

	private Region base;
	private UnionRegion regions;

	public ComplementRegion(Region base, List<Region> regions) {
		this.base = base;
		this.regions = new UnionRegion(regions);
	}

	@Override
	public boolean contains(Vector point) {
		return base.contains(point) && !regions.contains(point);
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("ComplementRegion{base=[" + base.toString() + "],regions=[");
		sb.append(regions.toString());
		sb.append("]}");
		return sb.toString();
	}

}
