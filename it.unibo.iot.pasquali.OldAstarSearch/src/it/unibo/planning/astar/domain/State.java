package it.unibo.planning.astar.domain;

import it.unibo.planning.astar.enums.Direction;
import it.unibo.planning.domain.AbstractState;

public class State extends AbstractState{
	
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
		super(x,y);
		this.genMove = null;
	}
	
	public State(int x, int y, Direction direction, Move genMove, int cost)
	{
		super(x,y,cost);
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
		
		if(super.x == s1.getX() &&
				super.y == s1.getY() &&
				this.getDirection().equals(s1.getDirection()))
			return true;
		else
			return false;
	}
	
	@Override
	public String toString()
	{
		return "state("+super.x+","+super.y+","+direction+")";
	}
	
	
}
