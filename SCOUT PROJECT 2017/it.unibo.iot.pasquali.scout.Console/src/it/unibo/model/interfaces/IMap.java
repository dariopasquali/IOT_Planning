package it.unibo.model.interfaces;

import java.util.List;

public interface IMap {
	
	public String getDefaultRep();
	void addElementFromString(String elem);
	
	public List<IMapElement> getElements();
	public Integer[][] getIntMap();
	public int getXmax();
	public int getYmax();
	
	void clearAll();
	void noneAll();
	
	void setCell(int y, int x, int state);	
	
	void setCellClear(int y, int x);
	boolean isCellClear(int y, int x);
	

}
