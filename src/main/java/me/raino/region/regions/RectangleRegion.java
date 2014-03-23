package me.raino.region.regions;

import me.raino.region.Region;

import org.bukkit.util.Vector;

public class RectangleRegion implements Region {
	
	private double minX;
	private double maxX;
	private double minZ;
	private double maxZ;

	public RectangleRegion(double minX, double maxX, double minZ, double maxZ) {
		this.minX = minX;
		this.maxX = maxX;
		this.minZ = minZ;
		this.maxZ = maxZ;
	}

	@Override
	public boolean contains(Vector point) {
		return (this.minX <= point.getX()) && (this.maxX >= point.getX()) && (this.minZ <= point.getZ()) && (this.maxZ >= point.getZ());
	}

	public String toString() {
		return "Region{type=RectangleRegion,minX=" + minX + ",maxX=" + maxX + ",minZ=" + minZ + ",maxZ=" + maxZ + '}';
	}

}
