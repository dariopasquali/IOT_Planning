package it.unibo.planning.astar.engine;

import java.util.ArrayList;
import java.util.List;

import it.unibo.planning.astar.domain.State;
import it.unibo.planning.astar.enums.PositionMove;
import it.unibo.planning.astar.interfaces.IEngine;

public class AStarEngine implements IEngine{

	private State goal;	
	int xmax = -1;
	int ymax = -1;	
	
	private Integer[][] intmap;
	
	public AStarEngine()
	{
		this.goal = null;
	}
	
	@Override
	public void setGoal(State goal) {
		this.goal = goal;		
	}
	
	@Override
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
	
	@Override
	public boolean isGoalState(State current) {
		if(current == null)
			return false;
		
		return current.equals(goal);
	}

	@Override
	public boolean isValidState(State state) {
		return (state.getX() >= 0 &&
				state.getX() <= xmax &&
				state.getY() >= 0 &&
				state.getY() <= ymax &&
				intmap[state.getY()][state.getX()] != 1);
	}

	
	@Override
	public double evaluateState(State state) {

		return Math.abs(goal.getX()-state.getX()) +  Math.abs(goal.getY()-state.getY());
	}
	
	@Override
	public State makeMove(State start, PositionMove dir) {
		
		State result = new State();
		
		int x = start.getX();
		int y = start.getY();
		
		switch(dir)
		{
		case NORTH:
			result.setX(x);
			result.setY(y-1);
			break;
		
		case NORTH_EAST:
			result.setX(x+1);
			result.setY(y-1);
			break;
			
		case EAST:
			result.setX(x+1);
			result.setY(y);
			break;
			
		case SOUTH_EAST:
			result.setX(x+1);
			result.setY(y+1);
			break;
			
		case SOUTH:
			result.setX(x);
			result.setY(y+1);
			break;
			
		case SOUTH_WEST:
			result.setX(x-1);
			result.setY(y+1);
			break;
			
		case WEST:
			result.setX(x-1);
			result.setY(y);
			break;
			
		case NORTH_WEST:
			result.setX(x-1);
			result.setY(y-1);
			break;
			
		default:
			break;
		}
		
		result.setCost(start.getCost()+dir.getCost());
		result.setPositionGenMove(dir);
		result.setHeuristic(evaluateState(result));
		
		return result;
		
	}

	@Override
	public List<State> getValidSuccessors(State state) {

		ArrayList<State> valid = new ArrayList<State>();
		
		
		
		for(PositionMove pm : PositionMove.values())
		{
			State next = null;
			
			boolean checkLateral = false;
			State l1 = null;
			State l2 = null;
			
			switch(pm)
			{
						
			case NORTH_EAST:
				checkLateral = true;
				next = makeMove(state, PositionMove.NORTH_EAST);
				l1 = makeMove(state, PositionMove.NORTH);
				l2 = makeMove(state, PositionMove.EAST);
				break;
				
							
			case SOUTH_EAST:
				checkLateral = true;
				next = makeMove(state, PositionMove.SOUTH_EAST);
				l1 = makeMove(state, PositionMove.SOUTH);
				l2 = makeMove(state, PositionMove.EAST);
				break;
				
							
			case SOUTH_WEST:
				checkLateral = true;
				next = makeMove(state, PositionMove.SOUTH_WEST);
				l1 = makeMove(state, PositionMove.SOUTH);
				l2 = makeMove(state, PositionMove.WEST);
				break;
				
							
			case NORTH_WEST:
				checkLateral = true;
				next = makeMove(state, PositionMove.NORTH_WEST);
				l1 = makeMove(state, PositionMove.NORTH);
				l2 = makeMove(state, PositionMove.WEST);
				break;
				
				default:
					next = makeMove(state, pm);
					break;
			}
			
			if(isValidState(next))
			{	if(!checkLateral)
					valid.add(next);
				else if((isValidState(l1) && isValidState(l2)))
						valid.add(next);
			}
		}
		
		return valid;
		
	}

}
