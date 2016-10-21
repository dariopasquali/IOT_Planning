package it.unibo.planning.astar.algo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.TreeMap;

import it.unibo.planning.astar.domain.Move;
import it.unibo.planning.astar.domain.State;
import it.unibo.planning.astar.engine.AStarEngine;
import it.unibo.planning.astar.enums.ForwardMoveType;
import it.unibo.planning.astar.enums.PositionMove;
import it.unibo.planning.astar.enums.SpinDirection;
import it.unibo.planning.astar.interfaces.IEngine;


public class AStarSearchAgent {
	
	private ArrayList<State> closedSet;
	private ArrayList<State> openSet;
	
	private HashMap<State,State> cameFrom;
	
	private IEngine engine;
	
	public AStarSearchAgent()
	{
		closedSet = new ArrayList<State>();
		openSet = new ArrayList<State>();
		cameFrom = new HashMap<State,State>();
	}
	
	
	public Path searchBestPath(IEngine engine, State start, State goal)
	{
		System.out.println("LET'S FIND BEST PATH");
		
		Path path = new Path();
		
		this.engine = (AStarEngine) engine;
		engine.setGoal(goal);
		
		openSet.add(start);
		
		while(!openSet.isEmpty())
		{
			State current = openSet.get(0);
			if(engine.isGoalState(current))
			{
				path.setMoves(recostructPath(start, current));
				path.setStates(recostructStatePath(current));
				return path;
			}
			
			openSet.remove(current);
			closedSet.add(current);
			
			ArrayList<State> validSuccessors = (ArrayList<State>) engine.getValidSuccessors(current);
			
			for (State ns : validSuccessors)
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
			
			Collections.sort((ArrayList<State>)openSet);
		}
		return null;		
	}
	
	/*
	// STATE VERSION
	public ArrayList<State> aStarSearchState(IEngine engine, State start, State goal)
	{
		System.out.println("LET'S FIND BEST PATH");
		
		this.engine = engine;
		engine.setGoal(goal);
		
		openSet.add(start);
		
		while(!openSet.isEmpty())
		{
			State current = openSet.get(0);
			if(engine.isGoalState(current))
				return recostructStatePath(current);
			
			openSet.remove(current);
			closedSet.add(current);
			
			ArrayList<State> validSuccessors = (ArrayList<State>) engine.getValidSuccessors(current);
			
			for (State ns : validSuccessors)
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
	*/
	
	private ArrayList<Move> recostructPath(State start, State current) {
		
		
		ArrayList<PositionMove> dirs = new ArrayList<PositionMove>();
		dirs.add(current.getPositionGenMove());
		
		while(cameFrom.containsKey(current))
		{
			current = cameFrom.get(current);
			if(current.getPositionGenMove()!=null)
				dirs.add(current.getPositionGenMove());
		}
		
		ArrayList<Move> path = new ArrayList<Move>();
		
		PositionMove actual = PositionMove.NORTH;
		
		for(PositionMove next : dirs)
		{
			ArrayList<Move> toAdd = fromDirsToMoves(actual, next);
			actual = next;
			path.addAll(toAdd);
		}
		
		return path;
				
	}
	
	private ArrayList<Move> fromDirsToMoves(PositionMove actual, PositionMove next)
	{
		ArrayList<Move> moves = new ArrayList<Move>();
		
		int spins = (next.getPhase()-actual.getPhase())/45;
		
		if(spins > 0)
		{
			for(int i=0; i<Math.abs(spins); i++)
				moves.add(new Move(SpinDirection.RIGHT));
		}
		else if(spins < 0)
		{
			for(int i=0; i<Math.abs(spins); i++)
				moves.add(new Move(SpinDirection.LEFT));
		}
		
		if(next.getPhase()%90 != 0)
			moves.add(new Move(ForwardMoveType.DIAGONAL));
		else
			moves.add(new Move(ForwardMoveType.TILED));
		
		return moves;
	}
	
	private ArrayList<State> recostructStatePath(State current) {
		
		ArrayList<State> path = new ArrayList<State>();
		path.add(current);
		
		while(cameFrom.containsKey(current))
		{
			current = cameFrom.get(current);
			if(current.getPositionGenMove()!=null)
				path.add(current);
		}
		
		Collections.reverse(path);		
		return path;
				
	}


	

}
