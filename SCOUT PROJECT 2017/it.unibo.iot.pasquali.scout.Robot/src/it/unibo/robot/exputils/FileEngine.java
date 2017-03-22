package it.unibo.robot.exputils;

import it.unibo.domain.model.implementation.State;
import it.unibo.domain.model.map.Map;
import it.unibo.planning.astar.domain.Move;
import it.unibo.planning.enums.Direction;
import it.unibo.planning.enums.MoveType;
import it.unibo.robot.Robot;

public class FileEngine extends Engine {

	// the map on superclass mantain the current explored map
	// this world map is used for the sensing
	
	Map worldMap = null;
	
	public FileEngine(int startX, int startY, Map worldMap, Robot actor, boolean checkModifyState) {
		
		super(startX, startY, worldMap.getXmax(), worldMap.getYmax(), actor, checkModifyState);
		this.worldMap = worldMap;
		
	}
	
	// SONAR LIKE CHECKS ---------------------------------
	
	protected boolean checkObject(State state)
	{
		if( worldMap.isCellObj(state.getY(), state.getX()))
		{
			if(checkModifyState)
				super.updateModelAndNotify(state, "object");
			return true;
		}
		else
		{
			if(checkModifyState)
				super.updateModelAndNotify(state, "clear");
			return false;
		}
	}
	
	@Override
	public boolean checkObjFront() {

		State frontState = moveForwardSafe(state);
		
		return checkObject(frontState);		
	}

	@Override
	public boolean checkObjLeft() {		
		
		State leftState = moveForwardSafe(turnDoubleLeftSafe(state));

		return checkObject(leftState);	
	}	
}

