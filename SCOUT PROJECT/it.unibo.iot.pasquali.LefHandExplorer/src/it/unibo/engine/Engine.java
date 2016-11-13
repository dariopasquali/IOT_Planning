package it.unibo.engine;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;

import it.unibo.domain.Map;
import it.unibo.domain.Move;
import it.unibo.domain.State;
import it.unibo.enums.Direction;
import it.unibo.enums.MoveType;
import it.unibo.enums.PossMoves;
import it.unibo.enums.SpinDirection;

public class Engine {
	
	private State current;
	private Map currentMap;
	private Map worldMap;
	
	private HashMap<Direction, Direction> leftMap,rightMap;
	

	public Engine(Point start, Map map) {
	
		current = new State(start, Direction.NORTH);
		
		this.worldMap = map;
		this.currentMap = new Map(worldMap.getWidth(), worldMap.getHeight());
		
		leftMap = new HashMap<Direction,Direction>();
		leftMap.put(Direction.NORTH, Direction.WEST);
		leftMap.put(Direction.WEST, Direction.SOUTH);
		leftMap.put(Direction.SOUTH, Direction.EAST);
		leftMap.put(Direction.EAST, Direction.NORTH);		
		
		rightMap = new HashMap<Direction,Direction>();
		rightMap.put(Direction.NORTH, Direction.EAST);
		rightMap.put(Direction.EAST, Direction.SOUTH);
		rightMap.put(Direction.SOUTH, Direction.WEST);
		rightMap.put(Direction.WEST, Direction.NORTH);
	}

	public boolean checkObjFront() {

		State bk = current;
		
		moveForward();

		boolean obj = worldMap.isCellObj(current.getX(), current.getY());
				
		if(obj)
			currentMap.setCellObj(current.getX(), current.getY());
		else
			currentMap.setCellClear(current.getX(), current.getY());
		
		current = bk;	
			
		return obj;
		
	}

	public boolean checkObjLeft() {
		
		State bk = current;
		
		turnLeft();
		moveForward();

		boolean obj = worldMap.isCellObj(current.getX(), current.getY());
		
		if(obj)
			currentMap.setCellObj(current.getX(), current.getY());
		else
			currentMap.setCellClear(current.getX(), current.getY());
		
		current = bk;	
			
		return obj;
	}	
		
	public void turnLeft() {
		Direction cd = current.getDirection();
		current.setDirection(leftMap.get(cd));
		current.addCost(1);
	}
	
	public void turnRight() {
		Direction cd = current.getDirection();
		current.setDirection(rightMap.get(cd));
		current.addCost(1);
	}

	public void moveForward() {

		Direction cd = current.getDirection();
		
		int x = current.getX();
		int y = current.getY();
		
		Point resCoord = null;
		
		switch(cd)
		{
		case NORTH:
			resCoord = new Point(x, y-1);
			break;
		
		case EAST:
			resCoord = new Point(x+1, y);
			break;
			
		case SOUTH:
			resCoord = new Point(x, y+1);
			break;
			
		case WEST:
			resCoord = new Point(x-1, y);
			break;
			
		default:
			break;
		}
		
		current = new State(resCoord, cd);
		current.addCost(1);
		currentMap.setCellClear(current.getX(), current.getY());
	}
	
	public void moveLeft()
	{
		turnLeft();
		moveForward();
	}
	
	public void moveRight()
	{
		turnLeft();
		moveForward();
	}
	
	public void moveBackward()
	{
		turnLeft();
		turnLeft();
		moveForward();
	}
	
	
	public boolean currentAlreadyVisited() {
		
		return ! worldMap.isCellNone(current.getX(), current.getY());
	}

	public boolean checkExploredLeft() {
		
		State bk = current;
		
		turnLeft();
		moveForward();
		boolean ret = currentAlreadyVisited();
		
		current = bk;
		
		return ret;	
	}
	
	public boolean moreToExplore() {
		return worldMap.moreToExplore();
	}
	
	public State getCurrent() {
		return current;
	}
	
	public void travel(ArrayList<Move> path) {

		for(Move m : path)
		{
			makeMove(m);
		}

	}

	public void makeMove(Move m) {
		
		if(m.getType().equals(MoveType.SPIN))
		{
			if(m.getSpin().equals(SpinDirection.LEFT))
				turnLeft();
			else
				turnRight();
		}
		else
			moveForward();		
	}

	private boolean isUnexploredState()
	{
		return (current.getX() >= 0 &&
				current.getX() <= currentMap.getWidth() &&
				current.getY() >= 0 &&
				current.getY() <= currentMap.getHeight() &&
				currentMap.isCellNone(current.getX(), current.getY()));
	}
	
	private boolean isClearState()
	{
		return (current.getX() >= 0 &&
				current.getX() <= currentMap.getWidth() &&
				current.getY() >= 0 &&
				current.getY() <= currentMap.getHeight() &&
				currentMap.isCellClear(current.getX(), current.getY()));
	}
	
	
	public ArrayList<State> getUnexploredNeighbours()
	{
		ArrayList<State> neighList = new ArrayList<State>();
		ArrayList<State> closedList = new ArrayList<State>();
		ArrayList<State> unexpList = new ArrayList<State>();
		
		State actualCurrent = current;
		neighList.add(current);
		
		while(unexpList.isEmpty())
		{
			State bk = current;
			
			for(PossMoves pm : PossMoves.values())
			{
				switch(pm)
				{
				case LEFT:
					moveLeft();					
					break;
					
				case FORWARD:
					moveForward();
					break;
					
				case RIGHT:
					moveRight();
					break;
					
				case BACKWARD:
					moveBackward();
					break;
				}
				
				if(closedList.contains(current))
					continue;
				
				if(isClearState() && !neighList.contains(current))
					neighList.add(current);
				
				if(isUnexploredState() && !unexpList.contains(current))
					unexpList.add(current);
				
				current = bk;
			}
			
			if( unexpList.isEmpty())
			{
				Collections.sort(neighList);
				current = neighList.get(0);
				neighList.remove(current);
				closedList.add(current);
			}
		}		
		
		current = actualCurrent;		
		return unexpList;
	}

	public Map getCurrentMap() {
		return currentMap;
	}


}
