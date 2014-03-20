package io.sporkpgm.module.modules;

import io.sporkpgm.module.Module;
import io.sporkpgm.module.ModuleInfo;
import io.sporkpgm.team.SporkTeam;
import io.sporkpgm.team.SporkTeam.TeamType;
import io.sporkpgm.util.Config;
import io.sporkpgm.util.XMLUtils;

import java.util.Collection;
import java.util.List;

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

	public static Module parse(Document doc) {
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

	public Collection<SporkTeam> getAllTeams() {
		List<SporkTeam> allTeams = Lists.newArrayList(teams);
		allTeams.add(getDefaultTeam());
		return allTeams;
	}

}
