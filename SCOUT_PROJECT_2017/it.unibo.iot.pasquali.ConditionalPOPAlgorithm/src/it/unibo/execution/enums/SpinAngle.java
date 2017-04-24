package it.unibo.execution.enums;

public enum SpinAngle {
	
	d45(45), d90(90);
	
	private int angle;
	
	private SpinAngle(int angle){ this.angle = angle;}
	
	public int getAngle(){ return angle; }
}
