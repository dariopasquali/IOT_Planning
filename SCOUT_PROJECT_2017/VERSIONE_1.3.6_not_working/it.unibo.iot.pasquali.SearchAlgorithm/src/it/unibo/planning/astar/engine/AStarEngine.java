package it.unibo.planning.astar.engine;

import java.util.ArrayList;
import java.util.List;

import it.unibo.planning.astar.domain.State;
import it.unibo.planning.astar.interfaces.IEngine;
import it.unibo.planning.enums.PositionMove;

public class AStarEngine implements IEngine{

	private State goal;	
	int xmax = -1;
	int ymax = -1;	
	
	private Integer[][] intmap;
	//refactor of the intmap
	/*			NORTH
	 * 		---------->X
	 * 		|
	 * 		|
	 * WESR	|			EAST
	 * 		|
	 * 		\/
	 * 		Y
	 * 			SOUTH 
	 * 
	 * so width = xmax
	 * and height = ymax
	 * 
	 * and the intmap is intmap[y][x] like the explorer
	 * because is better, shout up 
	 */
	
	public AStarEngine()
	{
		this.goal = null;
	}
	
	@Override
	public void setGoal(State goal) {
		this.goal = goal;		
	}
	
	@Override
	public void setIntMap(Integer[][] map, int ymax, int xmax)
	{
		this.intmap = map.clone();
		this.ymax = ymax;
		this.xmax = xmax;
		
		//printIntMap();
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
			y-=1;
			break;
		
		case NORTH_EAST:
			x+=1;
			y-=1;
			break;
			
		case EAST:
			x+=1;
			break;
			
		case SOUTH_EAST:
			x+=1;
			y+=1;
			break;
			
		case SOUTH:
			y+=1;
			break;
			
		case SOUTH_WEST:
			x-=1;
			y+=1;
			break;
			
		case WEST:
			x-=1;
			break;
			
		case NORTH_WEST:
			x-=1;
			y-=1;
			break;
			
		default:
			break;
		}
		
		result.setX(x);
		result.setY(y);
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
	
	private void printIntMap()
	{
		String s = "  ";
		
		for(int x=0; x<=xmax; x++)
			s+=x;
		
		s+="\n";
		
		for(int y=0; y<=ymax; y++)
		{
			s+=y+" ";
			for(int x=0; x<=xmax; x++)
			{
				s+=intmap[y][x];
			}
			s+="\n";
		}
		
		System.out.println(s);
	}

}
