package it.unibo.execution.domain;

import it.unibo.execution.enums.ConditionalMoveType;

public class CStep extends CMove{

	public CStep(int ID)
	{
		super(ID, ConditionalMoveType.STEP);
		this.prologRep = "t";
	}
	
	public CStep(int ID, String prologRep)
	{
		super(ID, ConditionalMoveType.STEP);
		this.prologRep = prologRep;
	}
		
	public CMove getReverse()
	{
		return new CStep(this.id, "b");
	}
}
