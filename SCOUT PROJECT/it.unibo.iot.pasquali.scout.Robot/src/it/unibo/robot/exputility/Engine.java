package it.unibo.robot.exputility;

import java.awt.Point;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import it.unibo.domain.model.implmentation.ExplorationMap;
import it.unibo.domain.model.implmentation.ExplorationState;
import it.unibo.planning.astar.algo.Path;
import it.unibo.planning.astar.domain.Move;
import it.unibo.planning.enums.Direction;
import it.unibo.planning.enums.MoveType;

public class Engine {
	
	private ExplorationMap map;
	private ExplorationState state;
	
	private Set<Point> visited;
	
	private HashMap<Direction, Direction> leftMap,rightMap;
	
	public Engine(int startX, int startY, int mapW, int mapH) {
		
		state = new ExplorationState(startY, startX, Direction.NORTH);
		map = new ExplorationMap(mapH, mapW);
		
		visited = new HashSet<Point>();
		
		leftMap = new HashMap<Direction,Direction>();
		leftMap.put(Direction.NORTH, Direction.NORTH_WEST);
		leftMap.put(Direction.NORTH_WEST, Direction.WEST);
		leftMap.put(Direction.WEST, Direction.SOUTH_WEST);
		leftMap.put(Direction.SOUTH_WEST, Direction.SOUTH);
		leftMap.put(Direction.SOUTH, Direction.SOUTH_EAST);
		leftMap.put(Direction.SOUTH_EAST, Direction.EAST);
		leftMap.put(Direction.EAST, Direction.NORTH_EAST);
		leftMap.put(Direction.NORTH_EAST, Direction.NORTH);
		
		rightMap = new HashMap<Direction,Direction>();
		rightMap.put(Direction.NORTH, Direction.NORTH_EAST);
		rightMap.put(Direction.NORTH_EAST, Direction.EAST);
		rightMap.put(Direction.EAST, Direction.SOUTH_EAST);
		rightMap.put(Direction.SOUTH_EAST, Direction.SOUTH);
		rightMap.put(Direction.SOUTH, Direction.SOUTH_WEST);
		rightMap.put(Direction.SOUTH_WEST, Direction.WEST);
		rightMap.put(Direction.WEST, Direction.NORTH_WEST);
		rightMap.put(Direction.NORTH_WEST, Direction.NORTH);
	}
	
	
	// GETTERS ---------------------------------
	
	public ExplorationState getState() {
		return state;
	}
		
	public ExplorationMap getMap() {
		return map;
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
			moveForward();		
		}
	}	
	
	public void moveForward() {

		Direction cd = state.getDirection();
		
		int x = state.getX();
		int y = state.getY();
				
		switch(cd)
		{
		case NORTH:
			y -= 1;
			break;
			
		case NORTH_EAST:
			y -= 1;
			x += 1;
			break;
		
		case EAST:
			x += 1;
			break;
			
		case SOUTH_EAST:
			x += 1;
			y += 1;
			break;
			
		case SOUTH:
			y += 1;
			break;
			
		case SOUTH_WEST:
			y += 1;
			x += 1;
			break;
			
		case WEST:
			x -= 1;
			break;
		
		case NORTH_WEST:
			x -= 1;
			y -= 1;
			break;
			
		default:
			break;
		}
		
		state = new ExplorationState(y, x, cd);
		state.addCost(1);
		
		map.setCellClear(y, x);
		
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
		Direction cd = state.getDirection();
		state.setDirection(leftMap.get(cd));
		state.addCost(1);
	}
	
	public void turnRight() {
		Direction cd = state.getDirection();
		state.setDirection(rightMap.get(cd));
		state.addCost(1);
	}
	
	
// SAFE MOVES ------------ state doesn't change ------------------
	
	private ExplorationState moveForwardSafe(ExplorationState start) {

		Direction cd = start.getDirection();
		
		int x = start.getX();
		int y = start.getY();
		
		switch(cd)
		{
		
		case NORTH:
			y -= 1;
			break;
			
		case NORTH_EAST:
			y -= 1;
			x += 1;
			break;
		
		case EAST:
			x += 1;
			break;
			
		case SOUTH_EAST:
			x += 1;
			y += 1;
			break;
			
		case SOUTH:
			y += 1;
			break;
			
		case SOUTH_WEST:
			y += 1;
			x += 1;
			break;
			
		case WEST:
			x -= 1;
			break;
		
		case NORTH_WEST:
			x -= 1;
			y -= 1;
			break;
			
		default:
			break;
		}
		
		//if(!map.isValidCell(y, x)) //nosense se non ho i bordi
		//	return null;
		
		ExplorationState s = new ExplorationState(y, x, cd);
		s.addCost(start.getCost()+1);
		
		return s;
	}
	
	private ExplorationState moveDoubleLeftSafe(ExplorationState s)
	{
		return moveForwardSafe(turnDoubleLeftSafe(s));
	}
	
	private ExplorationState moveDoubleRightSafe(ExplorationState s)
	{
		return moveForwardSafe(turnDoubleRightSafe(s));
	}
	
	private ExplorationState turnDoubleLeftSafe(ExplorationState s)
	{
		return turnLeftSafe(turnLeftSafe(s));
	}
	
	private ExplorationState turnDoubleRightSafe(ExplorationState s)
	{
		return turnRightSafe(turnRightSafe(s));
	}
	
	private ExplorationState turnLeftSafe(ExplorationState s) {
		Direction cd = s.getDirection();
		ExplorationState ret = new ExplorationState(s.getY(), s.getX(), leftMap.get(cd));
		ret.addCost(s.getCost()+1);
		
		return ret;
	}
	
	private ExplorationState turnRightSafe(ExplorationState s) {
		Direction cd = s.getDirection();
		ExplorationState ret = new ExplorationState(s.getY(), s.getX(), rightMap.get(cd));
		ret.addCost(s.getCost()+1);
		
		return ret;
	}
	
	
// STATE CHECKS ----------------------------------------------------
	
	public boolean checkExploredLeft() {
		
		ExplorationState leftState = moveForwardSafe(turnDoubleLeftSafe(state));
		
		return isAlreadyVisited(leftState);
	}
	
	private boolean isUnexploredState(ExplorationState next) {
		return (next.getX() >= 0 &&
				next.getX() <= map.getXMax() &&
				next.getY() >= 0 &&
				next.getY() <= map.getYMax() &&
						map.isCellNone(next.getY(), next.getX()));
	}

	private boolean isClearState(ExplorationState next) {
		return (next.getX() >= 0 &&
				next.getX() <= map.getXMax() &&
				next.getY() >= 0 &&
				next.getY() <= map.getYMax() &&
						map.isCellClear(next.getY(), next.getX()));
	}
		
	public boolean isAlreadyVisited(ExplorationState s) {
		
		boolean visit = visited.contains(s.getCoordinates());		
		return visit;
	}
	
	public boolean isCurrentAlreadyVisited() {
		
		return isAlreadyVisited(state);
	}
	
	public void addCurrentToVisited() {
		visited.add(state.getCoordinates());
	}

	public void setCurrentClear() {
		map.setCellClear(state.getY(), state.getX());
	}

	
// PATHFINDING ------------------------------------------------------
	
	
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
		
		if(!state.getDirection().isTiled())
			turnRight();
	}		
	
}
