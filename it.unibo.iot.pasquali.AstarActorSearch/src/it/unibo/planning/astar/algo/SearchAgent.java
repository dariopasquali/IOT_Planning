package it.unibo.planning.astar.algo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.TreeMap;

import it.unibo.planning.astar.domain.Move;
import it.unibo.planning.astar.domain.State;
import it.unibo.qactors.planned.QActorPlanned;

public class SearchAgent {
	
	private ArrayList<State> closedSet;
	private ArrayList<State> openSet;
	
	private HashMap<State,State> cameFrom;
	
	private Engine engine;
	
	public SearchAgent()
	{
		closedSet = new ArrayList<State>();
		openSet = new ArrayList<State>();
		cameFrom = new HashMap<State,State>();
	}
	
	
	public ArrayList<Move> searchBestPath(QActorPlanned actor, State start, State goal)
	{
		System.out.println("LET'S FIND BEST PATH");
		
		engine = new Engine(actor, goal);
		
		openSet.add(start);
		
		while(!openSet.isEmpty())
		{
			System.out.println("Nodi Aperti: "+openSet.size());
			System.out.println("Nodi Chiusi: "+closedSet.size());
			
			State current = openSet.get(0);
			if(engine.isGoalState(current))
				return recostructPath(current);
			
			openSet.remove(current);
			closedSet.add(current);
			
			ArrayList<Move> possibleMoves = (ArrayList<Move>) engine.getPossibleMoves(current);
			
			for (Move m : possibleMoves)
			{
				State ns = engine.makeMove(current, m);
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
	
	
	private ArrayList<Move> recostructPath(State current) {
		
		ArrayList<Move> path = new ArrayList<Move>();
		path.add(current.getGenMove());
		
		while(cameFrom.containsKey(current))
		{
			current = cameFrom.get(current);
			if(current.getGenMove()!=null)
				path.add(current.getGenMove());
		}
		return path;		
	}
	
	// STATE VERSION
	public ArrayList<State> searchBestStatePath(QActorPlanned actor, State start, State goal)
	{
		System.out.println("LET'S FIND BEST PATH");
		
		engine = new Engine(actor, goal);
		
		openSet.add(start);
		
		while(!openSet.isEmpty())
		{
			State current = openSet.get(0);
			if(engine.isGoalState(current))
				return recostructStatePath(current);
			
			openSet.remove(current);
			closedSet.add(current);
			
			ArrayList<Move> possibleMoves = (ArrayList<Move>) engine.getPossibleMoves(current);
			
			for (Move m : possibleMoves)
			{
				State ns = engine.makeMove(current, m);
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
	
	
	private ArrayList<State> recostructStatePath(State current) {
		
		ArrayList<State> path = new ArrayList<State>();
		path.add(current);
		
		while(cameFrom.containsKey(current))
		{
			current = cameFrom.get(current);
			if(current.getGenMove()!=null)
				path.add(current);
		}
		
		Collections.reverse(path);		
		return path;		
	}
	

}
