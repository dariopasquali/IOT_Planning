package it.unibo.model.interfaces;

import java.util.List;

public interface IMap {
	
	public String getDefaultRep();
	void addElementFromString(String elem);
	
	public List<IMapElement> getElements();
	public Integer[][] getIntMap();
	public int getWidth();
	public int getHeight();
	
	void clearAll();
	
	void setCellClear(int y, int x);
	boolean isCellClear(int y, int x);

}
