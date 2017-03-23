/* Generated by AN DISI Unibo */ 
package it.unibo.robot;


import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import alice.tuprolog.Term;
import it.unibo.contactEvent.interfaces.IEventItem;
import it.unibo.domain.model.implementation.State;
import it.unibo.domain.model.map.Map;
import it.unibo.iot.configurator.Configurator;
import it.unibo.iot.models.sensorData.SensorType;
import it.unibo.iot.models.sensorData.distance.IDistanceSensorData;
import it.unibo.iot.sensors.ISensor;
import it.unibo.iot.sensors.distanceSensor.DistanceSensor;
import it.unibo.is.interfaces.IOutputEnvView;
import it.unibo.qactors.action.AsynchActionResult;
import it.unibo.qactors.action.IActorAction.ActionExecMode;
import it.unibo.qactors.akka.QActorPlanUtils;
import it.unibo.robot.exploration.algo.Explorer;
import it.unibo.robot.planutils.*;
import it.unibo.robot.utility.*;
import it.unibo.qactors.QActorContext;
import it.unibo.qactors.QActorUtils;

//
/**
 * @author Dario
 *
 */
/**
 * @author Dario
 *
 */
public class Robot extends AbstractRobot { 
	
	private final static String MODE_SIMULATED = "simulated";
	private final static String MODE_REAL_ROBOT = "robot";
	
	private int defaultSpeed, defaultTime;
	private int defaultTurnSpeed, defaultTurnTime;
	
	private ArrayList<State> path;
	
	private Engine engine = null;	
	
	//private HashMap<Integer, Direction> spinMap;
	
//{{ CONSTRUCTOR
	
	public Robot(String actorId, QActorContext myCtx, IOutputEnvView outEnvView ) throws Exception
	{
		super(actorId,myCtx,outEnvView ,it.unibo.qactors.QActorUtils.robotBase );
		
		QActorPlanUtils myUtils = new QActorPlanUtilsDebug(this, actionUtils, outEnvView);
		
		this.planUtils = myUtils;		
		/*
		spinMap = new HashMap<Integer, Direction>();
		spinMap.put(0, Direction.NORTH);
		spinMap.put(1, Direction.NORTH_EAST);
		spinMap.put(2, Direction.EAST);
		spinMap.put(3, Direction.SOUTH_EAST);
		spinMap.put(4, Direction.SOUTH);
		spinMap.put(5, Direction.SOUTH_WEST);
		spinMap.put(6, Direction.WEST);
		spinMap.put(7, Direction.NORTH_WEST);
		*/
	}

//}}

	
//{{ INITIALIZATION - COMMON ******************************************************
	
	/**
	 * To config the speed and duration of the movements.
	 * Those params are defined in the prolog files navRobotTheory.pl and expRobotTheory.pl.
	 * You can change that without recompiling all the project. 
	 * 
	 * @param defSpeed		default forward/backward speed
	 * @param defTime		default forward/backward time
	 * @param defTurnSpeed	default rotate speed (different between exp and nav)
	 * @param defTurnTime	default rotate time (different between exp and nav)
	 */
	public void initialConfigRobot(int defSpeed, int defTime, int defTurnSpeed, int defTurnTime)
	{
		this.defaultSpeed = defSpeed;
		this.defaultTime = defTime;
		this.defaultTurnSpeed = defTurnSpeed;
		this.defaultTurnTime = defTurnTime;
	}
//}}
	
	
//{{ INITIALIZATION - NAVIGATION **************************************************
		
	/** 
	 * Redefined in order to add my personal events
	 * 
	 * @see it.unibo.robot.AbstractRobot#addSensorObservers()
	 */
	@Override
	protected void addSensorObservers(){
		
		Set<ISensor<?>> sensors = Configurator.getInstance().getSensors(SensorType.DISTANCE);
		
		for(ISensor<?> sense : sensors)
		{
			DistanceSensor s = (DistanceSensor)sense;
			s.addObserver(new SensorObserver<IDistanceSensorData>(this,outEnvView,s.getPosition().getDefStringRep(), 5));
		}
		
	}
//}}	

	
//{{ INITIALIZATION - EXPLORATION *************************************************
	
	/**
	 * Debug exploration, i know the initial position referred to a well defined map.</br>
	 * Sensing and Movements are on the real world.</br>
	 * This is the method used with the raspberry Pi.<br>
	 * 
	 * It use the java Explorer.
	 * 
	 * @param startX		initial X position
	 * @param startY		initial Y position
	 * @param mapWidth		map width (X)
	 * @param mapHeight		map height (Y)
	 */
	public void initExploreMap(int startX, int startY, int mapWidth, int mapHeight){
		engine = new Engine(startX, startY, mapWidth, mapHeight, this, true);
		
		Explorer explorer = new Explorer(this, engine);
		explorer.startExploration();
		
		System.out.println("EXPLORATION DONE");	
	}
	
	/**
	 * Normal Exploration Method, without any information
	 */
	public void initExploreMap(){
		//TODO
	}
	
	/**
	 * Debug exploration, i know the initial position referred to a well defined map.
	 * Sensing and Movements are on a simulated prolog Map.
	 * This is the method used for debug simulation
	 *  
	 * @param startX		initial X position
	 * @param startY		initial Y position
	 * @param filename		absolute path of the map file, received from the Console
	 */
	public void initExploreFile(int startX, int startY, String filename){
		Map m = null;
		
		List<String> data = new ArrayList<String>();						
		try
		{
			InputStream fs = new FileInputStream(filename);
			InputStreamReader inpsr = new InputStreamReader(fs);
			BufferedReader br       = new BufferedReader(inpsr);
			Iterator<String> lsit   = br.lines().iterator();

			while(lsit.hasNext())
			{
				data.add(lsit.next());
			}
			br.close();
			
		} catch (Exception e)
		{
			System.out.println("QActor  ERROR " + e.getMessage());
		}
		
		for(int i=0; i<data.size(); i++)
		{
			if(i == 0)
			{
				m = Map.createMapFromPrologRep(data.get(i));
			}
			else
			{
				String s[] = data.get(i).split(" ");
				m.addElementFromString(s[1]);
			}
		}
		
		engine = new FileEngine(startX, startY, m, this, true);
		((QActorPlanUtilsDebug)planUtils).setEngine((FileEngine) engine);
		
		Explorer explorer = new Explorer(this, engine);
		explorer.startExploration();
		
		System.out.println("EXPLORATION DONE");		
	}
	
//}}
	
	
//{{ EXPLORATION MANAGEMENT **********************************************
	
/*
	public void makeMove(String direction)
	{
		if(direction.equals("forward"))
			engine.moveForward();
		else
			engine.moveBackward();
		
		State s = engine.getState();
		
		System.out.println(s.toString());
		
		String payload = "position("+s.getX() + "," + s.getY() + ")," +
				s.getDirection().toString().toLowerCase();
		
		emit("show", "show(" + payload + ")");
	}
	
	public void turn(String spinDir)
	{
		if(spinDir.equals("doubleRight"))
			engine.turnDoubleRight();
		else
			engine.turnDoubleLeft();
		
		State s = engine.getState();
		
		System.out.println(s.toString());
		
		String payload = "position("+s.getX() + "," + s.getY() + ")," +
				s.getDirection().toString().toLowerCase();
		
		emit("show", "show(" + payload + ")");
	}
	
	public void addCurrentToVisited()
	{
		engine.addCurrentToVisited();
	}
	
	public boolean checkCurrentAlreadyVisited()
	{
		return engine.isCurrentAlreadyVisited();
	}	
	
	public boolean checkLeftVisited()
	{
		return engine.checkExploredLeft();
	}	
	
	public void updateModel(String dir, String state)
	{
		State next = engine.checkAndUpdate(dir, state);
		
		this.newCellX = next.getX();
		this.newCellY = next.getY();
		
		//return "position("+next.getX()+","+next.getY()+")";
	}
	
	public int getNewCellX()
	{
		return newCellX;
	}
	
	public int getNewCellY()
	{
		return newCellY;
	}
	
*/	
//}}


//{{ NAVIGATION MANAGEMENT ****************************************************
	
	/**
	 * Create a runnable Plan from the representation received from the Console,
	 * then store it in a proper file that can be loaded and executed in every moment.</br>
	 * 
	 * It uses the default speed and duration, defined in the initial config method.</br>
	 * 
	 * <b>WARNING</b>: the default rotation angle is 45°;</br>
	 * <b>WARNING</b>: The diagonal moments require more time then the tiled moments.
	 * 
	 * @param planName	The name of the executable plan and the relative file
	 * @param plan		prolog list [m1, m2, ...] of movements
	 */
	public void setNavigationPlan(String planName, String plan)	{
		
		String speed = ""+defaultSpeed;
		String time = ""+defaultTime;
		String diagoTime = ""+(Math.round(defaultTime*1.414));
		
		plan = plan.split("\\[")[1];
		plan = plan.split("\\]")[0];
		
		println("Salvataggio piano in corso");
		println(plan);
		println(speed);
		println(time);
		
		Plan pathPlan = new Plan(planName);
		
		pathPlan.addPrint("Inizio Navigazione");
		
		String[] moves = plan.split(",");
		
		for(String m : moves)
		{
			switch(m)
			{
			case "t":
				pathPlan.addSenseEvent(1000, "obstaclefront", "waitAndEvaluate");
				pathPlan.addForwardMove(speed, time);
				break;
				
			case "d":
				pathPlan.addSenseEvent(1000, "obstaclefront", "waitAndEvaluate");
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
		pathPlan.addSwitchPlan("notifyEndOfNavigation");
		
		PlanSaver planSaver = new PlanSaver(planName, PlanExtension.PLAIN_TEXT);
		planSaver.addPlan(pathPlan);
		planSaver.storePlan();
		
		
		println(planSaver.getPlans());
		println("PLAN SAVED IN "+planSaver.getFileName());		
	}
	
	
	/**
	 * Configure the Engine that manage the dynamically update of the robot position</br>
	 * Robot doesn't need to know the map, it's a stupid executor that simply move on it.
	 * 
	 * @param sx	start X position
	 * @param sy	start Y position
	 * @param mode	<i>"robot"</i> for a real robot or <i>"simulated"</i> for a file simulation
	 */
	public void configNavigationEngine(int sx, int sy, String mode)	{
		
		if(mode.equals(MODE_SIMULATED))
		{
			engine = new FileEngine(sx, sy, this);
			enableDebugSensing();
		}
		else if(mode.equals(MODE_REAL_ROBOT))
		{
			engine = new Engine(sx, sy, this);
			disableDebugSensing();
		}
		
		println(engine.getState().toString());
	}
	
	
	/**
	 * Dynamically Update the Robot position during the Navigation
	 * 
	 * @param move	the Move applied to the current position
	 */
	public void updateMyPosition(String move){
		
		engine.makeMove(move);
		System.out.println(engine.getState().toString());
		notifyMyPosition();
	}
	
	
	/**
	 * Sends an event to the RobotGUIManager to update the position during the navigation
	 */
	public void notifyMyPosition(){
		
		State s = engine.getState();
		
		String payload = "position("+s.getX() + "," + s.getY() + ")," +
						s.getDirection().toString().toLowerCase();
		
		emit("show", "show(" + payload + ")");
	}
	
	public void consultFromFile(String filename)
	{
		System.out.println("loading...");
		QActorUtils.consultFromFile(pengine, filename);
	}		
	
//	@Override
//	public AsynchActionResult solveGoalReactive(String goal, int time, String evList, String planList){
//		
//		
//		return new AsynchActionResult(null, time+1, true, true, "", null);		
//	}
	
	
	/*
	public Direction makeSpin(Direction start, SpinDirection spin) { //TODO use the Engine
		int newID = (start.getValue() + (8+spinFactor*spin.getRotation()))%8;
		return spinMap.get(newID);
	}
	*/
//}}	

	
//{{ SYSTEM INTERACTION *****************************************************


	public void enableDebugSensing()
	{
		((QActorPlanUtilsDebug) planUtils).enableFileSensingMode();
	}
	
	public void disableDebugSensing()
	{
		((QActorPlanUtilsDebug) planUtils).disableFileSensingMode();
	}

	
	
	/**
	 * Sense events
	 * 
	 * @param timeout	Sensing timeout
	 * @param event		Event name
	 * @param plan		What to do when the event come
	 * 
	 * @return			The first Event that is detected or null
	 */
	public IEventItem senseEvent(int timeout, String event, String plan){
		
		setCurrentEvent(null);
		try
		{			
			AsynchActionResult aar1 =
					planUtils.senseEvents( timeout , event, plan, "" , "",ActionExecMode.synch );
			
			if( ! aar.getGoon() || aar.getTimeRemained() <= 0 )
			{
    			println("WARNING: sense timeout");
    			addRule("tout(senseevent,"+getName()+")");
    		}	
			
			return currentEvent;
		}
		catch (Exception e) {
			
			e.printStackTrace();
		}
		
		return null;
	    		
	}
	
	
	/**
	 * Send a Message to a well defined actor
	 * 
	 * @param msgName		the message ID
	 * @param paramsList	prolog params list
	 * @param destActor		destination
	 */
	public void sendDispatch(String msgName, String paramsList, String destActor){
		String payload = msgName+"(";
		
		paramsList = paramsList.replace("[", "");
		paramsList = paramsList.replace("]", "");
		payload += paramsList;
		
		payload += ")";
		
		try
		{
			sendMsg(msgName, destActor, QActorContext.dispatch, payload );
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		
		
	}
	
	
	/**
	 * Implements a Message switch.</br>
	 * Messages are in the format <i>msgID : msgID(Payload)</i><br>
	 * When a defined combo of msgID - msgPayload is received, the relative PrologGoal is solved in the defined time.
	 * 
	 * @param timeout			receive Message timeout
	 * @param msgList			list of msg IDs
	 * @param msgPayloadList	list of msg Payloads
	 * @param goalList			list of goals to solve when the relative message is received
	 * @param solveTime			prolog solve timeout
	 */
	public void receiveMessageAndSolve(int timeout,
										String msgList,
										String msgPayloadList,
										String goalList,
										int solveTime){
		
		try
		{
			AsynchActionResult aar = planUtils.receiveAMsg(mysupport, timeout, "" , "" ); 	//could block
			
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
					parg = updateVars(Term.createTerm(msgPayload[i]), Term.createTerm(msgPayload[i]), 
		  					Term.createTerm(currentMessage.msgContent()), parg);
					
					if( parg != null ) {
    					aar = solveGoalReactive( parg , solveTime, "" , "");
    								
    					if( aar.getResult().equals("failure")){
    						if( ! planUtils.switchToPlan("prologFailure").getGoon() ) return;
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
	
	public void switchPlan(String plan)
	{		
		try 
		{
			planUtils.switchToPlan(plan);
		}
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
//}}
	
	
//{{ GUI INTERACTION **************************************************
	
		public void showPathOnGui()
		{
			for(State s : path)
			{
				System.out.println(s.toString());
			}
		}
	//}}
	
	
//{{ REAL ROBOT CONTROLS *****************************************************
	
	/**
	 * Move Real robot forward at default speed and for default duration
	 */
	public void moveForward()
	{
		try
		{
			execute("forward", defaultSpeed, 0, defaultTime, "", "");
		}
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Move Real robot backward at default speed and for default duration
	 */
	public void moveBackward()
	{
		try
		{
			execute("backward", defaultSpeed, 0, defaultTime, "", "");
		}
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Turn Real robot left at default speed and for default duration
	 */
	public void turnLeft()
	{
		try
		{
			execute("left", defaultTurnSpeed, 0, defaultTurnTime, "", "");
		}
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Turn Real robot right at default speed and for default duration
	 */
	public void turnRight()
	{
		try
		{
			execute("right", defaultTurnSpeed, 0, defaultTurnTime, "", "");
		}
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
//}}
}
