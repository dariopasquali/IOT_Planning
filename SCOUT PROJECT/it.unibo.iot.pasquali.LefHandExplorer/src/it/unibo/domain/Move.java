package it.unibo.domain;

import it.unibo.enums.*;

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

	public Move(it.unibo.planning.astar.domain.Move mm) {
		this.spin = SpinDirection.valueOf(mm.getSpin().toString());
		this.forward = ForwardMoveType.valueOf(mm.getForwardType().toString());
		this.type = MoveType.valueOf(mm.getType().toString());
		this.prologRep = mm.toString();
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
