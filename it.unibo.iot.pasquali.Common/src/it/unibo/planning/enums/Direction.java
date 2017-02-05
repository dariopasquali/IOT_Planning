package it.unibo.planning.enums;

public enum Direction {
	
	NORTH ("N", true),
	NORTHEAST("NE", false),
	EAST ("E", true),
	SOUTHEAST("SE", false),
	SOUTH ("S", true),
	SOUTHWEST("SE", false),
	WEST ("W", true),
	NORTHWEST("NW", false),
	NONE ("NN", true);
	
	private final String direction;
	private final boolean tiled;
	
    private Direction(String s, boolean t)
    {
    	direction = s;
    	tiled = t;
    }
    
    public String toString()
    {
    	return direction; 
    }
    
    public boolean isTiled()
    {
    	return tiled;
    }
    
    public static Direction fromDirectionString(String dir)
    {
    	for(Direction pm : Direction.values())
    		if(dir.equals(pm.toString()))
    			return pm;
    	
    	return null;
    }

}
