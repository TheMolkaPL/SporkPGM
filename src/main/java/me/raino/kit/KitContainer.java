package me.raino.kit;

import tc.oc.commons.core.util.ContextStore;

public class KitContainer extends ContextStore<SporkKit> {

	private KitParser parser;

	private KitContainer() {
		this.parser = new KitParser(this);
	}

}
