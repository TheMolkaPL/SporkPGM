package me.raino.util;

import java.util.Collection;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import com.google.common.collect.Lists;
import com.sk89q.minecraft.util.commands.CommandException;

public abstract class PaginatedResult<T> {

	private final int resultsPerPage;
	private String header;

	public PaginatedResult(String header) {
		this(header, 8);
	}

	public PaginatedResult(String header, int resultsPerPage) {
		assert (resultsPerPage > 0);
		this.header = header;
		this.resultsPerPage = resultsPerPage;
	}

	public void display(CommandSender sender, Collection<? extends T> results, int page) throws CommandException {
		display(sender, Lists.newArrayList(results), page);
	}

	public void display(CommandSender sender, List<? extends T> results, int page) throws CommandException {
		if (results.size() == 0)
			throw new CommandException("No results match!");

		int maxPages = results.size() / this.resultsPerPage + 1;
		if ((page <= 0) || (page > maxPages))
			throw new CommandException("Unknown page selected! " + maxPages + " total pages.");

		sender.sendMessage(formatHeader(page, maxPages));

		for (int i = this.resultsPerPage * (page - 1); (i < this.resultsPerPage * page) && (i < results.size()); ++i)
			sender.sendMessage(format(results.get(i), i));
	}

	public String formatHeader(int page, int pages) {
		String middle = ChatColor.DARK_AQUA + header + " (" + ChatColor.AQUA + page + ChatColor.DARK_AQUA + " of " + ChatColor.AQUA + pages + ChatColor.DARK_AQUA + ")";
		return StringUtils.padMessage(middle, "-", ChatColor.RED);
	}

	public abstract String format(T paramT, int index);

}
