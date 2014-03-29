package me.raino.kit;

import java.util.List;
import java.util.Map;

import me.raino.module.exceptions.ModuleLoadException;
import me.raino.util.ItemUtils;
import me.raino.util.StringUtils;
import me.raino.util.XMLUtils;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.dom4j.Element;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class KitParser {

	private KitContainer container;

	public KitParser(KitContainer container) {
		this.container = container;
	}

	public static KitItem parseItem(Element ele) throws ModuleLoadException {
		String slot = ele.attributeValue("slot");

		ItemStack itemstack = parseItemStack(ele);

		return new KitItem(itemstack, XMLUtils.parseInteger(slot));
	}

	public static PotionEffect parsePotion(Element ele) throws ModuleLoadException {
		PotionEffectType type = PotionEffectType.getByName(ele.getText());
		if (type == null)
			throw new ModuleLoadException("Invalid potion type: " + ele.getText());
		String rawDuration = ele.attributeValue("duration");
		String rawAmplifier = ele.attributeValue("amplifier");
		if (rawDuration == null || rawAmplifier == null)
			throw new ModuleLoadException("Potion duration or amplifier cannot be blank!");
		int duration = Integer.parseInt(rawDuration);
		int amplifier = Integer.parseInt(rawAmplifier);
		boolean ambient = XMLUtils.parseBoolean(ele.attributeValue("ambient"), false);
		return new PotionEffect(type, duration, amplifier, ambient);
	}

	public static Map<Enchantment, Integer> parseEnchantments(String enchants) throws ModuleLoadException {
		Map<Enchantment, Integer> res = Maps.newHashMap();
		for (String s : enchants.split(";")) {
			if (s.split(":").length < 2)
				throw new ModuleLoadException("Cannot parse enchantment: " + s);
			Enchantment e = Enchantment.getByName(s.split(":")[0].toLowerCase().replace("_", " "));
			if (e == null)
				throw new ModuleLoadException("Invalid enchantment: " + s);
			res.put(e, XMLUtils.parseInteger(s.split(":")[2]));
		}
		return res;
	}

	public static ItemStack parseItemStack(Element ele) throws ModuleLoadException {
		String amount = ele.attributeValue("amount");
		String enchantList = ele.attributeValue("enchant");
		String rawLore = ele.attributeValue("lore");

		Material material = XMLUtils.getEnumFromString(Material.class, ele.getText());
		if (material == null)
			throw new ModuleLoadException("You must specify a valid material for the item!");

		short damage = XMLUtils.parseShort(ele.attributeValue("damage"));

		ItemStack itemstack = new ItemStack(material, XMLUtils.parseInteger(amount, 1), damage);

		String name = ele.attributeValue("name");

		if (name != null) {
			name = StringUtils.colorize(name);
			ItemUtils.setName(itemstack, name);
		}

		if (rawLore != null) {
			List<String> lore = Lists.newArrayList();;
			for (String s : rawLore.split("|"))
				lore.add(StringUtils.colorize(s));
			ItemUtils.setLore(itemstack, lore);
		}

		if (enchantList != null)
			itemstack.addUnsafeEnchantments(parseEnchantments(enchantList));

		return itemstack;
	}

}
