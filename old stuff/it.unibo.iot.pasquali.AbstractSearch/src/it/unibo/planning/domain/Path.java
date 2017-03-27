package it.unibo.planning.domain;

import java.util.ArrayList;
import java.util.List;

public class Path {

	private ArrayList<AbstractState> states;
	private ArrayList<AbstractMove> moves;
	
	public Path()
	{
		states = new ArrayList<AbstractState>();
		moves = new ArrayList<AbstractMove>();
	}
	
	public List<AbstractState> getStates() {
		return states;
	}

	public List<AbstractMove> getMoves() {
		return moves;
	}

	public void setStates(List<AbstractState> states) {
		this.states = (ArrayList<AbstractState>)states;		
	}

	public void setMoves(List<AbstractMove> moves) {
		this.moves = (ArrayList<AbstractMove>)moves;
		
	}

	public void addState(AbstractState state) {
		states.add(state);
		
	}

	public void addMove(AbstractMove move) {
		moves.add(move);
		
	}
}
