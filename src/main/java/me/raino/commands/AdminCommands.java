package me.raino.commands;

import me.raino.Spork;
import me.raino.countdown.CountdownManager;
import me.raino.map.SporkMap;
import me.raino.match.Match;
import me.raino.team.SporkTeam;
import me.raino.util.CommandUtils;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import com.sk89q.minecraft.util.commands.Command;
import com.sk89q.minecraft.util.commands.CommandContext;
import com.sk89q.minecraft.util.commands.CommandException;
import com.sk89q.minecraft.util.commands.CommandPermissions;

public class AdminCommands {

	@Command(aliases = { "setnext", "sn" }, desc = "Manually sets the next map played, does not affect rotation", min = 1, max = -1)
	@CommandPermissions("spork.setnext")
	public static void setnext(CommandContext args, CommandSender sender) throws CommandException {
		SporkMap map = CommandUtils.getMap(args.getJoinedStrings(0));
		Spork.get().getMatchManager().setNext(map);
		sender.sendMessage(ChatColor.DARK_PURPLE + "Next map set to " + ChatColor.GOLD + map.getName());
	}

	@Command(aliases = { "cycle" }, desc = "Manually cycles the server to the next map", min = 0, max = 1)
	public static void cycle(CommandContext args, CommandSender sender) throws CommandException {
		CountdownManager.cycle(args.getInteger(0, 10));
	}

	@Command(aliases = { "start" }, desc = "Manually starts the match countdown")
	public static void start(CommandContext args, CommandSender sender) throws CommandException {
		Match match = Spork.get().getMatchManager().getCurrentMatch();
		if(match.isRunning()) throw new CommandException("Match is already started.");
		if(match.isFinished()) throw new CommandException("Match is finished and may not be resumed.");
		CountdownManager.start(match, args.getInteger(0, 10));
	}

	@Command(aliases = { "cancel" }, desc = "Manually starts the match countdown")
	public static void cancel(CommandContext args, CommandSender sender) throws CommandException {
		CountdownManager.cancelAll();
		sender.sendMessage(ChatColor.GREEN + "Countdowns cancelled");
	}

	@Command(aliases = { "end" }, desc = "hi")
	public static void test2(CommandContext args, CommandSender sender) throws CommandException {
		SporkTeam team = null;
		if (args.argsLength() > 0)
			team = CommandUtils.getTeam(args.getJoinedStrings(0));
		Spork.get().getMatchManager().getCurrentMatch().end(team);
	}

}
