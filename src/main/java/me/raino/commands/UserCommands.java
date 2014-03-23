package me.raino.commands;

import me.raino.Spork;
import me.raino.base.SporkPlayer;
import me.raino.map.SporkMap;
import me.raino.util.CommandUtils;
import me.raino.util.PaginatedResult;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import com.sk89q.minecraft.util.commands.Command;
import com.sk89q.minecraft.util.commands.CommandContext;
import com.sk89q.minecraft.util.commands.CommandException;

public class UserCommands {

	@Command(aliases = { "join" }, desc = "Lets you join a team, to play the match", min = 0, max = -1)
	public static void join(CommandContext args, CommandSender sender) throws CommandException {

	}

	@Command(aliases = { "g" }, desc = "Lets you talk globally to all teams", min = 1, max = -1)
	public static void global(CommandContext args, CommandSender sender) {
		String message = args.getJoinedStrings(0);
		
		SporkPlayer player = SporkPlayer.getPlayer(CommandUtils.getPlayer(sender));
		
		Bukkit.broadcastMessage("<" + player.getColoredName() + ChatColor.WHITE + ">: " + message);
	}

	@Command(aliases = { "listmaps", "maps" }, desc = "Lists all the loaded maps on the server", min = 0, max = 1)
	public static void maps(CommandContext args, CommandSender sender) throws CommandException {
		new PaginatedResult<SporkMap>("Loaded maps") {
			public String format(SporkMap map, int index) {
				return (index + 1) + ". " + ChatColor.GOLD + map.getName();
			}
		}.display(sender, Spork.get().getMapManager().getMaps(), args.getInteger(0, 1));
	}

	@Command(aliases = { "rotation", "rot" }, desc = "Lists all the maps in the rotation", min = 0, max = 1)
	public static void rotation(CommandContext args, CommandSender sender) throws CommandException {
		new PaginatedResult<SporkMap>("Rotation") {
			public String format(SporkMap map, int index) {
				return (index + 1) + ". " + ChatColor.GOLD + map.getName();
			}
		}.display(sender, Spork.get().getMatchManager().getRotation(), args.getInteger(0, 1));
	}

	@Command(aliases = { "nextmap", "next", "nm" }, desc = "Shows the next map played", min = 0, max = 0)
	public static void next(CommandContext args, CommandSender sender) throws CommandException {
		sender.sendMessage(ChatColor.DARK_PURPLE + "Next map: " + ChatColor.GOLD + Spork.get().getMatchManager().getNextMap().getName());
	}

}
