package it.unibo.planning.astar.algo;

import java.util.ArrayList;
import java.util.List;

import it.unibo.console.Console;
import it.unibo.planning.astar.domain.Move;
import it.unibo.planning.astar.domain.Move.MoveType;
import it.unibo.planning.astar.domain.Move.SpinDirection;
import it.unibo.planning.astar.domain.State;
import it.unibo.planning.astar.domain.State.Direction;
import it.unibo.qactors.action.AsynchActionResult;

public class Engine {
	
	public static Console actor;
	public static State goal;
	public static final int DEFAULT_SPEED = 60;
	public static final int DEFAULT_DURATION = 500;
	
	public static boolean isValidState(State state)
	{
		AsynchActionResult aar;
		
		String parg = "checkValidState("+state.getX()+","+state.getY()+")";
		try
		{
			aar = actor.solveGoal(parg, 0, "", "", "");
			if(aar.getResult().contains("failure"))
				return false;
			else
				return true;
			
		} catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}
	
	public static State makeMove(State start, Move move)
	{
		State result = new State();
		
		if(move.getType().equals(MoveType.SPIN))
		{
			result.setX(start.getX());
			result.setY(start.getY());
			
			if(!isValidState(result))
				return null;
			
			result.setDirection(makeSpin(start.getDirection(), move.getSpin()));			
		}
		else
		{
			int x = start.getX();
			int y = start.getY();
			
			switch(start.getDirection())
			{
			case NORTH:
				result.setX(x);
				result.setY(y+1);
				
			case EAST:
				result.setX(x+1);
				result.setY(y);
				
			case SOUTH:
				result.setX(x);
				result.setY(y-1);
				
			case WEST:
				result.setX(x-1);
				result.setY(y);
				default:
			}
			
			if(!isValidState(result))
				return null;
			
			result.setDirection(start.getDirection());			
		}
		
		result.setCost(start.getCost()+1);
		result.setGenMove(move);
		result.setHeuristic(evaluateState(result));
		
		return result;
	}
	
	private static double evaluateState(State state) {
		//distanza in linea d'aria (pitagora)
		
		double catX = Math.abs(goal.getX()-state.getX());
		double catY = Math.abs(goal.getY()-state.getY());
		
		double H = Math.sqrt(Math.pow(catX, 2) + Math.pow(catY, 2));		
		return H;		
	}

	private static Direction makeSpin(Direction start, SpinDirection dir)
	{
		switch(start)
		{
		case NORTH:
			if(dir.equals(SpinDirection.LEFT))
				return Direction.WEST;
			else
				return Direction.EAST;
			
		case EAST:
			if(dir.equals(SpinDirection.LEFT))
				return Direction.NORTH;
			else
				return Direction.SOUTH;
			
		case WEST:
			if(dir.equals(SpinDirection.LEFT))
				return Direction.SOUTH;
			else
				return Direction.NORTH;
			
		case SOUTH:
			if(dir.equals(SpinDirection.LEFT))
				return Direction.EAST;
			else
				return Direction.WEST;
			
		default: return null;
		}
	}

	public static List<Move> getPossibleMoves(State state)
	{
		/*
		 * move forward
		 * move right
		 * move left
		 * 
		 * NEVER GO BACK!!!!!!!!!
		 */
		
		ArrayList<Move> possible = new ArrayList<Move>();
		
		int x = state.getX();
		int y = state.getY();
		
		switch(state.getDirection())
		{
		case NORTH:
			y += 1;
			
		case EAST:
			x += 1;
			
		case SOUTH:
			y -= 1;
			
		case WEST:
			x -= 1;
			default:
		}
		
		State s = new State();
		s.setX(x);
		s.setY(y);
		
		if(isValidState(s))
			possible.add(new Move(DEFAULT_SPEED, DEFAULT_DURATION));		
		possible.add(new Move(SpinDirection.RIGHT, DEFAULT_SPEED));
		possible.add(new Move(SpinDirection.LEFT, DEFAULT_SPEED));
		
		return possible;
	}

	
	public static boolean isGoalState(State current) {
		if(current == null)
			return false;
		
		if(current.getX() == goal.getX() && current.getY() == goal.getY())
			return true;
		else
			return false;
	}
	
	
}
