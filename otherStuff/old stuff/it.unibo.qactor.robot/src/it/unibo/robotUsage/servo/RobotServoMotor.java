/* By AN DISI Unibo */ 
package it.unibo.robotUsage.servo;
import it.unibo.is.interfaces.IOutputEnvView;
import it.unibo.is.interfaces.protocols.IConnInteraction;
import it.unibo.iot.executors.baseRobot.IBaseRobot;
import it.unibo.iot.models.commands.baseRobot.*;
import it.unibo.qactor.robot.RobotActor;
import it.unibo.qactor.robot.utils.RobotActorCmdUtils;
import it.unibo.qactors.ActorContext;
import it.unibo.supports.FactoryProtocol;

/*
 * ----------------------------------------------------------------
 * RobotServoMotor:
 * a basic robot that executes moves
 * ----------------------------------------------------------------
 */
 public class RobotServoMotor extends RobotActor{ 
	protected IConnInteraction conn; 	
 	protected FactoryProtocol factoryP;
 	
	public RobotServoMotor( String id, ActorContext myCtx, IOutputEnvView outView, IBaseRobot robot ) throws Exception{
 		super(id,myCtx, "./plans.txt","./WorldTheory.pl",outView,robot,"initialPlan"); 
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
  		println("RobotServoMotor STARTS" );
 			try{
 		 		String cmd = "robotCommand(CMD , robotSpeed(SPEED) ))".replace("CMD", "baseRobotforward").replace("SPEED", "40");
 				IBaseRobotCommand robotCommand =  RobotActorCmdUtils.createRobotCommandFromRepString(cmd);
				println("RobotServoMotor EXECUTES : " + robotCommand.getDefStringRep());
				baseRobot.execute(robotCommand); //done by it.unibo.iot.executors.baseRobot.BaseRobot
			}catch(Exception e){
				println("RobotServoMotor ERROR : " + e.getMessage() );
			}
 	}
}
 
