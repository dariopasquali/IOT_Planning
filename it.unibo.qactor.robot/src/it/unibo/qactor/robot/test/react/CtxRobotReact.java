package it.unibo.qactor.robot.test.react;
import java.io.FileInputStream;
import java.io.InputStream;

import it.unibo.iot.executors.baseRobot.IBaseRobot;
import it.unibo.is.interfaces.IOutputEnvView;
import it.unibo.qactor.robot.RobotActor;
import it.unibo.qactor.robot.RobotSysKb;
import it.unibo.qactor.robot.web.RobotHttpServer;
import it.unibo.qactors.ActorContext;
import it.unibo.system.SituatedSysKb;
/*
 * A robot accepts commands from a user WebGUI
 * The robot executes the command for a prefixed amount of time.
 * During the command execution the robot is able to react to events
 * (another user commad) by executing (via reflection) an alternative operation.
 * The user commands are handled by a RobotHttpServer activated in 'event' mode
 * (i.e. it generates an event 
 * 			 msg(usercmd,event ,wsock, none ,'usercmd("X")','0')
 * 	with X =  a-Low ot h-Low ...
 * handled by the UserCmdHandleMemo handler that does a 'memo' of the event.
 * At the end of an interupted usercommand the robot removes from the WorldTheory the
 * usercmd event and then the interrupting event command
 * If the alternative operation terminate by returning 'true', 
 * the robot continues its previous work, otherwise it terminates its job.
 * If the user press the STOP commend when generating an interruption event,
 * the robot reacts and then terminates its job.
 * 
 * At the beginning of its behavuor the robot executes a multimedia operation
 * (plays a sound or tales a photo) according to the value if a guard.
 */
public class CtxRobotReact extends ActorContext{
protected RobotActor robot;

	public CtxRobotReact(String name, IOutputEnvView outEnvView,
			InputStream sysKbStream, InputStream sysRulesStream)
			throws Exception {
		super(name, outEnvView, sysKbStream, sysRulesStream);
 	}

	@Override
	public void configure() {
		try {
			IBaseRobot baseRobot = RobotSysKb.setRobotBase(this, "rbase");
			robot   = new RobotActorReact("robot", this, 
					"./plans.txt","./srcMore/WorldTheory.pl", outEnvView, baseRobot, "init");
			new UserCmdHandleMemo("ucmdHanldeMemo", this,  outEnvView, new String[]{"usercmd"} );
 			//we activate a server to receive user commmands
	 		new RobotHttpServer(outEnvView,"./srcWeb",8080,robot, RobotHttpServer.Mode.EVENT).start();		 			 		
		} catch (Exception e) {
			println("WARNING: " + e.getMessage());
		} 		
	}
	
 
	public static void main(String[] args) throws Exception{
		InputStream sysKbStream    = new FileInputStream("robotreact.pl");
		InputStream sysRulesStream = new FileInputStream("sysRules.pl");
		new CtxRobotReact("ctxrobot", SituatedSysKb.standardOutEnvView, sysKbStream, sysRulesStream ).configure();
 	}

}