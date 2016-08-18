package it.unibo.qactor.robot.web;
import java.util.List;

import alice.tuprolog.SolveInfo;
import alice.tuprolog.Struct;
import alice.tuprolog.Term;
import alice.tuprolog.Var;
import it.unibo.iot.executors.baseRobot.IBaseRobot;
import it.unibo.iot.models.commands.baseRobot.BaseRobotBackward;
import it.unibo.iot.models.commands.baseRobot.BaseRobotForward;
import it.unibo.iot.models.commands.baseRobot.BaseRobotLeft;
import it.unibo.iot.models.commands.baseRobot.BaseRobotRight;
import it.unibo.iot.models.commands.baseRobot.BaseRobotStop;
import it.unibo.iot.models.commands.baseRobot.IBaseRobotSpeed;
import it.unibo.is.interfaces.IOutputEnvView;
import it.unibo.qactor.robot.RobotActor;
import it.unibo.qactor.robot.RobotSysKb;
import it.unibo.qactors.QActor;
import it.unibo.qactors.action.AsynchActionResult;
import it.unibo.qactors.planned.QActorPlanned;
import it.unibo.qactors.web.GuiUiKb;
import it.unibo.qactors.web.QActorHttpServer;
 
/*
 * RobotHttpServer is a QActorHttpServer that accepts user commands sent from the webGUI
 * and then performs one of the following actions:
 * if activated in NAIVE mode   : it calls a proper execute operation of the IBaseRobot
 * if activated in MESSAGE mode : it send a messag robotCommand(CMD ,  SPEED ) to the given RobotActor
 * if activated in EVENT   mode : it emits an event usercmd with payload = usercmd(X) with X='w-High' ...
 */
public class RobotHttpServer extends QActorHttpServer{
	
public enum Mode{
	NAIVE,MESSAGE,EVENT
}

protected RobotActor robot;
private IBaseRobot baserobot ; //see DifferentialDriveBaseRobot
private RobotActor myrobot ;
private int speed;
private IBaseRobotSpeed robotspeed;
private Mode mode;
private String moveToDo ="";

 	public RobotHttpServer(IOutputEnvView outEnvView, String dirPath, int port, RobotActor robot, Mode mode) {
		super(outEnvView,  dirPath, port);
		this.robot = robot; 
		myrobot = robot;
		this.mode  = mode;
		try {
			baserobot  = RobotSysKb.getRobotBase();
		} catch (Exception e) {
			e.printStackTrace();
		} 
 	}
 	
	protected void handleUserCmd(String cmd) throws Exception{
		outEnvView.addOutput("--- RobotHttpServer handleUserCmd: " + cmd );
		setTheArg(cmd);
		switch( mode ){
			case NAIVE   : executeNaive( cmd ); break;
			case MESSAGE : executeMsg( cmd ); break;
			case EVENT   : executeEvent( cmd ); break;
			default: executeNaive( cmd );
		}
 	}
	
	protected void setTheArg(String cmd){
		//cmd=w(low) ...
		Struct ct = (Struct) Term.createTerm(cmd);
		String speedStr = ""+ct.getArg(0);
		if( ct.getName().equals("i") ) {
			moveToDo = ""+ct.getArg(0);
			System.out.println("			moveToDo " + moveToDo);
		}
//		if( cmd.startsWith("i-")){
//			moveToDo = cmd.split("-")[1];
////			moveToDo = moveToDo.replace("'", "\"");
//			System.out.println("			moveToDo " + moveToDo);
//		}
		else if( speedStr.equals("low") ){
			speed=40;	
			robotspeed = RobotSysKb.SPEED_LOW;
		}
		else if( speedStr.equals("high") ){
			speed=100; 
			robotspeed = RobotSysKb.SPEED_HIGH;
		}
		else if( speedStr.equals("medium") ){
			speed=70; 
			robotspeed = RobotSysKb.SPEED_MEDIUM;
		}
		else{
			speed=70;
			robotspeed = RobotSysKb.SPEED_MEDIUM;
		}
		
	}
	
/*
 * -------------------------------------------------------------
 * The command is executed by the baserobot	
 * -------------------------------------------------------------
 */
	protected void 	executeNaive(String cmd){
		char moveChar = cmd.charAt(0);
		outEnvView.addOutput("--- RobotHttpServer executeNaive: " + moveChar );
		if (moveChar == 'h') {
			baserobot.execute( new BaseRobotStop(robotspeed) );	
		} else if (moveChar == 'w') {
			baserobot.execute( new BaseRobotForward(robotspeed) );
		} else if (moveChar == 's') {
			baserobot.execute( new BaseRobotBackward(robotspeed) );
	 	} else if (moveChar == 'a') {
	 		baserobot.execute( new BaseRobotLeft(robotspeed) );
	 	} else if (moveChar == 'd') {
	 		baserobot.execute( new BaseRobotRight(robotspeed) );
	 	} else if (moveChar == 'i') {
  			try {
  				//play( './audio/tada2.wav', 1500 , endplay)
  				
  				String goal = "executeInputTerm( "+moveToDo+" )";
  				System.out.println("			goal " + goal + " " + myrobot.getPrologEngine() );
  				//SolveInfo sol  = myrobot.getPrologEngine().solve( goal  );
  				List<Var> sol = myrobot.solveTheGoal(  goal );
  				System.out.println("			goal sol=" + sol );
//  				if( aar.getResult().equals("failure"))
//					if( ! ((QActorPlanned) baserobot).switchToPlan("prologFailure").getGoon() ) break;
			} catch (Exception e) {
 				e.printStackTrace();
			}
	  	} 
	}

	/*
	 * -------------------------------------------------------------
	 * The command is executed by sending a message to the robot qactor
	 * -------------------------------------------------------------
	 */
	protected void 	executeMsg(String cmd) throws Exception{
		char moveChar = cmd.charAt(0);
//		outEnvView.addOutput("--- RobotHttpServer executeMsg: " + moveChar );
		String robotMove = "baseRobotstop";
		if (moveChar == 'h') {
			robotMove="baseRobotstop";
		} else if (moveChar == 'w') {
			robotMove="baseRobotforward";
		} else if (moveChar == 's') {
			robotMove="baseRobotbackward";
	 	} else if (moveChar == 'a') {
	 		robotMove="baseRobotleft";
	 	} else if (moveChar == 'd') {
	 		robotMove="baseRobotright";
	 	}
		//robotCommand(baseRobotLeft,robotSpeed(robot_speed_low))
 		String robotCmd ="robotCommand(CMD ,  SPEED )".replace("CMD", robotMove).replace("SPEED", ""+speed   ); 
		//robotCommand(CMD , SPEED , ANGLE )) CMD=baseRobotForward SPEED=INT ANGLE=INT
		outEnvView.addOutput("--- RobotHttpServer executeMsg: " + robotCmd );
  		robot.sendMsg("robotCommand", robot.getName(), "dispatch", robotCmd);		//sends to itself NOT VERY GOOD 		 
	}
	/*
	 * -------------------------------------------------------------
	 * The command is executed by emitting an event
	 * -------------------------------------------------------------
	 */
	protected void 	executeEvent(String cmd) throws Exception{
		String eventMsg="";
		if( cmd.startsWith("i-")){
			eventMsg="usercmd(executeInput("+moveToDo+"))";
		}
		else eventMsg = "usercmd(\""+cmd+"\")";
 		outEnvView.addOutput("--- RobotHttpServer cmd: " + cmd + 
				" emits " + GuiUiKb.terminalCmd +":"+eventMsg );
		platform.raiseEvent("wsock", GuiUiKb.terminalCmd, eventMsg);
 	}
 }
