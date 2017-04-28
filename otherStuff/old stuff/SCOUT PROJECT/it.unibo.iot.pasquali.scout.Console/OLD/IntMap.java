package it.unibo.model.implmentation;

import java.util.ArrayList;
import java.util.List;

import it.unibo.model.interfaces.IMapElement;


public class IntMap implements it.unibo.model.interfaces.IMap{

	private int width;
	private int height;
	private Integer[][] intmap;
	
	public IntMap(int width, int height, Integer[][] intmap)
	{
		this.width = width;
		this.height = height;
		this.intmap = intmap;
		
	}
	
	public IntMap(int x, int y) {
		this.width = x;
		this.height = y;
		
		intmap = new Integer[y+1][x+1];
		for(int k=0; k<=width; k++)
		{
			for(int j = 0; j<=height; j++)
			{
				intmap[k][j] = 0;
			}
		}
	}
	
	@Override
	public int getHeight() {
		return height;
	}
	
	@Override
	public int getWidth() {
		return width;
	}

	@Override
	public Integer[][] getIntMap()
	{
		return intmap;
	}
	
	@Override
	public void clearAll() {
		for(int k=0; k<=height; k++)
		{
			for(int j = 0; j<=width; j++)
			{
				intmap[k][j] = 0;
			}
		}	
	}

	@Override
	public void addElement(IMapElement newElement) {
		intmap[newElement.getY()][newElement.getX()] = 1;
		
	}

	@Override
	public List<IMapElement> getElements() {
		
		ArrayList<IMapElement> list = new ArrayList<IMapElement>();
		for(int k=0; k<=height; k++)
		{
			for(int j = 0; j<=width; j++)
			{
				if(intmap[k][j] != 0)
					list.add(new MapElement(j,k));
			}
		}
		return list;
	}

	@Override
	public String getDefaultRep() {

		String map = "map("+width+","+height+")\n";
		for(IMapElement e : this.getElements())
			map+=e.toString()+"\n";
		
		return map;
		
	}

	@Override
	public void removeElement(int x, int y) {
		intmap[y][x] = 0;		
	}

	@Override
	public boolean isCellClear(int x, int y) {
		
		return(intmap[y][x]==0);
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
				
				intmap[Integer.parseInt(st[0])][Integer.parseInt(sh[1])] = 1;
				i+=2;
			}	
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}

	@Override
	public void addElementsFromList(List<String> els) {
		
	}

	public static IntMap createMapFromPrologRep(String map) {
		if(!map.contains("map"))
			return null;
		
		String[] s = map.split(",");
		String[] sh = s[0].split("\\(");
		String[] st = s[1].split("\\)");
		
		return new IntMap(Integer.parseInt(sh[1]), Integer.parseInt(st[0]));
	}
}
