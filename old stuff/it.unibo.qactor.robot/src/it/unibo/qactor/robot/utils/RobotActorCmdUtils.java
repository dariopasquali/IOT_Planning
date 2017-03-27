package it.unibo.qactor.robot.utils;
import alice.tuprolog.Struct;
import alice.tuprolog.Term;
import it.unibo.contactEvent.platform.ContactEventPlatform;
import it.unibo.iot.models.commands.baseRobot.*;
import it.unibo.qactors.action.IActorAction;
import it.unibo.qactor.robot.RobotSysKb;
import it.unibo.qactor.robot.action.ActionDummy;
import it.unibo.qactor.robot.action.ActionWebCam;
import it.unibo.qactor.robot.action.ActorActionDescr;
import it.unibo.qactors.action.ActionSound;
import it.unibo.qactors.action.IActionHandler;
import it.unibo.qactors.action.IActorAction.ActorActionType;
import it.unibo.qactors.action.IAsynchAction;
import it.unibo.system.SituatedSysKb;


public class RobotActorCmdUtils {
	private static int nameCount = 1;
 	public static IBaseRobotCommand createRobotCommandFromRepString(String robotCommandStringRep) {
		//Rep : "robotCommand(CMD , robotSpeed(SPEED) ))" OR "robotCommand(CMD , SPEED , ANGLE ))"
		int speed;
 		System.out.println("RobotActorCmdUtils createRobotCommandFromRepString " + robotCommandStringRep );

		IBaseRobotSpeed rbSpeed = new BaseRobotSpeed(30);
		Struct repTerm = (Struct) Term.createTerm(robotCommandStringRep);
//  		System.out.println("RobotActorCmdUtils	getArg for speed " + repTerm.getArg(1));
 			speed   = Integer.parseInt( repTerm.getArg(1).toString() );
			rbSpeed = new BaseRobotSpeed(speed);
//  			System.out.println("	createRobotCommandFromRepString rbSpeed " + rbSpeed.getPercentageOfSpeed());
  			int angle = 0;
  			try{
  				angle  = Integer.parseInt( repTerm.getArg(2).toString() );
  			}catch(Exception e){  				
  			}
			IBaseRobotAngle robAngle= new BaseRobotAngle(angle);
			if (robotCommandStringRep.contains("forward")) { //"baseRobotforward"
				return new BaseRobotForward( rbSpeed, robAngle );
			}else if (robotCommandStringRep.contains("backward")) { //"baseRobotbackward"
				return new BaseRobotBackward( rbSpeed, robAngle );
			}else if (robotCommandStringRep.contains("left")) { //"baseRobotleft"
				return new BaseRobotLeft( rbSpeed, robAngle );
			}else if (robotCommandStringRep.contains("right")) { //"baseRobotright"
				return new BaseRobotRight( rbSpeed, robAngle);
			}else if (robotCommandStringRep.contains("stop")) { //"baseRobotstop">
				return new BaseRobotStop( rbSpeed, robAngle );
			}else return null;   //null before 27 May
			
//		}
	}
 
	public static IBaseRobotCommand buildRobotCommand(String command, int speed, int angle) {
//		System.out.println("	buildRobotCommand " + command + " speed=" + speed + " angle=" + angle);
 		IBaseRobotCommand robotCommand = null;
 		String cmd = null;
  		// robotCommand(robotForward(robotSpeed(xxx)))
 		if( angle < 0 ){
			cmd = "robotCommand(CMD , SPEED )";
			cmd = cmd.replace("CMD", "baseRobot" + command);
			cmd = cmd.replace("SPEED", ""+speed);
 		}else{
			cmd = "robotCommand(CMD , SPEED, ANGLE )";
			cmd = cmd.replace("CMD", "baseRobot" + command);
			cmd = cmd.replace("SPEED", ""+speed);
			cmd = cmd.replace("ANGLE", ""+angle);
 		}
		robotCommand = RobotActorCmdUtils.createRobotCommandFromRepString(cmd);
		return robotCommand;
	}
	public static ActorActionDescr buildRobotAction(String actionStr, int duration, String arg3a,
											String answerEvID, String actionInfo) throws Exception, IllegalAccessException {	
   		//System.out.println("		%%%% buildRobotAction " + actionStr + " arg3a=" + arg3a);
 
		/*
		 * RobotActorActionDescr is an object that describes the action to be performed by the robot
		 * First we check if it is  predefined robot action 
		 * It is not a move command, but something that the robot is able to do
		 */
		if( actionStr.equals("dummyAction")){ 
 			IActorAction a = new ActionDummy("dummy", true, "dummyEv", "null", 
 					SituatedSysKb.standardOutEnvView, duration, actionInfo );
			return 
				new ActorActionDescr( ActorActionType.sound, "dummy", a , 
						duration, "dummyEv", answerEvID, actionInfo);
		}
		/*
		 * SHOULD BE DONE BY QACTOR
		 */
//		else if( actionStr.equals("receive")){ 
// 			String terminationEvId = IActorAction.endBuiltinEvent+nameCount++;
//			IActorAction a = new ActionReceive("receive", false, terminationEvId, answerEvID, 
//					SituatedSysKb.standardOutEnvView, duration, actionInfo  );
// 	 		//Set the action handler  
//	 		IActionHandler ah = RobotSysKb.getHandler(arg3a);
//	 		if( ah != null ) ah.addEventId(answerEvID);
//			return 
//				new RobotActorActionDescr( ActorActionType.RECEIVE, "receive", a , 
//						duration, terminationEvId, answerEvID, actionInfo);			
//		}
		else if( actionStr.equals("sound")){ 
 			String terminationEvId = IActorAction.endBuiltinEvent+nameCount++;
			IActorAction a = new ActionSound("sound", true, terminationEvId, answerEvID, 
					SituatedSysKb.standardOutEnvView, duration, actionInfo );
 	 		//Set the action handler
	 		IActionHandler ah = RobotSysKb.getHandler(arg3a);
	 		if( ah != null ) ah.addEventId(answerEvID);
			return 
				new ActorActionDescr( ActorActionType.sound, "sound", a , 
						duration, terminationEvId, answerEvID, actionInfo);
		}
		else if( actionStr.equals("photo")){
			String terminationEvId = IActorAction.endBuiltinEvent+nameCount++;
			IActorAction a = new ActionWebCam("photo", true, terminationEvId, answerEvID, 
					SituatedSysKb.standardOutEnvView, duration, true, actionInfo);
 	 		//Set the action handler
	 		IActionHandler ah = RobotSysKb.getHandler(arg3a);
	 		if( ah != null ) ah.addEventId(answerEvID);
			return 
					new ActorActionDescr( ActorActionType.photo, "photo", a, duration, 
							terminationEvId, answerEvID, actionInfo);
		}
		else if( actionStr.equals("video")){ 
			String terminationEvId = IActorAction.endBuiltinEvent+nameCount++;
			IActorAction a = new ActionWebCam("video", true, terminationEvId, answerEvID, 
					SituatedSysKb.standardOutEnvView, duration, false, actionInfo);
 	 		//Set the action handler
	 		IActionHandler ah = RobotSysKb.getHandler(arg3a);
	 		if( ah != null ) ah.addEventId(answerEvID);
			return 
				new ActorActionDescr( ActorActionType.video, "video", a, duration, 
						terminationEvId, answerEvID, actionInfo);
		}
		else{
		/*
		 * The action is not a predefined action
		 */
			String actionName = actionStr.trim();
//			if( actionName.equals("activatetask")){
//				IActionHandler h = RobotSysKb.getHandler( actionName );
//				ContactEventPlatform.getPlatform().activate(answerEvID);
//				return null;
//			}
			String endEvId = ContactEventPlatform.locEvPrefix+"end"+actionStr;
			IAsynchAction a = RobotSysKb.getAsynchAction( actionName  );	
	   		System.out.println("		%%%% buildRobotAction for " + actionName + " found " + a + " answerEvID=" + answerEvID + " a termi" + a.getTerminationEventId());
			if( a != null ){
			 	System.out.println("		%%%% buildRobotAction " + actionName + " answerEvID=" + answerEvID.trim() );
				if( answerEvID.trim().equals("null") ){
					//we should create a anew instance without handler
					//with the current duration
					//or we should change the duration to an already existing instance
					endEvId = a.getTerminationEventId();
					a.setMaxDuration(duration);
				}
//				if( ! answerEvID.trim().equals("null") ){
//					//we should create a anew instance without handler
//					//with the current duration
//					//or we should change the duration to an already existing instance
//					endEvId = a.getTerminationEventId();
//					//a.setTerminationEventId(answerEvID);
//					a.setMaxDuration(duration);
//				}
 			 	else{
					actionName  = actionName+nameCount++;
					endEvId = ContactEventPlatform.locEvPrefix+"end"+actionStr+nameCount++;
					//dynamic creation of an instance
					IActorAction anew = (IActorAction) a.getClass().newInstance();
					anew.setTheName(actionName);
					anew.setCanCompensate(false);	//TOCHECK
					anew.setAnswerEventId(answerEvID);
					anew.setTerminationEventId(endEvId);
					anew.setMaxDuration(duration);
 			 		//System.out.println("		%%%% buildRobotAction create new instance " + actionStr + " dt=" + anew.getMaxDuration());
					//Memo the new instance
			 		RobotSysKb.setAsynchAction( actionName, anew );
			 		//Set the action handler
			 		IActionHandler ah = RobotSysKb.getHandler(arg3a);
			 		System.out.println("		%%%% buildRobotAction ah " + ah + " for " + arg3a);
			 		if( ah != null ) ah.addEventId(answerEvID);
			 		//TO BE REMOVED (just for testing)
					//new ActionEventHandler("evh"+actionInstncaName,RobotSysKb.robotActorCtx,answerEvID);
//				}
 				}
 			}
			ActorActionDescr action = new ActorActionDescr( 
					ActorActionType.userdef, actionName, a , duration, 
						endEvId, answerEvID, actionInfo.trim());
			return action;			
		}
	}

}
