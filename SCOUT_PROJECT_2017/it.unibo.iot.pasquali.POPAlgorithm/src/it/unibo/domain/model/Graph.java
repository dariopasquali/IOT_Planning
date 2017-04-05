package it.unibo.domain.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import alice.tuprolog.Term;

public class Graph implements Serializable{
	
	private List<State> states;
	
	private List<Fact> connections;
	
	private List<Move> moves;
	
	public Graph()
	{
		this.states = new ArrayList<>();
		this.connections = new ArrayList<>();
		this.moves = new ArrayList<>();
	}
	
	public void addState(State s){
		if(!states.contains(s))
			states.add(s);
	}
	
	public void addConnection(String from, String to){
		
		Fact c = new Fact("connected", null);
		c.addParam(from);
		c.addParam(to);
		
		if(!connections.contains(c))
			connections.add(c);
	}
	
	public void addMove(Move m){
		if(!moves.contains(m))
			moves.add(m);
	}
	
	@Override
	public String toString()
	{
		String ret ="";
		
		for(State n : states)
		{
			ret += n.toString()+"\n";
		}
		
		for(Fact s : connections)
			ret += s.toString() +".\n";
		
		for(Move m : moves)
			ret += m.toString() + "\n";
		
		return ret;
	}

	public List<Fact> getInitialState() {
		return connections;
	}

	public List<Move> getMoves() {
		return moves;
	}
	
	public List<State> getStates(){
		return states;
	}

}
