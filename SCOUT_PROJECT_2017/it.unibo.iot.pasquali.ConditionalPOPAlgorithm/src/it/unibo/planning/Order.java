package it.unibo.planning;

import java.io.Serializable;

import it.unibo.domain.model.conditional.ConditionalAction;

public class Order implements Serializable{
	
	private ConditionalAction before;
	private ConditionalAction after;
	
	public Order(ConditionalAction before, ConditionalAction after) {
		super();
		this.before = before;
		this.after = after;
	}

	public ConditionalAction getBefore() {
		return before;
	}

	public ConditionalAction getAfter() {
		return after;
	}
	
	@Override
	public String toString(){
		return "order("+before+","+after+")";
	}
	
	@Override
	public boolean equals(Object o)
	{
		if(!(o instanceof Order))
			return false;
		
		Order l = (Order)o;
		
		return l.getBefore().equals(before) && l.getAfter().equals(after);				
	}
		
	

}
