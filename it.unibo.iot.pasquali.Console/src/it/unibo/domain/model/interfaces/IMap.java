package it.unibo.domain.model.interfaces;

import java.util.List;

public interface IMap {
	
	public void addElement(IMapElement newElement);
	public List<IMapElement> getElements();
	
	public String getDefaultRep();

}
