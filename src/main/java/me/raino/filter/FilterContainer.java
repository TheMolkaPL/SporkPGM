package me.raino.filter;

import tc.oc.commons.core.util.ContextStore;

public class FilterContainer extends ContextStore<Filter> {

	private FilterParser parser;

	public FilterContainer() {
		this.parser = new FilterParser(this);
	}

	public FilterParser getParser() {
		return parser;
	}

}
