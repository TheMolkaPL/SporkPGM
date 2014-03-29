package me.raino.kit;

import org.bukkit.inventory.ItemStack;

public class KitItem {

	private ItemStack item;
	private int slot;

	public KitItem(ItemStack itemStack, int slotNumber) {
		this.item = itemStack;
		this.slot = slotNumber;
	}

	public int getSlot() {
		return slot;
	}

	public ItemStack getItem() {
		return item;
	}
	
}
