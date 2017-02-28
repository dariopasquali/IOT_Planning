package it.unibo.robot.planutility;

public enum PlanMoveDirection {
	
	FORWARD("mf"),
	BACKWARD("mb"),
	HALT("mh");
	
	private String direction;
	
	private PlanMoveDirection(String dir){
		this.direction = dir;
	}

	public String getDirection()
	{
		return direction;
	}
}
