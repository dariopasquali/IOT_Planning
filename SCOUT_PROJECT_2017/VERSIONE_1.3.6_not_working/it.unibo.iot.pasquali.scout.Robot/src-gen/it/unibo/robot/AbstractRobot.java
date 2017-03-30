/* Generated by AN DISI Unibo */ 
package it.unibo.robot;
import alice.tuprolog.SolveInfo;
import alice.tuprolog.Struct;
import alice.tuprolog.Term;
import it.unibo.qactors.QActorContext;
import it.unibo.is.interfaces.IOutputEnvView;
import it.unibo.qactors.action.ActionReceiveTimed;
import it.unibo.qactors.action.AsynchActionResult;
import it.unibo.qactors.action.IActorAction;
import it.unibo.qactors.action.IActorAction.ActionExecMode;
import it.unibo.iot.configurator.Configurator;
import it.unibo.iot.executors.baseRobot.IBaseRobot; 
import it.unibo.iot.models.sensorData.distance.IDistanceSensorData;
import it.unibo.iot.models.sensorData.impact.IImpactSensorData;
import it.unibo.iot.models.sensorData.line.ILineSensorData;
import it.unibo.iot.models.sensorData.magnetometer.IMagnetometerSensorData;
import it.unibo.iot.sensors.ISensor; 
import it.unibo.iot.sensors.ISensorObserver;
import it.unibo.iot.sensors.distanceSensor.DistanceSensor;
import it.unibo.iot.sensors.impactSensor.ImpactSensor;
import it.unibo.iot.sensors.lineSensor.LineSensor;
import it.unibo.iot.sensors.magnetometerSensor.MagnetometerSensor;
import it.unibo.qactors.action.IMsgQueue;
import it.unibo.qactors.QActorMessage;
import it.unibo.qactors.QActorUtils;


class QaRobotActor extends it.unibo.qactor.robot.RobotActor{
	public QaRobotActor(
		String name, QActorContext ctx, String worldTheoryPath,
			IOutputEnvView outEnvView, String baserobot, String defaultPlan )  throws Exception{
		super(name, ctx, "./srcMore/it/unibo/robot/plans.txt", worldTheoryPath,
		outEnvView, it.unibo.qactor.robot.RobotSysKb.setRobotBase(ctx, baserobot) , defaultPlan);
	}
}

public class AbstractRobot extends QaRobotActor { 
protected AsynchActionResult aar = null;
protected boolean actionResult = true;
protected alice.tuprolog.SolveInfo sol;
//protected IMsgQueue mysupport ;  //defined in QActor
protected String planFilePath    = null;
protected String terminationEvId = "default";
protected String parg="";
protected boolean bres=false;
protected IActorAction  action;

		protected static IOutputEnvView setTheEnv(IOutputEnvView outEnvView ){
			return outEnvView;
		}


	public AbstractRobot(String actorId, QActorContext myCtx, IOutputEnvView outEnvView ,String baserobot)  throws Exception{
		super(actorId, myCtx,  
		"./srcMore/it/unibo/robot/WorldTheory.pl",
		setTheEnv( outEnvView ) ,baserobot , "init");		
		this.planFilePath = "./srcMore/it/unibo/robot/plans.txt";
		//Plan interpretation is done in Prolog
		//if(planFilePath != null) planUtils.buildPlanTable(planFilePath);
 	}
	@Override
	protected void doJob() throws Exception {
		String name  = getName().replace("_ctrl", "");
		mysupport = (IMsgQueue) QActorUtils.getQActor( name ); 
 		initSensorSystem();
		boolean res = init();
		//println(getName() + " doJob " + res );
		QActorContext.terminateQActorSystem(this);
	} 
	/* 
	* ------------------------------------------------------------
	* PLANS
	* ------------------------------------------------------------
	*/
    public boolean init() throws Exception{	//public to allow reflection
    try{
    	int nPlanIter = 0;
    	//curPlanInExec =  "init";
    	boolean returnValue = suspendWork;
    while(true){
    	curPlanInExec =  "init";	//within while since it can be lost by switchlan
    	nPlanIter++;
    		temporaryStr = "\"load theories\"";
    		println( temporaryStr );  
    		parg = "consult(\"robotTheory.pl\")";
    		//REGENERATE AKKA
    		aar = solveGoalReactive(parg,210000000,"","");
    		//println(getName() + " plan " + curPlanInExec  +  " interrupted=" + aar.getInterrupted() + " action goon="+aar.getGoon());
    		if( aar.getInterrupted() ){
    			curPlanInExec   = "init";
    			if( ! aar.getGoon() ) break;
    		} 			
    		parg = "consult(\"navRobotTheory.pl\")";
    		//REGENERATE AKKA
    		aar = solveGoalReactive(parg,210000000,"","");
    		//println(getName() + " plan " + curPlanInExec  +  " interrupted=" + aar.getInterrupted() + " action goon="+aar.getGoon());
    		if( aar.getInterrupted() ){
    			curPlanInExec   = "init";
    			if( ! aar.getGoon() ) break;
    		} 			
    		parg = "consult(\"exploreRobotTheory.pl\")";
    		//REGENERATE AKKA
    		aar = solveGoalReactive(parg,210000000,"","");
    		//println(getName() + " plan " + curPlanInExec  +  " interrupted=" + aar.getInterrupted() + " action goon="+aar.getGoon());
    		if( aar.getInterrupted() ){
    			curPlanInExec   = "init";
    			if( ! aar.getGoon() ) break;
    		} 			
    		parg = "consult(\"talkTheory.pl\")";
    		//REGENERATE AKKA
    		aar = solveGoalReactive(parg,210000000,"","");
    		//println(getName() + " plan " + curPlanInExec  +  " interrupted=" + aar.getInterrupted() + " action goon="+aar.getGoon());
    		if( aar.getInterrupted() ){
    			curPlanInExec   = "init";
    			if( ! aar.getGoon() ) break;
    		} 			
    		temporaryStr = "\"++++++++++++++++++ robot(starts) ++++++++++++++++++\"";
    		println( temporaryStr );  
    		if( ! planUtils.switchToPlan("waitConsoleCommand").getGoon() ) break;
    break;
    }//while
    return returnValue;
    }catch(Exception e){
       //println( getName() + " plan=init WARNING:" + e.getMessage() );
       QActorContext.terminateQActorSystem(this); 
       return false;  
    }
    }
    public boolean waitConsoleCommand() throws Exception{	//public to allow reflection
    try{
    	int nPlanIter = 0;
    	//curPlanInExec =  "waitConsoleCommand";
    	boolean returnValue = suspendWork;
    while(true){
    	curPlanInExec =  "waitConsoleCommand";	//within while since it can be lost by switchlan
    	nPlanIter++;
    		temporaryStr = "\"++++++++++++++++++ WAIT CONSOLE COMMAND ++++++++++++++++++\"";
    		println( temporaryStr );  
    		//ReceiveMsg
    		 		aar = planUtils.receiveAMsg(mysupport,600000000, "" , "" ); 	//could block
    			    if( ! aar.getGoon() || aar.getTimeRemained() <= 0 ){
    			    	//println("	WARNING: receivemsg timeout " + aar.getTimeRemained());
    			    	addRule("tout(receivemsg,"+getName()+")");
    			    }
    		printCurrentMessage(false);
    		memoCurrentMessage( false );
    		//onMsg
    		if( currentMessage.msgId().equals("explore") ){
    			String parg = "";
    			/* SwitchPlan */
    			parg =  updateVars(  Term.createTerm("explore"), Term.createTerm("explore"), 
    				    		  					Term.createTerm(currentMessage.msgContent()), parg);
    				if( parg != null ){
    					 if( ! planUtils.switchToPlan("exploration").getGoon() ) break; 
    				}//else println("guard  fails");  //parg is null when there is no guard (onEvent)
    		}//onMsg
    		if( currentMessage.msgId().equals("exploredebug") ){
    			String parg = "";
    			/* SwitchPlan */
    			parg =  updateVars(  Term.createTerm("exploredebug(START,BOUNDS)"), Term.createTerm("exploredebug(START,BOUNDS)"), 
    				    		  					Term.createTerm(currentMessage.msgContent()), parg);
    				if( parg != null ){
    					 if( ! planUtils.switchToPlan("explorationDebug").getGoon() ) break; 
    				}//else println("guard  fails");  //parg is null when there is no guard (onEvent)
    		}//onMsg
    		if( currentMessage.msgId().equals("explorefile") ){
    			String parg = "";
    			/* SwitchPlan */
    			parg =  updateVars(  Term.createTerm("explorefile(START,FILENAME)"), Term.createTerm("explorefile(START,FILENAME)"), 
    				    		  					Term.createTerm(currentMessage.msgContent()), parg);
    				if( parg != null ){
    					 if( ! planUtils.switchToPlan("explorationFile").getGoon() ) break; 
    				}//else println("guard  fails");  //parg is null when there is no guard (onEvent)
    		}//onMsg
    		if( currentMessage.msgId().equals("navigate") ){
    			String parg = "";
    			/* SwitchPlan */
    			parg =  updateVars(  Term.createTerm("navigate(PLAN_MOVES,POS)"), Term.createTerm("navigate(PLAN_MOVES,POS)"), 
    				    		  					Term.createTerm(currentMessage.msgContent()), parg);
    				if( parg != null ){
    					 if( ! planUtils.switchToPlan("navigation").getGoon() ) break; 
    				}//else println("guard  fails");  //parg is null when there is no guard (onEvent)
    		}//onMsg
    		if( currentMessage.msgId().equals("navigatefile") ){
    			String parg = "";
    			/* SwitchPlan */
    			parg =  updateVars(  Term.createTerm("navigatefile(PLAN_MOVES,POS,FILENAME)"), Term.createTerm("navigatefile(PLAN_MOVES,POS,FILENAME)"), 
    				    		  					Term.createTerm(currentMessage.msgContent()), parg);
    				if( parg != null ){
    					 if( ! planUtils.switchToPlan("navigationFile").getGoon() ) break; 
    				}//else println("guard  fails");  //parg is null when there is no guard (onEvent)
    		}if( planUtils.repeatPlan(nPlanIter,0).getGoon() ) continue;
    break;
    }//while
    return returnValue;
    }catch(Exception e){
       //println( getName() + " plan=waitConsoleCommand WARNING:" + e.getMessage() );
       QActorContext.terminateQActorSystem(this); 
       return false;  
    }
    }
    public boolean exploration() throws Exception{	//public to allow reflection
    try{
    	int nPlanIter = 0;
    	//curPlanInExec =  "exploration";
    	boolean returnValue = suspendWork;
    while(true){
    	curPlanInExec =  "exploration";	//within while since it can be lost by switchlan
    	nPlanIter++;
    		temporaryStr = "\"\"";
    		println( temporaryStr );  
    break;
    }//while
    return returnValue;
    }catch(Exception e){
       //println( getName() + " plan=exploration WARNING:" + e.getMessage() );
       QActorContext.terminateQActorSystem(this); 
       return false;  
    }
    }
    public boolean explorationFile() throws Exception{	//public to allow reflection
    try{
    	int nPlanIter = 0;
    	//curPlanInExec =  "explorationFile";
    	boolean returnValue = suspendWork;
    while(true){
    	curPlanInExec =  "explorationFile";	//within while since it can be lost by switchlan
    	nPlanIter++;
    		temporaryStr = "\"Let's explore a file!!\"";
    		println( temporaryStr );  
    		parg = "initialConfigExpRobot";
    		//REGENERATE AKKA
    		aar = solveGoalReactive(parg,0,"","");
    		//println(getName() + " plan " + curPlanInExec  +  " interrupted=" + aar.getInterrupted() + " action goon="+aar.getGoon());
    		if( aar.getInterrupted() ){
    			curPlanInExec   = "explorationFile";
    			if( ! aar.getGoon() ) break;
    		} 			
    		if( (guardVars = QActorUtils.evalTheGuard(this, " !?msg(_,_,SENDER,X,explorefile(START,FILENAME),_)" )) != null ){
    		temporaryStr = QActorUtils.unifyMsgContent(pengine, "enableGUI(START,FILENAME)","enableGUI(START,FILENAME)", guardVars ).toString();
    		emit( "enableGUI", temporaryStr );
    		}
    		if( (guardVars = QActorUtils.evalTheGuard(this, " ??msg(_,_,SENDER,X,explorefile(START,FILENAME),_)" )) != null ){
    		parg = "initExploreFile(START,FILENAME)";
    		parg = QActorUtils.substituteVars(guardVars,parg);
    		//REGENERATE AKKA
    		aar = solveGoalReactive(parg,0,"abort","abort");
    		//println(getName() + " plan " + curPlanInExec  +  " interrupted=" + aar.getInterrupted() + " action goon="+aar.getGoon());
    		if( aar.getInterrupted() ){
    			curPlanInExec   = "explorationFile";
    			if( ! aar.getGoon() ) break;
    		} 			
    		}
    		temporaryStr = QActorUtils.unifyMsgContent(pengine, "end","end", guardVars ).toString();
    		emit( "end", temporaryStr );
    		temporaryStr = "\"Exploration Done ****************************\"";
    		println( temporaryStr );  
    		returnValue = continueWork; //we must restore nPlanIter and curPlanInExec of the 'interrupted' plan
    break;
    }//while
    return returnValue;
    }catch(Exception e){
       //println( getName() + " plan=explorationFile WARNING:" + e.getMessage() );
       QActorContext.terminateQActorSystem(this); 
       return false;  
    }
    }
    public boolean explorationDebug() throws Exception{	//public to allow reflection
    try{
    	int nPlanIter = 0;
    	//curPlanInExec =  "explorationDebug";
    	boolean returnValue = suspendWork;
    while(true){
    	curPlanInExec =  "explorationDebug";	//within while since it can be lost by switchlan
    	nPlanIter++;
    		temporaryStr = "\"Let's explore!!\"";
    		println( temporaryStr );  
    		if( (guardVars = QActorUtils.evalTheGuard(this, " ??msg(_,_,SENDER,X,explore(START,BOUNDS),_)" )) != null ){
    		parg = "initExploreMap(START,BOUNDS)";
    		parg = QActorUtils.substituteVars(guardVars,parg);
    		//REGENERATE AKKA
    		aar = solveGoalReactive(parg,0,"abort","abort");
    		//println(getName() + " plan " + curPlanInExec  +  " interrupted=" + aar.getInterrupted() + " action goon="+aar.getGoon());
    		if( aar.getInterrupted() ){
    			curPlanInExec   = "explorationDebug";
    			if( ! aar.getGoon() ) break;
    		} 			
    		}
    		temporaryStr = QActorUtils.unifyMsgContent(pengine, "end","end", guardVars ).toString();
    		emit( "end", temporaryStr );
    		temporaryStr = "\"Exploration Done ****************************\"";
    		println( temporaryStr );  
    		returnValue = continueWork; //we must restore nPlanIter and curPlanInExec of the 'interrupted' plan
    break;
    }//while
    return returnValue;
    }catch(Exception e){
       //println( getName() + " plan=explorationDebug WARNING:" + e.getMessage() );
       QActorContext.terminateQActorSystem(this); 
       return false;  
    }
    }
    public boolean navigation() throws Exception{	//public to allow reflection
    try{
    	int nPlanIter = 0;
    	//curPlanInExec =  "navigation";
    	boolean returnValue = suspendWork;
    while(true){
    	curPlanInExec =  "navigation";	//within while since it can be lost by switchlan
    	nPlanIter++;
    		temporaryStr = "\"I Received some navigation data\"";
    		println( temporaryStr );  
    		parg = "initialConfigNavRobot";
    		//REGENERATE AKKA
    		aar = solveGoalReactive(parg,0,"","");
    		//println(getName() + " plan " + curPlanInExec  +  " interrupted=" + aar.getInterrupted() + " action goon="+aar.getGoon());
    		if( aar.getInterrupted() ){
    			curPlanInExec   = "navigation";
    			if( ! aar.getGoon() ) break;
    		} 			
    		if( (guardVars = QActorUtils.evalTheGuard(this, " ??msg(_,_,SENDER,X,navigate(PLAN_MOVES,POS),MSGNUM)" )) != null ){
    		parg = "loadNavigationData(PLAN_MOVES,POS)";
    		parg = QActorUtils.substituteVars(guardVars,parg);
    		//REGENERATE AKKA
    		aar = solveGoalReactive(parg,100000,"","");
    		//println(getName() + " plan " + curPlanInExec  +  " interrupted=" + aar.getInterrupted() + " action goon="+aar.getGoon());
    		if( aar.getInterrupted() ){
    			curPlanInExec   = "navigation";
    			if( ! aar.getGoon() ) break;
    		} 			
    		}
    		if( ! planUtils.switchToPlan("startNavigation").getGoon() ) break;
    break;
    }//while
    return returnValue;
    }catch(Exception e){
       //println( getName() + " plan=navigation WARNING:" + e.getMessage() );
       QActorContext.terminateQActorSystem(this); 
       return false;  
    }
    }
    public boolean navigationFile() throws Exception{	//public to allow reflection
    try{
    	int nPlanIter = 0;
    	//curPlanInExec =  "navigationFile";
    	boolean returnValue = suspendWork;
    while(true){
    	curPlanInExec =  "navigationFile";	//within while since it can be lost by switchlan
    	nPlanIter++;
    		temporaryStr = "\"I Received some navigation data and a filename\"";
    		println( temporaryStr );  
    		parg = "initialConfigNavRobot";
    		//REGENERATE AKKA
    		aar = solveGoalReactive(parg,0,"","");
    		//println(getName() + " plan " + curPlanInExec  +  " interrupted=" + aar.getInterrupted() + " action goon="+aar.getGoon());
    		if( aar.getInterrupted() ){
    			curPlanInExec   = "navigationFile";
    			if( ! aar.getGoon() ) break;
    		} 			
    		if( (guardVars = QActorUtils.evalTheGuard(this, " !?msg(_,_,SENDER,X,navigatefile(PLAN_MOVES,POS,FILENAME),MSGNUM)" )) != null ){
    		temporaryStr = QActorUtils.unifyMsgContent(pengine, "enableGUI(START,FILENAME)","enableGUI(POS,FILENAME)", guardVars ).toString();
    		emit( "enableGUI", temporaryStr );
    		}
    		if( (guardVars = QActorUtils.evalTheGuard(this, " ??msg(_,_,SENDER,X,navigatefile(PLAN_MOVES,POS,FILENAME),MSGNUM)" )) != null ){
    		parg = "loadNavigationData(PLAN_MOVES,POS,FILENAME)";
    		parg = QActorUtils.substituteVars(guardVars,parg);
    		//REGENERATE AKKA
    		aar = solveGoalReactive(parg,100000,"","");
    		//println(getName() + " plan " + curPlanInExec  +  " interrupted=" + aar.getInterrupted() + " action goon="+aar.getGoon());
    		if( aar.getInterrupted() ){
    			curPlanInExec   = "navigationFile";
    			if( ! aar.getGoon() ) break;
    		} 			
    		}
    		if( ! planUtils.switchToPlan("startNavigation").getGoon() ) break;
    break;
    }//while
    return returnValue;
    }catch(Exception e){
       //println( getName() + " plan=navigationFile WARNING:" + e.getMessage() );
       QActorContext.terminateQActorSystem(this); 
       return false;  
    }
    }
    public boolean startNavigation() throws Exception{	//public to allow reflection
    try{
    	int nPlanIter = 0;
    	//curPlanInExec =  "startNavigation";
    	boolean returnValue = suspendWork;
    while(true){
    	curPlanInExec =  "startNavigation";	//within while since it can be lost by switchlan
    	nPlanIter++;
    		if( (guardVars = QActorUtils.evalTheGuard(this, " !?planName(PLANNAME)" )) != null ){
    		parg = "myClearPlan(PLANNAME)";
    		parg = QActorUtils.substituteVars(guardVars,parg);
    		//REGENERATE AKKA
    		aar = solveGoalReactive(parg,0,"","");
    		//println(getName() + " plan " + curPlanInExec  +  " interrupted=" + aar.getInterrupted() + " action goon="+aar.getGoon());
    		if( aar.getInterrupted() ){
    			curPlanInExec   = "startNavigation";
    			if( ! aar.getGoon() ) break;
    		} 			
    		}
    		if( (guardVars = QActorUtils.evalTheGuard(this, " !?planFilename(FILENAME)" )) != null ){
    		parg = "loadThePlan(FILENAME)";
    		parg = QActorUtils.substituteVars(guardVars,parg);
    		//REGENERATE AKKA
    		aar = solveGoalReactive(parg,300000000,"","");
    		//println(getName() + " plan " + curPlanInExec  +  " interrupted=" + aar.getInterrupted() + " action goon="+aar.getGoon());
    		if( aar.getInterrupted() ){
    			curPlanInExec   = "startNavigation";
    			if( ! aar.getGoon() ) break;
    		} 			
    		}
    		temporaryStr = "\"++++++++++++++++++ Plan Loaded ++++++++++++++++++\"";
    		println( temporaryStr );  
    		temporaryStr = "\" \"";
    		println( temporaryStr );  
    		temporaryStr = "\" \"";
    		println( temporaryStr );  
    		if( (guardVars = QActorUtils.evalTheGuard(this, " !?planName(PLANNAME)" )) != null ){
    		parg = "showPlan(PLANNAME)";
    		parg = QActorUtils.substituteVars(guardVars,parg);
    		//REGENERATE AKKA
    		aar = solveGoalReactive(parg,1000,"","");
    		//println(getName() + " plan " + curPlanInExec  +  " interrupted=" + aar.getInterrupted() + " action goon="+aar.getGoon());
    		if( aar.getInterrupted() ){
    			curPlanInExec   = "startNavigation";
    			if( ! aar.getGoon() ) break;
    		} 			
    		}
    		temporaryStr = "\" \"";
    		println( temporaryStr );  
    		temporaryStr = "\" \"";
    		println( temporaryStr );  
    		temporaryStr = "\"++++++++++++++++++ ++++++++++++++++++ ++++++++++++++++++\"";
    		println( temporaryStr );  
    		if( (guardVars = QActorUtils.evalTheGuard(this, " !?planName(PLANNAME)" )) != null ){
    		parg = "myRunPlan(PLANNAME)";
    		parg = QActorUtils.substituteVars(guardVars,parg);
    		//REGENERATE AKKA
    		aar = solveGoalReactive(parg,1000000000,"","");
    		//println(getName() + " plan " + curPlanInExec  +  " interrupted=" + aar.getInterrupted() + " action goon="+aar.getGoon());
    		if( aar.getInterrupted() ){
    			curPlanInExec   = "startNavigation";
    			if( ! aar.getGoon() ) break;
    		} 			
    		}
    break;
    }//while
    return returnValue;
    }catch(Exception e){
       //println( getName() + " plan=startNavigation WARNING:" + e.getMessage() );
       QActorContext.terminateQActorSystem(this); 
       return false;  
    }
    }
    public boolean waitAndEvaluate() throws Exception{	//public to allow reflection
    try{
    	int nPlanIter = 0;
    	//curPlanInExec =  "waitAndEvaluate";
    	boolean returnValue = suspendWork;
    while(true){
    	curPlanInExec =  "waitAndEvaluate";	//within while since it can be lost by switchlan
    	nPlanIter++;
    		temporaryStr = "\"maybe there is an unexpected static obstacle\"";
    		println( temporaryStr );  
    		//delay
    		aar = delayReactive(6000,"" , "");
    		if( aar.getInterrupted() ) curPlanInExec   = "waitAndEvaluate";
    		if( ! aar.getGoon() ) break;
    		temporaryStr = "\"are you there obstacle ??\"";
    		println( temporaryStr );  
    		//senseEvent
    		timeoutval = 5000;
    		aar = planUtils.senseEvents( timeoutval,"obstaclefront","notifyUnexpectedObstacle",
    		"" , "",ActionExecMode.synch );
    		if( ! aar.getGoon() || aar.getTimeRemained() <= 0 ){
    			//println("			WARNING: sense timeout");
    			addRule("tout(senseevent,"+getName()+")");
    		}
    		returnValue = continueWork;  
    break;
    }//while
    return returnValue;
    }catch(Exception e){
       //println( getName() + " plan=waitAndEvaluate WARNING:" + e.getMessage() );
       QActorContext.terminateQActorSystem(this); 
       return false;  
    }
    }
    public boolean notifyUnexpectedObstacle() throws Exception{	//public to allow reflection
    try{
    	int nPlanIter = 0;
    	//curPlanInExec =  "notifyUnexpectedObstacle";
    	boolean returnValue = suspendWork;
    while(true){
    	curPlanInExec =  "notifyUnexpectedObstacle";	//within while since it can be lost by switchlan
    	nPlanIter++;
    		temporaryStr = "\"I found an unexpected static obstacle\"";
    		println( temporaryStr );  
    		parg = "notifyUnexpectedObstacle";
    		//REGENERATE AKKA
    		aar = solveGoalReactive(parg,0,"","");
    		//println(getName() + " plan " + curPlanInExec  +  " interrupted=" + aar.getInterrupted() + " action goon="+aar.getGoon());
    		if( aar.getInterrupted() ){
    			curPlanInExec   = "notifyUnexpectedObstacle";
    			if( ! aar.getGoon() ) break;
    		} 			
    		temporaryStr = "\"Unexpected Obstacle notified, I wait the new plan\"";
    		println( temporaryStr );  
    		if( ! planUtils.switchToPlan("waitConsoleCommand").getGoon() ) break;
    break;
    }//while
    return returnValue;
    }catch(Exception e){
       //println( getName() + " plan=notifyUnexpectedObstacle WARNING:" + e.getMessage() );
       QActorContext.terminateQActorSystem(this); 
       return false;  
    }
    }
    public boolean notifyEndOfNavigation() throws Exception{	//public to allow reflection
    try{
    	int nPlanIter = 0;
    	//curPlanInExec =  "notifyEndOfNavigation";
    	boolean returnValue = suspendWork;
    while(true){
    	curPlanInExec =  "notifyEndOfNavigation";	//within while since it can be lost by switchlan
    	nPlanIter++;
    		temporaryStr = "\"notify End\"";
    		println( temporaryStr );  
    		temporaryStr = QActorUtils.unifyMsgContent(pengine, "end","end", guardVars ).toString();
    		emit( "end", temporaryStr );
    		if( ! planUtils.switchToPlan("waitConsoleCommand").getGoon() ) break;
    break;
    }//while
    return returnValue;
    }catch(Exception e){
       //println( getName() + " plan=notifyEndOfNavigation WARNING:" + e.getMessage() );
       QActorContext.terminateQActorSystem(this); 
       return false;  
    }
    }
    public boolean simulatedWorldChanged() throws Exception{	//public to allow reflection
    try{
    	int nPlanIter = 0;
    	//curPlanInExec =  "simulatedWorldChanged";
    	boolean returnValue = suspendWork;
    while(true){
    	curPlanInExec =  "simulatedWorldChanged";	//within while since it can be lost by switchlan
    	nPlanIter++;
    		temporaryStr = "\"******************** Simulated World Changed ****************************\"";
    		println( temporaryStr );  
    		returnValue = continueWork;  
    break;
    }//while
    return returnValue;
    }catch(Exception e){
       //println( getName() + " plan=simulatedWorldChanged WARNING:" + e.getMessage() );
       QActorContext.terminateQActorSystem(this); 
       return false;  
    }
    }
    public boolean abort() throws Exception{	//public to allow reflection
    try{
    	int nPlanIter = 0;
    	//curPlanInExec =  "abort";
    	boolean returnValue = suspendWork;
    while(true){
    	curPlanInExec =  "abort";	//within while since it can be lost by switchlan
    	nPlanIter++;
    		temporaryStr = "\"Current command aborted\"";
    		println( temporaryStr );  
    		if( ! planUtils.switchToPlan("waitConsoleCommand").getGoon() ) break;
    break;
    }//while
    return returnValue;
    }catch(Exception e){
       //println( getName() + " plan=abort WARNING:" + e.getMessage() );
       QActorContext.terminateQActorSystem(this); 
       return false;  
    }
    }
    public boolean handleTimeout() throws Exception{	//public to allow reflection
    try{
    	int nPlanIter = 0;
    	//curPlanInExec =  "handleTimeout";
    	boolean returnValue = suspendWork;
    while(true){
    	curPlanInExec =  "handleTimeout";	//within while since it can be lost by switchlan
    	nPlanIter++;
    		temporaryStr = "\"timeout!! GOODBYE\"";
    		println( temporaryStr );  
    break;
    }//while
    return returnValue;
    }catch(Exception e){
       //println( getName() + " plan=handleTimeout WARNING:" + e.getMessage() );
       QActorContext.terminateQActorSystem(this); 
       return false;  
    }
    }
    public boolean explorationFailure() throws Exception{	//public to allow reflection
    try{
    	int nPlanIter = 0;
    	//curPlanInExec =  "explorationFailure";
    	boolean returnValue = suspendWork;
    while(true){
    	curPlanInExec =  "explorationFailure";	//within while since it can be lost by switchlan
    	nPlanIter++;
    		temporaryStr = "\"Explore FAILURE\"";
    		println( temporaryStr );  
    break;
    }//while
    return returnValue;
    }catch(Exception e){
       //println( getName() + " plan=explorationFailure WARNING:" + e.getMessage() );
       QActorContext.terminateQActorSystem(this); 
       return false;  
    }
    }
    public boolean loadMapFailure() throws Exception{	//public to allow reflection
    try{
    	int nPlanIter = 0;
    	//curPlanInExec =  "loadMapFailure";
    	boolean returnValue = suspendWork;
    while(true){
    	curPlanInExec =  "loadMapFailure";	//within while since it can be lost by switchlan
    	nPlanIter++;
    		temporaryStr = "\"Load Map FAILURE\"";
    		println( temporaryStr );  
    break;
    }//while
    return returnValue;
    }catch(Exception e){
       //println( getName() + " plan=loadMapFailure WARNING:" + e.getMessage() );
       QActorContext.terminateQActorSystem(this); 
       return false;  
    }
    }
    public boolean findpathFailure() throws Exception{	//public to allow reflection
    try{
    	int nPlanIter = 0;
    	//curPlanInExec =  "findpathFailure";
    	boolean returnValue = suspendWork;
    while(true){
    	curPlanInExec =  "findpathFailure";	//within while since it can be lost by switchlan
    	nPlanIter++;
    		temporaryStr = "\"Find Path FAILURE\"";
    		println( temporaryStr );  
    break;
    }//while
    return returnValue;
    }catch(Exception e){
       //println( getName() + " plan=findpathFailure WARNING:" + e.getMessage() );
       QActorContext.terminateQActorSystem(this); 
       return false;  
    }
    }
    public boolean navigationFailure() throws Exception{	//public to allow reflection
    try{
    	int nPlanIter = 0;
    	//curPlanInExec =  "navigationFailure";
    	boolean returnValue = suspendWork;
    while(true){
    	curPlanInExec =  "navigationFailure";	//within while since it can be lost by switchlan
    	nPlanIter++;
    		temporaryStr = "\"Navigation FAILURE\"";
    		println( temporaryStr );  
    break;
    }//while
    return returnValue;
    }catch(Exception e){
       //println( getName() + " plan=navigationFailure WARNING:" + e.getMessage() );
       QActorContext.terminateQActorSystem(this); 
       return false;  
    }
    }
    public boolean alternativeFindpathFailure() throws Exception{	//public to allow reflection
    try{
    	int nPlanIter = 0;
    	//curPlanInExec =  "alternativeFindpathFailure";
    	boolean returnValue = suspendWork;
    while(true){
    	curPlanInExec =  "alternativeFindpathFailure";	//within while since it can be lost by switchlan
    	nPlanIter++;
    		temporaryStr = "\"Alternative Find Path FAILURE\"";
    		println( temporaryStr );  
    break;
    }//while
    return returnValue;
    }catch(Exception e){
       //println( getName() + " plan=alternativeFindpathFailure WARNING:" + e.getMessage() );
       QActorContext.terminateQActorSystem(this); 
       return false;  
    }
    }
    public boolean consultPrologFailure() throws Exception{	//public to allow reflection
    try{
    	int nPlanIter = 0;
    	//curPlanInExec =  "consultPrologFailure";
    	boolean returnValue = suspendWork;
    while(true){
    	curPlanInExec =  "consultPrologFailure";	//within while since it can be lost by switchlan
    	nPlanIter++;
    		temporaryStr = "\"The consult is gone wrong, maybe there are errors in the prolog file\"";
    		println( temporaryStr );  
    break;
    }//while
    return returnValue;
    }catch(Exception e){
       //println( getName() + " plan=consultPrologFailure WARNING:" + e.getMessage() );
       QActorContext.terminateQActorSystem(this); 
       return false;  
    }
    }
    public boolean generalPrologFailure() throws Exception{	//public to allow reflection
    try{
    	int nPlanIter = 0;
    	//curPlanInExec =  "generalPrologFailure";
    	boolean returnValue = suspendWork;
    while(true){
    	curPlanInExec =  "generalPrologFailure";	//within while since it can be lost by switchlan
    	nPlanIter++;
    		temporaryStr = "\"Prolog goal FAILURE\"";
    		println( temporaryStr );  
    break;
    }//while
    return returnValue;
    }catch(Exception e){
       //println( getName() + " plan=generalPrologFailure WARNING:" + e.getMessage() );
       QActorContext.terminateQActorSystem(this); 
       return false;  
    }
    }
    /* 
    * ------------------------------------------------------------
    * SENSORS
    * ------------------------------------------------------------
    */
    protected void initSensorSystem(){		
    	try {
    		String goal = "consult( \"./src/it/unibo/robot/sensorTheory.pl\" )";
    		SolveInfo sol = QActorUtils.solveGoal( goal ,pengine );
    		if( ! sol.isSuccess() ){
    			//println( "avatar initSensorSystem attempt to load sensorTheory "  );
    			goal = "consult( \"./sensorTheory.pl\" )";
    			QActorUtils.solveGoal( pengine, goal  );
    			//println( "avatar initSensorSystem= "  +  aar.getResult() );
    		}
    		addSensorObservers();
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    }
    /*
    //COMPONENTS
     RobotComponent motorleft 
     RobotComponent motorright 
    sensor distFrontMock simulated debug=0   
    sensor distLeftMock simulated debug=0   
    Composed component motors
    */
    protected void addSensorObservers(){
    	for (ISensor<?> sensor : Configurator.getInstance().getSensors()) {
    		//println( "robot sensor= "  + sensor.getDefStringRep() );
    		//println( "robot sensor class= "  + sensor.getClass().getName() );
        	if( sensor instanceof DistanceSensor){
        		DistanceSensor sensorDistance  = (DistanceSensor) sensor;
        		ISensorObserver<IDistanceSensorData> obs = new SensorObserver<IDistanceSensorData>(this,outEnvView);
        //		println( "avatar add observer to  "  + sensorDistance.getDefStringRep() );
        		sensorDistance.addObserver(  obs  ) ;
        	}
        	if( sensor instanceof LineSensor){
        		LineSensor sensorLine = (LineSensor) sensor;
         		ISensorObserver<ILineSensorData> obs = new SensorObserver<ILineSensorData>(this,outEnvView);
        //		println( "avatar add observer to  "  + sensorLine.getDefStringRep() );
        		sensorLine.addObserver(  obs  ) ;
        	}
         	if( sensor instanceof MagnetometerSensor){
        		MagnetometerSensor sensorMagneto = (MagnetometerSensor) sensor;
         		ISensorObserver<IMagnetometerSensorData> obs = new SensorObserver<IMagnetometerSensorData>(this,outEnvView);
        //		println( "avatar add observer to  "  + sensorMagneto.getDefStringRep() );
        		sensorMagneto.addObserver(  obs  ) ;
        	}
    		if( sensor instanceof ImpactSensor){
    			ImpactSensor sensorImpact = (ImpactSensor) sensor;
    			ISensorObserver<IImpactSensorData> obs = new SensorObserver<IImpactSensorData>(this,outEnvView);
    	//		println( "avatar add observer to  "  + sensorMagneto.getDefStringRep() );
    			sensorImpact.addObserver(  obs  ) ;
    		}
    	}		
    }	
    
 
	/* 
	* ------------------------------------------------------------
	* APPLICATION ACTIONS
	* ------------------------------------------------------------
	*/
	/* 
	* ------------------------------------------------------------
	* QUEUE  
	* ------------------------------------------------------------
	*/
	    protected void getMsgFromInputQueue(){
//	    	println( " %%%% getMsgFromInputQueue" ); 
	    	QActorMessage msg = mysupport.getMsgFromQueue(); //blocking
//	    	println( " %%%% getMsgFromInputQueue continues with " + msg );
	    	this.currentMessage = msg;
	    }
  }

