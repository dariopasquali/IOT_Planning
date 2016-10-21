package it.unibo.planning.astar.algo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import alice.tuprolog.interfaces.IEngine;
import it.unibo.planning.agent.AbstractSearchAgent;
import it.unibo.planning.astar.domain.Move;
import it.unibo.planning.astar.domain.State;
import it.unibo.planning.astar.engine.DirAStarEngine;
import it.unibo.planning.domain.AbstractMove;
import it.unibo.planning.domain.AbstractState;
import it.unibo.planning.domain.Path;
import it.unibo.planning.engine.AbstractEngine;

public class SearchAgent extends AbstractSearchAgent{
	
	private ArrayList<AbstractState> closedSet;
	private ArrayList<AbstractState> openSet;
	
	private HashMap<AbstractState,AbstractState> cameFrom;
	
	private DirAStarEngine engine;
	
	public SearchAgent()
	{
		closedSet = new ArrayList<AbstractState>();
		openSet = new ArrayList<AbstractState>();
		cameFrom = new HashMap<AbstractState,AbstractState>();
	}
	
	
	@Override
	public Path searchBestPath(AbstractEngine engine, AbstractState start, AbstractState goal)
	{
		System.out.println("LET'S FIND BEST PATH");
		
		Path path = new Path();
		
		this.engine = (DirAStarEngine) engine;
		engine.setGoal(goal);
		
		openSet.add(start);
		
		while(!openSet.isEmpty())
		{
		
			AbstractState current = openSet.get(0);
			if(engine.isGoalState(current))
			{
				path.setMoves(recostructPath(current));
				path.setStates(recostructStatePath(current));
				return path;
			}
			
			openSet.remove(current);
			closedSet.add(current);
			
			ArrayList<AbstractState> successors = ((DirAStarEngine)engine).getValidSuccessors(current);
			
			for (AbstractState ns : successors)
			{
				if(ns != null)
				{					
					if(closedSet.contains(ns))
						continue;
					
					if(!openSet.contains(ns))
						openSet.add(ns);
					else
					{
						if(ns.getCost() >= openSet.get(openSet.indexOf(ns)).getCost())
							continue;
						else
						{
							openSet.remove(ns);
							openSet.add(ns);
						}
					}
					
					cameFrom.put(ns,current);
				}
			}
			
			Collections.sort(openSet);
		}
		return null;		
	}
	
	
	private ArrayList<AbstractMove> recostructPath(AbstractState current) {
		
		ArrayList<AbstractMove> path = new ArrayList<AbstractMove>();
		path.add(((State)current).getGenMove());
		
		while(cameFrom.containsKey(current))
		{
			current = cameFrom.get(current);
			if(((State)current).getGenMove()!=null)
				path.add(((State)current).getGenMove());
		}
		return path;		
	}
	
	private ArrayList<AbstractState> recostructStatePath(AbstractState current) {
		
		ArrayList<AbstractState> path = new ArrayList<AbstractState>();
		path.add(((State)current));
		
		while(cameFrom.containsKey(current))
		{
			current = cameFrom.get(current);
			if(((State)current).getGenMove()!=null)
				path.add(((State)current));
		}
		
		Collections.reverse(path);		
		return path;		
	}
	

}
