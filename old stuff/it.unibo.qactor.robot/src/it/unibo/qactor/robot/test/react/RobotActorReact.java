package it.unibo.qactor.robot.test.react;
import java.util.List;

import alice.tuprolog.SolveInfo;
import alice.tuprolog.Var;
import it.unibo.iot.executors.baseRobot.IBaseRobot;
import it.unibo.is.interfaces.IOutputEnvView;
import it.unibo.qactor.robot.RobotActor;
import it.unibo.qactor.robot.RobotSysKb;
import it.unibo.qactor.robot.devices.RobotWebCam;
import it.unibo.qactors.ActorContext;
import it.unibo.qactors.action.AsynchActionResult;
import it.unibo.qactors.action.IActorAction;
import it.unibo.qactors.action.IActorAction.ActionExecMode;

/*
 *  RobotActorReact is a  RobotActor that moves in autonomous way for 15 secs.
 *  In the meantime the user can send a command to the robot, that reacts to this
 *  command by excuting 'reactToUserCmd'.
 */
public class RobotActorReact extends RobotActor{
private long tmove   = 10000;
private boolean goon = true;
private int speed    = 70;

 	public RobotActorReact( String id, ActorContext myCtx, 
 			String planFilePath,String worldTheoryPath,IOutputEnvView outView,  
 			IBaseRobot baseRobot, String defaultPlan ) throws Exception{
		super( id,myCtx,planFilePath, worldTheoryPath,outView, baseRobot, defaultPlan  ); 
   	}
 	
 	protected void explain(){
 		println("1) Please connect to http://localhost:8080." );
 		println("2) Please send a command (different from STOP). I'll wait for " + tmove/1000 + " secs");
		println("3) Press ANY COMMAND to interrupt the execution");
		println("If you press STOP ?ill finish, Otherwise tou can give another command");		
 	}
 	
	@Override
	protected void doJob() throws Exception {
  		/*
  		 * Just a test of guards and asynchronous operations
  		 */
 		aJobWithGuards();
		println("======================================================================");
 		explain();
		do{
 			String cmdMsg = waitForAnyCommand(30000);
			println("I'm able to react to any user command. In the meantime I execute " + cmdMsg);
			AsynchActionResult aar = execute(cmdMsg, speed, 0, (int) tmove, "usercmd", "reactToUserCmd");
			println("PLAN " + curPlanInExec + " aar=" + aar  );
			if( aar.getInterrupted() ){	//the action has been interruted by a usercmd
				String res = removeStoredEvent();	//remove the original event cmd
				println("interrupted by " + res);
			}
			if( ! aar.getGoon() ){	//The alternative plan does not allow continuation
				println("endofplan " + curPlanInExec );
 				break;
			}
			/*
			 * The event hanlder does a memo of the usercmd (interrupting)  event
			 */
 			String res = removeStoredEvent();	//remove the interrupt or original event 
  			goon = ! res.contains("h-") ;
			println("goon= " + goon + " res=" + res);
		}while(goon);
		println("bye bye"  );
		println("======================================================================");
  	} 
	
	protected String removeStoredEvent() throws Exception{
		SolveInfo sol = pengine.solve("retract( stored(usercmd,X) ).");
		String res = ""+sol.getVarValue("X");
		return res;
	}
	
	
 	protected String waitForAnyCommand(int time) throws Exception{
 		AsynchActionResult aar = this.senseEvents(time, "usercmd", "", "", "", ActionExecMode.synch);
 		String cmdMsg = aar.getEvent().getMsg() ;
 		//cmdMsg = 'usercmd("w-Low")
 		println("EVENT=" + cmdMsg);
 		if( cmdMsg.contains("Low")) speed=40;
 		else if( cmdMsg.contains("Medium")) speed=70;
 		else if( cmdMsg.contains("High")) speed=100;
 		return RobotSysKb.getCmdName(cmdMsg);	
 	}

	public boolean reactToUserCmd() {
		println(getName() + " ==== reactToUserCmd called by reflection.");	
		try {
			this.playSound("./audio/tada2.wav", 2000, "", "", "");
		} catch (Exception e) {
 			e.printStackTrace();
		}
//  		return IActorAction.suspendPlan; 
   		return IActorAction.continuePlan; 
 	}

/*
 * The result of the evaluation of the guard plays
 * a sound in asynchronous way
 */
	protected void aJobWithGuards() throws Exception{
 		addRule("goon(a)");
 		this.pengine.solve("test.");
		List<Var> lv = evalTheGuard("goon(X)", guardVolatile);
		if(  lv != null ){
			playSound("./audio/any_commander3.wav", 3000, "endsoundok", "", "");	//asynch
	 		takeAPhoto("myworld.jpg");
 			println(getName() + " the guard allows the operation with " + lv.toString());				
		}else{	
			playSound("./audio/illogical_most2.wav", 2000, "endsoundko", "", "");	//asynch
			println(getName() + " *** the guard does not allow the operation");
		}
			
	}
	/*
	 * Take a photo
	 */
protected void takeAPhoto(String fName)  {
	try{
		RobotWebCam webCam = RobotSysKb.getRobotwebCam();
		webCam.setForImage(2592,1944);
		webCam.captureImg(fName);
	}catch(Exception e){
		println(getName() + " takeAPhoto error " + e.getMessage());
	}
}
	
}
