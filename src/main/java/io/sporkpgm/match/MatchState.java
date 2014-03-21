package io.sporkpgm.match;

public enum MatchState {

	CYCLING(null),
	ENDED(CYCLING),
	RUNNING(ENDED),
	STARTING(RUNNING),
	WAITING(STARTING);
	
	private MatchState next;
	
	MatchState(MatchState next) {
		this.next = next;
	}
	
	public MatchState next() {
		return next;
	}

}
