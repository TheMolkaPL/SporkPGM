package me.raino.event;

import me.raino.match.Match;

public class MatchEndEvent extends SporkEvent {

	private Match match;

	public MatchEndEvent(Match match) {
		super(null, null);
		this.match = match;
	}

	public Match getMatch() {
		return match;
	}

}
