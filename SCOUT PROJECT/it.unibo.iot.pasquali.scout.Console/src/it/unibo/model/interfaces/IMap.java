package it.unibo.model.interfaces;

import java.util.List;

public interface IMap {
	
	public void addElement(IMapElement newElement);
	public List<IMapElement> getElements();
	
	public String getDefaultRep();
	void addElementsFromString(String elem);
	void addElementsFromList(List<String> els);
	void removeElement(int x, int y);
	
	public Integer[][] getIntMap();
	public int getWidth();
	public int getHeight();
	void clearAll();
	boolean isCellClear(int x, int y);

}
