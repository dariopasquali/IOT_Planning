package it.unibo.explorer.interfaces;

import it.unibo.explorer.domain.State;
import it.unibo.planning.astar.enums.Direction;

public interface IExplorerEngine {
	
	public boolean CheckObjectForward(State state, Direction dir);
	public boolean CheckObjectForward();
	public boolean CheckObjectLeft(State state, Direction dir);
	public boolean CheckObjectLeft();
	
	public State moveForward(State state, Direction dir);
	public State moveRight(State state, Direction dir);
	public State moveLeft(State state, Direction dir);
	
	public Direction turnRight(Direction dir);
	public Direction turnLeft(Direction dir);

	
}
