package it.unibo.planning;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import it.unibo.domain.model.Action;

public class PlanNode implements Comparable<PlanNode>, Serializable{
	
	public Action action;
	public List<Action> next;
	public List<Action> pre;
	
	public int id;
	
	public PlanNode(Action action) {
		super();
		
		this.action = action;
		next = new ArrayList<>();
		pre = new ArrayList<>();
		
		id = -1;
	}

	@Override
	public int compareTo(PlanNode p) {
		
		if(id > p.id)
			return 1;
		
		if(id == p.id)
			return 0;
		
		return -1;
	}
	
	@Override
	public boolean equals(Object o)
	{
		if(!(o instanceof PlanNode))
			return false;
		
		return ((PlanNode)o).action.equals(action);
	}

		
	
}
