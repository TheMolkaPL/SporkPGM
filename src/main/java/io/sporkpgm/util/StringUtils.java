package io.sporkpgm.util;

import java.util.Collection;

import org.bukkit.ChatColor;

import com.google.common.base.Strings;

public class StringUtils {

	// This method is from oc.tc's commons library
	public static String padMessage(String message, String c, ChatColor color) {
		message = new StringBuilder().append(" ").append(message).append(" ").toString();
		String dashes = Strings.repeat(c, (55 - ChatColor.stripColor(message).length() - 2) / 2);
		return new StringBuilder().append(color).append(dashes).append(message).append(color).append(dashes).toString();
	}

	public static <T> T fuzzySearch(Collection<T> search, String query) {
		return tc.oc.commons.core.util.StringUtils.bestFuzzyMatch(query, search, 0.9D);
	}

}
