package me.raino.filter;

public enum FilterState {

	ALLOW,
	DENY,
	ABSTAIN;

	public boolean allowed() {
		return toBoolean();
	}

	public boolean denied() {
		return !toBoolean();
	}

	public boolean toBoolean() {
		return this == ALLOW || this == ABSTAIN;
	}

	public static FilterState fromBoolean(boolean bool) {
		return bool ? ALLOW : DENY;
	}
	
}
