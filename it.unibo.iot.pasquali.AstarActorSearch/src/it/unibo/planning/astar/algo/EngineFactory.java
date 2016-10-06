package it.unibo.planning.astar.algo;

import it.unibo.planning.astar.enums.Algo;
import it.unibo.planning.astar.interfaces.IEngine;
import it.unibo.qactors.planned.QActorPlanned;

public class EngineFactory {

	public static IEngine getEngine(Algo type, QActorPlanned actor)
	{
		if(type.equals(Algo.ONLY_TILED))
			return new TiledEngine(actor);
		else
			return new TiledDiagonalEngine(actor);
	}
	
}
