package it.unibo.domain.model;

import java.io.Serializable;

public class Move extends Action implements Serializable{
	
	private State from, to;	
	

	public Move(State from, State to, double cost) {
		super("move", cost);
		this.from = from;
		this.to = to;
	}
	
	public Move(State from, State to) {
		super("move");
		this.from = from;
		this.to = to;
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

