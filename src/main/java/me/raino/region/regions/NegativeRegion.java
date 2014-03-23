package me.raino.region.regions;

import me.raino.region.Region;

import org.bukkit.util.Vector;

public class NegativeRegion implements Region {

	private Region invertedRegion;

	public NegativeRegion(Region r) {
		this.invertedRegion = r;
	}

	@Override
	public boolean contains(Vector point) {
		return !this.invertedRegion.contains(point);
	}

	public String toString() {
		return "NegativeRegion{invertedRegion=" + invertedRegion + '}';
	}

}
