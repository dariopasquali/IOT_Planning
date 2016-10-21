package it.unibo.planning.astar.domain;

import it.unibo.planning.astar.enums.PositionMove;

public class State implements Comparable<State>{
	
	private int x;
	private int y;
	
	private PositionMove genMove;
	private int cost;
	private double heuristic;
	
	public State()
	{
		this.x = -1;
		this.y = -1;
		
		this.genMove = null;
		this.cost = -1;
		this.heuristic = -1;
	}
	
	public State(int x, int y)
	{
		this.x = x;
		this.y = y;
		this.genMove = null;
		this.cost = -1;
		this.heuristic = -1;
	}
	
	public State(int x, int y, PositionMove genMove, int cost)
	{
		this.x = x;
		this.y = y;		
		
		this.genMove = genMove;
		this.cost = cost;
	}

	@Override
	public String toString()
	{
		return "state("+x+","+y+")";
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

	public PositionMove getPositionGenMove() {
		return genMove;
	}
	
	public void setPositionGenMove(PositionMove move) {
		this.genMove = (PositionMove)move;
	}

	public int getCost() {
		return cost;
	}
	
	public void setCost(int cost) {
		this.cost = cost;
	}

	public double getHeuristic() {
		return heuristic;
	}

	public void setHeuristic(double heuristic) {
		this.heuristic = heuristic;
	}
	
	private double getF()
	{
		return this.heuristic + this.cost;
	}
	
	@Override
	public boolean equals(Object o)
	{
		if(!(o instanceof State))
			return false;
		
		State s1 = ((State) o);
		
		if(this.x == s1.getX() &&	this.y == s1.getY())
			return true;
		else
			return false;
	}

	@Override
	public int compareTo(State s1) {
		if(getF() == s1.getF())
		{
			if(getHeuristic() == s1.getHeuristic())
				return 0;
			else if(getHeuristic() > s1.getHeuristic())
				return 1;
			else
				return -1;
		}
		
		else if(getF() > s1.getF())
			return 1;
		
		else return -1;
	}	
	
}
