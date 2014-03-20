package io.sporkpgm.util;

import org.bukkit.ChatColor;

import com.google.common.base.Strings;

public class StringUtils {

	// This method is from oc.tc's commons library
	public static String padMessage(String message, String c, ChatColor color) {
		message = new StringBuilder().append(" ").append(message).append(" ").toString();
		String dashes = Strings.repeat(c, (55 - ChatColor.stripColor(message).length() - 2) / 2);
		return new StringBuilder().append(color).append(dashes).append(message).append(color).append(dashes).toString();
	}

}
