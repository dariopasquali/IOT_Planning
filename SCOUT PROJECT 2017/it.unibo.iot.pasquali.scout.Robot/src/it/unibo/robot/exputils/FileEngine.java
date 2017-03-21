package it.unibo.robot.exputils;

import it.unibo.domain.model.implementation.ExplorationMap;
import it.unibo.domain.model.implementation.ExplorationState;

public class FileEngine extends Engine {

	// the map on superclass mantain the current explored map
	// this world map is used for the sensing
	
	ExplorationMap worldMap = null;
	
	public FileEngine(int startX, int startY, ExplorationMap worldMap) {
		
		super(startX, startY, worldMap.getXMax(), worldMap.getYMax());
		this.worldMap = worldMap;		
		
	}
	
	// SONAR LIKE CHECKS ---------------------------------
	
	private boolean checkObject(ExplorationState state)
	{
		return worldMap.isCellObj(state.getY(), state.getX());		
	}
	
	public boolean checkObjFront() {

		ExplorationState frontState = moveForwardSafe(state);
		
		return checkObject(frontState);		
	}

	public boolean checkObjLeft() {		
		
		ExplorationState leftState = moveForwardSafe(turnDoubleLeftSafe(state));

		return checkObject(leftState);	
	}

}

