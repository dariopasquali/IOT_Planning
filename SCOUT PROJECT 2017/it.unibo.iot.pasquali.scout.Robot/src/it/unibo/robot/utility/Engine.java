package it.unibo.robot.utility;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import it.unibo.contactEvent.interfaces.IEventItem;
import it.unibo.domain.model.implementation.*;
import it.unibo.domain.model.map.Map;
import it.unibo.planning.astar.algo.Path;
import it.unibo.planning.astar.domain.Move;
import it.unibo.planning.enums.Direction;
import it.unibo.planning.enums.ForwardMoveType;
import it.unibo.planning.enums.MoveType;
import it.unibo.planning.enums.SpinDirection;
import it.unibo.qactors.action.AsynchActionResult;
import it.unibo.qactors.action.IActorAction.ActionExecMode;
import it.unibo.robot.Robot;

public class Engine {
	
	protected final static int SLEEP_TIME = 300;
	
	protected Map map;
	protected State state;
	
	protected Set<Point> visited;
	
	protected Robot actor;
	
	protected boolean checkModifyState;
	
	protected HashMap<Direction, Direction> leftMap,rightMap;
	private HashMap<String, Move> moveMapping;
	
	public Engine(int startX, int startY, int mapW, int mapH, Robot actor, boolean checkModifyState) {
		
		state = new State(startY, startX, Direction.NORTH);
		map = new Map(mapH, mapW);
		this.actor = actor;
		this.checkModifyState = checkModifyState;
		
		init();
	}
	
	public Engine(int sx, int sy, Robot actor) {
		state = new State(sy, sx, Direction.NORTH);
		map = null;
		this.actor = actor;
		this.checkModifyState = false;
		
		init();
	}
	
	private void init(){
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
		
		this.moveMapping = new HashMap<String, Move>();
		moveMapping.put("forwardN", new Move(ForwardMoveType.TILED));
		moveMapping.put("forwardE", new Move(ForwardMoveType.TILED));
		moveMapping.put("forwardW", new Move(ForwardMoveType.TILED));
		moveMapping.put("forwardS", new Move(ForwardMoveType.TILED));
		moveMapping.put("forwardNE", new Move(ForwardMoveType.DIAGONAL));
		moveMapping.put("forwardNW", new Move(ForwardMoveType.DIAGONAL));
		moveMapping.put("forwardSE", new Move(ForwardMoveType.DIAGONAL));
		moveMapping.put("forwardSW", new Move(ForwardMoveType.DIAGONAL));
		moveMapping.put("leftN", new Move(SpinDirection.LEFT));
		moveMapping.put("leftNE", new Move(SpinDirection.LEFT));
		moveMapping.put("leftE", new Move(SpinDirection.LEFT));
		moveMapping.put("leftSE", new Move(SpinDirection.LEFT));
		moveMapping.put("leftS", new Move(SpinDirection.LEFT));
		moveMapping.put("leftSW", new Move(SpinDirection.LEFT));
		moveMapping.put("leftW", new Move(SpinDirection.LEFT));
		moveMapping.put("leftNW", new Move(SpinDirection.LEFT));
		moveMapping.put("rightN", new Move(SpinDirection.RIGHT));
		moveMapping.put("rightNE", new Move(SpinDirection.RIGHT));
		moveMapping.put("rightE", new Move(SpinDirection.RIGHT));
		moveMapping.put("rightSE", new Move(SpinDirection.RIGHT));
		moveMapping.put("rightS", new Move(SpinDirection.RIGHT));
		moveMapping.put("rightSW", new Move(SpinDirection.RIGHT));
		moveMapping.put("rightW", new Move(SpinDirection.RIGHT));
		moveMapping.put("rightNW", new Move(SpinDirection.RIGHT));
	}
	
	
	// GETTERS ---------------------------------
	
	public State getState() {
		return state;
	}
		
	public Map getMap() {
		return map;
	}
		
	// MOVES -----------------------------------
		
	public void makeMove(String move)
	{
		makeMove(moveMapping.get(move+state.getDirection().toString()));
	}
	
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
		
		state = new State(y, x, cd);
		state.addCost(1);
		
		map.setCellClear(y, x);
		
		if(!(this instanceof FileEngine))
			actor.moveForward();
		
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
		
		if(!(this instanceof FileEngine))
			actor.turnLeft();
	}
	
	public void turnRight() {
		Direction cd = state.getDirection();
		state.setDirection(rightMap.get(cd));
		state.addCost(1);
		
		if(!(this instanceof FileEngine))
			actor.turnRight();
	}
	
	
// SAFE MOVES ------------ state doesn't change ------------------
	
	protected State moveForwardSafe(State start) {

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
		
		State s = new State(y, x, cd);
		s.addCost(start.getCost()+1);
		
		return s;
	}
	
	protected State moveBackwardSafe(State start)
	{		
		return moveForwardSafe(turnDoubleLeftSafe(turnDoubleLeftSafe(start)));
	}
	
	protected State moveDoubleLeftSafe(State s)
	{
		return moveForwardSafe(turnDoubleLeftSafe(s));
	}
	
	protected State moveDoubleRightSafe(State s)
	{
		return moveForwardSafe(turnDoubleRightSafe(s));
	}
	
	protected State turnDoubleLeftSafe(State s)
	{
		return turnLeftSafe(turnLeftSafe(s));
	}
	
	protected State turnDoubleRightSafe(State s)
	{
		return turnRightSafe(turnRightSafe(s));
	}
	
	protected State turnLeftSafe(State s) {
		Direction cd = s.getDirection();
		State ret = new State(s.getY(), s.getX(), leftMap.get(cd));
		ret.addCost(s.getCost()+1);
		
		return ret;
	}
	
	protected State turnRightSafe(State s) {
		Direction cd = s.getDirection();
		State ret = new State(s.getY(), s.getX(), rightMap.get(cd));
		ret.addCost(s.getCost()+1);
		
		return ret;
	}
	
	
// STATE CHECKS ----------------------------------------------------
	
	public State checkAndUpdate(String direction, String upState)
	{
		State next = null;
		
		if(direction.equals("front"))
			next = moveForwardSafe(state);			
		else
			next = moveForwardSafe(turnDoubleLeftSafe(state));
		
		if(upState.equals("clear"))
		{
			map.setCellClear(next.getY(), next.getX());
		}
		else
		{
			map.setCellObj(next.getY(), next.getX());
		}
		
		return next;
	}
	
	protected void updateModelAndNotify(State position, String state)
	{
		if(state.equals("clear"))
			map.setCellClear(position.getY(), position.getX());
		else
			map.setCellObj(position.getY(), position.getX());
		
		//expdata : expdata( POS , STATE )	
		if(checkModifyState)
		{
			actor.emit("expdata", "expdata(position("+ position.getX() +
					"," + position.getY()+"), "+state+")");
			try {
				Thread.sleep(SLEEP_TIME);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public boolean checkObjFront() {

		State frontState = moveForwardSafe(state);
		
		IEventItem ev = actor.senseEvent(1000, "obstaclefront", "continue");
		
		if(ev != null && 
				ev.getEventId().equals("obstaclefront") &&
				ev.getMsg().equals("obstaclefront"))
		{
			
			updateModelAndNotify(frontState, "object");			
			return true;
		}
		else
		{
			updateModelAndNotify(frontState, "clear");			
			return false;
		}
	}

	public boolean checkObjLeft() {
		
		State leftState = moveForwardSafe(turnDoubleLeftSafe(state));

		IEventItem ev = actor.senseEvent(1000, "obstacleleft", "continue");
		
		if(ev != null && 
				ev.getEventId().equals("obstacleleft") &&
				ev.getMsg().equals("obstacleleft"))
		{
			
			updateModelAndNotify(leftState, "object");			
			return true;
		}
		else
		{
			updateModelAndNotify(leftState, "clear");			
			return false;
		}	
	}
	
	public boolean checkExploredLeft() {
		
		State leftState = moveForwardSafe(turnDoubleLeftSafe(state));
		
		return isAlreadyVisited(leftState);
	}
	
	protected boolean isUnexploredState(State next) {
		return (next.getX() >= 0 &&
				next.getX() <= map.getXmax() &&
				next.getY() >= 0 &&
				next.getY() <= map.getYmax() &&
						map.isCellNone(next.getY(), next.getX()));
	}

	protected boolean isClearState(State next) {
		return (next.getX() >= 0 &&
				next.getX() <= map.getXmax() &&
				next.getY() >= 0 &&
				next.getY() <= map.getYmax() &&
						map.isCellClear(next.getY(), next.getX()));
	}
		
	public boolean isAlreadyVisited(State s) {
		
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
		
		if(checkModifyState)
		{
			actor.emit("expdata", "expdata(position("+ state.getX() +		
					"," + state.getY()+"), clear)");
			
			try {
				Thread.sleep(SLEEP_TIME);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
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
	
	
	public ArrayList<State> getUnexploredNeighbors()
	{
		//search only top left and right neighbor 
		
		ArrayList<State> neighList = new ArrayList<State>();
		ArrayList<State> closedList = new ArrayList<State>();
		ArrayList<State> unexpList = new ArrayList<State>();
		
		State actual = state;
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
	
}
