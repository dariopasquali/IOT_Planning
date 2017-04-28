package it.unibo.domain.model.implmentation;

import java.awt.Point;

import it.unibo.planning.enums.Direction;

public class ExplorationState implements Comparable<ExplorationState>{

	private int x;
	private int y;
	private Direction dir;
	
	private double cost;
	
	public ExplorationState() {
		this.x = -1;
		this.y = -1;
		this.dir = Direction.NONE;
		cost = 0;
	}
	
	public ExplorationState(int y, int x, Direction dir) {
		this.y = y;
		this.x = x;
		this.dir = dir;
		cost = 0;
	}
	
	public int getY()
	{
		return y;
	}
	
	public int getX()
	{
		return x;
	}
	
	public Direction getDirection()
	{
		return dir;
	}
	
	public void setX(int x)
	{
		this.x = x;
	}
	
	public void setY(int y)
	{
		this.y = y;
	}
	
	public void setDirection(Direction dir)
	{
		this.dir = dir;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public void addCost(double qta) {
		this.cost += qta;
	}
	
	@Override
	public boolean equals(Object s)
	{
		if(!(s instanceof ExplorationState))
			return false;
		
		ExplorationState ss = (ExplorationState) s;
		
		return y == ss.getY() &&
				x == ss.getX();		
	}
	
	@Override
	public int compareTo(ExplorationState s) {
		
		if(cost > s.getCost())
			return 1;
		else if(cost == s.getCost())
			return 0;
		else
			return -1;	
	}
	
	@Override
	public String toString()
	{
		return "state("+y+","+x+","+dir.toString()+","+getCost()+")";
	}

	public Point getCoordinates() {
		return new Point(x,y);
	}
	

}
