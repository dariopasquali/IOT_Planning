package it.unibo.planning.astar.algo;

import java.util.ArrayList;
import java.util.List;

import it.unibo.planning.astar.domain.Move;
import it.unibo.planning.astar.domain.Move.MoveType;
import it.unibo.planning.astar.domain.Move.SpinDirection;
import it.unibo.planning.astar.domain.State;
import it.unibo.planning.astar.domain.State.Direction;
import it.unibo.qactors.action.AsynchActionResult;
import it.unibo.qactors.planned.QActorPlanned;

public class Engine {
	
	private QActorPlanned actor;
	private State goal;
	private int DEFAULT_SPEED;
	private int DEFAULT_DURATION;
	
	String elements="";
	int xmax = -1;
	int ymax = -1;
	
	public Engine(QActorPlanned actor, State goal)
	{
		this.actor = actor;
		this.goal = goal;
		loadParams();
	}
	
	public Engine(QActorPlanned actor)
	{
		this.actor = actor;
		loadParams();
	}
	
	private void loadParams()
	{
		AsynchActionResult aar;
		
		String parg = "defaultSpeed(S)";
		try
		{
			aar = actor.solveGoal(parg, 0, "", "", "");
			String s[] = aar.getResult().split("\\(");
			String s1[] = s[1].split("\\)");
			
			
			this.DEFAULT_SPEED = Integer.parseInt(s1[0]);			
			
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		
		parg = "defaultDuration(D)";
		try
		{
			aar = actor.solveGoal(parg, 0, "", "", "");
			String s[] = aar.getResult().split("\\(");
			String s1[] = s[1].split("\\)");
			
			
			this.DEFAULT_DURATION = Integer.parseInt(s1[0]);		
			
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public boolean isValidState(State state)
	{
		if(xmax == -1 || ymax==-1 || elements.equals(""))
		{
			AsynchActionResult aar;
			
			String parg = "map(Xmax,Ymax)";
			try
			{
				aar = actor.solveGoal(parg, 0, "", "", "");
				String s[] = aar.getResult().split(",");
				String sl[] = s[0].split("\\(");
				String sr[] = s[1].split("\\)");
				
				xmax = Integer.parseInt(sl[1]);
				ymax = Integer.parseInt(sr[0]);
				
				parg = "getElements(List)";
				aar = actor.solveGoal(parg, 0, "", "", "");
				
				String s0[] = aar.getResult().split("\\[");
				elements = s0[1].split("\\]")[0];
				
			} catch (Exception e)
			{
				e.printStackTrace();
				return false;
			}
		}
		return (state.getX() >= 0 &&
				state.getX() <= xmax &&
				state.getY() >= 0 &&
				state.getY() <= ymax &&
				!elements.contains("element("+state.getX()+","+state.getY()+")"));		
	}
	
	
	public boolean isValidStatePrologCheck(State state)
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
	
	public State makeMove(State start, Move move)
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
				result.setY(y-1);
				break;
				
			case EAST:
				result.setX(x+1);
				result.setY(y);
				break;
				
			case SOUTH:
				result.setX(x);
				result.setY(y+1);
				break;
				
			case WEST:
				result.setX(x-1);
				result.setY(y);
				break;
				
			default:
				break;
			}
			
			if(!isValidState(result))
				return null;
			
			result.setDirection(start.getDirection());			
		}
		
		result.setCost(start.getCost()+1);
		result.setGenMove(move);
		result.setHeuristic(evaluateState(result));
		result.setXmax(xmax);
		
		return result;
	}
	
	private double evaluateState(State state) {
		//distanza in linea d'aria (pitagora)
		
		double catX = Math.abs(goal.getX()-state.getX());
		double catY = Math.abs(goal.getY()-state.getY());
		
		double H = Math.sqrt(Math.pow(catX, 2) + Math.pow(catY, 2));		
		return H;		
	}

	private Direction makeSpin(Direction start, SpinDirection dir)
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

	public List<Move> getPossibleMoves(State state)
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
			y -= 1;
			break;
			
		case EAST:
			x += 1;
			break;
			
		case SOUTH:
			y += 1;
			break;
			
		case WEST:
			x -= 1;
			break;
			
			default:
				break;
		}
		
		State s = new State();
		s.setX(x);
		s.setY(y);
		
		if(isValidState(s))
			possible.add(new Move());
		
		possible.add(new Move(SpinDirection.RIGHT));
		possible.add(new Move(SpinDirection.LEFT));
		
		return possible;
	}

	
	public boolean isGoalState(State current) {
		if(current == null)
			return false;
		
		if(current.getX() == goal.getX() && current.getY() == goal.getY())
			return true;
		else
			return false;
	}
	
	
}
