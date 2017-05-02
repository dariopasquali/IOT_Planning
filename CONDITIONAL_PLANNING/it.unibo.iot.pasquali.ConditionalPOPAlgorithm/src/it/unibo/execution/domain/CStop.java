package it.unibo.execution.domain;

import it.unibo.execution.enums.ConditionalMoveType;

public class CStop extends CMove{
	
	public CStop(int ID)
	{
		super(ID, ConditionalMoveType.STOP);
		this.prologRep = "h";
		this.guiRep = "h";
	}

}
