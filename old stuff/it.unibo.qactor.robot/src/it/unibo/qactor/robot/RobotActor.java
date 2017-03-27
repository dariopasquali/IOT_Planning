/*
 * A RobotActor is a QActor that provides the operation:  
 * 		long execute(String command, int speed, int angle, int millisec, String  evId)
 * 
 * 
 */
package it.unibo.qactor.robot;
import java.util.List;
import java.util.StringTokenizer;
import java.util.Vector;

import alice.tuprolog.SolveInfo;
import alice.tuprolog.Var;
import it.unibo.contactEvent.interfaces.IEventItem;
import it.unibo.contactEvent.platform.ContactEventPlatform;
import it.unibo.iot.executors.baseRobot.IBaseRobot;
import it.unibo.iot.models.commands.baseRobot.IBaseRobotCommand;
import it.unibo.is.interfaces.IBasicUniboEnv;
import it.unibo.is.interfaces.IOutputEnvView;
import it.unibo.qactor.robot.action.RobotMoveAction;
import it.unibo.qactor.robot.action.RobotMoveActionTimed;
import it.unibo.qactor.robot.action.RobotTimedCommand;
import it.unibo.qactor.robot.utils.RobotActorCmdUtils;
import it.unibo.qactor.robot.web.CmdUilInterpreter;
import it.unibo.qactors.ActorContext;
import it.unibo.qactors.QActor;
import it.unibo.qactors.action.AsynchActionGenericResult;
import it.unibo.qactors.action.AsynchActionResult;
import it.unibo.qactors.action.IActorAction.ActionExecMode;
import it.unibo.qactors.action.IActorAction;
import it.unibo.qactors.action.IActorAction.ActorActionType;
import it.unibo.qactors.planned.PlanActionDescr;
import it.unibo.qactors.planned.QActorPlanned;
  
public class RobotActor extends QActorPlanned implements IRobotActor {
	private static int nameCount = 1;
	protected IBaseRobot baseRobot ;  
	protected CmdUilInterpreter cmdInterpreter ;
	
 	public RobotActor(String name, ActorContext ctx, String planPath,String worldTheoryPath,
 			IOutputEnvView outView, IBaseRobot robot, String defaultPlan ) throws Exception{
		super(name,ctx,planPath, worldTheoryPath, outView, defaultPlan);
		//super starts the actor before setting baseRobot
		this.baseRobot = robot; //RobotSysKb.getRobotBase();
		cmdInterpreter = new CmdUilInterpreter();
  		println("CREATED RobotActor with baseRobot=" + baseRobot   );
		RobotSysKb.setRobotActor(this);
  	}

	@Override
	protected void doJob() throws Exception {
		buildPlanTable();
		executeThePlan("normal");
 	} 	
 	@Override
	public void terminate() {
		println("Robot " + getName() + " terminate  "  );
   	}
	@Override
	public IBaseRobot getBaseRobot() {
		return baseRobot;
	} 	
    public boolean isSimpleActor()  {
    	return false;
    }

	
	protected String buildArgString(String arg){
		return arg;
	}
	public boolean execCmdGui(String guicmd){ 
		cmdInterpreter.execute(guicmd.replace("'", ""));
		return true;
	}
	public boolean execRobotMove( String curPlanName, 
			   String command, int speed, int angle, int moveTime, String  events, String plans) throws Exception
	{
//		println("%%% RobotActo execRobotMove=" +  command  + " baseRobot=" + baseRobot);
		AsynchActionResult aar;
		long tmove =moveTime;
		if( moveTime == 0)
		{	//execute a baserobot command
			IBaseRobotCommand robotCommand = 
					RobotActorCmdUtils.createRobotCommandFromRepString("robotCommand("+command+" , "+ speed+ " , " + angle +" )");
//   		println("%%% RobotActor robotCommand=" +  robotCommand.getDefStringRep() + " baseRobot=" + baseRobot);
			baseRobot.execute(robotCommand);
  			return true;
		}
		do
		{
//  		println("%%% RobotActor execRobotMove time= " +  tmove  );
			aar = execute(command, speed, angle, (int)tmove , events, plans);
			if( aar.getInterrupted() )
			{
				curPlanInExec   = curPlanName;
	  			println("%%% RobotActor  plan=" + curPlanInExec  +  " CONTINUES time=" +   aar.getTimeRemained() );
				if( ! aar.getGoon() ) return true;
			}
			tmove = aar.getTimeRemained();
//			println("tmove --> "+tmove);
		}while(tmove>0); 
				
		//return aar.getGoon();
		return true;
	}	
/*
 * Called by  prolog  
 */
  	@Override
	public AsynchActionResult execute(String command, int speed, int angle, int moveTime, 
			String  events, String plans) throws Exception{
   		IBaseRobotCommand robotCommand = RobotActorCmdUtils.buildRobotCommand(command, speed, angle);
//     	println("%%% RobotActor execute " + robotCommand.getDefStringRep() + " moveTime=" + moveTime + " events=" + events + " plans=" + plans);
  		if( moveTime == 0 ){ 
  			baseRobot.execute(robotCommand);
  			return new AsynchActionResult(null, 0, normalEnd, continueWork, "", null);
  		}
  		String endOfMoveEvent = ContactEventPlatform.locEvPrefix+"movetimeend"+nameCount++;  //"tmexpired"+getName();
  		long duration = moveTime;
  		IRobotTimedCommand tcommand = new RobotTimedCommand(robotCommand,duration,endOfMoveEvent);
//  		IActorAction moveAction     = new RobotMoveAction( "robotMove", baseRobot, tcommand, outEnvView);
// 		println("%%% RobotActor execute " + robotCommand.getDefStringRep() + " moveTime=" + moveTime + " events=" + events + " plans=" + plans);
//  		AsynchActionResult aar      = executeActionAsFSM(moveAction, events, plans, ActionExecMode.synch);	//if synch ...
  		/*
  		    String name, QActor myactor, IBaseRobot robot, IRobotTimedCommand tcommand, 
			boolean cancompensate, String terminationEvId, String answerEvId, String[] alarms,
			IOutputEnvView outView, long maxduration
  		 */
// 		String[] evarray      = createArray( events );
// 		String[] planarray    = createArray( plans );
   		
 		IActorAction moveAction = new RobotMoveActionTimed( 
 				"robotMoveTimed", this, baseRobot, tcommand, 
  				false, endOfMoveEvent, "", events ,outEnvView, duration);
   		 
// 		AsynchActionGenericResult<String> aar = moveAction.execSynch();
//   		return afterActorReactiveAction(moveAction,    events,   plans);
 		AsynchActionResult aar = executeReactiveAction( moveAction,ActionExecMode.synch,events,plans );
 		
 		//println("RESULT ---> " + aar.getResult());
 		
 		return aar;
   		/*
   		IEventItem eva = moveAction.getInterruptEvent();
   		String nextPlan = getNextPlanTodo(eva.getEventId(),evarray,planarray);
   		AsynchActionResult aarr = this.afterAction(moveAction, nextPlan, eva, aar.getTimeRemained());
   		return aarr;
   		 */
//   		return evalActionResult(moveAction,evarray,planarray,aar.getTimeRemained() );
   		// AsynchActionResult aar      = executeActionAsFSM(moveAction, events, plans, ActionExecMode.synch);
//  		return aar;
// 		println("%%% RobotActor execute " + command + " result= " + aar );
//  		IActorAction pseudoAction = new RobotMoveAction( "robotMovePseudo", baseRobot, tcommand, outEnvView);

//   		return new AsynchActionResult( moveAction, aar.getTimeRemained(), false, true, aar.getResult(), null); //TODO  		
	}
  	


  	
  	protected boolean isACommand(String command){
  		boolean b = 
  				command.toLowerCase().equals(  RobotSysKb.forwardCommand ) ||
 				command.toLowerCase().equals(  RobotSysKb.backwardCommand ) ||
 				command.toLowerCase().equals(  RobotSysKb.rightCommand ) ||
 				command.toLowerCase().equals(  RobotSysKb.leftCommand ) ||
 				command.toLowerCase().equals(  RobotSysKb.stopCommand );
 			;
  		return b;
  	}
 
 
/*
 * =====================================================
 */
 	@Override
	public void setEnv(IBasicUniboEnv env) {
		this.env = env;
		if( env != null ) this.outView = env.getOutputView();
		outView.addOutput("setEnv done");
	}

	/*
	 * Make visible sendMsg
	 */
	public void sendMsg(String msgID, String destActorId, String msgType, String msg) throws Exception{
		super.sendMsg(msgID, destActorId, msgType, msg) ;
 	}
	/*
	 * executeAction of a robot first checks for a  ActorActionType.move
	 * and calls the superclass if it is not a move
	 */
  	@Override
	public AsynchActionResult executeAction(PlanActionDescr pa) throws Exception {
// 		println("%%% RobotActor  executeAction " + pa.getDefStringRep() );
		if( pa.getType().equals( ActorActionType.move  ) ){
			int speed    = Integer.parseInt( pa.getArgs() );
			int moveTime = Integer.parseInt( pa.getDuration() );
			AsynchActionResult aar = execute( pa.getCommand(), speed, 0, moveTime, pa.getEvents(), pa.getPlans() );
//			println("RobotActor executeAction result = " + aar );
			return aar;
		}
		else return super.executeAction(  pa );
  
	}
 	public List<Var> solveTheGoal( String goal  ) throws Exception{
	 		println("RobotActor solveTheGoal goal=" + goal + " pengine=" + pengine);
 		SolveInfo sol = pengine.solve(goal+".");
 		if( sol.isSuccess() ) return sol.getBindingVars();
 		else{
 			return null;
 		}
 	}

 
}
