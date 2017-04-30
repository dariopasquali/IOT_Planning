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

	public static CMove fromStringToCMove(String move)
	{
		String[] s = move.split("#");		
		int ID = Integer.parseInt(s[0]);
		
		if(s[1].contains("f"))
			return new CFail(ID);
		
		if(s[1].contains("?"))
		{
			String check = s[1].replace("?", "");
			String[] ids = check.split(":");
			
			return new CSense(ID, Integer.parseInt(ids[0]), Integer.parseInt(ids[1]));
		}
		
		if(s[1].contains("r"))
			return new CSpin(ID, SpinDirection.RIGHT);
		
		if(s[1].contains("l"))
			return new CSpin(ID, SpinDirection.LEFT);
		
		if(s[1].contains("t"))
			return new CStep(ID);
		
		if(s[1].contains("h"))
			return new CStop(ID);
		
		return null;
		
		
		
	}
}
