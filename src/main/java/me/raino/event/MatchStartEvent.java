package me.raino.event;

import me.raino.match.Match;

public class MatchStartEvent extends SporkEvent {

	private Match match;
	
	public MatchStartEvent(Match match) {
		super(null, null);
		this.match = match;
	}
	
	public Match getMatch() {
		return match;
	}

}
