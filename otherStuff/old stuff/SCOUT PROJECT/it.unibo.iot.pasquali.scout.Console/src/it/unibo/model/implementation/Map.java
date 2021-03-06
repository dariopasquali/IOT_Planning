package it.unibo.model.implementation;

import java.util.ArrayList;
import java.util.List;

import it.unibo.model.interfaces.IMap;
import it.unibo.model.interfaces.IMapElement;

public class Map implements IMap{

	public static final int NONE = -1,
							CLEAR = 0,
							OBJ = 1;
	
	private int xmax; //aka X
	private int ymax; //aka Y
	private Integer[][] intmap;
	
	/*
	 * MAP FORMAT
	 * 			N
	 * 		------ X
	 * 		|
	 * 	W	|		E
	 * 		|
	 * 		Y
	 * 			S
	 */
	
	
	public Map(int ymax, int xmax, Integer[][] intmap)
	{
		this.xmax = xmax;
		this.ymax = ymax;
		this.intmap = new Integer[ymax+1][xmax+1];
		
		for(int y=0; y<=ymax; y++)
			for(int x=0; x<=xmax; x++)
				this.intmap[y][x] = intmap[y][x];
		
		//this.intmap = intmap.clone();
		
	}
	
	public Map(int ymax, int xmax) {

		this.ymax = ymax;
		this.xmax = xmax;
		
		intmap = new Integer[ymax+1][xmax+1];
		clearAll();
	}
	
	public Map(Map currentMap) {
		this.ymax = currentMap.ymax;
		this.xmax = currentMap.xmax;
		this.intmap = currentMap.intmap.clone();
	}

	public static int fromStringToState(String s)
	{
		s = s.toUpperCase();
		
		switch(s)
		{
		case("OBJECT"): return OBJ;
		case("OBJ"): return OBJ;
		case("CLEAR"): return CLEAR;
		default: return NONE;
		}
	}
	
	
	// ADD ELEMENTS ----------------------------------------------
	
	@Override
	public void addElementFromString(String elem) {

		int i = 0;
		String[] els = elem.split(",");
			
		try
		{
			while(i < els.length)
			{
				String[] x = els[i].split("\\(");
				String[] y = els[i+1].split("\\)");
				
				intmap[Integer.parseInt(y[0])][Integer.parseInt(x[1])] = OBJ;
				i+=2;
			}	
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
			
	}

	public static Map createMapFromPrologRep(String map)
	{
		if(!map.contains("map"))
			return null;
			
		/*
		 * read
		 * ---->X
		 * |
		 * |
		 * Y
		 */
		
		
		String[] s = map.split(",");
		String[] sxmax = s[0].split("\\(");
		String[] symax = s[1].split("\\)");
			
		return new Map(Integer.parseInt(symax[0]), Integer.parseInt(sxmax[1]));
	}
		
	
	// ACCESSOR ---------------------------------
	
	public int getYMax() {
		return ymax;
	}
	
	public int getXMax() {
		return xmax;
	}

	@Override
	public Integer[][] getIntMap()
	{
		return intmap;
	}
	
	// CHECKERS -------------------------------
	
	public boolean isCellClear(int y, int x) {
		
		return y>=0 &&
				y<=ymax &&
				x>=0 &&
				x<=xmax &&
				(intmap[y][x]==CLEAR);
	}
	
	public boolean isCellNone(int y, int x) {
		
		return y>=0 &&
				y<=ymax &&
				x>=0 &&
				x<=xmax &&
				(intmap[y][x]==NONE);
	}
	
	public boolean isCellObj(int y, int x) {
		
		return y>=0 &&
				y<=ymax &&
				x>=0 &&
				x<=xmax &&
				(intmap[y][x]==OBJ);
	}
	
	public boolean isValidCell(int y, int x)
	{
		return y>=0 && y <= ymax && x >=0 && x <= xmax;
	}
	
	// SETTERS --------------------------------------------
	
	public void setCellClear(int y, int x) {
		
		if(!(y>=0 &&
				y<=ymax &&
				x>=0 &&
				x<=xmax))
			return;
		
		intmap[y][x]=CLEAR;
	}
	
	public void setCellNone(int y, int x) {
		
		if(!(y>=0 &&
				y<=ymax &&
				x>=0 &&
				x<=xmax))
			return;
		
		intmap[y][x]=NONE;
	}
	
	public void setCellObj(int y, int x) {
		
		if(!(y>=0 &&
				y<=ymax &&
				x>=0 &&
				x<=xmax))
			return;
		
		intmap[y][x]=OBJ;
	}
	
	@Override
	public void setCell(int y, int x, int state) {
		
		if(state != Map.CLEAR && state != Map.NONE && state != Map.OBJ)
			return;
		
		if(y>=0 &&
				y<=ymax &&
				x >= 0 &&
				x <=xmax)
			intmap[y][x] = state;
		
	}
	
	@Override
	public void clearAll() {
		
		for(int y=0; y<=ymax; y++)
		{
			for(int x = 0; x<=xmax; x++)
			{
				intmap[y][x] = CLEAR;
			}
		}	
	}
	
	@Override
	public void noneAll() {
		
		for(int y=0; y<=ymax; y++)
		{
			for(int x = 0; x<=xmax; x++)
			{
				intmap[y][x] = NONE;
			}
		}	
	}
	
		
	@Override
	public String toString()
	{
		String s = "  ";
		
		for(int x=0; x<=xmax; x++)
			s+=x;
		
		s+="\n";
		
		for(int y=0; y<=ymax; y++)
		{
			s+=y+" ";
			for(int x=0; x<=xmax; x++)
			{
				if(intmap[y][x] == NONE)
					s += "-";
				if(intmap[y][x] == CLEAR)
					s += "0";
				if(intmap[y][x] == OBJ)
					s += "1";
			}
			s+="\n";
		}
		
		return s;
	}

	@Override
	public String getDefaultRep() {
		
		String m = "map("+xmax+","+ymax+")\n";
		
				
		List<IMapElement> list = getElements();
		
		for(int i = 0; i<list.size(); i++)
		{
			m += "mapdata(" + (i+1) +", " + list.get(i).toString() +")";
			if(i!=list.size()-1)
				m+="\n";
		}
		
		
		return m;
		
	}


	@Override
	public int getXmax() {
		return xmax;
	}

	@Override
	public int getYmax() {
		return ymax;
	}

	@Override
	public List<IMapElement> getElements() {

		List<IMapElement> list = new ArrayList<IMapElement>();
		
		for(int y=0; y<=ymax; y++)
		{
			for(int x=0; x<=xmax; x++)
			{
				if(intmap[y][x] == OBJ)
					list.add(new MapElement(y,x));
			}
		}
		
		return list;		
	}


	
	


	
	
	

	

	
}
