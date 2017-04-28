package it.unibo.execution.domain;

import it.unibo.execution.enums.ConditionalMoveType;
import it.unibo.planning.enums.ForwardMoveType;
import it.unibo.planning.enums.SpinDirection;

public class CMove{
	
	protected int id;
	protected ConditionalMoveType type;	
	protected String prologRep;

	
	public CMove()
	{
		this.id = -1;
		this.type = null;
		this.prologRep = "";
	}
	
	public CMove(int ID, ConditionalMoveType type)
	{
		this.id = ID;
		this.type = type;
		this.prologRep = "";
	}
	
	public CMove(int ID, ConditionalMoveType type, String rep)
	{
		this.id = ID;
		this.type = type;
		this.prologRep = rep;
	}

	public ConditionalMoveType getType() {
		return type;
	}

	@Override
	public String toString()
	{
		return id+"#"+prologRep;
	}

	public int getId() {
		return id;
	}


}
