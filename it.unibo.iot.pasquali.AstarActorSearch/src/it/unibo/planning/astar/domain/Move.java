package it.unibo.planning.astar.domain;

public class Move {
	
	public enum SpinDirection {
		LEFT ("L", 1),
		RIGHT ("R", 1),
		DOUBLELEFT("DL", 2),
		DOUBLERIGHT("DR", 2);
		
		private final String direction;
		private final int cost;
		
	    private SpinDirection(String s, int cost) { direction = s; this.cost = cost; }
	    public boolean equalsDirection(String otherDir){ return otherDir == direction; }
	    public int getCost(){return cost;}
	    public String toString(){switch(direction){
	    case "L": return "left";
	    case "R": return "right";
	    case "DL": return "doubleleft";
	    case "DR": return "doubleright";
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
