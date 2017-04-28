package it.unibo.execution.enums;

import it.unibo.planning.enums.PositionMove;

public enum CDirection {

	NORTH ("N", 0),
	EAST ("E", 90),
	SOUTH ("S", 180),
	WEST ("W", 270);
	
	private final String direction;
	private final int phase;
	
    private CDirection(String s, int phase)
    {
    	direction = s;
    	this.phase = phase;
    }
    
    public String toString(){ return direction; }
    public int getPhase(){return phase; }
    
    public static CDirection fromDirection(String dir)
    {
    	for(CDirection pm : CDirection.values())
    		if(dir.equals(pm.toString()))
    			return pm;
    	
    	return null;
    }
	
}
