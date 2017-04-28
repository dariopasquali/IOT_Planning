package it.unibo.execution.domain;

import it.unibo.execution.enums.ConditionalMoveType;

public class CFail extends CMove{

	public CFail(int ID)
	{
		super(ID, ConditionalMoveType.FAIL);
		this.prologRep = "f";
	}	
}
