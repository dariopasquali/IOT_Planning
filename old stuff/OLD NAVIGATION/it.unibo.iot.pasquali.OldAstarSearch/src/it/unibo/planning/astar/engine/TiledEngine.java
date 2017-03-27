package it.unibo.planning.astar.engine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import it.unibo.planning.astar.domain.Move;
import it.unibo.planning.astar.enums.*;
import it.unibo.planning.astar.domain.State;

public class TiledEngine{
	/*
	private State goal;
	
	String elements="";
	int xmax = -1;
	int ymax = -1;
	
	private HashMap<Integer, Direction> spinMap;
	private Integer[][] intmap;
	
	
	public TiledEngine()
	{
		this.goal = null;
		spinMap = new HashMap<Integer, Direction>();
		spinMap.put(0, Direction.NORTH);
		spinMap.put(2, Direction.EAST);
		spinMap.put(4, Direction.SOUTH);
		spinMap.put(6, Direction.WEST);
	}		
	
	
	 
	public boolean isValidState(State state) {
		
		return (((State)state).getX() >= 0 &&
				((State)state).getX() <= xmax &&
				((State)state).getY() >= 0 &&
				((State)state).getY() <= ymax &&
				intmap[((State)state).getY()][((State)state).getX()] != 1);
	}
	

	
	 
	public State makeMove(State start, Move move)
	{
		
		State result = new State();
		int cost = 10;
		
		if(move.getType().equals(MoveType.SPIN))
		{
			result.setX(start.getX());
			result.setY(start.getY());
			
			if(!isValidState(result))
				return null;
			
			result.setDirection(makeSpin(start.getDirection(), move.getSpin()));
			
			cost = move.getSpin().getCost();
		}
		else
		{
			int x = start.getX();
			int y = start.getY();
			
			switch(start.getDirection())
			{
			case NORTH:
				result.setX(x);
				result.setY(y-1);
				break;
				
			case EAST:
				result.setX(x+1);
				result.setY(y);
				break;
				
			case SOUTH:
				result.setX(x);
				result.setY(y+1);
				break;
				
			case WEST:
				result.setX(x-1);
				result.setY(y);
				break;
				
			default:
				break;
			}
			
			if(!isValidState(result))
				return null;
			
			result.setDirection(start.getDirection());	
			cost = 10;
		}
		
		result.setCost(start.getCost()+cost);
		result.setGenMove(move);
		result.setHeuristic(evaluateState(result));
		
		return result;
	}
	
	 
	public double evaluateState(State state) {
		//distanza manatthan
		
		double catX = Math.abs(goal.getX()-state.getX());
		double catY = Math.abs(goal.getY()-state.getY());
		
		return catX + catY;	
	}

	
	private Direction makeSpin(Direction start, SpinDirection spin)
	{
		int newID = (start.getValue() + (8+2*spin.getRotation()))%8;
		return spinMap.get(newID);
	}
	
	

	 
	public ArrayList<State> getValidSuccessors(State state)
	{
	
		ArrayList<State> valid = new ArrayList<State>();
		
		State next = makeMove(state, new Move(ForwardMoveType.TILED));
		
		if(isValidState(next))
			valid.add(next);
		
		valid.add(makeMove(state,new Move(SpinDirection.RIGHT)));
		valid.add(makeMove(state,new Move(SpinDirection.LEFT)));
		valid.add(makeMove(state,new Move(SpinDirection.DOUBLELEFT)));
		valid.add(makeMove(state,new Move(SpinDirection.DOUBLERIGHT)));
		
		return valid;
	}

	 
	public boolean isGoalState(State current) {
		if(current == null)
			return false;
		
		if(current.getX() == goal.getX() && current.getY() == goal.getY())
			return true;
		else
			return false;
	}

	 
	public void setGoal(State goal) {
		this.goal = goal;		
	}
	
	 
	public void setIntMap(Integer[][] map, int xmax, int ymax)
	{
		this.intmap = map;
		this.xmax = xmax;
		this.ymax = ymax;
		
		for(int k=0; k<=ymax; k++)
		{
			for(int j = 0; j<=xmax; j++)
			{
				System.out.print(intmap[k][j]);
			}
			System.out.println("");
		}
	}
	*/
}
