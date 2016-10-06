package it.unibo.planning.astar.domain;

import it.unibo.planning.astar.enums.ForwardMoveType;
import it.unibo.planning.astar.enums.MoveType;
import it.unibo.planning.astar.enums.SpinDirection;

public class Move {
	
	private SpinDirection spin;
	private ForwardMoveType forward;
	private MoveType type;	
	private String prologRep;
	
	public Move()
	{
		this.type = MoveType.STEP;
		this.forward = ForwardMoveType.TILED;
		prologRep = ForwardMoveType.TILED.toString();
	}	
	public Move(ForwardMoveType forward)
	{
		this.type = MoveType.STEP;
		this.forward = forward;
		prologRep = forward.toString();
	}
	
	public Move(SpinDirection spinDir)
	{
		this.spin = spinDir;
		this.type = MoveType.SPIN;
		prologRep = spinDir.toString();
	}
	
	@Override
	public String toString()
	{
		return prologRep;
	}

	public MoveType getType() {
		return type;
	}

	public SpinDirection getSpin() {
		return spin;
	}
	
	public ForwardMoveType getForwardType() {
		return forward;
	}	
	
}
