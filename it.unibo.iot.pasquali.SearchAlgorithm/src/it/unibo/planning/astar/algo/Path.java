package it.unibo.planning.astar.algo;

import java.util.ArrayList;
import java.util.List;

import it.unibo.planning.astar.domain.Move;
import it.unibo.planning.astar.domain.State;


public class Path{

	private ArrayList<State> states;
	private ArrayList<Move> moves;
	
	public Path()
	{
		states = new ArrayList<State>();
		moves = new ArrayList<Move>();
	}
	
	public List<State> getStates() {
		return states;
	}

	public List<Move> getMoves() {
		return moves;
	}

	public void setStates(List<State> states) {
		this.states = (ArrayList<State>)states;		
	}

	public void setMoves(List<Move> moves) {
		this.moves = (ArrayList<Move>)moves;
		
	}

	public void addState(State state) {
		states.add(state);
		
	}

	public void addMove(Move move) {
		moves.add(move);
		
	}

}
