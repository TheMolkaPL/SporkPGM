package me.raino.module.modules;

import java.util.Collection;

import me.raino.map.extra.Contributor;
import me.raino.module.Module;
import me.raino.module.ModuleContainer;
import me.raino.module.ModuleInfo;
import me.raino.util.XMLUtils;

import org.dom4j.Document;
import org.dom4j.Element;

import com.google.common.collect.Lists;

@ModuleInfo(module = InfoModule.class, name = "InfoModule")
public class InfoModule extends Module {

	private String name;
	private String version;
	private String objective;
	private Collection<Contributor> authors;
	private Collection<Contributor> contributors;
	private Collection<String> rules;

	public InfoModule(String name, String version, String objective, Collection<Contributor> authors, Collection<Contributor> contributors, Collection<String> rules) {
		this.name = name;
		this.version = version;
		this.objective = objective;
		this.authors = authors;
		this.contributors = contributors;
		this.rules = rules;
	}

	public void enable() {}

	public void disable() {}

	public static Module parse(ModuleContainer moduleContainer, Document doc) {
		Element root = doc.getRootElement();

		String name = root.elementText("name");
		String version = root.elementText("version");
		String objective = root.elementText("objective");

		Collection<Contributor> authors = Lists.newArrayList();
		for (Element e : XMLUtils.getElements(root, "authors", "author"))
			authors.add(new Contributor(e.getText(), e.attributeValue("contribution")));

		Collection<Contributor> contributors = Lists.newArrayList();
		for (Element e : XMLUtils.getElements(root, "contributors", "contributor"))
			contributors.add(new Contributor(e.getText(), e.attributeValue("contribution")));

		Collection<String> rules = Lists.newArrayList();
		for (Element e : XMLUtils.getElements(root, "rules", "rule"))
			rules.add(e.getText());

		return new InfoModule(name, version, objective, authors, contributors, rules);
	}

	public String getName() {
		return name;
	}

	public String getVersion() {
		return version;
	}

	public String getObjective() {
		return objective;
	}

	public Collection<Contributor> getAuthors() {
		return authors;
	}

	public Collection<Contributor> getContributors() {
		return contributors;
	}

	public Collection<String> getRules() {
		return rules;
	}

}
