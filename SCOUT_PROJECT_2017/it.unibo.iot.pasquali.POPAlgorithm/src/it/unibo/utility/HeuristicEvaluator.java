package it.unibo.utility;

import java.awt.Point;

import it.unibo.domain.model.State;
import it.unibo.model.interfaces.IMap;
import it.unibo.planning.astar.algo.AStarSearchAgent;
import it.unibo.planning.astar.algo.Path;
import it.unibo.planning.astar.engine.AStarEngine;

public class HeuristicEvaluator {
	
	private AStarSearchAgent agent = null;
	private AStarEngine engine = null;
	
	public HeuristicEvaluator(IMap m){
	
		agent = new AStarSearchAgent();		
		engine = new AStarEngine();
		
		engine.setIntMap(m.getIntMap(), m.getYmax(), m.getXmax());		
	}
	
	public double evaluate(State from, State to)
	{
		Path path = agent.searchBestPath(engine, new Point(from.getX(), from.getY()), new Point(to.getX(), to.getY()));
		return path.getPoints().size();
	}

}
