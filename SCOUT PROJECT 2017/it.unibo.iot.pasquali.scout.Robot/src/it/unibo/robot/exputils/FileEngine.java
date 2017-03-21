package it.unibo.robot.exputils;

import it.unibo.domain.model.implementation.State;
import it.unibo.domain.model.map.Map;

public class FileEngine extends Engine {

	// the map on superclass mantain the current explored map
	// this world map is used for the sensing
	
	Map worldMap = null;
	
	public FileEngine(int startX, int startY, Map worldMap) {
		
		super(startX, startY, worldMap.getXmax(), worldMap.getYmax());
		this.worldMap = worldMap;		
		
	}
	
	// SONAR LIKE CHECKS ---------------------------------
	
	private boolean checkObject(State state)
	{
		return worldMap.isCellObj(state.getY(), state.getX());		
	}
	
	public boolean checkObjFront() {

		State frontState = moveForwardSafe(state);
		
		return checkObject(frontState);		
	}

	public boolean checkObjLeft() {		
		
		State leftState = moveForwardSafe(turnDoubleLeftSafe(state));

		return checkObject(leftState);	
	}

}

