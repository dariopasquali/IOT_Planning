package it.unibo.domain.graph;

import java.io.Serializable;

public class State implements Serializable{

	private int x,y;	
	private double heuristic;
	
	
	
	public State(int x, int y)
	{
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	@Override
	public boolean equals(Object o)
	{
		if(!(o instanceof State))
			return false;
		
		return ((State)o).getX()==x && ((State)o).getY()==y;
	}
	
	@Override
	public String toString(){
		return "p("+x+","+y+")";
	}
	
//	public double getHeuristicDistanceTo(State s)
//	{
//		return Math.abs(s.getX()-x) + Math.abs(s.getY()-y);
//	}

	public double getHeuristic() {
		return heuristic;
	}

	public void setHeuristic(double heuristic) {
		this.heuristic = heuristic;
	}
}
