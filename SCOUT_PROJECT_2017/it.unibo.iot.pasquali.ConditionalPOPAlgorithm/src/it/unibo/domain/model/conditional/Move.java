package it.unibo.domain.model.conditional;

import java.io.Serializable;

import it.unibo.domain.graph.State;
import it.unibo.domain.model.Fact;
import it.unibo.enums.ActionType;

public class Move extends ConditionalAction implements Serializable{

	/*
	 * move(From, To, PRElist, EFFlist, heuristic).
	 * 
	 * move(From, To, [at(From), clear(From, To)], [at(To), not at(From)], heuristic).
	 */
	
	
	private State from, to;	
	

	public Move(State from, State to, double heuristic) {
		super("move", heuristic);
		this.from = from;
		this.to = to;
		
		init();
	}
	
	public Move(State from, State to) {
		super("move");
		this.from = from;
		this.to = to;
		
		init();
	}
	
	private void init(){
		
		this.type = ActionType.MOVE;
		
		Fact f = new Fact("clear", this);
		f.addParam(from.toString());
		f.addParam(to.toString());
		this.addPre(f);		
		
		f = new Fact("at", this);		
		f.addParam(from.toString());
		this.addPre(f);
		
//		f = new Fact("connected", this);
//		f.addParam(from.toString());
//		f.addParam(to.toString());
//		this.addPre(f);
				
		f = new Fact("at", this);
		f.addParam(to.toString());
		this.addEffect(f);
		
		f = new Fact("not at", this);
		f.addParam(from.toString());
		this.addEffect(f);
	}

	public State getFrom() {
		return from;
	}

	public void setFrom(State from) {
		this.from = from;
	}

	public State getTo() {
		return to;
	}

	public void setTo(State to) {
		this.to = to;
	}

	
	@Override
	public boolean equals(Object o)
	{
		if(!(o instanceof Move))
			return false;
		
		Move m = (Move)o;
		
		return m.toString().equalsIgnoreCase(this.toString());		
	}
	
	@Override
	public String toString()
	{
		String ret = "move(";
		
		ret += from+","+to+",[";
		
		for(int i=0; i<pre.size(); i++)
		{
			ret+=pre.get(i).toString();
			if(i != (pre.size()-1))
				ret+=",";
		}
		
		ret += "],[";
		
		for(int i=0; i<eff.size(); i++)
		{
			ret+=eff.get(i).toString();
			if(i != (eff.size()-1))
				ret+=",";
		}
		
		ret += "],";
		ret += (heuristic+cost);
		ret += ").";
		
		return ret;
	}

	public boolean isOppositeTo(Move m) {
		
		return m.getFrom().equals(to) && m.getTo().equals(from);
	}
	
}
