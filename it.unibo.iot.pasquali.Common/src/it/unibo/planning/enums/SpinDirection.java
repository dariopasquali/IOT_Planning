package it.unibo.planning.enums;


/**
 * Provides the types of rotation moves and relative Navigation cost
 */
public enum SpinDirection {
	
		LEFT ("L", 10, -1),
		RIGHT ("R", 10, 1),
		DOUBLELEFT("DL", 20, -2),
		DOUBLERIGHT("DR", 20, +2);
		
		private final String direction;
		private final int cost;
		private final int rotation;
		
	    /**
	     * @param s  String representation of the movement
	     * @param cost  Navigation cost of the movement
	     * @param rotation  increment of position relative to {@code Direction} enum
	     */
	    private SpinDirection(String s, int cost, int rotation)
	    { 
	    	direction = s;
	    	this.cost = cost;
	    	this.rotation = rotation;
	    }
	    
	    public boolean equalsDirection(String otherDir)
	    {
	    	return otherDir == direction;
	    }
	    
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
	    
	    public static SpinDirection fromString(String dir)
	    {
	    	for(SpinDirection pm : SpinDirection.values())
	    		if(dir.equals(pm.toString()))
	    			return pm;
	    	
	    	return null;
	    }
}
