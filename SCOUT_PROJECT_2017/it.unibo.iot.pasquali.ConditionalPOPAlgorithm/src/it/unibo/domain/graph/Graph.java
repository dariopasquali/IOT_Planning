package it.unibo.domain.graph;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import it.unibo.domain.model.Fact;
import it.unibo.domain.model.conditional.Check;
import it.unibo.domain.model.conditional.ConditionalAction;
import it.unibo.domain.model.conditional.Move;

public class Graph implements Serializable{
	
	private List<State> states;
	
	private List<Fact> connections;
	
	//private List<Move> moves;	
	//private List<Check> checks;
	
	private List<ConditionalAction> actions;
	
	public Graph()
	{
		this.states = new ArrayList<>();
		this.connections = new ArrayList<>();
//		this.moves = new ArrayList<>();
//		this.checks = new ArrayList<Check>();
		
		this.actions = new ArrayList<ConditionalAction>();
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
//		if(!actions.contains(m))
			actions.add(m);
	}
	
	public void addCheck(Check c){
//		if(!actions.contains(c))
			actions.add(c);
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
		
//		for(Move m : moves)
//			ret += m.toString() + "\n";

		for(ConditionalAction c : actions)
			ret += c.toString() + "\n";
		
		return ret;
	}

	public List<Fact> getInitialState() {
		return connections;
	}

	public List<ConditionalAction> getActions() {
		return actions;
	}
	
	public List<State> getStates(){
		return states;
	}
	
//	public List<Check> getChecks(){
//		return checks;
//	}

}
