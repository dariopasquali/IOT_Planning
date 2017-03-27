package it.unibo.planning.agent;

import it.unibo.planning.domain.AbstractState;
import it.unibo.planning.domain.Path;
import it.unibo.planning.engine.AbstractEngine;

public abstract class AbstractSearchAgent {

	public abstract Path searchBestPath(AbstractEngine engine,
										AbstractState start,
										AbstractState goal);
	
}
