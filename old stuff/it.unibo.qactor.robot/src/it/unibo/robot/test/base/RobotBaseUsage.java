package it.unibo.robot.test.base;
import it.unibo.iot.configurator.Configurator;
import it.unibo.iot.executors.baseRobot.IBaseRobot;
import it.unibo.iot.models.commands.baseRobot.IBaseRobotCommand;
import it.unibo.qactor.robot.RobotSysKb;
import it.unibo.system.SituatedPlainObject;
 
public class RobotBaseUsage extends SituatedPlainObject {
	protected IBaseRobot baseRobot  ;
	
	protected void explain(){
		println( "%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%"    );
		println( "RobotBaseUsage in project it.unibo.qactor.robot (package it.unibo.robot.test.base)"    );
		println( "----------------------------------------------------------------------------------------"    );
		println( "GOAL: show the usage of the base robot (by Sam)"    );
		println( "USE: base robot (labbaseRobotSam.jar)"    );
		println( "USE: hardwareConfiguration.properties if defined"    );
		println( "USE: configuration/xxx/iotRobot.properties with xxx the robt name \n"
				+ "	in hardwareConfiguration.properties. \n"
				+"	Otherwise uses a mock predefined in labbaseRobotSam.jar" );
		println( "%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%"    );
	}
  	protected void doJob() throws Exception {
  		explain();
  		baseRobot  = Configurator.getInstance().getBaseRobot();
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
 	public static void main(String[] args) throws Exception{
 		new RobotBaseUsage().doJob();
 	}
}
