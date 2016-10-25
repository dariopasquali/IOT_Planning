package it.unibo.planning.astar.algo;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import it.unibo.planning.astar.domain.Move;
import it.unibo.planning.astar.domain.State;


public class Path{

	private ArrayList<Point> states;
	private ArrayList<Move> moves;
	
	public Path()
	{
		states = new ArrayList<Point>();
		moves = new ArrayList<Move>();
	}
	
	public List<Point> getPoints() {
		return states;
	}

	public List<Move> getMoves() {
		return moves;
	}

	public void setPoints(List<Point> states) {
		this.states = (ArrayList<Point>)states;		
	}

	public void setMoves(List<Move> moves) {
		this.moves = (ArrayList<Move>)moves;
		
	}

	public void addState(Point state) {
		states.add(state);
		
	}

	public void addMove(Move move) {
		moves.add(move);
		
	}

}
