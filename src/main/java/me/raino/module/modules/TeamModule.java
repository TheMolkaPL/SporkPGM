package me.raino.module.modules;

import java.util.Collection;
import java.util.List;

import me.raino.module.Module;
import me.raino.module.ModuleContainer;
import me.raino.module.ModuleInfo;
import me.raino.team.SporkTeam;
import me.raino.team.SporkTeam.TeamType;
import me.raino.util.Config;
import me.raino.util.StringUtils;
import me.raino.util.XMLUtils;

import org.bukkit.ChatColor;
import org.dom4j.Document;
import org.dom4j.Element;

import com.google.common.collect.Lists;

@ModuleInfo(module = TeamModule.class, name = "TeamModule")
public class TeamModule extends Module {

	private SporkTeam defaultTeam;
	private Collection<SporkTeam> teams;

	public TeamModule(SporkTeam defaultTeam, Collection<SporkTeam> teams) {
		this.defaultTeam = defaultTeam;
		this.teams = teams;
	}

	public void enable() {}

	public void disable() {}

	public static Module parse(ModuleContainer moduleContainer, Document doc) {
		Element root = doc.getRootElement();

		List<SporkTeam> teams = Lists.newArrayList();

		for (Element e : XMLUtils.getElements(root, "teams", "team")) {
			String name = e.getText();
			ChatColor color = XMLUtils.parseChatColor(e.attributeValue("color"));
			int max = XMLUtils.parseInteger(e.attributeValue("max"));

			teams.add(new SporkTeam(name, color, max, TeamType.PARTICIPATING));
		}

		SporkTeam defaultTeam = new SporkTeam(Config.Team.DEFAULT_NAME, Config.Team.DEFAULT_COLOR, Config.Team.DEFAULT_MAX, TeamType.OBSERVING);

		return new TeamModule(defaultTeam, teams);
	}

	public SporkTeam getDefaultTeam() {
		return defaultTeam;
	}

	public Collection<SporkTeam> getTeams() {
		return teams;
	}

	public SporkTeam getTeam(String query) {
		return StringUtils.fuzzySearch(getTeams(), query);
	}

	public Collection<SporkTeam> getAllTeams() {
		List<SporkTeam> allTeams = Lists.newArrayList(teams);
		allTeams.add(getDefaultTeam());
		return allTeams;
	}

}
