package me.raino.util;

import java.util.List;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemUtils {

	public static void setName(ItemStack item, String name) {
		ItemMeta im = item.getItemMeta();
		im.setDisplayName(name);
		item.setItemMeta(im);
	}

	public static void setLore(ItemStack item, List<String> lore) {
		ItemMeta im = item.getItemMeta();
		im.setLore(lore);
		item.setItemMeta(im);
	}

}
