package it.unibo.domain.model.implmentation;

import java.util.ArrayList;
import java.util.List;

import it.unibo.domain.model.interfaces.IMap;
import it.unibo.domain.model.interfaces.IMapElement;

public class Map implements IMap{

	private ArrayList<IMapElement> elements = new ArrayList<IMapElement>();	
	
	@Override
	public void addElement(IMapElement newElement) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<IMapElement> getElements() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDefaultRep() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getJSONRep() {
		// TODO Auto-generated method stub
		return null;
	}

}
