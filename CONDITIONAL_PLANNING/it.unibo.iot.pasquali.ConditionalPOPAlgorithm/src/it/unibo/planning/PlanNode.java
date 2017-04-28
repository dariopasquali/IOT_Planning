package it.unibo.planning;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import it.unibo.domain.model.Action;

public class PlanNode implements Comparable<PlanNode>, Serializable{
	
	public Action action;
	public List<Action> next;
	public List<Action> pre;
	
	public PlanNode(Action action) {
		super();
		
		this.action = action;
		next = new ArrayList<>();
		pre = new ArrayList<>();
	}

//	@Override
//	public int compareTo(PlanNode p) {
//		
//		if(id > p.id)
//			return 1;
//		
//		if(id == p.id)
//			return 0;
//		
//		return -1;
//	}
	
	@Override
	public boolean equals(Object o)
	{
		if(!(o instanceof PlanNode))
			return false;
		
		return ((PlanNode)o).action.equals(action);
	}

	@Override
	public int compareTo(PlanNode o) {
		// TODO Auto-generated method stub
		return 0;
	}

		
	@Override
	public String toString(){
		return action.toString();
	}
	
}
