package it.unibo.planning.enums;


public enum PositionMove{
	
	NORTH ("N", 10, 0),
	NORTH_EAST("NE", 15, 45),
	EAST ("E", 10, 90),
	SOUTH_EAST("SE", 15, 135),
	SOUTH ("S", 10, 180),
	SOUTH_WEST("SW", 15, 225),
	WEST ("W", 10, 270),
	NORTH_WEST("NW", 15, 315);
	
	private final String direction;
	private final int cost;
	private final int phase;
	
    private PositionMove(String s, int val, int phase)
    {
    	direction = s;
    	cost = val;
    	this.phase = phase;
    }
    
    public String toString(){ return direction; }
    public int getCost() {return cost; }
    public int getPhase(){return phase; }
    
    public static PositionMove fromDirection(String dir)
    {
    	for(PositionMove pm : PositionMove.values())
    		if(dir.equals(pm.toString()))
    			return pm;
    	
    	return null;
    }
}
