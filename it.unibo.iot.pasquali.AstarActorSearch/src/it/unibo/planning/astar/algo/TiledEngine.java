package it.unibo.planning.astar.algo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import it.unibo.planning.astar.domain.Move;
import it.unibo.planning.astar.enums.*;
import it.unibo.planning.astar.interfaces.IEngine;
import it.unibo.planning.astar.domain.State;
import it.unibo.qactors.action.AsynchActionResult;
import it.unibo.qactors.planned.QActorPlanned;

public class TiledEngine implements IEngine{
	
	private QActorPlanned actor;
	private State goal;
	private int DEFAULT_SPEED;
	private int DEFAULT_DURATION;
	
	String elements="";
	int xmax = -1;
	int ymax = -1;
	
	private HashMap<Integer, Direction> spinMap;
	
	
	public TiledEngine(QActorPlanned actor)
	{
		this.actor = actor;
		this.goal = null;
		loadParams();
	}
	
	@Override
	public void loadParams()
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
		
		spinMap = new HashMap<Integer, Direction>();
		spinMap.put(0, Direction.NORTH);
		spinMap.put(2, Direction.EAST);
		spinMap.put(4, Direction.SOUTH);
		spinMap.put(6, Direction.WEST);
	}
	
	@Override
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
	
	@Override
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
	
	@Override
	public State makeMove(State start, Move move)
	{
		State result = new State();
		int cost = 10;
		
		if(move.getType().equals(MoveType.SPIN))
		{
			result.setX(start.getX());
			result.setY(start.getY());
			
			if(!isValidState(result))
				return null;
			
			result.setDirection(makeSpin(start.getDirection(), move.getSpin()));
			
			cost = move.getSpin().getCost();
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
			cost = 10;
		}
		
		result.setCost(start.getCost()+cost);
		result.setGenMove(move);
		result.setHeuristic(evaluateState(result));
		result.setXmax(xmax);
		
		return result;
	}
	
	@Override
	public double evaluateState(State state) {
		//distanza manatthan
		
		double catX = Math.abs(goal.getX()-state.getX());
		double catY = Math.abs(goal.getY()-state.getY());
		
		return catX + catY;	
	}

	@Override
	public Direction makeSpin(Direction start, SpinDirection spin)
	{
		int newID = (start.getValue() + (8+2*spin.getRotation()))%8;
		return spinMap.get(newID);
	}

	@Override
	public List<Move> getPossibleMoves(State state)
	{
		/*
		 * move forward
		 * move right
		 * move left
		 * move doubleright
		 * move doubleleft
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
		possible.add(new Move(SpinDirection.DOUBLELEFT));
		possible.add(new Move(SpinDirection.DOUBLERIGHT));
		
		return possible;
	}

	@Override
	public boolean isGoalState(State current) {
		if(current == null)
			return false;
		
		if(current.getX() == goal.getX() && current.getY() == goal.getY())
			return true;
		else
			return false;
	}

	@Override
	public void setGoal(State goal) {
		this.goal = goal;		
	}
	
	
}
