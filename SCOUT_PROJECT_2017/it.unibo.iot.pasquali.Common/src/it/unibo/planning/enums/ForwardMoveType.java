package it.unibo.planning.enums;

/**
 * Provides the types of forward moves and the relative Navigation cost.
 */
public enum ForwardMoveType {
	
		TILED ("T", 10),
		DIAGONAL ("D", 15);
		
		private final String direction;
		private final int cost;
		
		
	    /**
	     * @param s  String representation of the movement
	     * @param cost  Navigation cost of the movement
	     */
	    private ForwardMoveType(String s, int cost)
	    {
	    	direction = s;
	    	this.cost = cost;
	    }
	    
	    public boolean equalsDirection(String otherDir)
	    {
	    	return otherDir == direction;
	    }
	    
	    public int getCost(){return cost;}
	    
	    public String toString()
	    {
	    	switch(direction)
	    	{
	    		case "T": return "t";
	    		case "D": return "d";
	    		default: return "";
	    	}
	    }
	    
	    public static ForwardMoveType fromString(String dir)
	    {
	    	for(ForwardMoveType pm : ForwardMoveType.values())
	    		if(dir.equals(pm.toString()))
	    			return pm;
	    	
	    	return null;
	    }
}
