package it.unibo.execution.domain;

import it.unibo.execution.enums.ConditionalMoveType;

public class CSense extends CMove{

	private int branchID1 =-1, branchID2 =-1;
	
	public CSense(int ID, int branchID1, int branchID2)
	{
		super(ID, ConditionalMoveType.SENSE);
		
		this.branchID1 = branchID1;
		this.branchID2 = branchID2;		
		this.prologRep = "?"+branchID1+":"+branchID2;
	}	
	
	public void setBranches(int branch1, int branch2)
	{
		this.branchID1 = branch1;
		this.branchID2 = branch2;
		this.prologRep = "?"+branchID1+":"+branchID2;
	}
	
	public int getBranchIDClear()
	{
		return branchID1;
	}
	
	public int getBranchIDNotClear()
	{
		return branchID2;
	}
	
}
