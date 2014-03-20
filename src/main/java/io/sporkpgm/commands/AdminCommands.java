package io.sporkpgm.commands;

import io.sporkpgm.Spork;
import io.sporkpgm.map.SporkMap;
import io.sporkpgm.util.CommandUtils;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import com.sk89q.minecraft.util.commands.Command;
import com.sk89q.minecraft.util.commands.CommandContext;
import com.sk89q.minecraft.util.commands.CommandPermissions;

public class AdminCommands {

	@Command(aliases = { "setnext", "sn" }, desc = "Manually sets the next map played, does not affect rotation", min = 1, max = -1)
	@CommandPermissions("spork.setnext")
	public static void setnext(CommandContext args, CommandSender sender) {
		SporkMap map = CommandUtils.getMap(args.getJoinedStrings(0));
		Spork.get().getMatchManager().setNext(map);
		sender.sendMessage(ChatColor.DARK_PURPLE + "Next map set to " + ChatColor.GOLD + map.getName());
	}

}
