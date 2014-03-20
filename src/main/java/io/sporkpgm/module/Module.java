package io.sporkpgm.module;

import org.dom4j.Document;

public abstract class Module {

	public abstract void enable();

	public abstract void disable();
	
	public static Module parse(Document doc) {
		return null;
	}
}
