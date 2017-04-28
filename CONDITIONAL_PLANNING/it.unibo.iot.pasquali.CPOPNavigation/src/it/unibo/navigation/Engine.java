package it.unibo.navigation;

import java.awt.Point;
import java.util.HashMap;

import it.unibo.execution.domain.CMove;
import it.unibo.execution.domain.CSpin;
import it.unibo.execution.domain.CStep;
import it.unibo.execution.enums.CDirection;
import it.unibo.execution.enums.SpinAngle;

public class Engine {
	
	
	private Point current;
	private CDirection currentDir;
	
	HashMap<CDirection, CDirection> left = new HashMap<>(),
			rigth = new HashMap<>();

	
	public Engine(Point start, CDirection startDir)
	{
		this.current = start;
		this.currentDir = startDir;
		
		rigth.put(CDirection.NORTH, CDirection.EAST);
		rigth.put(CDirection.EAST, CDirection.SOUTH);
		rigth.put(CDirection.SOUTH, CDirection.WEST);
		rigth.put(CDirection.WEST, CDirection.NORTH);
		
		left.put(CDirection.NORTH, CDirection.WEST);
		left.put(CDirection.EAST, CDirection.NORTH);
		left.put(CDirection.SOUTH, CDirection.EAST);
		left.put(CDirection.WEST, CDirection.SOUTH);
	}
	
	public Point makeMove(CMove move)
	{
		if(move.toString().contains("t"))
			moveForward();
		else
			moveBackward();
		
		return current;
	}

	public CDirection makeSpin(CMove spin)
	{
		if(spin.toString().contains("r"))
			turnRight();
		else
			turnLeft();
		
		return currentDir;
	}
	
	private void moveBackward() {
		
		switch(currentDir)
		{
		case NORTH:
			current = new Point(current.x, current.y+1);
			break;
			
		case EAST:
			current = new Point(current.x-1, current.y);
			break;
			
		case SOUTH:
			current = new Point(current.x, current.y-1);
			break;
			
		case WEST:
			current = new Point(current.x+1, current.y);
			break;
			
		default:
			break;
		}
		
	}

	private void moveForward() {
		
		switch(currentDir)
		{
		case NORTH:
			current = new Point(current.x, current.y-1);
			break;
			
		case EAST:
			current = new Point(current.x+1, current.y);
			break;
			
		case SOUTH:
			current = new Point(current.x, current.y+1);
			break;
			
		case WEST:
			current = new Point(current.x-1, current.y);
			break;
			
		default:
			break;
		}
	}
	
	private void turnLeft(){

		currentDir = left.get(currentDir);
	}

	private void turnRight(){

		currentDir = rigth.get(currentDir);
	}

	public Point getPosition()
	{
		return current;
	}
	
	public CDirection getDirection()
	{
		return currentDir;
	}


}
