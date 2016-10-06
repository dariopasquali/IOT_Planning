package it.unibo.planning.astar.interfaces;

import java.util.List;

import it.unibo.planning.astar.domain.Move;
import it.unibo.planning.astar.domain.State;
import it.unibo.planning.astar.enums.Direction;
import it.unibo.planning.astar.enums.SpinDirection;

public interface IEngine {

	public void loadParams();
	public boolean isValidState(State state);
	public boolean isValidStatePrologCheck(State state);
	public State makeMove(State start, Move move);
	public Direction makeSpin(Direction start, SpinDirection spin);
	public List<Move> getPossibleMoves(State state);
	public boolean isGoalState(State current);
	public double evaluateState(State state);
	public void setGoal(State goal);
	
}
