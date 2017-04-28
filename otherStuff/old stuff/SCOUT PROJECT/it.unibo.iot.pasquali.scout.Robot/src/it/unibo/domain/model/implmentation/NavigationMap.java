package it.unibo.domain.model.implmentation;

import java.util.ArrayList;
import java.util.List;

import it.unibo.domain.model.interfaces.IMap;
import it.unibo.domain.model.interfaces.IMapElement;


public class NavigationMap implements IMap{

	private ArrayList<IMapElement> elements = new ArrayList<IMapElement>();	
	private int Xmax;
	private int Ymax;
	
	public static NavigationMap createMapFromPrologRep(String map)
	{
		if(!map.contains("map"))
			return null;
		
		String[] s = map.split(",");
		String[] sh = s[0].split("\\(");
		String[] st = s[1].split("\\)");
		
		return new NavigationMap(Integer.parseInt(sh[1]), Integer.parseInt(st[0]));
	}
	
	public NavigationMap(int x, int y) {
		this.Xmax = x;
		this.Ymax = y;
	}

	public NavigationMap() {
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
	public String getJSONRep() {
		return toString();
	}
	
	public int getYmax() {
		return Ymax;
	}
	
	public int getXmax() {
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
		
		s += "map("+Xmax+","+Ymax+")";
		
		for(int i = 0; i<elements.size(); i++)
		{
			s += "\nmapdata(" + (i+1) +", " + elements.get(i).toString() +")";
		}		
		return s;
	}

	@Override
	public void clearAll() {
		elements = new ArrayList<IMapElement>();		
	}

	public boolean isCellClear(int x, int y) {

		MapElement me = new MapElement(x,y);
		
		if(elements.contains(me))
			return false;
		else
			return true;
	}

}
