/* Generated by AN DISI Unibo */ 
package it.unibo.console;
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
public abstract class AbstractConsole extends QActor { 
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
	
	
		public AbstractConsole(String actorId, QActorContext myCtx, IOutputEnvView outEnvView )  throws Exception{
			super(actorId, myCtx,  
			"./srcMore/it/unibo/console/WorldTheory.pl",
			setTheEnv( outEnvView )  , "init");		
			this.planFilePath = "./srcMore/it/unibo/console/plans.txt";
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
	    	boolean returnValue = suspendWork;		//MARCHH2017
	    while(true){
	    	curPlanInExec =  "init";	//within while since it can be lost by switchlan
	    	nPlanIter++;
	    		parg = "consult(\"navConsoleTheory.pl\")";
	    		//REGENERATE AKKA
	    		aar = solveGoalReactive(parg,0,"","");
	    		//println(getName() + " plan " + curPlanInExec  +  " interrupted=" + aar.getInterrupted() + " action goon="+aar.getGoon());
	    		if( aar.getInterrupted() ){
	    			curPlanInExec   = "init";
	    			if( ! aar.getGoon() ) break;
	    		} 			
	    		parg = "consult(\"consoleTheory.pl\")";
	    		//REGENERATE AKKA
	    		aar = solveGoalReactive(parg,0,"","");
	    		//println(getName() + " plan " + curPlanInExec  +  " interrupted=" + aar.getInterrupted() + " action goon="+aar.getGoon());
	    		if( aar.getInterrupted() ){
	    			curPlanInExec   = "init";
	    			if( ! aar.getGoon() ) break;
	    		} 			
	    		temporaryStr = "\"++++++++++++++++++ console(starts) ++++++++++++++++++\"";
	    		println( temporaryStr );  
	    		if( ! planUtils.switchToPlan("waitGUICommand").getGoon() ) break;
	    break;
	    }//while
	    return returnValue;
	    }catch(Exception e){
	       //println( getName() + " plan=init WARNING:" + e.getMessage() );
	       QActorContext.terminateQActorSystem(this); 
	       return false;  
	    }
	    }
	    public boolean waitGUICommand() throws Exception{	//public to allow reflection
	    try{
	    	int nPlanIter = 0;
	    	//curPlanInExec =  "waitGUICommand";
	    	boolean returnValue = suspendWork;		//MARCHH2017
	    while(true){
	    	curPlanInExec =  "waitGUICommand";	//within while since it can be lost by switchlan
	    	nPlanIter++;
	    		temporaryStr = "\"++++++++++++++++++ WAIT COMMAND ++++++++++++++++++\"";
	    		println( temporaryStr );  
	    		//senseEvent
	    		aar = planUtils.senseEvents( 999999999,"local_gui_command","continue",
	    		"" , "",ActionExecMode.synch );
	    		if( ! aar.getGoon() || aar.getTimeRemained() <= 0 ){
	    			//println("			WARNING: sense timeout");
	    			addRule("tout(senseevent,"+getName()+")");
	    		}
	    		printCurrentEvent(false);
	    		memoCurrentEvent( false );
	    		//onEvent
	    		if( currentEvent.getEventId().equals("local_gui_command") ){
	    		 		String parg = "";
	    		 		/* SwitchPlan */
	    		 		parg =  updateVars(  Term.createTerm("local_gui_command(COMMAND)"), Term.createTerm("local_gui_command(loadmap(PATH))"), 
	    		 			    		  					Term.createTerm(currentEvent.getMsg()), parg);
	    		 			if( parg != null ){
	    		 				 if( ! planUtils.switchToPlan("loadMap").getGoon() ) break; 
	    		 			}//else println("guard  fails");  //parg is null when there is no guard (onEvent)
	    		 }
	    		//onEvent
	    		if( currentEvent.getEventId().equals("local_gui_command") ){
	    		 		String parg = "";
	    		 		/* SwitchPlan */
	    		 		parg =  updateVars(  Term.createTerm("local_gui_command(COMMAND)"), Term.createTerm("local_gui_command(createplan(START,GOAL,MODE))"), 
	    		 			    		  					Term.createTerm(currentEvent.getMsg()), parg);
	    		 			if( parg != null ){
	    		 				 if( ! planUtils.switchToPlan("createPlan").getGoon() ) break; 
	    		 			}//else println("guard  fails");  //parg is null when there is no guard (onEvent)
	    		 }
	    		//onEvent
	    		if( currentEvent.getEventId().equals("local_gui_command") ){
	    		 		String parg = "";
	    		 		/* SwitchPlan */
	    		 		parg =  updateVars(  Term.createTerm("local_gui_command(COMMAND)"), Term.createTerm("local_gui_command(navigate(robot))"), 
	    		 			    		  					Term.createTerm(currentEvent.getMsg()), parg);
	    		 			if( parg != null ){
	    		 				 if( ! planUtils.switchToPlan("navigation").getGoon() ) break; 
	    		 			}//else println("guard  fails");  //parg is null when there is no guard (onEvent)
	    		 }
	    		//onEvent
	    		if( currentEvent.getEventId().equals("local_gui_command") ){
	    		 		String parg = "";
	    		 		/* SwitchPlan */
	    		 		parg =  updateVars(  Term.createTerm("local_gui_command(COMMAND)"), Term.createTerm("local_gui_command(navigate(simulated))"), 
	    		 			    		  					Term.createTerm(currentEvent.getMsg()), parg);
	    		 			if( parg != null ){
	    		 				 if( ! planUtils.switchToPlan("navigationFile").getGoon() ) break; 
	    		 			}//else println("guard  fails");  //parg is null when there is no guard (onEvent)
	    		 }
	    		//onEvent
	    		if( currentEvent.getEventId().equals("local_gui_command") ){
	    		 		String parg = "";
	    		 		/* SwitchPlan */
	    		 		parg =  updateVars(  Term.createTerm("local_gui_command(COMMAND)"), Term.createTerm("local_gui_command(saveplan(PLAN))"), 
	    		 			    		  					Term.createTerm(currentEvent.getMsg()), parg);
	    		 			if( parg != null ){
	    		 				 if( ! planUtils.switchToPlan("savePlan").getGoon() ) break; 
	    		 			}//else println("guard  fails");  //parg is null when there is no guard (onEvent)
	    		 }
	    		if( planUtils.repeatPlan(nPlanIter,0).getGoon() ) continue;
	    break;
	    }//while
	    return returnValue;
	    }catch(Exception e){
	       //println( getName() + " plan=waitGUICommand WARNING:" + e.getMessage() );
	       QActorContext.terminateQActorSystem(this); 
	       return false;  
	    }
	    }
	    public boolean loadMap() throws Exception{	//public to allow reflection
	    try{
	    	int nPlanIter = 0;
	    	//curPlanInExec =  "loadMap";
	    	boolean returnValue = suspendWork;		//MARCHH2017
	    while(true){
	    	curPlanInExec =  "loadMap";	//within while since it can be lost by switchlan
	    	nPlanIter++;
	    		if( (guardVars = QActorUtils.evalTheGuard(this, " ??msg(local_gui_command,\"event\",SENDER,none,local_gui_command(loadmap(PATH)),MSGNUM)" )) != null ){
	    		parg = "loadMap(PATH)";
	    		parg = QActorUtils.substituteVars(guardVars,parg);
	    		//REGENERATE AKKA
	    		aar = solveGoalReactive(parg,210000000,"","");
	    		//println(getName() + " plan " + curPlanInExec  +  " interrupted=" + aar.getInterrupted() + " action goon="+aar.getGoon());
	    		if( aar.getInterrupted() ){
	    			curPlanInExec   = "loadMap";
	    			if( ! aar.getGoon() ) break;
	    		} 			
	    		}
	    		temporaryStr = "\"++++++++++++++++++ MAP LOADED ++++++++++++++++++\"";
	    		println( temporaryStr );  
	    		returnValue = continueWork;  
	    break;
	    }//while
	    return returnValue;
	    }catch(Exception e){
	       //println( getName() + " plan=loadMap WARNING:" + e.getMessage() );
	       QActorContext.terminateQActorSystem(this); 
	       return false;  
	    }
	    }
	    public boolean savePlan() throws Exception{	//public to allow reflection
	    try{
	    	int nPlanIter = 0;
	    	//curPlanInExec =  "savePlan";
	    	boolean returnValue = suspendWork;		//MARCHH2017
	    while(true){
	    	curPlanInExec =  "savePlan";	//within while since it can be lost by switchlan
	    	nPlanIter++;
	    		if( (guardVars = QActorUtils.evalTheGuard(this, " ??msg(local_gui_command,\"event\",SENDER,none,local_gui_command(saveplan(PLAN)),MSGNUM)" )) != null ){
	    		parg = "savePlan(PATH)";
	    		parg = QActorUtils.substituteVars(guardVars,parg);
	    		//REGENERATE AKKA
	    		aar = solveGoalReactive(parg,210000000,"","");
	    		//println(getName() + " plan " + curPlanInExec  +  " interrupted=" + aar.getInterrupted() + " action goon="+aar.getGoon());
	    		if( aar.getInterrupted() ){
	    			curPlanInExec   = "savePlan";
	    			if( ! aar.getGoon() ) break;
	    		} 			
	    		}
	    		temporaryStr = "\"++++++++++++++++++ MAP LOADED ++++++++++++++++++\"";
	    		println( temporaryStr );  
	    		returnValue = continueWork;  
	    break;
	    }//while
	    return returnValue;
	    }catch(Exception e){
	       //println( getName() + " plan=savePlan WARNING:" + e.getMessage() );
	       QActorContext.terminateQActorSystem(this); 
	       return false;  
	    }
	    }
	    public boolean createPlan() throws Exception{	//public to allow reflection
	    try{
	    	int nPlanIter = 0;
	    	//curPlanInExec =  "createPlan";
	    	boolean returnValue = suspendWork;		//MARCHH2017
	    while(true){
	    	curPlanInExec =  "createPlan";	//within while since it can be lost by switchlan
	    	nPlanIter++;
	    		if( (guardVars = QActorUtils.evalTheGuard(this, " ??msg(local_gui_command,\"event\",SENDER,none,local_gui_command(createplan(START,GOAL,MODE)),MSGNUM)" )) != null ){
	    		parg = "createPlan(START,GOAL,MODE)";
	    		parg = QActorUtils.substituteVars(guardVars,parg);
	    		//REGENERATE AKKA
	    		aar = solveGoalReactive(parg,210000000,"","");
	    		//println(getName() + " plan " + curPlanInExec  +  " interrupted=" + aar.getInterrupted() + " action goon="+aar.getGoon());
	    		if( aar.getInterrupted() ){
	    			curPlanInExec   = "createPlan";
	    			if( ! aar.getGoon() ) break;
	    		} 			
	    		}
	    		temporaryStr = "\"++++++++++++++++++ PLAN CREATED ++++++++++++++++++\"";
	    		println( temporaryStr );  
	    		returnValue = continueWork;  
	    break;
	    }//while
	    return returnValue;
	    }catch(Exception e){
	       //println( getName() + " plan=createPlan WARNING:" + e.getMessage() );
	       QActorContext.terminateQActorSystem(this); 
	       return false;  
	    }
	    }
	    public boolean navigation() throws Exception{	//public to allow reflection
	    try{
	    	int nPlanIter = 0;
	    	//curPlanInExec =  "navigation";
	    	boolean returnValue = suspendWork;		//MARCHH2017
	    while(true){
	    	curPlanInExec =  "navigation";	//within while since it can be lost by switchlan
	    	nPlanIter++;
	    		temporaryStr = "\"++++++++++++++++++ NAVIGATION ++++++++++++++++++\"";
	    		println( temporaryStr );  
	    		parg = "startNavigation";
	    		//REGENERATE AKKA
	    		aar = solveGoalReactive(parg,60000,"","");
	    		//println(getName() + " plan " + curPlanInExec  +  " interrupted=" + aar.getInterrupted() + " action goon="+aar.getGoon());
	    		if( aar.getInterrupted() ){
	    			curPlanInExec   = "navigation";
	    			if( ! aar.getGoon() ) break;
	    		} 			
	    		if( ! planUtils.switchToPlan("waitEndOfNavigation").getGoon() ) break;
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
	    	boolean returnValue = suspendWork;		//MARCHH2017
	    while(true){
	    	curPlanInExec =  "navigationFile";	//within while since it can be lost by switchlan
	    	nPlanIter++;
	    		temporaryStr = "\"++++++++++++++++++ NAVIGATION FILE ++++++++++++++++++\"";
	    		println( temporaryStr );  
	    		parg = "startNavigationFile";
	    		//REGENERATE AKKA
	    		aar = solveGoalReactive(parg,60000,"","");
	    		//println(getName() + " plan " + curPlanInExec  +  " interrupted=" + aar.getInterrupted() + " action goon="+aar.getGoon());
	    		if( aar.getInterrupted() ){
	    			curPlanInExec   = "navigationFile";
	    			if( ! aar.getGoon() ) break;
	    		} 			
	    		if( ! planUtils.switchToPlan("waitEndOfNavigation").getGoon() ) break;
	    break;
	    }//while
	    return returnValue;
	    }catch(Exception e){
	       //println( getName() + " plan=navigationFile WARNING:" + e.getMessage() );
	       QActorContext.terminateQActorSystem(this); 
	       return false;  
	    }
	    }
	    public boolean waitEndOfNavigation() throws Exception{	//public to allow reflection
	    try{
	    	int nPlanIter = 0;
	    	//curPlanInExec =  "waitEndOfNavigation";
	    	boolean returnValue = suspendWork;		//MARCHH2017
	    while(true){
	    	curPlanInExec =  "waitEndOfNavigation";	//within while since it can be lost by switchlan
	    	nPlanIter++;
	    		//senseEvent
	    		aar = planUtils.senseEvents( 999999999,"end","endOfNavigation",
	    		"" , "",ActionExecMode.synch );
	    		if( ! aar.getGoon() || aar.getTimeRemained() <= 0 ){
	    			//println("			WARNING: sense timeout");
	    			addRule("tout(senseevent,"+getName()+")");
	    		}
	    		if( planUtils.repeatPlan(nPlanIter,0).getGoon() ) continue;
	    break;
	    }//while
	    return returnValue;
	    }catch(Exception e){
	       //println( getName() + " plan=waitEndOfNavigation WARNING:" + e.getMessage() );
	       QActorContext.terminateQActorSystem(this); 
	       return false;  
	    }
	    }
	    public boolean endOfNavigation() throws Exception{	//public to allow reflection
	    try{
	    	int nPlanIter = 0;
	    	//curPlanInExec =  "endOfNavigation";
	    	boolean returnValue = suspendWork;		//MARCHH2017
	    while(true){
	    	curPlanInExec =  "endOfNavigation";	//within while since it can be lost by switchlan
	    	nPlanIter++;
	    		temporaryStr = "\"++++++++++++++++++ NAVIGATION ENDED :D ++++++++++++++++++\"";
	    		println( temporaryStr );  
	    		if( ! planUtils.switchToPlan("waitGUICommand").getGoon() ) break;
	    break;
	    }//while
	    return returnValue;
	    }catch(Exception e){
	       //println( getName() + " plan=endOfNavigation WARNING:" + e.getMessage() );
	       QActorContext.terminateQActorSystem(this); 
	       return false;  
	    }
	    }
	    public boolean handleTimeout() throws Exception{	//public to allow reflection
	    try{
	    	int nPlanIter = 0;
	    	//curPlanInExec =  "handleTimeout";
	    	boolean returnValue = suspendWork;		//MARCHH2017
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
	    public boolean loadMapFailure() throws Exception{	//public to allow reflection
	    try{
	    	int nPlanIter = 0;
	    	//curPlanInExec =  "loadMapFailure";
	    	boolean returnValue = suspendWork;		//MARCHH2017
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
	    public boolean navigationFailure() throws Exception{	//public to allow reflection
	    try{
	    	int nPlanIter = 0;
	    	//curPlanInExec =  "navigationFailure";
	    	boolean returnValue = suspendWork;		//MARCHH2017
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
	    public boolean consultPrologFailure() throws Exception{	//public to allow reflection
	    try{
	    	int nPlanIter = 0;
	    	//curPlanInExec =  "consultPrologFailure";
	    	boolean returnValue = suspendWork;		//MARCHH2017
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
	    	boolean returnValue = suspendWork;		//MARCHH2017
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
	
