package it.unibo.model.implmentation;

import java.util.ArrayList;
import java.util.List;

import it.unibo.model.interfaces.IMap;
import it.unibo.model.interfaces.IMapElement;


public class Map implements IMap{

	private ArrayList<IMapElement> elements = new ArrayList<IMapElement>();	
	private int Xmax;
	private int Ymax;
	private Integer[][] intmap;
	
	
	public static Map createMapFromPrologRep(String map)
	{
		if(!map.contains("map"))
			return null;
		
		String[] s = map.split(",");
		String[] sh = s[0].split("\\(");
		String[] st = s[1].split("\\)");
		
		return new Map(Integer.parseInt(sh[1]), Integer.parseInt(st[0]));
	}
	
	public Map(int width, int heigh, Integer[][] intmap)
	{
		this.Xmax = width;
		this.Ymax = heigh;
		this.intmap = intmap;
		
	}
	
	public Map(int x, int y) {
		this.Xmax = x;
		this.Ymax = y;
		
		intmap = new Integer[x+1][y+1];
		for(int k=0; k<=Xmax; k++)
		{
			for(int j = 0; j<=Ymax; j++)
			{
				intmap[k][j] = 0;
			}
		}
	}

	public Map() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void addElement(IMapElement newElement) {
		elements.add(newElement);		
	}

	@Override
	public List<IMapElement> getElements() {
		return elements;
	}

	@Override
	public String getDefaultRep() {
		return toString();
	}

	@Override
	public int getHeight() {
		return Ymax;
	}
	
	@Override
	public int getWidth() {
		return Xmax;
	}

	@Override
	public void removeElement(int c, int r) {
		MapElement toRemove = new MapElement(c,r);		
		elements.remove(toRemove);		
	}
	

	@Override
	public void addElementsFromList(List<String> els) {
		
		for(String e : els)
		{
			String[] s = e.split(",");
			String[] sh = s[0].split("\\(");
			String[] st = s[1].split("\\)");
			
			MapElement me = new MapElement(Integer.parseInt(sh[1]), Integer.parseInt(st[0]));
			elements.add(me);
		}
	}	
	
	public Integer[][] getIntMap()
	{
		return intmap;
	}
	
	@Override
	public void addElementsFromString(String elem) {
				
		int i = 0;
		String[] els = elem.split(",");
		
		try
		{
			while(i < els.length)
			{
				String[] sh = els[i].split("\\(");
				String[] st = els[i+1].split("\\)");
				
				MapElement me = new MapElement(Integer.parseInt(sh[1]), Integer.parseInt(st[0]));
				intmap[Integer.parseInt(sh[1])][Integer.parseInt(st[0])] = 1;
				elements.add(me);
				i+=2;
			}	
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	
	@Override
	public String toString()
	{
		String s = "";
		
		s += "map("+Xmax+","+Ymax+"),[";
		
		for(int i = 0; i<elements.size(); i++)
		{
			s += "mapdata(" + (i+1) +", " + elements.get(i).toString() +")";
			if(i!=elements.size()-1)
				s+=",";
		}
		s+="]";
		return s;
	}

	@Override
	public void clearAll() {
		elements = new ArrayList<IMapElement>();		
	}

	@Override
	public boolean isCellClear(int x, int y) {

		MapElement me = new MapElement(x,y);
		
		if(elements.contains(me))
			return false;
		else
			return true;
	}

}
