package me.raino.region.regions;

import me.raino.region.Region;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.util.Vector;

public class BlockRegion implements Region {
	
	private Vector block;

	public BlockRegion(Vector block) {
		this.block = block;
	}

	@Override
	public boolean contains(Vector point) {
		return point.equals(block);
	}

	public Location getLocation(World world) {
		return getVector().toLocation(world);
	}

	public Vector getVector() {
		return block;
	}

	public String toString() {
		return "BlockRegion{block=" + block + '}';
	}

}
