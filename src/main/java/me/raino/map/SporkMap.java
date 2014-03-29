package me.raino.map;

import java.io.File;

import me.raino.module.Module;
import me.raino.module.ModuleContainer;
import me.raino.module.modules.InfoModule;
import me.raino.util.Config;
import me.raino.util.Log;

import org.bukkit.ChatColor;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

import tc.oc.commons.core.util.StringUtils;

public class SporkMap {

	private File folder;

	private ModuleContainer moduleContainer;

	public SporkMap(File folder) {
		this.folder = folder;

		Document doc = null;
		try {
			doc = new SAXReader().read(new File(folder, Config.Map.CONFIG));
		} catch (DocumentException e) {
			Log.log(e);
		}
		if (doc != null)
			this.moduleContainer = new ModuleContainer(doc);
	}

	public InfoModule getInfo() {
		return (InfoModule) getModule(InfoModule.class);
	}

	public Module getModule(Class<? extends Module> module) {
		return getModuleContainer().getModule(module);
	}

	public ModuleContainer getModuleContainer() {
		return moduleContainer;
	}

	public String getName() {
		return getInfo().getName();
	}
	
	public String getDescription() {
		return ChatColor.GOLD + getName() + ChatColor.DARK_PURPLE + " by " + StringUtils.listToEnglishCompound(getInfo().getAuthors(), ChatColor.RED.toString(), ChatColor.DARK_PURPLE.toString());
	}

	public File getFolder() {
		return folder;
	}

	public String toString() {
		return getName();
	}

}