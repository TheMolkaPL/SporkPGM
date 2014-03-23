package me.raino.region.regions;

import java.util.Random;

import me.raino.region.Region;

import org.bukkit.util.Vector;

public class CylinderRegion implements Region {
	
	public Vector base;
	public double radius;
	public double height;

	public CylinderRegion(Vector base, double radius, double height) {
		this.base = base;
		this.radius = radius;
		this.height = height;
	}

	@Override
	public boolean contains(Vector point) {
		if (point.getY() < this.base.getY() || point.getY() > (this.base.getY() + this.height))
			return false;
		double diffx = point.getX() - this.base.getX();
		double diffz = point.getZ() - this.base.getZ();
		return Math.pow(diffx, 2.0D) + Math.pow(diffz, 2.0D) <= (this.radius * this.radius);
	}

	public Vector getRandom() {
		Random random = new Random();
		double angle = random.nextDouble() * Math.PI * 2.0D;
		double hyp = random.nextDouble() * radius;
		double x = Math.cos(angle) * hyp + base.getX();
		double z = Math.sin(angle) * hyp + base.getZ();
		double y = height * random.nextDouble() + base.getY();
		return new Vector(x, y, z);
	}
	
	public String toString() {
		return "Region{type=CylinderRegion,base=" + base + ",radius=" + radius + ",height=" + height + '}';
	}

}
