package me.raino.util;

import java.util.List;
import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.material.MaterialData;
import org.bukkit.util.Vector;
import org.dom4j.Element;

import com.google.common.base.Optional;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

public class XMLUtils {

	@SuppressWarnings("unchecked")
	public static List<Element> getElements(Element root, String parent) {
		return root.element(parent) != null ? root.element(parent).elements() : Lists.newArrayList();
	}

	public static List<Element> getElements(Element root, String parent, String child) {
		List<Element> ret = Lists.newArrayList();
		for (Element e : getElements(root, parent)) {
			if (e.getName().equalsIgnoreCase(child))
				ret.add(e);
		}
		return ret;
	}

	public static List<Element> getElements(Element root) {
		List<Element> elements = Lists.newArrayList();
		if (root == null)
			return elements;
		for (Object object : root.elements()) {
			if (object instanceof Element) {
				elements.add((Element) object);
			}
		}

		return elements;
	}

	public static boolean parseBoolean(String text, boolean def) {
		if (text == null)
			return def;

		if (def) { return (!text.equalsIgnoreCase("no")) && (!text.equalsIgnoreCase("off")) && (!text.equalsIgnoreCase("false")); }
		return (text.equalsIgnoreCase("yes")) || (text.equalsIgnoreCase("on")) || (text.equalsIgnoreCase("true"));
	}

	public static List<Double> parseDoubleList(String text) {
		String[] pieces = text.split("[^o0-9\\.-]");
		List<Double> numbers = Lists.newLinkedList();
		for (String piece : pieces)
			try {
				double num = parseDouble(piece);
				numbers.add(Double.valueOf(num));
			} catch (NumberFormatException e) {}
		return numbers;
	}

	public static Vector parseVector(String text) {
		List<Double> numbers = parseDoubleList(text);
		if (numbers.size() >= 3)
			return new Vector(((Double) numbers.get(0)).doubleValue(), ((Double) numbers.get(1)).doubleValue(), ((Double) numbers.get(2)).doubleValue());
		return null;
	}

	public static double parseDouble(String s) throws NumberFormatException {
		if (s.equals("oo"))
			return (1.0D / 0.0D);
		if (s.equals("-oo"))
			return (-1.0D / 0.0D);
		return Double.parseDouble(s);
	}

	public static ChatColor parseChatColor(String text) {
		return parseChatColor(text, null);
	}

	public static ChatColor parseChatColor(String text, ChatColor defaultColor) {
		if (text == null) { return defaultColor; }
		for (ChatColor color : ChatColor.values()) {
			if (text.equalsIgnoreCase(color.name().replace("_", " "))) { return color; }
		}
		return defaultColor;
	}

	@SuppressWarnings("deprecation")
	public static MaterialData parseMaterialData(String text) {
		String[] pieces = text.split(":");
		Material material = Material.matchMaterial(pieces[0]);
		if (material == null) { throw new IllegalArgumentException("Could not find material '" + pieces[0] + "'."); }
		byte data = 0;
		if (pieces.length > 1) {
			try {
				data = Byte.parseByte(pieces[1]);
			} catch (NumberFormatException e) {
				throw new IllegalArgumentException("Invalid damage value: " + pieces[1], e);
			}
		}
		return material.getNewData(data);
	}

	public static Set<MaterialData> parseMaterialDataList(String text) {
		Set<MaterialData> materials = Sets.newHashSet();
		for (String rawMat : Splitter.on(";").split(text)) {
			materials.add(parseMaterialData(rawMat.trim()));
		}
		return materials;
	}

	public static MaterialData parseBlockMaterial(String text) {
		MaterialData data = parseMaterialData(text);
		if (data.getItemType().isBlock())
			return data;
		throw new IllegalArgumentException("Material must be a block.");
	}

	public static DyeColor parseDyeColor(String text) {
		for (DyeColor color : DyeColor.values()) {
			if (color.toString().replace("_", " ").equalsIgnoreCase(text)) { return color; }
		}
		return null;
	}

	public static Optional<Enchantment> parseEnchantment(String text) {
		return Optional.fromNullable(Enchantment.getByName(text.toUpperCase().replace(" ", "_")));
	}

	public static <T extends Enum<T>> T getEnumFromString(Class<T> c, String string) {
		return getEnumFromString(c, string, null);
	}

	public static <T extends Enum<T>> T getEnumFromString(Class<T> c, String string, T defaultValue) {
		if ((c != null) && (string != null)) {
			try {
				return Enum.valueOf(c, string.trim().toUpperCase().replace(' ', '_'));
			} catch (IllegalArgumentException ex) {}
		}
		return defaultValue;
	}

	public static List<Double> parse2DVector(String text) {
		if (text == null) { return null; }
		List<Double> nums = XMLUtils.parseDoubleList(text);
		if (nums.size() == 2)
			return nums;
		return null;
	}

	public static Integer parseInteger(String text) {
		return parseInteger(text, 0);
	}

	public static Integer parseInteger(String text, int i) {
		try {
			return Integer.parseInt(text);
		} catch (Exception e) {
			return i;
		}
	}

	public static Short parseShort(String text) {
		return parseShort(text, (short) 0);
	}

	public static Short parseShort(String text, short i) {
		try {
			return Short.parseShort(text);
		} catch (Exception e) {
			return i;
		}
	}

}
