package it.unibo.domain;

import it.unibo.domain.interfaces.IMapElement;

public class MapElement implements IMapElement{

	private int x, y;
	
	public MapElement(int x, int y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public int getX() {
		return x;
	}

	@Override
	public void setX(int x) {
		this.x = x;
	}

	@Override
	public int getY() {
		return y;
	}

	@Override
	public void setY(int y) {
		this.y = y;
	}

	@Override
	public String toString()
	{
		return "element("+x+","+y+")";
	}
	
	@Override
	public boolean equals(Object o)
	{
		if(!(o instanceof MapElement))
			return false;
		
		MapElement e = (MapElement) o;
		
		if(this.getX()==e.getX() && this.getY()==e.getY())
			return true;
		else
			return false;
	}


}
