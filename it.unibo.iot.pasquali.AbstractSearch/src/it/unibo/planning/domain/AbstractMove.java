package it.unibo.planning.domain;

import it.unibo.planning.enums.ForwardMoveType;
import it.unibo.planning.enums.MoveType;
import it.unibo.planning.enums.SpinDirection;

public abstract class AbstractMove {
	
	private SpinDirection spin;
	private ForwardMoveType forward;
	private MoveType type;	
	private String prologRep;
	
	public AbstractMove()
	{
		this.type = MoveType.STEP;
		this.forward = ForwardMoveType.TILED;
		prologRep = ForwardMoveType.TILED.toString();
	}	
	
	public AbstractMove(ForwardMoveType forward)
	{
		this.type = MoveType.STEP;
		this.forward = forward;
		prologRep = forward.toString();
	}
	
	public AbstractMove(SpinDirection spinDir)
	{
		this.spin = spinDir;
		this.type = MoveType.SPIN;
		prologRep = spinDir.toString();
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
	
	@Override
	public String toString()
	{
		return prologRep;
	}

}
