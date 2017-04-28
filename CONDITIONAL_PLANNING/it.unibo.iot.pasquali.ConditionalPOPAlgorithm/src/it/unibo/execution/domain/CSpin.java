package it.unibo.execution.domain;

import it.unibo.execution.enums.ConditionalMoveType;
import it.unibo.planning.enums.SpinDirection;

public class CSpin extends CMove{

	public CSpin(int ID, SpinDirection dir)
	{
		super(ID, ConditionalMoveType.SPIN);
		this.prologRep = dir.toString();
	}	
	
	public CMove getReverse()
	{
		if(this.prologRep.contains("r"))
			return new CSpin(this.id, SpinDirection.LEFT);
		else
			return new CSpin(this.id, SpinDirection.RIGHT);
	}
}
