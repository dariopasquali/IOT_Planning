package it.unibo.planning.engine;


import it.unibo.planning.domain.AbstractState;

public abstract class AbstractEngine {
	
	protected AbstractState goal;	
	protected int xmax = -1;
	protected int ymax = -1;	
	protected Integer[][] intmap;
	
	public AbstractEngine()
	{
		this.goal = null;
	}
	

	public void setGoal(AbstractState goal) {
		this.goal = goal;		
	}
	

	public void setIntMap(Integer[][] map, int xmax, int ymax)
	{
		this.intmap = map;
		this.xmax = xmax;
		this.ymax = ymax;
	}
	
	public boolean isGoalState(AbstractState current) {
		if(current == null)
			return false;		
		return current.getX() == goal.getX() && current.getY()==goal.getY();
	}

	public boolean isValidState(AbstractState state) {
		return (state.getX() >= 0 &&
				state.getX() <= xmax &&
				state.getY() >= 0 &&
				state.getY() <= ymax &&
				intmap[state.getY()][state.getX()] != 1);
	}

	
	public double evaluateState(AbstractState state) {

		return Math.abs(goal.getX()-state.getX()) +  Math.abs(goal.getY()-state.getY());
	}

}
