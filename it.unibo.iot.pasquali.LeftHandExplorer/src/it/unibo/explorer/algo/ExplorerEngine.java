package it.unibo.explorer.algo;

import java.util.HashMap;

import it.unibo.domain.model.implmentation.Map;
import it.unibo.explorer.domain.State;
import it.unibo.explorer.interfaces.IExplorerEngine;
import it.unibo.planning.astar.enums.Direction;

public class ExplorerEngine implements IExplorerEngine{


	/*
	 * THIS IS THE ROBOT BRAIN
	 * in this implementation use a predefined map to check object presence
	 * in the real implementation it will use two sonar 
	 */
	
	private HashMap<Integer, Direction> spinMap;
	
	private String envMap;
	
	public ExplorerEngine(Map map)
	{
		spinMap = new HashMap<Integer, Direction>();
		spinMap.put(0, Direction.NORTH);
		spinMap.put(2, Direction.EAST);
		spinMap.put(4, Direction.SOUTH);
		spinMap.put(6, Direction.WEST);
		
		envMap = map.toString();
	}

	@Override
	public boolean CheckObjectForward(State state, Direction dir) {

		State ns = moveForward(state, dir);
		return envMap.contains("element("+state.getX()+","+state.getY()+")");		
	}

	@Override
	public boolean CheckObjectLeft(State state, Direction dir) {
		
		State ns = moveLeft(state, dir);
		return envMap.contains("element("+state.getX()+","+state.getY()+")");
	}	
	
	@Override
	public boolean CheckObjectForward() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean CheckObjectLeft() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public State moveForward(State state, Direction dir) {
		
		State result = new State();
		
		int x = state.getX();
		int y = state.getY();
		
		switch(dir)
		{
		case NORTH:
			result.setY(y-1);
			result.setX(x);
			break;
			
		case EAST:
			result.setX(x+1);
			result.setY(y);
			break;
			
		case SOUTH:
			result.setY(y+1);
			result.setX(x);
			break;
			
		case WEST:
			result.setX(x-1);
			result.setY(y);
			break;
			
		default:
			break;
		}
		
		return result;
	}

	@Override
	public Direction turnRight(Direction dir) {
		int newID = (dir.getValue() + (8+2)%8);
		return spinMap.get(newID);
	}

	@Override
	public Direction turnLeft(Direction dir) {
		int newID = (dor.getValue() + (8-2)%8);
		return spinMap.get(newID);
	}

	@Override
	public State moveRight(State state, Direction dir) {
		Direction nd = turnRight(dir);
		return moveForward(state, nd);
	}

	@Override
	public State moveLeft(State state, Direction dir) {
		Direction nd = turnLeft(dir);
		return moveForward(state, nd);
	}
	
	
	
}
