package it.unibo.planning.astar.domain;

public class State {
	
	public enum Direction {
		NORD ('N'),
		EAST ('E'),
		WEST ('W'),
		SOUTH ('S');
		
		private final char direction;       
	    private Direction(char s) { direction = s; }
	    public boolean equalsDirection(char otherDir){ return otherDir == direction; }
	    public String toString(){ return ""+direction; }
		
	}

	private Direction direction;
	private int x;
	private int y;
	
	private Move genMove;
	private int cost;
	private int heuristic;
	
	
	public State(int x, int y, Direction direction, Move genMove, int cost, int heuristic)
	{
		this.x = x;
		this.y = y;
		this.direction = direction;
		
		this.genMove = genMove;
		this.cost = cost;
		this.heuristic = heuristic;
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

	public int getHeuristic() {
		return heuristic;
	}

	public void setHeuristic(int heuristic) {
		this.heuristic = heuristic;
	}
	
	public int getF()
	{
		return this.heuristic + this.cost;
	}
	
	
}
