package io.sporkpgm.map;

import io.sporkpgm.module.ModuleContainer;
import io.sporkpgm.module.modules.InfoModule;
import io.sporkpgm.util.Config;
import io.sporkpgm.util.Log;

import java.io.File;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

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
		return (InfoModule) moduleContainer.getModule(InfoModule.class);
	}
	
	public String getName() {
		return getInfo().getName();
	}

	public File getFolder() {
		return folder;
	}

}
