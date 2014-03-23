package me.raino.listener;

import me.raino.event.BlockTransformEvent;
import me.raino.util.BlockUtils;
import me.raino.util.EventUtils;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.event.block.BlockFadeEvent;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.w3c.dom.events.EventTarget;

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
	public void event(BlockFromToEvent event) {
		BlockTransformEvent bte = new BlockTransformEvent(event, event.getBlock().getState(), event.getToBlock().getState());
		EventUtils.callEvent(bte);
	}

}
