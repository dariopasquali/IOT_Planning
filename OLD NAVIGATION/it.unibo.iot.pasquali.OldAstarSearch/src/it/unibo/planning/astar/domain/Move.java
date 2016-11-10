package it.unibo.planning.astar.domain;

import it.unibo.planning.domain.*;
import it.unibo.planning.enums.*;

public class Move extends AbstractMove{
	
	
	
	public Move()
	{
		super();
	}	
	public Move(ForwardMoveType forward)
	{
		super(forward);
	}
	
	public Move(SpinDirection spinDir)
	{
		super(spinDir);
	}
	
	@Override
	public String toString()
	{
		return super.toString();
	}

	
}
