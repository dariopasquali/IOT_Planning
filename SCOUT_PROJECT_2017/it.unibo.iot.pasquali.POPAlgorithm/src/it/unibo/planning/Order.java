package it.unibo.planning;

import it.unibo.domain.model.Action;

public class Order {
	
	private Action before;
	private Action after;
	
	public Order(Action before, Action after) {
		super();
		this.before = before;
		this.after = after;
	}

	public Action getBefore() {
		return before;
	}

	public Action getAfter() {
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
