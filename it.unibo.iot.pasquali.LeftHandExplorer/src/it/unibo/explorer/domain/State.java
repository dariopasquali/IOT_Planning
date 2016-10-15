package it.unibo.explorer.domain;

public class State {
	
	public enum ExplorationState{
		CLEAR, //navigable
		OBJECT, //obstacle
		NONE //not-visited
	}
	
	private int x;
	private int y;
	private ExplorationState expS;
	
	public State(int x, int y) {
		super();
		this.x = x;
		this.y = y;
		this.expS = ExplorationState.NONE;
	}
	
	public State(int x, int y, ExplorationState expS) {
		super();
		this.x = x;
		this.y = y;
		this.expS = expS;
	}
	
	public State() {
		super();
		this.x = -1;
		this.y = -1;
		this.expS = ExplorationState.NONE;
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

	public ExplorationState getExpS() {
		return expS;
	}

	public void setExpS(ExplorationState expS) {
		this.expS = expS;
	}
	
	

}
