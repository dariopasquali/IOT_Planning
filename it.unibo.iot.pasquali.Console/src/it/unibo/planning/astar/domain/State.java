package it.unibo.planning.astar.domain;

public class State implements Comparable<State>{
	
	public enum Direction {
		NORTH ('N', 0),
		EAST ('E', 1),
		WEST ('W', 2),
		SOUTH ('S', 3),
		NONE ('0', -1);
		
		private final char direction;
		private final int value;
	    private Direction(char s, int val) { direction = s; value = val;}
	    public String toString(){ return ""+direction; }
	    public int getValue() {return value; }		
	}

	private Direction direction;
	private int x;
	private int y;
	
	private Move genMove;
	private int cost;
	private double heuristic;
	
	public State()
	{
		this.x = -1;
		this.y = -1;
		this.direction = null;
		
		this.genMove = null;
		this.cost = -1;
		this.heuristic = -1;
	}
	
	public State(int x, int y, Direction direction, Move genMove, int cost)
	{
		this.x = x;
		this.y = y;
		this.direction = direction;
		
		this.genMove = genMove;
		this.cost = cost;
	}
	
	@Override
	public String toString()
	{
		return "state("+x+","+y+","+direction+")";
	}

	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
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

	public Move getGenMove() {
		return genMove;
	}

	public void setGenMove(Move genMove) {
		this.genMove = genMove;
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
	
	public double getF()
	{
		return this.heuristic + this.cost;
	}
	
	@Override
	public int hashCode()
	{
		int prime = 37;
		return prime * (x + y + direction.getValue());
	}
	
	@Override
	public boolean equals(Object o)
	{
		if(!(o instanceof State))
			return false;
		
		if(this.hashCode() == ((State) o).hashCode())
			return true;
		else
			return false;
	}

	@Override
	public int compareTo(State s1) {
		if(getF() == s1.getF())
			return 0;
		
		else if(getF() > s1.getF())
			return 1;
		
		else return -1;
	}
	
}
