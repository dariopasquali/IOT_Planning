package it.unibo.qactor.robot.action;
import it.unibo.iot.executors.baseRobot.IBaseRobot;
import it.unibo.is.interfaces.IOutputEnvView;
import it.unibo.qactor.robot.IRobotTimedCommand;
import it.unibo.qactor.robot.RobotSysKb;
import it.unibo.qactors.action.IActorAction;
import it.unibo.qactors.action.ActorAction; 
 
public class RobotMoveAction extends ActorAction implements IActorAction{
private IBaseRobot robot;
private IRobotTimedCommand command;

	public RobotMoveAction(String name, IBaseRobot robot, IRobotTimedCommand tcommand, IOutputEnvView outView) throws Exception {
		super(name, false, tcommand.getCompletionEventId(), "", outView, tcommand.getDuration()); //synchronous activation
		this.robot    	= robot;
		this.command	= tcommand;
//		println("	%%% RobotMoveAction CREATED " + command.getRobotBaseCommand().getDefStringRep() + " maxduration=" + maxduration   );
	}
 
 	@Override
	public void execAction() throws Exception {
//		println("	%%% RobotMoveAction " + command.getRobotBaseCommand().getDefStringRep() + " robot=" + robot   );
		robot.execute(command.getRobotBaseCommand());
  		println("	%%% RobotMoveAction sleep " + this.maxduration  );
		boolean b = dosleep(this.maxduration);
//  		println("	%%% RobotMoveAction CONTINUES with suspended=" +  suspended + " terminated=" + b );
		if( suspended ) return;
//		println("	%%% RobotMoveAction STOPS "     );
		if( b ){
//			this.actionTerminated = true;
 			robot.execute(RobotSysKb.STOP);
			suspended = true;  
		}
		
	}
	@Override
	public void suspendAction(){
 		println("	%%% RobotMoveAction suspendAction "     );
 		robot.execute(RobotSysKb.STOP);
 		super.suspendAction();
 	}

	//Entry point for the Executor
	@Override
	protected void execTheAction() throws Exception {
 		println("	%%% RobotMoveAction execTheAction  ");
		execAction();
		println("	%%% RobotMoveAction execTheAction done ");
	}

	@Override
	protected String getApplicationResult() throws Exception {
		String s =  "robotMove(" + name + ", done)";
		println("	%%% RobotMoveAction getApplicationResult " + s);
		return s;
	}

	@Override
	public String getTerminationEventId() {
		return "toset";
	}
 
}
