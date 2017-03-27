package it.unibo.model.interfaces;

import java.util.List;

public interface IMap {
	
	public static final int NONE = -1,
			CLEAR = 0,
			OBJ = 1;
	
	/**
	 * provides a formal Default Representation
	 * 
	 * @return  a String representation of the map
	 */
	public String getDefaultRep();
	
	/**
	 * Add one or more elements from a String representation.
	 * 
	 * @param elem  the element 
	 */
	void addElementFromString(String elem);
	
	/**
	 * Provides a List representation of the obstacles of the Map
	 * 
	 * @return  a List of obstacles
	 */
	public List<IMapElement> getElements();

	/**
	 * @return the internal model representation
	 */
	public Integer[][] getIntMap();
	
	/**
	 * @return the map width
	 */
	public int getXmax();
	
	/**
	 * @return the map height
	 */
	public int getYmax();
	
	/**
	 * Check if the cell is {@code CLEAR}
	 * 
	 * @param y  the Y coordinate
	 * @param x  the x coordinate
	 */
	boolean isCellClear(int y, int x);
	
	/**
	 * Check if the cell is {@code NONE}
	 * 
	 * @param y  the Y coordinate
	 * @param x  the x coordinate
	 */
	boolean isCellNone(int y, int x);
	
	/**
	 * Check if the cell is {@code OBJ}
	 * 
	 * @param y  the Y coordinate
	 * @param x  the x coordinate
	 */
	boolean isCellObj(int y, int x);
	
	/**
	 * Check if the cell is inside the map
	 * 
	 * @param y  the Y coordinate
	 * @param x  the x coordinate
	 */
	boolean isValidCell(int y, int x);
	
	
	/**
	 * Sets all the map cells to {@code CLEAR} 
	 */
	void clearAll();
	
	/**
	 * Sets all the map cells to {@code CLEAR} 
	 */
	void noneAll();
	
	
	/**
	 * Set a cell state.
	 * 
	 * @param y  height coordinate of the cell
	 * @param x  width coordinate of the cell
	 * @param state  state to impose
	 */
	void setCell(int y, int x, int state);	
	
	/**
	 * Set a cell state to {@code CLEAR}.
	 * 
	 * @param y  height coordinate of the cell
	 * @param x  width coordinate of the cell
	 */
	void setCellClear(int y, int x);

	/**
	 * Set a cell state to {@code NONE}.
	 * 
	 * @param y  height coordinate of the cell
	 * @param x  width coordinate of the cell
	 */
	void setCellNone(int y, int x);

	/**
	 * Set a cell state to {@code OBJ}.
	 * 
	 * @param y  height coordinate of the cell
	 * @param x  width coordinate of the cell
	 */
	void setCellObj(int y, int x);
}
