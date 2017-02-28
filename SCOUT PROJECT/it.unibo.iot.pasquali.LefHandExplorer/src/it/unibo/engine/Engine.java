package it.unibo.engine;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import it.unibo.domain.Map;
import it.unibo.domain.State;
import it.unibo.gui.MapViewerPanel;
import it.unibo.planning.astar.algo.Path;
import it.unibo.planning.astar.domain.Move;
import it.unibo.planning.enums.Direction;
import it.unibo.planning.enums.MoveType;
import it.unibo.planning.enums.SpinDirection;

public class Engine {
	
	private State current;
	private Map currentMap;
	private Map worldMap;
	
	private MapViewerPanel viewer;
	
	private Set<Point> visited;
	
	private HashMap<Direction, Direction> leftMap,rightMap;
	

	public Engine(Point start, Map map, MapViewerPanel viewer) {
	
		current = new State(start.y, start.x, Direction.NORTH);
		
		this.worldMap = map;
		this.viewer = viewer;
		this.currentMap = new Map(worldMap.getYMax(), worldMap.getXMax());
		
		visited = new HashSet<Point>();
		
		leftMap = new HashMap<Direction,Direction>();
		leftMap.put(Direction.NORTH, Direction.NORTHWEST);
		leftMap.put(Direction.NORTHWEST, Direction.WEST);
		leftMap.put(Direction.WEST, Direction.SOUTHWEST);
		leftMap.put(Direction.SOUTHWEST, Direction.SOUTH);
		leftMap.put(Direction.SOUTH, Direction.SOUTHEAST);
		leftMap.put(Direction.SOUTHEAST, Direction.EAST);
		leftMap.put(Direction.EAST, Direction.NORTHEAST);
		leftMap.put(Direction.NORTHEAST, Direction.NORTH);
		
		rightMap = new HashMap<Direction,Direction>();
		rightMap.put(Direction.NORTH, Direction.NORTHEAST);
		rightMap.put(Direction.NORTHEAST, Direction.EAST);
		rightMap.put(Direction.EAST, Direction.SOUTHEAST);
		rightMap.put(Direction.SOUTHEAST, Direction.SOUTH);
		rightMap.put(Direction.SOUTH, Direction.SOUTHWEST);
		rightMap.put(Direction.SOUTHWEST, Direction.WEST);
		rightMap.put(Direction.WEST, Direction.NORTHWEST);
		rightMap.put(Direction.NORTHWEST, Direction.NORTH);
	}
	
	// GETTERS ---------------------------------
	
	public State getCurrent() {
		return current;
	}
	
	public Map getCurrentMap() {
		return currentMap;
	}
	
	
	// MOVES -----------------------------------
	
	public void makeMove(Move m) {
		
		if(m.getType().equals(MoveType.SPIN))
		{
			switch(m.getSpin())
			{
			case LEFT:
				turnLeft();
				break;
			case RIGHT:
				turnRight();
				break;
			default:
				break;
			}
		}
		else
		{
			if(!checkObjFront())
				moveForward();		
		}
	}

	public void moveForward() {

		Direction cd = current.getDirection();
		
		int x = current.getX();
		int y = current.getY();
				
		switch(cd)
		{
		case NORTH:
			y -= 1;
			break;
			
		case NORTHEAST:
			y -= 1;
			x += 1;
			break;
		
		case EAST:
			x += 1;
			break;
			
		case SOUTHEAST:
			x += 1;
			y += 1;
			break;
			
		case SOUTH:
			y += 1;
			break;
			
		case SOUTHWEST:
			y += 1;
			x += 1;
			break;
			
		case WEST:
			x -= 1;
			break;
		
		case NORTHWEST:
			x -= 1;
			y -= 1;
			break;
			
		default:
			break;
		}
		
		viewer.setCellClear(current.getY(), current.getX());
		
		current = new State(y, x, cd);
		current.addCost(1);
		
		currentMap.setCellClear(y, x);
		viewer.setCellPos(y, x);
		
		//System.out.println(currentMap.toString());
	}
	
	public void moveBackward()
	{
		turnLeft();
		turnLeft();
		moveForward();
	}
		
	public void turnDoubleLeft()
	{
		turnLeft();
		turnLeft();
	}
	
	public void turnDoubleRight()
	{
		turnRight();
		turnRight();
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

	public void moveDoubleLeft()
	{
		turnDoubleLeft();
		moveForward();
	}

	public void moveDobuleRight()
	{
		turnDoubleRight();
		moveForward();
	}
	
	public void moveLeft()
	{
		turnLeft();
		moveForward();
	}
	
	public void moveRight()
	{
		turnRight();
		moveForward();
	}
	
	
	// SAFE MOVES ------------------------------
	
	private State moveForwardSafe(State start) {

		Direction cd = start.getDirection();
		
		int x = start.getX();
		int y = start.getY();
		
		switch(cd)
		{
		
		case NORTH:
			y -= 1;
			break;
			
		case NORTHEAST:
			y -= 1;
			x += 1;
			break;
		
		case EAST:
			x += 1;
			break;
			
		case SOUTHEAST:
			x += 1;
			y += 1;
			break;
			
		case SOUTH:
			y += 1;
			break;
			
		case SOUTHWEST:
			y += 1;
			x += 1;
			break;
			
		case WEST:
			x -= 1;
			break;
		
		case NORTHWEST:
			x -= 1;
			y -= 1;
			break;
			
		default:
			break;
		}
		
		if(!worldMap.isValidCell(y, x))
			return null;
		
		State s = new State(y, x, cd);
		s.addCost(start.getCost()+1);
		
		return s;
	}
	
	private State moveDoubleLeftSafe(State s)
	{
		return moveForwardSafe(turnDoubleLeftSafe(s));
	}
	
	private State moveDoubleRightSafe(State s)
	{
		return moveForwardSafe(turnDoubleRightSafe(s));
	}
	
	private State moveLeftSafe(State start)
	{
		return moveForwardSafe(turnLeftSafe(start));
	}
	
	private State moveRightSafe(State start)
	{
		return moveForwardSafe(turnRightSafe(start));
	}


	private State moveBackwardSafe(State start)
	{		
		return moveForwardSafe(turnDoubleLeftSafe(turnDoubleLeftSafe(start)));
	}

	
	private State turnDoubleLeftSafe(State s)
	{
		return turnLeftSafe(turnLeftSafe(s));
	}
	
	private State turnDoubleRightSafe(State s)
	{
		return turnRightSafe(turnRightSafe(s));
	}
	
	private State turnLeftSafe(State s) {
		Direction cd = s.getDirection();
		State ret = new State(s.getY(), s.getX(), leftMap.get(cd));
		ret.addCost(s.getCost()+1);
		
		return ret;
	}
	
	private State turnRightSafe(State s) {
		Direction cd = s.getDirection();
		State ret = new State(s.getY(), s.getX(), rightMap.get(cd));
		ret.addCost(s.getCost()+1);
		
		return ret;
	}
	
	
	// SONAR LIKE CHECKS ---------------------------------
	
	private boolean checkObject(State state)
	{
		boolean obj = worldMap.isCellObj(state.getY(), state.getX());
		
		if(obj)
		{
			currentMap.setCellObj(state.getY(), state.getX());
			viewer.setCellObj(state.getY(), state.getX());
		}
		else
		{
			currentMap.setCellClear(state.getY(), state.getX());
			viewer.setCellClear(state.getY(), state.getX());
		}
		
		return obj;
	}
	
	public boolean checkObjFront() {

		State frontState = moveForwardSafe(current);
		
		if(frontState == null)
			return true;

		boolean obj = checkObject(frontState);
		
		//System.out.println(currentMap.toString());
			
		return obj;
		
	}

	public boolean checkObjLeft() {		
		
		State leftState = moveForwardSafe(turnDoubleLeftSafe(current));

		if(leftState == null)
			return true;
		
		boolean obj = checkObject(leftState);
		
		//System.out.println(currentMap.toString());	
			
		return obj;
	}	
		
	public boolean checkExploredLeft() {
		
		State leftState = moveForwardSafe(turnDoubleLeftSafe(current));
		
		return isAlreadyVisited(leftState);
	}
	
	
	// STATE MANAGEMENT ------------------------------
	
	private boolean isUnexploredState()
	{
		return (current.getX() >= 0 &&
				current.getX() <= currentMap.getXMax() &&
				current.getY() >= 0 &&
				current.getY() <= currentMap.getYMax() &&
				currentMap.isCellNone(current.getY(), current.getX()));
	}
	
	private boolean isClearState()
	{
		return (current.getX() >= 0 &&
				current.getX() <= currentMap.getXMax() &&
				current.getY() >= 0 &&
				current.getY() <= currentMap.getYMax() &&
				currentMap.isCellClear(current.getY(), current.getX()));
	}
	
	private boolean isUnexploredState(State next) {
		return (next.getX() >= 0 &&
				next.getX() <= currentMap.getXMax() &&
				next.getY() >= 0 &&
				next.getY() <= currentMap.getYMax() &&
				currentMap.isCellNone(next.getY(), next.getX()));
	}

	private boolean isClearState(State next) {
		return (next.getX() >= 0 &&
				next.getX() <= currentMap.getXMax() &&
				next.getY() >= 0 &&
				next.getY() <= currentMap.getYMax() &&
				currentMap.isCellClear(next.getY(), next.getX()));
	}
		
	public boolean isAlreadyVisited(State s) {
		
		boolean visit = visited.contains(s.getCoordinates());		
		return visit;
	}
	
	public boolean isCurrentAlreadyVisited() {
		
		return isAlreadyVisited(current);
	}
	
	public void addCurrentToVisited() {
		visited.add(current.getCoordinates());
	}

	public void setCurrentClear() {
		currentMap.setCellClear(current.getY(), current.getX());
		viewer.setCellClear(current.getY(), current.getX());
	}

	
	// PATHFINDING -----------------------------------
		
	public void travel(Path path) {

		/*
		 * I choose to use tiled moves during exploration
		 * in order to preserve a more defined orientation
		 * and because i don't need to minimize the length of the path
		 * 
		 * During the navigation to the nearest not explored cell i choose
		 * to use tiled and also diagonal to minimize the travel time
		 * 
		 * when the travel is over maybe the Robot non be tiled oriented
		 * so I choose to only turn right because the algorithm is a left wall follower
		 * and it's convenient
		 */
	
		for(Move m : path.getMoves())
		{
			makeMove(m);
		}
		
		if(!current.getDirection().isTiled())
			turnRight();
	}	
	


	public ArrayList<State> getUnexploredNeighbors()
	{
		//search only top left and right neighbor 
		
		ArrayList<State> neighList = new ArrayList<State>();
		ArrayList<State> closedList = new ArrayList<State>();
		ArrayList<State> unexpList = new ArrayList<State>();
		
		State actual = current;
		neighList.add(actual);
		
		while(unexpList.isEmpty() && !neighList.isEmpty())
		{
			//Collections.sort(neighList);
			actual = neighList.get(0);
			neighList.remove(actual);  
			closedList.add(actual);			
			
			for(int i=0; i<4; i++)
			{
				State next = null;
				switch(i)
				{
				case 0:
					next = moveDoubleRightSafe(actual);					
					break;
					
				case 1:
					next = moveForwardSafe(actual);
					break;
					
				case 2:
					next = moveDoubleLeftSafe(actual);
					break;
					
				case 3:
					next = moveBackwardSafe(actual);
					break;
				default:
					break;
				}
				
				if(next!=null && closedList.contains(next))
					continue;
				
				if(next!=null && isClearState(next) && !neighList.contains(next))
					neighList.add(next);
				
				if(next!=null && isUnexploredState(next) && !unexpList.contains(next))
					unexpList.add(next);
			}			
		}		
	
		return unexpList;
	}

	
	// UTILITIES --------------------------------------

	

	@Override
	public String toString()
	{
		//PRINT the current state, useful for debug!!
		return current.toString();
	}

	


}
