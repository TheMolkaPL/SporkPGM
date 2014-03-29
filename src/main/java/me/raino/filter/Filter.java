package me.raino.filter;

public interface Filter {

	public FilterState matches(Object obj);
	
}
