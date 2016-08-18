package it.unibo.planning.astar.algo;

import java.text.CollationKey;
import java.text.Collator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

import it.unibo.planning.astar.domain.Move;
import it.unibo.planning.astar.domain.State;

public class SearchAgent {
	
	private TreeMap<State, Integer> closedSet;
	private TreeMap<State, Integer> openSet;
	
	private HashMap<State, State> cameFrom;
	
	public SearchAgent()
	{
		closedSet = new TreeMap<State, Integer>();
		openSet = new TreeMap<State, Integer>();
		cameFrom = new HashMap<State, State>();
	}
	
	
	public ArrayList<Move> searchBestPath(State start, State goal)
	{
		Engine.goal = goal;
		openSet.put(start, start.getCost());
		
		while(!openSet.isEmpty())
		{
			State current = openSet.firstKey();
			if(Engine.isGoalState(current))
				return recostructPath(current);
			
			openSet.remove(current);
			closedSet.put(current, current.getCost());
			
			ArrayList<Move> possibleMoves = (ArrayList<Move>) Engine.getPossibleMoves(current);
			
			for (Move m : possibleMoves)
			{
				State ns = Engine.makeMove(current, m);
				if(ns != null)
				{
					if(closedSet.containsKey(ns))
						continue;
					
					if(!openSet.containsKey(ns))
						openSet.put(ns, ns.getCost());
					else
					{
						if(ns.getCost() >= openSet.get(ns))
							continue;
						else
						{
							cameFrom.put(ns, current);
							openSet.remove(ns);
							openSet.put(ns, ns.getCost());
						}
					}					
				}
			}			
		}
		return null;		
	}
	
	
	private ArrayList<Move> recostructPath(State current) {
		
		ArrayList<Move> path = new ArrayList<Move>();
		path.add(current.getGenMove());
		
		while(cameFrom.containsKey(current))
		{
			current = cameFrom.get(current);
			path.add(current.getGenMove());
		}
		return path;		
	}
	

}
