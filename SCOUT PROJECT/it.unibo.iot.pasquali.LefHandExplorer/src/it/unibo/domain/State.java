package it.unibo.domain;

import java.awt.Point;

import it.unibo.enums.Direction;

public class State implements Comparable<State>{

	private Point point;
	private Direction dir;
	
	private double cost;
	
	public State() {
		this.point = null;
		this.dir = Direction.NONE;
		cost = 0;
	}
	
	public State(Point point, Direction dir) {
		this.point = point;
		this.dir = dir;
		cost = 0;
	}
	
	public Point getCoordinates()
	{
		return point;
	}
	
	public Direction getDirection()
	{
		return dir;
	}
	
	public void setCoordinates(Point point)
	{
		this.point = point;
	}
	
	public void setDirection(Direction dir)
	{
		this.dir = dir;
	}

	public int getX() {
		return point.x;
	}
	
	public int getY() {
		return point.y;
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
		
		return point.x == ss.getX() &&
				point.y == ss.getY();		
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
	
	

}
