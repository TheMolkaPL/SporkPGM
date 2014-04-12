package io.sporkpgm.module.modules.tdm;

import io.sporkpgm.module.Module;
import io.sporkpgm.module.ModuleInfo;
import io.sporkpgm.module.builder.Builder;
import io.sporkpgm.objective.scored.ScoredBuilder;
import io.sporkpgm.objective.scored.ScoredObjective;

@ModuleInfo(name = "TeamDeathmatchModule", description = "Handles ScoredObjective Kill/Death scoring", requires = ScoredObjective.class, listener = true)
public class TeamDeathmatchModule extends Module {

	private ScoredObjective objective;
	private int value;
	private int natural;

	public TeamDeathmatchModule(ScoredObjective objective, int value, int natural) {
		this.objective = objective;
		this.value = value;
		this.natural = natural;
	}

	public Class<? extends Builder> builder() {
		return ScoredBuilder.class;
	}

}
