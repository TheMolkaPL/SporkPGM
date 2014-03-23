package me.raino.util;

import org.bukkit.Material;
import org.bukkit.block.BlockState;

public class BlockUtils {

	// Taken from Overcast Networks Commons plugin
	public static BlockState air(BlockState state) {
		BlockState newState = state.getBlock().getState(); // this creates a new copy of the state
		newState.setType(Material.AIR);
		newState.setRawData((byte) 0);
		return newState;
	}

}
