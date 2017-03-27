package it.unibo.qactor.robot.test.avatar;
import it.unibo.iot.executors.baseRobot.IBaseRobot;
import it.unibo.is.interfaces.IOutputEnvView;
import it.unibo.qactor.robot.RobotActor;
import it.unibo.qactors.ActorContext;
 
public class RobotBaseAvatar extends RobotActor{
String ipLocal = "http://localhost:8080";
String ipRobot = "http://192.168.43.219:8080";
 	public RobotBaseAvatar( String id, ActorContext myCtx, String planPath, String worldTheoryPath,IOutputEnvView outView,  IBaseRobot baseRobot, String defaultPlan ) throws Exception{
		super( id,myCtx,planPath, worldTheoryPath,outView, baseRobot, defaultPlan  ); 
   	}
	@Override
	protected void doJob() throws Exception {
		println("======================================================================");
 		println("Please connect to " + ipRobot + ". I'll wait for 30 sec");
		Thread.sleep(30000);
  		println("bye bye");
		println("======================================================================");
  	} 	
}
