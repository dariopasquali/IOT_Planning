package it.unibo.planning.astar.domain;

import it.unibo.planning.astar.domain.Move.SpinDirection;

public class Move {
	
	public enum SpinDirection {
		LEFT ('L'),
		RIGHT ('R');
		
		private final char direction;
		
	    private SpinDirection(char s) { direction = s; }
	    public boolean equalsDirection(char otherDir){ return otherDir == direction; }
	    public String toString(){switch(direction){
	    case 'L': return "left";
	    case 'R': return "right";
	    default: return "";
	    }
	    }		
	}
	
	public enum MoveType {
		STEP,
		SPIN		
	}
	
	

	private SpinDirection spin;
	private MoveType type;	
	private String prologRep;
	
	public Move()
	{
		this.type = MoveType.STEP;
		prologRep = "robotmove";
	}
	
	public Move(SpinDirection spinDir)
	{
		this.spin = spinDir;
		this.type = MoveType.SPIN;
		prologRep = "robotspin("+spinDir+")";
	}
	
	@Override
	public String toString()
	{
		return prologRep;
	}

	public MoveType getType() {
		// TODO Auto-generated method stub
		return type;
	}

	public SpinDirection getSpin() {
		return spin;
	}	


}
