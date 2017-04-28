package it.unibo.model.map;

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
	
//{{ CONSTRUCTORS ---------------------------------------------------
	
	/**
	 * Construct the {@code Map} based on another map.
	 * 
	 * @param ymax  the height of the map
	 * @param xmax  the width of the map
	 * @param intmap  the other initialization map
	 */
	public Map(int ymax, int xmax, Integer[][] intmap)
	{
		this.xmax = xmax;
		this.ymax = ymax;
		this.intmap = new Integer[ymax+1][xmax+1];
		
		for(int y=0; y<=ymax; y++)
			for(int x=0; x<=xmax; x++)
				this.intmap[y][x] = intmap[y][x];
		
	}
	
	/**
	 * Construct a, all {@code CLEAR Map}.
	 * 
	 * @param ymax  is the height of the map
	 * @param xmax  is the width of the map
	 */
	public Map(int ymax, int xmax) {

		this.ymax = ymax;
		this.xmax = xmax;
		
		intmap = new Integer[ymax+1][xmax+1];
		clearAll();
	}
	
	
	/**
	 * Clone another {@code Map}
	 * 
	 * @param other  the other map
	 */
	public Map(Map other) {
		this.ymax = other.ymax;
		this.xmax = other.xmax;
		this.intmap = other.intmap.clone();
	}

//}}
	
	
//{{ MAP CREATION ----------------------------------------------
	

	/**
	 * @see it.unibo.model.interfaces.IMap#addElementFromString(java.lang.String)
	 * 
	 * @param elem  the element represented like element(X,Y)
	 */
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

	
	/**
	 * Instantiate a {@code Map} from a Prolog representation.
	 * 
	 * @param map  the map represented like map(W,H)
	 * @return
	 */
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

//}}	

	
//{{ GET/SET ---------------------------------
	
	/* (non-Javadoc)
	 * @see it.unibo.model.interfaces.IMap#getIntMap()
	 */
	@Override
	public Integer[][] getIntMap()
	{
		return intmap;
	}

	/* (non-Javadoc)
	 * @see it.unibo.model.interfaces.IMap#getXmax()
	 */
	@Override
	public int getXmax() {
		return xmax;
	}


	/* (non-Javadoc)
	 * @see it.unibo.model.interfaces.IMap#getYmax()
	 */
	@Override
	public int getYmax() {
		return ymax;
	}


	/* (non-Javadoc)
	 * @see it.unibo.model.interfaces.IMap#getElements()
	 */
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
	
//}}
	
	
//{{ CHECK MAP STATE -------------------------------
	
	
	/* (non-Javadoc)
	 * @see it.unibo.model.interfaces.IMap#isCellClear(int, int)
	 */
	@Override
	public boolean isCellClear(int y, int x) {
		
		return y>=0 &&
				y<=ymax &&
				x>=0 &&
				x<=xmax &&
				(intmap[y][x]==CLEAR);
	}
	
	/* (non-Javadoc)
	 * @see it.unibo.model.interfaces.IMap#isCellNone(int, int)
	 */
	@Override
	public boolean isCellNone(int y, int x) {
		
		return y>=0 &&
				y<=ymax &&
				x>=0 &&
				x<=xmax &&
				(intmap[y][x]==NONE);
	}
	
	
	/* (non-Javadoc)
	 * @see it.unibo.model.interfaces.IMap#isCellObj(int, int)
	 */
	@Override
	public boolean isCellObj(int y, int x) {
		
		return y>=0 &&
				y<=ymax &&
				x>=0 &&
				x<=xmax &&
				(intmap[y][x]==OBJ);
	}
	

	/* (non-Javadoc)
	 * @see it.unibo.model.interfaces.IMap#isValidCell(int, int)
	 */
	@Override
	public boolean isValidCell(int y, int x)
	{
		return y>=0 && y <= ymax && x >=0 && x <= xmax;
	}

//}}
	
	
//{{ SET CELL STATE --------------------------------------------
	
	/* (non-Javadoc)
	 * @see it.unibo.model.interfaces.IMap#setCellClear(int, int)
	 */
	@Override
	public void setCellClear(int y, int x) {
		
		if(!(y>=0 &&
				y<=ymax &&
				x>=0 &&
				x<=xmax))
			return;
		
		intmap[y][x]=CLEAR;
	}
	
	/* (non-Javadoc)
	 * @see it.unibo.model.interfaces.IMap#setCellNone(int, int)
	 */
	@Override
	public void setCellNone(int y, int x) {
		
		if(!(y>=0 &&
				y<=ymax &&
				x>=0 &&
				x<=xmax))
			return;
		
		intmap[y][x]=NONE;
	}
	
	/* (non-Javadoc)
	 * @see it.unibo.model.interfaces.IMap#setCellObj(int, int)
	 */
	@Override
	public void setCellObj(int y, int x) {
		
		if(!(y>=0 &&
				y<=ymax &&
				x>=0 &&
				x<=xmax))
			return;
		
		intmap[y][x]=OBJ;
	}
	
	/* (non-Javadoc)
	 * @see it.unibo.model.interfaces.IMap#setCell(int, int, int)
	 */
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
	
	/* (non-Javadoc)
	 * @see it.unibo.model.interfaces.IMap#clearAll()
	 */
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
	
	/* (non-Javadoc)
	 * @see it.unibo.model.interfaces.IMap#noneAll()
	 */
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
	
	/**
	 * Fill the {@code NONE} cells in the map with {@code OBJ} cells
	 */
	public void fillUnexploredCell()
	{
		for(int y=0; y<=ymax; y++)
		{
			for(int x = 0; x<=xmax; x++)
			{
				if(intmap[y][x] == NONE)
					intmap[y][x] = OBJ;
			}
		}
	}
	
	/**
	 * String mapping for cell states constants.
	 * 
	 * @param s  a state name
	 * @return  the relative constant
	 */
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

//}}
	
	
//{{ STRING REPRESENTATION -------------------------------------
		
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
//}}

	


	
	


	
	
	

	

	
}
