package it.unibo.robot.planutils;

public enum PlanExtension {

	PLAIN_TEXT("txt"),
	PROLOG("pl");
	
	private String extension;
	
	private PlanExtension(String extension) {
		this.extension = extension;
	}
	
	public String getExtension(){
		return extension;
	}
	
}
