package it.unibo.enums;

public enum Direction {
	NORTH ("N"),
	EAST ("E"),
	SOUTH ("S"),
	WEST ("W"),
	NONE ("NN");
	
	private final String direction;
	
    private Direction(String s) { direction = s; }
    public String toString(){ return direction; }

}
