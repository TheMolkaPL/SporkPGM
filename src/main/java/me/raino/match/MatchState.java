package me.raino.match;

public enum MatchState {

	CYCLING(null),
	ENDED(CYCLING),
	RUNNING(ENDED),
	WAITING(RUNNING);
	
	private MatchState next;
	
	MatchState(MatchState next) {
		this.next = next;
	}
	
	public MatchState next() {
		return next;
	}

}
