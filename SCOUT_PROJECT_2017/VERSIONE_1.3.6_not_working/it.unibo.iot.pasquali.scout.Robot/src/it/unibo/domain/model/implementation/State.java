package it.unibo.domain.model.implementation;

import java.awt.Point;

import it.unibo.planning.enums.Direction;

public class State implements Comparable<State>{

	private int x;
	private int y;
	private Direction dir;
	
	private double cost;
	
	public State() {
		this.x = -1;
		this.y = -1;
		this.dir = Direction.NORTH;
		cost = 0;
	}
	
	public State(int y, int x, Direction dir) {
		this.y = y;
		this.x = x;
		this.dir = dir;
		cost = 0;
	}
	
	public State(int y, int x) {
		this.y = y;
		this.x = x;
		this.dir = Direction.NORTH;
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
		if(!(s instanceof State))
			return false;
		
		State ss = (State) s;
		
		return y == ss.getY() &&
				x == ss.getX();		
	}
	
	@Override
	public int compareTo(State s) {
		
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
		return "state("+x+","+y+","+dir.toString()+")";
	}

	public Point getCoordinates() {
		return new Point(x,y);
	}
	

}
