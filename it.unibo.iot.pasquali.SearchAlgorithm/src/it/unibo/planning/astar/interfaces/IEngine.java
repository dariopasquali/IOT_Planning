package it.unibo.planning.astar.interfaces;

import java.util.List;

import it.unibo.planning.astar.domain.State;
import it.unibo.planning.astar.enums.PositionMove;

public interface IEngine {

	public boolean isValidState(State state);
	public State makeMove(State start, PositionMove dir);
	public List<State> getValidSuccessors(State state);
	public boolean isGoalState(State current);
	public double evaluateState(State state);
	public void setGoal(State goal);
	public void setIntMap(Integer[][] map, int xmax, int ymax);
	
}
