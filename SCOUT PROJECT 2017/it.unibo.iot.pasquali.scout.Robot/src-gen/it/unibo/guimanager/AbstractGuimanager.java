/* Generated by AN DISI Unibo */ 
package it.unibo.guimanager;
import alice.tuprolog.Struct;
import alice.tuprolog.Term;
import it.unibo.qactors.QActorContext;
import it.unibo.qactors.ActorTerminationMessage;
import it.unibo.qactors.QActorMessage;
import it.unibo.qactors.QActorUtils;
import it.unibo.contactEvent.interfaces.IEventItem;
import it.unibo.is.interfaces.IOutputEnvView;
import it.unibo.qactors.action.ActionReceiveTimed;
import it.unibo.qactors.action.AsynchActionResult;
import it.unibo.qactors.action.IActorAction;
import it.unibo.qactors.action.IActorAction.ActionExecMode;
import it.unibo.qactors.action.IMsgQueue;
import it.unibo.qactors.akka.QActor;


//REGENERATE AKKA: QActor instead QActorPlanned
public abstract class AbstractGuimanager extends QActor { 
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
	
	
		public AbstractGuimanager(String actorId, QActorContext myCtx, IOutputEnvView outEnvView )  throws Exception{
			super(actorId, myCtx,  
			"./srcMore/it/unibo/guimanager/WorldTheory.pl",
			setTheEnv( outEnvView )  , "init");		
			this.planFilePath = "./srcMore/it/unibo/guimanager/plans.txt";
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
	    		temporaryStr = "\"++++++++++++++++++++++++++ guiManager(starts) ++++++++++++++++++\"";
	    		println( temporaryStr );  
	    		parg = "consult(\"guiTheory.pl\")";
	    		//REGENERATE AKKA
	    		aar = solveGoalReactive(parg,210000000,"","");
	    		//println(getName() + " plan " + curPlanInExec  +  " interrupted=" + aar.getInterrupted() + " action goon="+aar.getGoon());
	    		if( aar.getInterrupted() ){
	    			curPlanInExec   = "init";
	    			if( ! aar.getGoon() ) break;
	    		} 			
	    		if( ! planUtils.switchToPlan("enableOrDie").getGoon() ) break;
	    break;
	    }//while
	    return returnValue;
	    }catch(Exception e){
	       //println( getName() + " plan=init WARNING:" + e.getMessage() );
	       QActorContext.terminateQActorSystem(this); 
	       return false;  
	    }
	    }
	    public boolean enableOrDie() throws Exception{	//public to allow reflection
	    try{
	    	int nPlanIter = 0;
	    	//curPlanInExec =  "enableOrDie";
	    	boolean returnValue = suspendWork;
	    while(true){
	    	curPlanInExec =  "enableOrDie";	//within while since it can be lost by switchlan
	    	nPlanIter++;
	    		if( (guardVars = QActorUtils.evalTheGuard(this, " !?robotMode(gui)" )) != null ){
	    		parg = "showGUI";
	    		parg = QActorUtils.substituteVars(guardVars,parg);
	    		//REGENERATE AKKA
	    		aar = solveGoalReactive(parg,0,"","");
	    		//println(getName() + " plan " + curPlanInExec  +  " interrupted=" + aar.getInterrupted() + " action goon="+aar.getGoon());
	    		if( aar.getInterrupted() ){
	    			curPlanInExec   = "enableOrDie";
	    			if( ! aar.getGoon() ) break;
	    		} 			
	    		}
	    		else{ println( "robot mode = robot" );
	    		//QActorContext.terminateQActorSystem(this); 
	    		}if( ! planUtils.switchToPlan("waitMap").getGoon() ) break;
	    break;
	    }//while
	    return returnValue;
	    }catch(Exception e){
	       //println( getName() + " plan=enableOrDie WARNING:" + e.getMessage() );
	       QActorContext.terminateQActorSystem(this); 
	       return false;  
	    }
	    }
	    public boolean waitMap() throws Exception{	//public to allow reflection
	    try{
	    	int nPlanIter = 0;
	    	//curPlanInExec =  "waitMap";
	    	boolean returnValue = suspendWork;
	    while(true){
	    	curPlanInExec =  "waitMap";	//within while since it can be lost by switchlan
	    	nPlanIter++;
	    		//senseEvent
	    		timeoutval = 1000000000;
	    		aar = planUtils.senseEvents( timeoutval,"enableGUI","continue",
	    		"" , "",ActionExecMode.synch );
	    		if( ! aar.getGoon() || aar.getTimeRemained() <= 0 ){
	    			//println("			WARNING: sense timeout");
	    			addRule("tout(senseevent,"+getName()+")");
	    		}
	    		//onEvent
	    		if( currentEvent.getEventId().equals("enableGUI") ){
	    		 		String parg="showMap(START,FILENAME)";
	    		 		/* PHead */
	    		 		parg =  updateVars( Term.createTerm("enableGUI(START,FILENAME)"), Term.createTerm("enableGUI(START,FILENAME)"), 
	    		 			    		  					Term.createTerm(currentEvent.getMsg()), parg);
	    		 			if( parg != null ) {
	    		 			    aar = QActorUtils.solveGoal(this,myCtx,pengine,parg,"",outEnvView,0);
	    		 				//println(getName() + " plan " + curPlanInExec  +  " interrupted=" + aar.getInterrupted() + " action goon="+aar.getGoon());
	    		 				if( aar.getInterrupted() ){
	    		 					curPlanInExec   = "waitMap";
	    		 					if( ! aar.getGoon() ) break;
	    		 				} 			
	    		 				if( aar.getResult().equals("failure")){
	    		 					if( ! aar.getGoon() ) break;
	    		 				}else if( ! aar.getGoon() ) break;
	    		 			}
	    		 }
	    		if( ! planUtils.switchToPlan("waitUpdates").getGoon() ) break;
	    break;
	    }//while
	    return returnValue;
	    }catch(Exception e){
	       //println( getName() + " plan=waitMap WARNING:" + e.getMessage() );
	       QActorContext.terminateQActorSystem(this); 
	       return false;  
	    }
	    }
	    public boolean waitUpdates() throws Exception{	//public to allow reflection
	    try{
	    	int nPlanIter = 0;
	    	//curPlanInExec =  "waitUpdates";
	    	boolean returnValue = suspendWork;
	    while(true){
	    	curPlanInExec =  "waitUpdates";	//within while since it can be lost by switchlan
	    	nPlanIter++;
	    		//senseEvent
	    		timeoutval = 1000000000;
	    		aar = planUtils.senseEvents( timeoutval,"show,end,abort","continue,continue,continue",
	    		"" , "",ActionExecMode.synch );
	    		if( ! aar.getGoon() || aar.getTimeRemained() <= 0 ){
	    			//println("			WARNING: sense timeout");
	    			addRule("tout(senseevent,"+getName()+")");
	    		}
	    		//onEvent
	    		if( currentEvent.getEventId().equals("show") ){
	    		 		String parg="updateState(POS,DIR)";
	    		 		/* PHead */
	    		 		parg =  updateVars( Term.createTerm("show(POS,DIR)"), Term.createTerm("show(POS,DIR)"), 
	    		 			    		  					Term.createTerm(currentEvent.getMsg()), parg);
	    		 			if( parg != null ) {
	    		 			    aar = QActorUtils.solveGoal(this,myCtx,pengine,parg,"",outEnvView,0);
	    		 				//println(getName() + " plan " + curPlanInExec  +  " interrupted=" + aar.getInterrupted() + " action goon="+aar.getGoon());
	    		 				if( aar.getInterrupted() ){
	    		 					curPlanInExec   = "waitUpdates";
	    		 					if( ! aar.getGoon() ) break;
	    		 				} 			
	    		 				if( aar.getResult().equals("failure")){
	    		 					if( ! aar.getGoon() ) break;
	    		 				}else if( ! aar.getGoon() ) break;
	    		 			}
	    		 }
	    		//onEvent
	    		if( currentEvent.getEventId().equals("end") ){
	    		 		//println("WARNING: variable substitution not yet implmented " ); 
	    		 		returnValue = continueWork; //we must restore nPlanIter and curPlanInExec of the 'interrupted' plan
	    		 }
	    		//onEvent
	    		if( currentEvent.getEventId().equals("abort") ){
	    		 		//println("WARNING: variable substitution not yet implmented " ); 
	    		 		returnValue = continueWork; //we must restore nPlanIter and curPlanInExec of the 'interrupted' plan
	    		 }
	    		if( planUtils.repeatPlan(nPlanIter,0).getGoon() ) continue;
	    break;
	    }//while
	    return returnValue;
	    }catch(Exception e){
	       //println( getName() + " plan=waitUpdates WARNING:" + e.getMessage() );
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
	    		temporaryStr = "\"GUI ----------------------------- consult gone wrong\"";
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
	    protected void initSensorSystem(){
	    	//doing nothing in a QActor
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
	
