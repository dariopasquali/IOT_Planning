package it.unibo.planning.astar.engine;

import java.util.ArrayList;
import java.util.HashMap;

import it.unibo.planning.astar.domain.Move;
import it.unibo.planning.astar.domain.State;
import it.unibo.planning.astar.enums.Direction;
import it.unibo.planning.domain.*;
import it.unibo.planning.engine.AbstractEngine;
import it.unibo.planning.enums.*;

public class DirAStarEngine extends AbstractEngine{


	private HashMap<Integer, Direction> spinMap;
	
		
	public DirAStarEngine()
	{
		super();
		
		spinMap = new HashMap<Integer, Direction>();
		spinMap.put(0, Direction.NORTH);
		spinMap.put(1, Direction.NORTH_EAST);
		spinMap.put(2, Direction.EAST);
		spinMap.put(3, Direction.SOUTH_EAST);
		spinMap.put(4, Direction.SOUTH);
		spinMap.put(5, Direction.SOUTH_WEST);
		spinMap.put(6, Direction.WEST);
		spinMap.put(7, Direction.NORTH_WEST);
	}
	
	@Override
	public void setIntMap(Integer[][] map, int xmax, int ymax)
	{
		super.setIntMap(map, xmax, ymax);
		
		for(int k=0; k<=ymax; k++)
		{
			for(int j = 0; j<=xmax; j++)
			{
				System.out.print(map[k][j]);
			}
			System.out.println("");
		}
	}
	
	public State makeMove(State start, Move move) {
		
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
		
			result.setDirection(start.getDirection());	
			cost = move.getForwardType().getCost();
		}
		
		result.setCost(start.getCost()+cost);
		result.setGenMove(move);
		result.setHeuristic(evaluateState(result));
		
		return result;
	}

	
	private Direction makeSpin(Direction start, SpinDirection spin) {
		int newID = (start.getValue() + (8+spin.getRotation()))%8;
		return spinMap.get(newID);
	}

	public ArrayList<AbstractState> getValidSuccessors(AbstractState state) {
		/*
		 * move forward
		 * move right
		 * move left
		 * move doubleright
		 * move doubleleft
		 * 
		 * NEVER GO BACK!!!!!!!!!
		 */
		
		ArrayList<AbstractState> valid = new ArrayList<AbstractState>();
		
		int x = state.getX();
		int y = state.getY();
		
		State next = new State();
		
		boolean checkLateral = false;
		State l1 = null;
		State l2 = null;
		
		State s = (State) state;
		
		switch(s.getDirection())
		{
		
		case NORTH_EAST:
			checkLateral = true;
			l1 = new State(x, y-1);
			l2 = new State(x+1, y);
			next = makeMove(s, new Move(ForwardMoveType.DIAGONAL));
			break;
			
			
		case SOUTH_EAST:
			checkLateral = true;
			l1 = new State(x+1, y);
			l2 = new State(x, y+1);
			next = makeMove(s, new Move(ForwardMoveType.DIAGONAL));
			break;
			
			
		case SOUTH_WEST:
			checkLateral = true;
			l1 = new State(x, y+1);
			l2 = new State(x-1, y);
			next = makeMove(s, new Move(ForwardMoveType.DIAGONAL));
			break;
			
		case NORTH_WEST:
			checkLateral = true;
			l1 = new State(x, y-1);
			l2 = new State(x-1, y);
			next = makeMove(s, new Move(ForwardMoveType.DIAGONAL));
			break;
			
			default:
				next = makeMove(s, new Move(ForwardMoveType.TILED));
				break;
		}
		
		if(isValidState(next))
		{	if(!checkLateral)
				valid.add(next);
			else if((isValidState(l1) && isValidState(l2)))
					valid.add(next);
		}
		
		valid.add(makeMove(s,new Move(SpinDirection.RIGHT)));
		valid.add(makeMove(s,new Move(SpinDirection.LEFT)));
		valid.add(makeMove(s,new Move(SpinDirection.DOUBLELEFT)));
		valid.add(makeMove(s,new Move(SpinDirection.DOUBLERIGHT)));
		
		return valid;
	}



	public double evaluateState(State state) {
		//distanza manatthan
		
		double catX = Math.abs(goal.getX()-state.getX());
		double catY = Math.abs(goal.getY()-state.getY());
		
		return catX + catY;	
	}


	

}
