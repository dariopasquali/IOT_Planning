package it.unibo.planning.astar.algo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import it.unibo.planning.astar.domain.Move;
import it.unibo.planning.astar.domain.State;
import it.unibo.planning.astar.enums.Direction;
import it.unibo.planning.astar.enums.ForwardMoveType;
import it.unibo.planning.astar.enums.MoveType;
import it.unibo.planning.astar.enums.SpinDirection;
import it.unibo.planning.astar.interfaces.IEngine;
import it.unibo.qactors.action.AsynchActionResult;
import it.unibo.qactors.planned.QActorPlanned;

public class TiledDiagonalEngine implements IEngine{

	private QActorPlanned actor;
	private State goal;
	private int DEFAULT_SPEED;
	private int DEFAULT_DURATION;
	
	String elements="";
	int xmax = -1;
	int ymax = -1;	
	
	private HashMap<Integer, Direction> spinMap;
	
		
	public TiledDiagonalEngine(QActorPlanned actor)
	{
		this.actor = actor;
		this.goal = null;
		loadParams();
	}
	
		
	@Override
	public void loadParams() {
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
		spinMap.put(1, Direction.NORTH_EAST);
		spinMap.put(2, Direction.EAST);
		spinMap.put(3, Direction.SOUTH_EAST);
		spinMap.put(4, Direction.SOUTH);
		spinMap.put(5, Direction.SOUTH_WEST);
		spinMap.put(6, Direction.WEST);
		spinMap.put(7, Direction.NORTH_WEST);
	}

	@Override
	public boolean isValidState(State state) {
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
	public boolean isValidStatePrologCheck(State state) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public State makeMove(State start, Move move) {
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
			
			case NORTH_EAST:
				result.setX(x+1);
				result.setY(y-1);
				break;
				
			case EAST:
				result.setX(x+1);
				result.setY(y);
				break;
				
			case SOUTH_EAST:
				result.setX(x+1);
				result.setY(y+1);
				break;
				
			case SOUTH:
				result.setX(x);
				result.setY(y+1);
				break;
				
			case SOUTH_WEST:
				result.setX(x-1);
				result.setY(y+1);
				break;
				
			case WEST:
				result.setX(x-1);
				result.setY(y);
				break;
				
			case NORTH_WEST:
				result.setX(x-1);
				result.setY(y-1);
				break;
				
			default:
				break;
			}
			
			if(!isValidState(result))
				return null;
			
			result.setDirection(start.getDirection());	
			cost = move.getForwardType().getCost();
		}
		
		result.setCost(start.getCost()+cost);
		result.setGenMove(move);
		result.setHeuristic(evaluateState(result));
		result.setXmax(xmax);
		
		return result;
	}

	@Override
	public Direction makeSpin(Direction start, SpinDirection spin) {
		int newID = (start.getValue() + (8+spin.getRotation()))%8;
		return spinMap.get(newID);
	}

	@Override
	public List<Move> getPossibleMoves(State state) {
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
		
		boolean checkLateral = false;
		State l1 = null;
		State l2 = null;
		
		switch(state.getDirection())
		{
		case NORTH:
			y -= 1;
			break;
		
		case NORTH_EAST:
			checkLateral = true;
			l1 = new State(x, y-1);
			l2 = new State(x+1, y);
			y -= 1;
			x += 1;
			break;
			
		case EAST:
			x += 1;
			break;
			
		case SOUTH_EAST:
			checkLateral = true;
			l1 = new State(x+1, y);
			l2 = new State(x, y+1);
			y += 1;
			x += 1;
			break;
			
		case SOUTH:
			y += 1;
			break;
			
		case SOUTH_WEST:
			checkLateral = true;
			l1 = new State(x, y+1);
			l2 = new State(x-1, y);
			y += 1;
			x -= 1;
			break;
			
		case WEST:
			x -= 1;
			break;
			
		case NORTH_WEST:
			checkLateral = true;
			l1 = new State(x, y-1);
			l2 = new State(x-1, y);
			y -= 1;
			x -= 1;
			break;
			
			default:
				break;
		}
		
		State s = new State();
		s.setX(x);
		s.setY(y);
		
		if(isValidState(s))
		{	if(!checkLateral)
				possible.add(new Move());
			else if((isValidState(l1) && isValidState(l2)))
					possible.add(new Move(ForwardMoveType.DIAGONAL));
		}
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
	public double evaluateState(State state) {
		//distanza manatthan
		
		double catX = Math.abs(goal.getX()-state.getX());
		double catY = Math.abs(goal.getY()-state.getY());
		
		return catX + catY;	
	}


	@Override
	public void setGoal(State goal) {
		this.goal = goal;		
	}

}
