package me.raino.event;

import me.raino.match.Match;

public class MatchCycleEvent extends SporkEvent {

	private Match oldMatch;
	private Match newMatch;

	public MatchCycleEvent(Match oldMatch, Match newMatch) {
		super(null, null);
		this.oldMatch = oldMatch;
		this.newMatch = newMatch;
	}

	public Match getOldMatch() {
		return oldMatch;
	}

	public Match getNewMatch() {
		return newMatch;
	}

}
