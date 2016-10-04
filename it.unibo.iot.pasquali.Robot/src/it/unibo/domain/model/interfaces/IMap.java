package it.unibo.domain.model.interfaces;

import java.util.List;

public interface IMap {
	
	public void addElement(IMapElement newElement);
	public List<IMapElement> getElements();
	
	public String getDefaultRep();
	public String getJSONRep();
	void clearAll();
	void addElementsFromString(String elem);
	void addElementsFromList(List<String> els);
	void removeElement(int c, int r);

}
