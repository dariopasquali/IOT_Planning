package it.unibo.domain;



public class Map {

	public static final int NONE = -1,
							CLEAR = 0,
							OBJ = 1;
	
	private int width;
	private int height;
	private Integer[][] intmap;
	
	private int noneCounter;
	
	public Map(int width, int height, Integer[][] intmap)
	{
		this.width = width;
		this.height = height;
		this.intmap = intmap;
		noneCounter = 0;
		
	}
	
	public Map(int x, int y) {
		this.width = x;
		this.height = y;
		
		noneCounter = 0;
		
		intmap = new Integer[y+1][x+1];
		for(int k=0; k<=width; k++)
		{
			for(int j = 0; j<=height; j++)
			{
				intmap[k][j] = NONE;
				noneCounter++;
				
			}
		}
	}
	
	public int getHeight() {
		return height;
	}
	
	public int getWidth() {
		return width;
	}

	public Integer[][] getIntMap()
	{
		return intmap;
	}
	
	public void clearAll() {
		
		noneCounter = 0;
		
		for(int k=0; k<=height; k++)
		{
			for(int j = 0; j<=width; j++)
			{
				intmap[k][j] = NONE;
				noneCounter++;
			}
		}	
	}

	public boolean isCellClear(int x, int y) {
		
		return(intmap[x][y]==CLEAR);
	}
	
	public boolean isCellNone(int x, int y) {
		
		return(intmap[x][y]==NONE);
	}
	
	public boolean isCellObj(int x, int y) {
		
		return(intmap[x][y]==OBJ);
	}
	
	public void setCellClear(int x, int y) {
		
		intmap[x][y]=CLEAR;
		noneCounter --;
	}
	
	public void setCellNone(int x, int y) {
		
		intmap[x][y]=NONE;
		noneCounter ++;
	}
	
	public void setCellObj(int x, int y) {
		
		intmap[x][y]=OBJ;
		noneCounter --;
	}
	
	public boolean moreToExplore()
	{
		return noneCounter > 0;
	}
	
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

	public static Map createMapFromPrologRep(String map) {
		if(!map.contains("map"))
			return null;
		
		String[] s = map.split(",");
		String[] sh = s[0].split("\\(");
		String[] st = s[1].split("\\)");
		
		return new Map(Integer.parseInt(sh[1]), Integer.parseInt(st[0]));
	}
	
	public void fillUnexploredCell()
	{
		for(int k=0; k<=height; k++)
		{
			for(int j = 0; j<=width; j++)
			{
				if(intmap[k][j] == NONE)
					intmap[k][j] = OBJ;
			}
		}
	}

	public void setElement(int x, int y, int state) {
		
		if(state != Map.CLEAR || state != Map.NONE || state != Map.OBJ)
			return;
		
		if(x>=0 &&
				x<width &&
				y >= 0 &&
				y < height)
			intmap[x][y] = state;
		
	}
	
}
