package it.unibo.planning.enums;

public enum Direction {
	
	NORTH ("N", 0, true),
	NORTH_EAST("NE", 1, false),
	EAST ("E", 2, true),
	SOUTH_EAST("SE", 3, false),
	SOUTH ("S", 4, true),
	SOUTH_WEST("SW", 5, false),
	WEST ("W", 6, true),
	NORTH_WEST("NW", 7, false),
	NONE ("O", -1, false);
	
	private final String direction;
	private final int value;
	private final boolean tiled;
	
    private Direction(String s, int val, boolean tiled) { direction = s; value = val; this.tiled = tiled;}
    
    public String toString(){ return direction; }
    
    public int getValue() {return value; }
    
    public boolean isTiled() {
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
