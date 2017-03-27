package it.unibo.qactor.robot.action;
import it.unibo.iot.executors.baseRobot.IBaseRobot;
import it.unibo.is.interfaces.IOutputEnvView;
import it.unibo.qactor.robot.IRobotTimedCommand;
import it.unibo.qactor.robot.RobotSysKb;
import it.unibo.qactors.QActor;
import it.unibo.qactors.action.ActorTimedAction;
 
 
public class RobotMoveActionTimed extends ActorTimedAction{
private IBaseRobot robot;
private IRobotTimedCommand command;

	public RobotMoveActionTimed(String name, QActor myactor, IBaseRobot robot, IRobotTimedCommand tcommand, 
			boolean cancompensate, String terminationEvId, String answerEvId, String alarms,
			IOutputEnvView outView, long maxduration) throws Exception {
		super(name+actionCount++, myactor, cancompensate, terminationEvId, answerEvId, alarms, outView, maxduration);  
		this.robot    	= robot;
		this.command	= tcommand;
// 		println("	%%% RobotMoveActionTimed CREATED " + alarms    );
	}
 
	 /*
	  * This operation is called by ActionObservableGeneric.call
	  */
	//Entry point for the Executor
	@Override
	protected void execTheAction() throws Exception {
// 		println("	%%% RobotMoveActionTimed execTheAction  ");
 		robot.execute(command.getRobotBaseCommand());
//		println("	%%% RobotMoveActionTimed " + getName() + " STARTS  §§§  ");
		try {
			Thread.sleep(maxduration);
		} catch (Exception e) {
//			println("	%%% RobotMoveActionTimed " + getName() + " interrupted §§§  ");
		} 
//		println("	%%% RobotMoveActionTimed " + getName() + "  ENDS §§§ ");
 		robot.execute(RobotSysKb.STOP);
 		this.actionTerminated = true;
//		println("	%%% RobotMoveActionTimed execTheAction done ");
	}

	@Override
	protected String getApplicationResult() throws Exception {
//		robot.execute(RobotSysKb.STOP);
		String s = "robotMove(restTime("+timeRemained+"))";
		return s;
	}

}
