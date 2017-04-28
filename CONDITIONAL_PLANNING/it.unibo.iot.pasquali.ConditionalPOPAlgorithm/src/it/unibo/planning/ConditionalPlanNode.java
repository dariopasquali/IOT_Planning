package it.unibo.planning;

import it.unibo.domain.model.conditional.ConditionalAction;

public class ConditionalPlanNode {
	
	private ConditionalAction action;

	private int id;
	
	private int branchClearID, branchNotClearID;
	
	public ConditionalPlanNode(ConditionalAction action, int id)
	{
		this.action = action;
		this.id = id;
		this.branchClearID = -1;
		this.branchNotClearID = -1;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getBranchClearID() {
		return branchClearID;
	}

	public void setBranchClearID(int branchClearID) {
		this.branchClearID = branchClearID;
	}

	public int getBranchNotClearID() {
		return branchNotClearID;
	}

	public void setBranchNotClearID(int branchNotClearID) {
		this.branchNotClearID = branchNotClearID;
	}

	public ConditionalAction getAction() {
		return action;
	}
	
	@Override
	public String toString(){
		
		String s = id + " --- " + action.getShortName();
		
		if(branchClearID >= 0)
		{
			s += " ? "+branchClearID+" : "+branchNotClearID;
		}
		
		s += " ;";
		
		return s;
	}
}
