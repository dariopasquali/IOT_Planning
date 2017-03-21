package it.unibo.gui;

import java.awt.Color;

import it.unibo.domain.model.map.Map;

public enum CellState {
	CLEAR(Color.WHITE, Map.CLEAR),
	OBJECT(Color.BLACK, Map.OBJ),
	NONE(Color.GRAY, Map.NONE),
	START(Color.GREEN, Map.CLEAR),
	GOAL(Color.RED, Map.CLEAR),
	PATH(Color.CYAN, Map.CLEAR);
	
	private Color color;
	private int mapState;
	
	private CellState(Color c, int mapState){this.color = c; this.mapState = mapState;}
	
	public Color getColor(){return color;}
	public int getMapState(){return mapState;}

	public static CellState fromColor(Color background) {
		
		if(background.equals(Color.WHITE))
			return CLEAR;
		
		if(background.equals(Color.BLACK))
			return OBJECT;
		
		if(background.equals(Color.GREEN))
			return START;
		
		if(background.equals(Color.BLUE))
			return GOAL;
		
		if(background.equals(Color.RED))
			return PATH;
		
		return NONE;
	}
	
	public static CellState fromString(String name)
	{
		name = name.toUpperCase();
		
		switch(name)
		{
		case "CLEAR": return CLEAR;
		case "OBJECT": return OBJECT;
		case "START": return START;
		case "GOAL": return GOAL;
		case "PATH": return PATH;		
		case "NONE": return NONE;
		}
		
		return NONE;
		
	}
}
