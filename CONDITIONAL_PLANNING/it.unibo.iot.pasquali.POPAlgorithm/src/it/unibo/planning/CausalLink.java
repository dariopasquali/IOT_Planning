package it.unibo.planning;

import it.unibo.domain.model.Action;
import it.unibo.domain.model.Fact;

public class CausalLink {

	private Action from;
	private Action to;
	private Fact condition;
	
	public CausalLink(Action from, Action to, Fact condition) {
		super();
		this.from = from;
		this.to = to;
		this.condition = condition;
	}

	public Action getFrom() {
		return from;
	}

	public Action getTo() {
		return to;
	}

	public Fact getCondition() {
		return condition;
	}
	
	@Override
	public String toString(){
		return "link("+from+","+to+","+condition+")";
	}
	
	@Override
	public boolean equals(Object o)
	{
		if(!(o instanceof CausalLink))
			return false;
		
		CausalLink l = (CausalLink)o;
		
		return l.getFrom().equals(from) && l.getTo().equals(to) && l.getCondition().equals(condition);
				
	}
	
}
