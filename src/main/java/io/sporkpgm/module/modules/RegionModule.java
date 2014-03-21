package io.sporkpgm.module.modules;

import io.sporkpgm.module.Module;
import io.sporkpgm.module.ModuleContainer;
import io.sporkpgm.module.ModuleInfo;
import io.sporkpgm.region.RegionParser;
import io.sporkpgm.util.Log;
import io.sporkpgm.util.XMLUtils;

import org.dom4j.Document;
import org.dom4j.Element;

@ModuleInfo(module = RegionModule.class, name = "RegionModule")
public class RegionModule extends Module {

	public void enable() {}

	public void disable() {}

	public static Module parse(ModuleContainer moduleContainer, Document doc) {
		Element root = doc.getRootElement();

		RegionParser parser = moduleContainer.getRegionContainer().getParser();

		for (Element el : XMLUtils.getElements(root, "regions")) {
			try {
				parser.parse(el);
			} catch (Exception e) {
				Log.log(e);
			}
		}

		return new RegionModule();
	}

}
