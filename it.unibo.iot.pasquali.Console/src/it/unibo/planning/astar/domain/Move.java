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
	
	public enum MoveType {
		STEP,
		SPIN		
	}
	
	
	private int duration;	
	private SpinDirection spin;
	
	private int speed;
	
	private MoveType type;

	
	private String prologRep;
	
	public Move(int speed, int duration)
	{
		this.duration = duration;
		this.speed = speed;
		this.type = MoveType.STEP;
		prologRep = "move(robotmove,forward,"+speed+","+duration+",0)";
	}
	
	public Move(SpinDirection spinDir, int speed)
	{
		this.spin = spinDir;
		this.speed = speed;
		this.type = MoveType.SPIN;
		prologRep = "move(robotspin,"+spinDir+","+speed+",0,90)";
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

	public int getDuration() {
		return duration;
	}

	public SpinDirection getSpin() {
		return spin;
	}

	public int getSpeed() {
		return speed;
	}

	public String getPrologRep() {
		return prologRep;
	}
	
	

}
