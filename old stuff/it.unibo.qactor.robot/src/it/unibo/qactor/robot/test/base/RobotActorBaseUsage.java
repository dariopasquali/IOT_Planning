package it.unibo.qactor.robot.test.base;
import it.unibo.iot.executors.baseRobot.IBaseRobot;
import it.unibo.iot.models.commands.baseRobot.IBaseRobotCommand;
import it.unibo.is.interfaces.IOutputEnvView;
import it.unibo.qactor.robot.RobotActor;
import it.unibo.qactor.robot.RobotSysKb;
import it.unibo.qactors.ActorContext;
  
public class RobotActorBaseUsage extends RobotActor{
 	public RobotActorBaseUsage( String id, ActorContext myCtx, 
 			String planPath,String worldTheoryPath, IOutputEnvView outView,  
 			IBaseRobot baseRobot, String defaultPlan ) throws Exception{
		super( id,myCtx, planPath, worldTheoryPath, outView, baseRobot, defaultPlan  ); 
   	}
	@Override
	protected void doJob() throws Exception {
		doMove();
  	} 	
  	protected void doMove() throws Exception{
  		IBaseRobotCommand command = RobotSysKb.FORWARD;
 		println("base robot formward");
		baseRobot.execute(command);
		Thread.sleep(1000);
		command = RobotSysKb.STOP;
		baseRobot.execute(command);
 		println("bye bye");  		
  	}
 	
}
