package it.unibo.robot.planutility;

public enum PlanSpinDirection {

	RIGHT("mr"),
	LEFT("ml");

	private String direction;
	
	private PlanSpinDirection(String dir){
		this.direction = dir;
	}

	public String getDirection()
	{
		return direction;
	}
}
