package it.unibo.robot.utility;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import it.unibo.contactEvent.interfaces.IEventItem;
import it.unibo.domain.model.implementation.*;
import it.unibo.model.map.Map;
import it.unibo.planning.enums.Direction;
import it.unibo.planning.enums.ForwardMoveType;
import it.unibo.planning.enums.MoveType;
import it.unibo.planning.enums.SpinDirection;
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
	
	public void setCurrentState(int sx, int sy) {
		state.setX(sx);
		state.setY(sy);		
	}

	
	public State getForwardState() {
		return moveForwardSafe(state);
	}
	
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
			x -= 1;
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
		
		notifyMyPosition();
		
		if(!(this instanceof FileEngine))
			actor.moveForward();
		
		
		//System.out.println(currentMap.toString());
	}
	
	public void moveBackward() {
		
		Direction cd = state.getDirection();
		
		int x = state.getX();
		int y = state.getY();
		
		switch(cd)
		{
		case NORTH:
			y += 1;
			break;
			
		case EAST:
			x -= 1;
			break;
			
		case SOUTH:
			y -= 1;
			break;
			
		case WEST:
			x += 1;
			break;
			
		default:
			break;
		}
		
		state = new State(y, x, cd);
		state.addCost(1);
		
		notifyMyPosition();
		
		if(!(this instanceof FileEngine))
			actor.moveBackward();		
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
		
		notifyMyPosition();
		
		if(!(this instanceof FileEngine))
			actor.turnLeft();
	}
	
	public void turnRight() {
		Direction cd = state.getDirection();
		state.setDirection(rightMap.get(cd));
		state.addCost(1);
		
		notifyMyPosition();
		
		if(!(this instanceof FileEngine))
			actor.turnRight();
	}
	
	public void setNorthDirection() {
		
		int val = state.getDirection().getValue();
		
		if(val<=4)
		{
			for(int i=0; i<val; i++)
				turnLeft();
		}
		else
		{			
			for(int i=0; i<(8-val); i++)
				turnRight();
		}
		
	}
	
	/**
	 * Sends an event to the RobotGUIManager to update the position during the navigation
	 */
	public void notifyMyPosition(){
		
		String payload = "position("+state.getX() + "," + state.getY() + ")," +
				state.getDirection().toString().toLowerCase();
		
		actor.emit("show", "show(" + payload + ")");
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
			x -= 1;
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
	
	
	public boolean checkObjFront() {

		State frontState = moveForwardSafe(state);
		
		IEventItem ev = actor.senseEvent(1000, "obstaclefront", "continue");
		
		if(ev != null && 
				ev.getEventId().equals("obstaclefront") &&
				ev.getMsg().equals("obstaclefront"))
		{		
			return true;
		}
		else
		{		
			return false;
		}
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
		
	
// PATHFINDING ------------------------------------------------------
	
	
	public void noneAll() {
		map.noneAll();	
	}

	public void clearAll() {
		map.clearAll();	
	}
	
}
