package it.unibo.robot.utility;

import it.unibo.domain.model.implementation.State;
import it.unibo.model.map.Map;
import it.unibo.robot.Robot;

public class FileEngine extends Engine {

	// the map on superclass maintain the current explored map
	// this world map is used for the sensing
	
	Map worldMap = null;
	
	public FileEngine(int startX, int startY, Map worldMap, Robot actor, boolean checkModifyState) {
		
		super(startX, startY, worldMap.getXmax(), worldMap.getYmax(), actor, checkModifyState);
		this.worldMap = worldMap;
		
	}
	
	public FileEngine(int sx, int sy, Robot robot) {
		super(sx, sy, robot);
		this.worldMap = null;
	}
	
	// SONAR LIKE CHECKS ---------------------------------
	
	public void setObject(State s)
	{
		System.out.println("World Changed --> " + s.getCoordinates().toString());
		worldMap.setCellObj(s.getY(), s.getX());
	}

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

	public Object getWorldMap() {
		return worldMap;
	}	
}

