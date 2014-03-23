package me.raino.region.regions;

import me.raino.region.Region;

import org.bukkit.util.Vector;

public class CircleRegion implements Region {
	
	private double baseX;
	private double baseZ;
	private double radius;

	public CircleRegion(double baseX, double baseZ, double radius) {
		this.baseX = baseX;
		this.baseZ = baseZ;
		this.radius = radius;
	}

	@Override
	public boolean contains(Vector point) {
		double diffx = point.getX() - this.baseX;
		double diffz = point.getZ() - this.baseZ;
		return (Math.pow(diffx, 2.0D) + Math.pow(diffz, 2.0D)) <= (this.radius * this.radius);
	}

	public String toString() {
		return "Region{type=CircleRegion,baseX=" + baseX + ",baseZ=" + baseZ + ",radius=" + radius + '}';
	}

}
