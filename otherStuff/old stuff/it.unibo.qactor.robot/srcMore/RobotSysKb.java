/*
 * Defines a basic set of robot commands  to be used for fast prototyping.
 * Each BaseRobotXXX command inherits from 
 *    BaseRobotCommand (implements IBaseRobotCommand) that inherits from 
 *    Command (implements ICommand)
 */

/*
 * ADDED TO TEST robot code generation
 * It could be removed
 */
package it.unibo.qactor.robot.utils; 
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.ListIterator;
import java.util.Map;
import java.util.Properties;
import java.util.Map.Entry;

import alice.tuprolog.Prolog;
import alice.tuprolog.SolveInfo;
import alice.tuprolog.Struct;
import alice.tuprolog.Term;
import alice.tuprolog.Var;
import it.unibo.iot.configurator.Configurator;
import it.unibo.iot.executors.baseRobot.IBaseRobot;
import it.unibo.iot.models.commands.baseRobot.*;
import it.unibo.is.interfaces.IBasicUniboEnv;
import it.unibo.is.interfaces.IOutputView;
import it.unibo.qactor.robot.IRobotActor;
import it.unibo.qactor.robot.RobotActor;
import it.unibo.qactor.robot.devices.RobotWebCam;
import it.unibo.qactors.ActorContext;
import it.unibo.qactors.action.*;
import it.unibo.system.SituatedSysKb;
public class RobotSysKb {
	public static final String sep="%";
	private static  Hashtable<String, IAsynchAction> asynchActionTable = 
			new Hashtable<String,IAsynchAction>();
	private static  Hashtable<String, IActionHandler> handlerTable = 
			new Hashtable<String, IActionHandler>();
	private static  Hashtable<String, Boolean> guardTable = 
			new Hashtable<String, Boolean>();

 	public static IBasicUniboEnv deviceEnv = null; 
	public static IBaseRobot robotBase ;
	public static IRobotActor robotActor ;
	public static ActorContext robotActorCtx;
 	public static Prolog prologEngine ;
  	public static RobotWebCam webCam  = null ;
	
	public static final String speedCmd = "speed";
	public static final String forwardCmd = "w";
	public static final String backwardCmd = "s";
	public static final String leftCmd = "a";
	public static final String rightCmd = "d";
	public static final String stopCmd = "h";
	public static final String halt= "alarmhalt";

	public static final String forwardCommand = "forward";
	public static final String backwardCommand = "backward";
	public static final String leftCommand = "left";
	public static final String rightCommand = "right";
	public static final String stopCommand = "stop";

 
	public static final IBaseRobotSpeed SPEED_LOW    = new BaseRobotSpeed(BaseRobotSpeedValue.ROBOT_SPEED_LOW);
	public static final IBaseRobotSpeed SPEED_MEDIUM = new BaseRobotSpeed(BaseRobotSpeedValue.ROBOT_SPEED_MEDIUM);
	public static final IBaseRobotSpeed SPEED_HIGH   = new BaseRobotSpeed(BaseRobotSpeedValue.ROBOT_SPEED_HIGH);

	public static final IBaseRobotCommand FORWARD  = new BaseRobotForward(SPEED_HIGH);
	public static final IBaseRobotCommand BACKWARD = new BaseRobotBackward(SPEED_MEDIUM);
	public static final IBaseRobotCommand STOP     = new BaseRobotStop(SPEED_LOW);
	public static final IBaseRobotCommand LEFT     = new BaseRobotLeft(SPEED_LOW );
	public static final IBaseRobotCommand RIGHT    = new BaseRobotRight(SPEED_LOW );
	
	public static final RobotWebCam getRobotwebCam( ){
		if( webCam == null ) {
			try{ 
	 			webCam    = new RobotWebCam(SituatedSysKb.standardOutEnvView);
			}catch(Exception e){
				return null;
			}			
		}
		return webCam;
	}
	public static final void setRobotBase( ){
		robotBase = Configurator.getInstance().getBaseRobot();
	}
 	public static final IBaseRobot setRobotBase(ActorContext ctx, String robotName ){
 		robotBase  = Configurator.getInstance().getBaseRobot();//Works using reflection on dir configuration
// 		System.out.println(" *** robotBase class= " + robotBase.getClass().getName() );
 		robotActorCtx = ctx;
		prologEngine  = ctx.getPrologEngine();
		return robotBase;
	}
	public static final IBaseRobot getRobotBase( ) throws Exception{
		if( robotBase == null ) throw new Exception("non robotBase");
		else return robotBase;
	}
	public static final void setRobotActor(ActorContext ctx, String robotName, InputStream resourceAsStream) throws Exception{
		loadRobotActorConfig(resourceAsStream); //"./srcMore/robotConfig.properties"
		robotBase  = Configurator.getInstance().getBaseRobot();
		robotActor = new RobotActor(robotName,  ctx, "","", SituatedSysKb.standardOutEnvView, robotBase,"");
		robotActorCtx = ctx;
		prologEngine  = ctx.getPrologEngine();
	}
	public static final IRobotActor getRobotActor( ){
		return robotActor ;
	}
	public static String getCmdName(String userCmdMsg){
  		//userCmdMsg : usercmd("h-Low")
  		Struct cmdMsgStruct = (Struct) Term.createTerm(userCmdMsg);
		char b = cmdMsgStruct.getArg(0).toString().replace("'","").charAt(0);
		if (b == 'q' || b=='h' ) {
			return stopCommand;
		} else if (b == 'w') {
			return forwardCommand;
		} else if (b == 's') {
			return backwardCommand;
	 	} else if (b == 'a') {
	 		return leftCommand;
	 	} else if (b == 'd') {
	 		return rightCommand;
	 	}
		return stopCommand;
	}
	
/*
 * Robot actor feature configuration	
 */
	public static String usbConnKey     = "usbConn";
	public static String httpConnKey    = "httpConn";
	public static String envKey         = "hasEnv";
	public static String robotAvatarKey = "robotAvatar";
	public static Map<String, Boolean> robotConfigMap ;
	
	static void loadRobotActorConfig(InputStream resourceAsStream) {
		Properties properties = new Properties();
		robotConfigMap = new HashMap<String, Boolean>();
		try {
//			InputStream resourceAsStream = getClass().getResourceAsStream(fileName);
			properties.load(resourceAsStream);

			for (Entry<Object, Object> prop : properties.entrySet()) {
				String keyValStr = ((String)prop.getValue()).trim();
				Boolean keyVal = keyValStr.equals("true")  ;
//				System.out.println("key  = "+(String)prop.getKey());
//				System.out.println("value= "+(String)prop.getValue() + " " + keyVal);
				robotConfigMap.put((String) prop.getKey(), keyVal );
			}
 		} catch (IOException e) {
			e.printStackTrace();
		}
 	}
/*
* Asynch Actions , handlers and guards
*/
	
 
	public static void setAsynchAction(String actionName, IAsynchAction asynchAction){
		asynchActionTable.put(actionName, asynchAction);  
	}
	public static void removeAsynchAction( String actionName ){
		asynchActionTable.remove( actionName );  
	}
	public static IAsynchAction getAsynchAction( String actionName ){
		return asynchActionTable.get( actionName );
	}
	public static void setHandler(String actionName, IActionHandler h){
		handlerTable.put(actionName, h);  
	}
	public static void removeHandler( String hname ){
		handlerTable.remove( hname );  
	}
	public static IActionHandler getHandler( String hname ){
		return handlerTable.get( hname );
	}
/*
 * GUARD
 */
 
 	public static String getGuardPredicate( String guard ){
 		try{
//  			System.out.println("getGuardPredicate " + guard    );
 			boolean toremove = true;
 			boolean hasNot = false;
 			guard = guard.trim();
 			if( guard.startsWith("not")){
 				hasNot = true;
 				guard = guard.substring(3);
 			}
 			if( guard.startsWith("??")){
 				guard = guard.substring(2);
 				toremove = true;
 			}else if( guard.startsWith("!?")){
 				guard = guard.substring(2);
 				toremove = false; 				
			}else if( guard.endsWith("!")){
 				guard = guard.substring(0,guard.lastIndexOf('!'));
 				toremove = false; 				
 			}
  			SolveInfo sol = prologEngine.solve(  guard +".");
  			//System.out.println("getGuardPredicate " + guard + " " + sol.isSuccess()  );
			if( sol.isSuccess() ){
				if(toremove){
		  			//The guard is removed, once evaluated true  
					removeRule( guard );
				} 
				if( hasNot ) return null;
				//we must replace , with sep into a binding value in order to allow RobotActr to split correctly 
				String bindVarsOk = "";
				ListIterator<Var> bvit= sol.getBindingVars().listIterator();
				while( bvit.hasNext() ){
					Var v = bvit.next();
					String varName = v.getName();
					String val = v.getTerm().toString();
//					System.out.println("*** RobotSysKb  " + varName + " val=" + val );
					bindVarsOk = bindVarsOk + varName+"/"+val.replaceAll(",", sep) + ",";
				}
				if(bindVarsOk.length()>0) 
					bindVarsOk=bindVarsOk.substring(0, bindVarsOk.lastIndexOf(",")); //remove last ,
//				System.out.println("*** RobotSysKb bindVarsOk " + bindVarsOk );
				return "["+bindVarsOk+"]";
			}
			else if( hasNot ){
					return "[]";
				}
				else return null;
 		}catch(Exception e){
// 			System.out.println("RobotSysKb getGuardPredicate " + guard + " FAILURE " + e.getMessage()  );
 			return null;
 		}
 	}
 	public static synchronized void addRule( String rule  ){
 		try{
 			rule = rule.trim();
 			if( rule.equals("true")) return;
  			SolveInfo sol = prologEngine.solve( "setRule( " + rule + " ).");
 			//System.out.println("addRule:" + rule + " " + sol.isSuccess() );
			//TODGUARD: the guardEventRule should be called
  		}catch(Exception e){
  			System.out.println("addRule ERROR:" + rule + " " + e.getMessage() );
   		}
   	}
 	public static synchronized void removeRule( String rule  ){
 		try{
 			rule = rule.trim();
 			if( rule.equals("true")) return;
 			SolveInfo sol = prologEngine.solve( "removeRule( " + rule + " ).");
 			//System.out.println("removeRule:" + rule + " " + sol.isSuccess() );
			//TODGUARD: the guardEventRule should be called
  		}catch(Exception e){
  			System.out.println("removeRule ERROR:" + e.getMessage() );
   		}
 		
 	}
 	


}
