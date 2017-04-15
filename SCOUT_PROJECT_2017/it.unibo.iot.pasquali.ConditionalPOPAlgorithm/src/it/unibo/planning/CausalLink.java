package it.unibo.planning;

import java.io.Serializable;

import it.unibo.domain.model.Fact;
import it.unibo.domain.model.conditional.ConditionalAction;

public class CausalLink implements Serializable{

	private ConditionalAction from;
	private ConditionalAction to;
	private Fact condition;
	
	public CausalLink(ConditionalAction from, ConditionalAction to, Fact condition) {
		super();
		this.from = from;
		this.to = to;
		this.condition = condition;
	}

	public ConditionalAction getFrom() {
		return from;
	}

	public ConditionalAction getTo() {
		return to;
	}

	public Fact getCondition() {
		return condition;
	}
	
	@Override
	public String toString(){
		return "causal<"+from+","+to+","+condition+">";
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
