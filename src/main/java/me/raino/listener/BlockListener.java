package me.raino.listener;

import me.raino.event.BlockTransformEvent;
import me.raino.util.BlockUtils;
import me.raino.util.EventUtils;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockFadeEvent;
import org.bukkit.event.block.BlockFormEvent;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.block.BlockPistonRetractEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.BlockSpreadEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;

public class BlockListener implements Listener {

	@EventHandler
	public void event(BlockPlaceEvent event) {
		BlockTransformEvent bte = new BlockTransformEvent(event, event.getBlockReplacedState(), event.getBlock().getState(), event.getPlayer());
		EventUtils.callEvent(bte);
	}

	@EventHandler
	public void event(BlockBreakEvent event) {
		BlockTransformEvent bte = new BlockTransformEvent(event, event.getBlock().getState(), BlockUtils.air(event.getBlock().getState()), event.getPlayer());
		EventUtils.callEvent(bte);
	}

	@EventHandler
	public void event(BlockFadeEvent event) {
		BlockTransformEvent bte = new BlockTransformEvent(event, event.getBlock().getState(), BlockUtils.air(event.getBlock().getState()));
		EventUtils.callEvent(bte);
	}

	@EventHandler
	public void event(BlockFormEvent event) {
		BlockTransformEvent bte = new BlockTransformEvent(event, BlockUtils.air(event.getBlock().getState()), event.getBlock().getState());
		EventUtils.callEvent(bte);
	}

	@EventHandler
	public void event(BlockFromToEvent event) {
		BlockTransformEvent bte = new BlockTransformEvent(event, event.getBlock().getState(), event.getToBlock().getState());
		EventUtils.callEvent(bte);
	}

	@EventHandler
	public void event(BlockSpreadEvent event) {
		BlockTransformEvent bte = new BlockTransformEvent(event, event.getSource().getState(), event.getBlock().getState());
		EventUtils.callEvent(bte);
	}

	@EventHandler
	public void event(BlockBurnEvent event) {
		BlockTransformEvent bte = new BlockTransformEvent(event, event.getBlock().getState(), BlockUtils.air(event.getBlock().getState()));
		EventUtils.callEvent(bte);
	}

	@EventHandler
	public void event(BlockPistonExtendEvent event) {
		for (Block b : event.getBlocks()) {
			BlockTransformEvent bte = new BlockTransformEvent(event, b.getState(), b.getRelative(event.getDirection()).getState());
			EventUtils.callEvent(bte);
		}
	}

	@EventHandler
	public void event(BlockPistonRetractEvent event) {
		if (!event.isSticky())
			return;
		BlockState state = event.getBlock().getWorld().getBlockAt(event.getRetractLocation()).getState();
		BlockTransformEvent bte = new BlockTransformEvent(event, state, BlockUtils.air(state));
		EventUtils.callEvent(bte);
	}

	@EventHandler
	public void event(EntityExplodeEvent event) {
		Player player = null;
		if (event.getEntity() instanceof TNTPrimed) {
			TNTPrimed ent = (TNTPrimed) event.getEntity();
			if (ent.getSource() instanceof Player)
				player = (Player) ent.getSource();
		}

		for (Block b : event.blockList()) {
			BlockTransformEvent bte = new BlockTransformEvent(event, b.getState(), BlockUtils.air(b.getState()), player);
			EventUtils.callEvent(bte);
		}
	}

	@EventHandler
	public void event(PlayerBucketEmptyEvent event) {
		Block block = event.getBlockClicked();

		BlockState newState = block.getState();
		newState.setType(event.getBucket() == Material.WATER_BUCKET ? Material.WATER : Material.LAVA);

		BlockTransformEvent bte = new BlockTransformEvent(event, block.getState(), newState, event.getPlayer());
		EventUtils.callEvent(bte);
	}

}
