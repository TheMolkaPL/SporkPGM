package io.sporkpgm.region;

import tc.oc.commons.core.util.ContextStore;

public class RegionContainer extends ContextStore<Region> {

	private RegionParser parser;

	public RegionContainer() {
		this.parser = new RegionParser(this);
	}
	
	public RegionParser getParser() {
		return parser;
	}

}
