/* Generated by AN DISI Unibo */ 
package it.unibo.robot;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import alice.tuprolog.Term;
import it.unibo.domain.model.implmentation.ExplorationMap;
import it.unibo.domain.model.implmentation.ExplorationState;
import it.unibo.domain.model.implmentation.NavigationMap;
import it.unibo.is.interfaces.IOutputEnvView;
import it.unibo.planning.astar.domain.Move;
import it.unibo.planning.enums.Direction;
import it.unibo.planning.enums.ForwardMoveType;
import it.unibo.planning.enums.MoveType;
import it.unibo.planning.enums.SpinDirection;
import it.unibo.qactors.ActorContext;
import it.unibo.qactors.action.AsynchActionResult;
import it.unibo.qactors.action.IActorAction.ActionExecMode;
import it.unibo.robot.planutility.Plan;
import it.unibo.robot.planutility.PlanExtension;
import it.unibo.robot.planutility.PlanSaver;
import it.unibo.robot.planutility.PlanSpinDirection;

public class Robot extends AbstractRobot { 
	
	private NavigationMap navMap;
	private ArrayList<it.unibo.domain.model.State> path;
	
	private it.unibo.domain.model.State position, goal;
	private Direction direction;
	int spinFactor = 1;
	
	private ExplorationMap expMap = null;
	private ExplorationState expState = null;
	
	
	
	private HashMap<String, Move> moveMapping;
	private HashMap<Integer, Direction> spinMap;
	
	public Robot(String actorId, ActorContext myCtx, IOutputEnvView outEnvView ,it.unibo.iot.executors.baseRobot.IBaseRobot baserobot) throws Exception{
		super(actorId,myCtx,outEnvView ,baserobot );
		
		this.moveMapping = new HashMap<String, Move>();
		moveMapping.put("forwardN", new Move(ForwardMoveType.TILED));
		moveMapping.put("forwardE", new Move(ForwardMoveType.TILED));
		moveMapping.put("forwardW", new Move(ForwardMoveType.TILED));
		moveMapping.put("forwardS", new Move(ForwardMoveType.TILED));
		moveMapping.put("forwardNE", new Move(ForwardMoveType.DIAGONAL));
		moveMapping.put("forwardNW", new Move(ForwardMoveType.DIAGONAL));
		moveMapping.put("forwardSE", new Move(ForwardMoveType.DIAGONAL));
		moveMapping.put("forwardSW", new Move(ForwardMoveType.DIAGONAL));
		moveMapping.put("leftN", new Move(SpinDirection.LEFT));
		moveMapping.put("leftNE", new Move(SpinDirection.LEFT));
		moveMapping.put("leftE", new Move(SpinDirection.LEFT));
		moveMapping.put("leftSE", new Move(SpinDirection.LEFT));
		moveMapping.put("leftS", new Move(SpinDirection.LEFT));
		moveMapping.put("leftSW", new Move(SpinDirection.LEFT));
		moveMapping.put("leftW", new Move(SpinDirection.LEFT));
		moveMapping.put("leftNW", new Move(SpinDirection.LEFT));
		moveMapping.put("rightN", new Move(SpinDirection.RIGHT));
		moveMapping.put("rightNE", new Move(SpinDirection.RIGHT));
		moveMapping.put("rightE", new Move(SpinDirection.RIGHT));
		moveMapping.put("rightSE", new Move(SpinDirection.RIGHT));
		moveMapping.put("rightS", new Move(SpinDirection.RIGHT));
		moveMapping.put("rightSW", new Move(SpinDirection.RIGHT));
		moveMapping.put("rightW", new Move(SpinDirection.RIGHT));
		moveMapping.put("rightNW", new Move(SpinDirection.RIGHT));
		
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
	
	// INITIALIZATION - NAVIGATION **************************************************
	
	public void createMap(int x, int y)
	{
		navMap = new NavigationMap(x,y);
	}
	
	public void setMapElements(List<String> elements)
	{
		navMap.addElementsFromList(elements);
	}
	
	public void setMapElements(String elements)
	{
		navMap.addElementsFromString(elements);
	}
	
	// INITIALIZATION - EXPLORATION *************************************************
	
	public void initExploreMap(int startX, int startY, int mapWidth, int mapHeight)
	{
		// for debug use
		// i know the initial position referred to a well defined map
		
		
		expMap.setCellClear(expState.getY(), expState.getX());
		notifyClearCell();
	}
	

	public void initExploreMap()
	{
		//TODO
	}
	
	
	// EXPLORATION MANAGEMENT **********************************************
	
	
	
	
	
	// EXPLORATION INTERACTION ************************************************
	
	private void notifyClearCell() {
		// TODO Auto-generated method stub
		
	}
	
	
	
	
	
	//GUI INTERACTION **************************************************
	
	public void showPathOnGui()
	{
		for(it.unibo.domain.model.State s : path)
		{
			System.out.println(s.toString());
		}
	}
	
	
	// NAVIGATION MANAGEMENT ****************************************************
	
	public void setNavigationPlan(String planName, String algo, String plan, int s, int t)
	{
		//NB: rotation step is 45 degrees
		
		String speed = ""+s;
		String time = ""+t;
		String diagoTime = ""+(Math.round(t*1.414));
		
		plan = plan.split("\\[")[1];
		plan = plan.split("\\]")[0];
		
		println("Salvataggio piano in corso");
		println(plan);
		println(speed);
		println(time);
		
		Plan pathPlan = new Plan("path");
		
		pathPlan.addPrint("Inizio Navigazione");
		
		String[] moves = plan.split(",");
		
		for(String m : moves)
		{
			switch(m)
			{
			case "t":
				pathPlan.addSenseEvent(1000, "obstacle", "waitAndEvaluate");
				pathPlan.addForwardMove(speed, time);
				break;
				
			case "d":
				pathPlan.addSenseEvent(1000, "obstacle", "waitAndEvaluate");
				pathPlan.addForwardMove(speed, diagoTime);
				break;
				
			case "l":
				pathPlan.addSpinMove(speed, time, PlanSpinDirection.LEFT);
				break;
				
			case "r":
				pathPlan.addSpinMove(speed, time, PlanSpinDirection.RIGHT);
				break;
				
			case "dl":
				pathPlan.addSpinMove(speed, time, PlanSpinDirection.LEFT);
				pathPlan.addSpinMove(speed, time, PlanSpinDirection.LEFT);
				break;
				
			case "dr":
				pathPlan.addSpinMove(speed, time, PlanSpinDirection.RIGHT);
				pathPlan.addSpinMove(speed, time, PlanSpinDirection.RIGHT);
				break;
			}			
		}
		
		pathPlan.addPrint("fine Navigazione");
		pathPlan.addSolve("continueProgram", ""+0);
		
		PlanSaver planSaver = new PlanSaver(planName, PlanExtension.PLAIN_TEXT);
		planSaver.addPlan(pathPlan);
		planSaver.storePlan();
		
		
		println(planSaver.getPlans());
		println("PLAN SAVED IN "+planSaver.getFileName());		
	}
	
	public void setPosition(int sx, int sy)
	{
		this.position =	new it.unibo.domain.model.State(sx, sy);		
		this.direction = Direction.NORTH;
		
		println(position.toString());
	}
	
	public void updateMyPosition(String move)
	{
		this.position = this.makeMove(position, moveMapping.get(move+direction.toString()));
		System.out.println(position);
	}
	
	
	private it.unibo.domain.model.State makeMove(it.unibo.domain.model.State state, Move move)
	{
		it.unibo.domain.model.State result = new it.unibo.domain.model.State();
		
		
		if(move.getType().equals(MoveType.SPIN))
		{
			result.setX(state.getX());
			result.setY(state.getY());
			
			result.setDirection(makeSpin(state.getDirection(), move.getSpin()));
		}
		else
		{
			int x = state.getX();
			int y = state.getY();
			
			switch(state.getDirection())
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
			
			result.setDirection(state.getDirection());
		}
		return result;
	}
	
	public Direction makeSpin(Direction start, SpinDirection spin) {
		int newID = (start.getValue() + (8+spinFactor*spin.getRotation()))%8;
		return spinMap.get(newID);
	}
	
	
	// SYSTEM INTERACTION *****************************************************
		
	public void raiseEvent(String emitterName, String eventName, String payload)
	{
		platform.raiseEvent(emitterName, eventName, payload);
	}

	public void mySenseEvent(int timeout, String eventsList, String plansList)
	{
		eventsList = eventsList.replace("[","");
		eventsList = eventsList.replace("]","");
		plansList = plansList.replace("[","");
		plansList = plansList.replace("]","");
		
		try
		{
			AsynchActionResult aar1 =
					senseEvents( timeout,eventsList, plansList,	"" , "",ActionExecMode.synch );
			
			if( ! aar.getGoon() || aar.getTimeRemained() <= 0 )
			{
    			println("WARNING: sense timeout");
    			addRule("tout(senseevent,"+getName()+")");
    		}			
		}
		catch (Exception e) {
			
			e.printStackTrace();
		}
	    		
	}
	
	public void sendDispatch(String msgName, String paramsList, String destActor)
	{
		String payload = msgName+"(";
		
		paramsList = paramsList.replace("[", "");
		paramsList = paramsList.replace("]", "");
		payload += paramsList;
		
		payload += ")";
		
		try
		{
			sendMsg(msgName, destActor, ActorContext.dispatch, payload );
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		
		
	}
	
	public void receiveMessageAndSolve(int timeout,
										String msgList,
										String msgPayloadList,
										String goalList,
										int solveTime)
	{
		
		try
		{
			AsynchActionResult aar = receiveAMsg(timeout, "" , "" ); 	//could block
			
			if( aar.getInterrupted() )
			{
			
				curPlanInExec   = "playTheGame";
				
				if( ! aar.getGoon() ) return;
			} 			
			if( ! aar.getGoon() )
			{
				System.out.println("WARNING: receiveMsg in " + getName() + " TOUT " + aar.getTimeRemained() + "/" +  timeout);
				addRule("tout(receive,"+getName()+")");
			}
			
			printCurrentMessage(false);
			
			msgList = msgList.replace("[", "");
			msgList = msgList.replace("]", "");
			
			msgPayloadList = msgPayloadList.replace("[", "");
			msgPayloadList = msgPayloadList.replace("]", "");
			
			goalList = goalList.replace("[", "");
			goalList = goalList.replace("]", "");
			
			String[] msg = msgList.split(",");
			String[] msgPayload = msgPayloadList.split(",");
			String[] goal = goalList.split(",");
			
			for(int i=0; i<msg.length; i++)
			{
				if(currentMessage.msgId().equals(msg[i]))
				{
					String parg = goal[i];
					parg = updateVars(null, Term.createTerm(msgPayload[i]), Term.createTerm(msgPayload[i]), 
		  					Term.createTerm(currentMessage.msgContent()), parg);
					
					if( parg != null ) {
    					aar = solveGoal( parg , solveTime, "","" , "");
    								
    					if( aar.getResult().equals("failure")){
    						if( ! switchToPlan("prologFailure").getGoon() ) return;
    					}else if( ! aar.getGoon() ) return;
    				}
				}
			}
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		
		
	}
	
	
}
