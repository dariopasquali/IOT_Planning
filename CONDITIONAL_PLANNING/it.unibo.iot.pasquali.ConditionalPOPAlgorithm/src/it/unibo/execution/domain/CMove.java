package it.unibo.execution.domain;

import it.unibo.execution.enums.ConditionalMoveType;
import it.unibo.planning.enums.ForwardMoveType;
import it.unibo.planning.enums.SpinDirection;

public class CMove{
	
	protected int id;
	protected ConditionalMoveType type;	
	protected String prologRep;
	protected String guiRep;
	
	public static final String SPLITTER = "m";

	
	public CMove()
	{
		this.id = -1;
		this.type = null;
		this.prologRep = "";
		this.guiRep = "";
	}
	
	public CMove(int ID, ConditionalMoveType type)
	{
		this.id = ID;
		this.type = type;
		this.prologRep = "";
		this.guiRep = "";
	}
	
	public CMove(int ID, ConditionalMoveType type, String rep, String guiRep)
	{
		this.id = ID;
		this.type = type;
		this.prologRep = rep;
		this.guiRep = guiRep;
	}

	public ConditionalMoveType getType() {
		return type;
	}

	@Override
	public String toString()
	{
		return id+"#"+guiRep;
	}
	
	public String getPrologRep(){
		return prologRep+SPLITTER+id;
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
	
	public static CMove fromPrologToCMove(String move)
	{
		String[] s = move.split(SPLITTER);		
		int ID = Integer.parseInt(s[1]);
		
		if(s[0].contains("f"))
			return new CFail(ID);
		
		if(s[0].contains(CSense.CHECK_SPLITTER))
		{
			String check = s[0].replace(CSense.CHECK_SPLITTER, "");
			String[] ids = check.split(CSense.OR_SPLITTER);
			
			return new CSense(ID, Integer.parseInt(ids[0]), Integer.parseInt(ids[1]));
		}
		
		if(s[0].contains("r"))
			return new CSpin(ID, SpinDirection.RIGHT);
		
		if(s[0].contains("l"))
			return new CSpin(ID, SpinDirection.LEFT);
		
		if(s[0].contains("t"))
			return new CStep(ID);
		
		if(s[0].contains("h"))
			return new CStop(ID);
		
		return null;
	}
}
