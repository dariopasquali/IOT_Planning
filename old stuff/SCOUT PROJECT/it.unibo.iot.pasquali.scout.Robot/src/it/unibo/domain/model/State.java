package it.unibo.domain.model;

import it.unibo.planning.enums.Direction;

public class State{
	
	private int x;
	private int y;
	
	private Direction direction;
	private Move genMove;
	
	public State()
	{
		super();		
		this.direction = null;		
		this.genMove = null;

	}
	
	public State(int x, int y)
	{
		this.x = x;
		this.y = y;
		this.genMove = null;
	}
	
	public State(int x, int y, Direction direction, Move genMove)
	{
		this.x = x;
		this.y = y;
		this.direction = direction;		
		this.genMove = genMove;
	}

	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	public Move getGenMove() {
		return genMove;
	}

	public void setGenMove(Move genMove) {
		this.genMove = genMove;
	}


	@Override
	public boolean equals(Object o)
	{
		if(!(o instanceof State))
			return false;
		
		State s1 = ((State) o);
		
		if(this.x == s1.getX() &&
				this.y == s1.getY() &&
				this.getDirection().equals(s1.getDirection()))
			return true;
		else
			return false;
	}
	
	@Override
	public String toString()
	{
		return "state("+this.x+","+this.y+","+direction+")";
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
	
	
	
}
