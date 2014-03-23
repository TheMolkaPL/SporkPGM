package me.raino.event;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

public class BlockTransformEvent extends SporkEvent {

	private BlockState oldState;
	private BlockState newState;

	public BlockTransformEvent(Event parent, BlockState oldState, BlockState newState, Player player) {
		super(parent, player);
	}

	public BlockTransformEvent(Event parent, BlockState oldState, BlockState newState) {
		this(parent, oldState, newState, null);
	}

	public BlockState getOldState() {
		return oldState;
	}

	public BlockState getNewState() {
		return newState;
	}

	public boolean isBreak() {
		return getOldState().getType() != Material.AIR && getNewState().getType() == Material.AIR;
	}

	public boolean isPlace() {
		return getOldState().getType() == Material.AIR && getNewState().getType() != Material.AIR;
	}

	public World getWorld() {
		return getOldState().getWorld();
	}

}
