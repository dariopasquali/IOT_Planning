package it.unibo.planning.enums;

public enum SpinDirection {
		LEFT ("L", 10, -1),
		RIGHT ("R", 10, 1),
		DOUBLELEFT("DL", 20, -2),
		DOUBLERIGHT("DR", 20, +2);
		
		private final String direction;
		private final int cost;
		private final int rotation;
		
	    private SpinDirection(String s, int cost, int rotation)
	    { 
	    	direction = s;
	    	this.cost = cost;
	    	this.rotation = rotation;
	    }
	    public boolean equalsDirection(String otherDir)
	    { return otherDir == direction; }
	    
	    public int getCost(){return cost;}
	    public int getRotation(){return rotation;}
	    
	    public String toString()
	    {
	    	switch(direction)
	    	{
			    case "L": return "l";
			    case "R": return "r";
			    case "DL": return "dl";
			    case "DR": return "dr";
			    default: return "";
		    }
	    }
}
