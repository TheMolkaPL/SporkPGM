package me.raino.module.modules;

import me.raino.module.Module;
import me.raino.module.ModuleContainer;
import me.raino.module.ModuleInfo;
import me.raino.region.RegionParser;
import me.raino.util.Log;
import me.raino.util.XMLUtils;

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
