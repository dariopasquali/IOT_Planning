package it.unibo.gui.enums;

import java.awt.Color;

import it.unibo.model.map.Map;

/**
 * Provides the possible value of a cell on the Map in the GUI.
 */
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

	
	/**
	 * Give the {@code CellState} relative to the background Color
	 * 
	 * @param background  {@code Color} of the cell
	 * @return the relative {@code CellState} 
	 */
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
	
	
	/**
	 * Gives the {@code CellState} relative to the type of cell.
	 * 
	 * @param name  of the cell type
	 * @return  the relative {@code CellState}
	 */
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
