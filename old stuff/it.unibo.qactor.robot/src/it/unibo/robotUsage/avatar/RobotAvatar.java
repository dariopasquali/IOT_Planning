/* By AN DISI Unibo */ 
package it.unibo.robotUsage.avatar;
import it.unibo.is.interfaces.IOutputEnvView;
import it.unibo.is.interfaces.protocols.IConnInteraction;
import it.unibo.iot.device.serial.impl.SerialIOManager;
import it.unibo.iot.executors.baseRobot.IBaseRobot;
import it.unibo.iot.models.commands.baseRobot.*;
import it.unibo.qactor.robot.RobotActor;
import it.unibo.qactor.robot.utils.RobotActorCmdUtils;
import it.unibo.qactors.ActorContext;
import it.unibo.qactors.QActor;
import it.unibo.supports.FactoryProtocol;

/*
 * ----------------------------------------------------------------
 * RobotAvatar:
 * a basic robot that executes remote commands sent by a web interface 
 * and handles a distance sensor
 * ----------------------------------------------------------------
 */
 public class RobotAvatar extends RobotActor{ 
	protected IConnInteraction conn; 	
 	protected FactoryProtocol factoryP;
 	
	public RobotAvatar( String id, ActorContext myCtx, IOutputEnvView outView, IBaseRobot robot ) throws Exception{
 		super(id,myCtx, "./plans.txt","./WorldTheory.pl",outView,robot,"initialPlan"); 
//		super(id,myCtx, "./plans.txt","./srcMore/WorldTheory.pl",outView,robot,"initialPlan"); 
 		/*
 		 * 'plans.txt' is just to recall the meaning of the argument, since we redefine doJob
 		 * Also the last argument 'initialPlan' is ignored
 		 */
	}
	/*
	 * There are no plans: the robot executes commands (as messages)
	 */
 	@Override 
	protected void doJob() throws Exception {
  		println("RobotAvatar STARTS" );
  		/*
  		 * We solve a goal just to show the behavuor of the WorldTheory
  		 */
		this.pengine.solve("test.");
		
		/*
		 * The robot waits for a message sent by the RobotHttpServer activated in 'message' mode
		 * and then calls a move in the IBaseRobot
		 */
		while(true){
			try{
		 		String msg = receiveMsg();
		 		String cmd = myCtx.getContentMsg(msg).replaceAll("'", "");
		 		//cmd ... robotCommand(baseRobotforward,40)
				println("RobotAvatar RECEIVED : " + cmd + " baseRobot=" + baseRobot.getClass().getName());
				IBaseRobotCommand robotCommand =  RobotActorCmdUtils.createRobotCommandFromRepString(cmd);
				println("RobotAvatar EXECUTES : " + robotCommand.getDefStringRep());
				baseRobot.execute(robotCommand); //done by it.unibo.iot.executors.baseRobot.BaseRobot
			}catch(Exception e){
				println("RobotAvatar ERROR : " + e.getMessage() );
			}
 		}
	}
}
 
