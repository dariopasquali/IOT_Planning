package it.unibo.domain.model.implmentation;

import java.util.List;

import it.unibo.domain.model.interfaces.IMap;
import it.unibo.domain.model.interfaces.IMapElement;

public class IntMap implements IMap{

	private int Xmax;
	private int Ymax;
	private Integer[][] intmap;
	
	public IntMap(int width, int heigh, Integer[][] intmap)
	{
		this.Xmax = width;
		this.Ymax = heigh;
		this.intmap = intmap;
		
	}
	
	public IntMap(int x, int y) {
		this.Xmax = x;
		this.Ymax = y;
		
		intmap = new Integer[y+1][x+1];
		for(int k=0; k<=Ymax; k++)
		{
			for(int j = 0; j<=Xmax; j++)
			{
				intmap[k][j] = 0;
			}
		}
	}
	
	@Override
	public int getYmax() {
		return Ymax;
	}
	
	@Override
	public int getXmax() {
		return Xmax;
	}

	@Override
	public Integer[][] getIntMap()
	{
		return intmap;
	}
	
	@Override
	public void clearAll() {
		for(int k=0; k<=Ymax; k++)
		{
			for(int j = 0; j<=Xmax; j++)
			{
				intmap[k][j] = 0;
			}
		}	
	}

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

	@Override
	public void addElementsFromString(String elem) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addElementsFromList(List<String> els) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeElement(int c, int r) {
		// TODO Auto-generated method stub
		
	}
	
	
}
