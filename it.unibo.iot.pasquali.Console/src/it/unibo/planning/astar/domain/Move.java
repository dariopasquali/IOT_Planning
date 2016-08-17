package it.unibo.planning.astar.domain;

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
	
	
	private int toX;
	private int toY;
	private SpinDirection spin;
	
	private String prologRep;
	
	public Move(int toX, int toY)
	{
		this.toX = toX;
		this.toY = toY;		
		prologRep = "move(robotmove, forward, 60, 500, 0)";
	}
	
	public Move(SpinDirection spinDir)
	{
		this.spin = spinDir;		
		prologRep = "move(robotmove,"+spinDir+", 60, 0, 90)";
	}
	
	@Override
	public String toString()
	{
		return prologRep;
	}

}
