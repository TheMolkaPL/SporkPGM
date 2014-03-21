package io.sporkpgm.region.regions;

import io.sporkpgm.region.Region;

import org.bukkit.util.Vector;

public class SphereRegion implements Region {

	private Vector center;
	private double radius;

	public SphereRegion(Vector center, double radius) {
		this.center = center;
		this.radius = radius;
	}

	@Override
	public boolean contains(Vector point) {
		return point.isInSphere(center, radius);
	}

	public String toString() {
		return "Region{type=SphereRegion,center=" + center + ",radius=" + radius + '}';
	}

}
