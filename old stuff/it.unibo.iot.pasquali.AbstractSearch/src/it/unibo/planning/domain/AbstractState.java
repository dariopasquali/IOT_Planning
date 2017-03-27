package it.unibo.planning.domain;

public abstract class AbstractState implements Comparable<AbstractState> {
	
	protected int x;
	protected int y;
	
	protected int cost;
	protected double heuristic;
	
	public AbstractState()
	{
		this.x = -1;
		this.y = -1;
		
		this.cost = -1;
		this.heuristic = -1;
	}
	
	public AbstractState(int x, int y)
	{
		this.x = x;
		this.y = y;
		this.cost = -1;
		this.heuristic = -1;
	}
	
	public AbstractState(int x, int y, int cost)
	{
		this.x = x;
		this.y = y;
		this.cost = cost;
		this.heuristic = -1;
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
	public int compareTo(AbstractState s1) {
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
