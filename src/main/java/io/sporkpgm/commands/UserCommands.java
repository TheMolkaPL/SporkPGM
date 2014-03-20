package io.sporkpgm.commands;

import io.sporkpgm.Spork;
import io.sporkpgm.map.SporkMap;
import io.sporkpgm.util.PaginatedResult;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import com.sk89q.minecraft.util.commands.Command;
import com.sk89q.minecraft.util.commands.CommandContext;
import com.sk89q.minecraft.util.commands.CommandException;

public class UserCommands {

	@Command(aliases = { "maps" }, desc = "Lists all the loaded maps on the server", anyFlags = false, min = 0, max = 1)
	public static void maps(CommandContext args, CommandSender sender) throws CommandException {
		new PaginatedResult<SporkMap>("Loaded maps") {
			public String format(SporkMap map, int index) {
				return (index + 1) + ". " + ChatColor.GOLD + map.getName();
			}
		}.display(sender, Spork.get().getMapManager().getMaps(), args.getInteger(0, 1));;
	}

}
