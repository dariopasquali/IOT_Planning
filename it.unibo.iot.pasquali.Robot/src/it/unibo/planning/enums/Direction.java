package it.unibo.planning.enums;

public enum Direction {
	NORTH ("N", 0),
	NORTH_EAST("NE", 1),
	EAST ("E", 2),
	SOUTH_EAST("SE", 3),
	SOUTH ("S", 4),
	SOUTH_WEST("SW", 5),
	WEST ("W", 6),
	NORTH_WEST("NW", 7),
	NONE ("O", -1);
	
	private final String direction;
	private final int value;
    private Direction(String s, int val) { direction = s; value = val;}
    public String toString(){ return direction; }
    public int getValue() {return value; }
}
