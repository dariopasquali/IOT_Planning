/* Generated by AN DISI Unibo */ 
package it.unibo.console;
import alice.tuprolog.Term;
import alice.tuprolog.Struct;
import it.unibo.qactors.ActorContext;
import it.unibo.is.interfaces.IOutputEnvView;
import it.unibo.qactors.planned.QActorPlanned;
import it.unibo.qactors.action.ActionDummy;
import it.unibo.qactors.action.AsynchActionResult;
import it.unibo.qactors.action.IActorAction;
import it.unibo.qactors.action.IActorAction.ActionExecMode;
import it.unibo.baseEnv.basicFrame.EnvFrame;
import it.unibo.gui.ConsoleGUI;
import alice.tuprolog.SolveInfo;
import it.unibo.is.interfaces.IActivity;
import it.unibo.is.interfaces.IIntent;

public abstract class AbstractConsole extends QActorPlanned implements IActivity{ 
	protected AsynchActionResult aar = null;
	protected boolean actionResult = true;
	protected alice.tuprolog.SolveInfo sol;
	
	protected static IOutputEnvView setTheEnv(IOutputEnvView outEnvView ){
		//EnvFrame env = new EnvFrame( "Env_console", java.awt.Color.cyan  , java.awt.Color.black );
		//env.init();
		//env.setSize(800,400);
		
		ConsoleGUI env = new ConsoleGUI();
		env.setEnvVisible(true);
		
		//IOutputEnvView newOutEnvView = ((EnvFrame) env).getOutputEnvView();
		
		IOutputEnvView newOutEnvView = env;
		return newOutEnvView;
	}


public AbstractConsole(String actorId, ActorContext myCtx, IOutputEnvView outEnvView )  throws Exception{
	super(actorId, myCtx, "./srcMore/it/unibo/console/plans.txt", 
	"./srcMore/it/unibo/console/WorldTheory.pl",
	setTheEnv( outEnvView )  , "init");
	//addInputPanel(80);
	//addCmdPanels();	
	}
	protected void addInputPanel(int size){
		((EnvFrame) env).addInputPanel(size);			
	}
	protected void addCmdPanels(){
		((EnvFrame) env).addCmdPanel("input", new String[]{"INPUT"}, this);
		((EnvFrame) env).addCmdPanel("alarm", new String[]{"FIRE"}, this);
		((EnvFrame) env).addCmdPanel("help",  new String[]{"HELP"}, this);				
	}
		@Override
		protected void doJob() throws Exception {
	 		initSensorSystem();
			boolean res = init();
			//println(getName() + " doJob " + res );
		} 
		/* 
		* ------------------------------------------------------------
		* PLANS
		* ------------------------------------------------------------
		*/
	    public boolean init() throws Exception{	//public to allow reflection
	    try{
	    	curPlanInExec =  "init";
	    	boolean returnValue = suspendWork;
	    while(true){
	    nPlanIter++;
	    		{ String parg = "consult( \"expConsoleTheory.pl\" )";
	    		  aar = solveGoal( parg , 0, "","" , "" );
	    		//println(getName() + " plan " + curPlanInExec  +  " interrupted=" + aar.getInterrupted() + " action goon="+aar.getGoon());
	    		if( aar.getInterrupted() ){
	    			curPlanInExec   = "init";
	    			if( ! aar.getGoon() ) break;
	    		} 			
	    		if( aar.getResult().equals("failure")){
	    		if( ! switchToPlan("consultPrologFailure").getGoon() ) break;
	    		}else if( ! aar.getGoon() ) break;
	    		}
	    		{ String parg = "consult( \"navConsoleTheory.pl\" )";
	    		  aar = solveGoal( parg , 0, "","" , "" );
	    		//println(getName() + " plan " + curPlanInExec  +  " interrupted=" + aar.getInterrupted() + " action goon="+aar.getGoon());
	    		if( aar.getInterrupted() ){
	    			curPlanInExec   = "init";
	    			if( ! aar.getGoon() ) break;
	    		} 			
	    		if( aar.getResult().equals("failure")){
	    		if( ! switchToPlan("consultPrologFailure").getGoon() ) break;
	    		}else if( ! aar.getGoon() ) break;
	    		}
	    		{ String parg = "consult( \"consoleTheory.pl\" )";
	    		  aar = solveGoal( parg , 0, "","" , "" );
	    		//println(getName() + " plan " + curPlanInExec  +  " interrupted=" + aar.getInterrupted() + " action goon="+aar.getGoon());
	    		if( aar.getInterrupted() ){
	    			curPlanInExec   = "init";
	    			if( ! aar.getGoon() ) break;
	    		} 			
	    		if( aar.getResult().equals("failure")){
	    		if( ! switchToPlan("consultPrologFailure").getGoon() ) break;
	    		}else if( ! aar.getGoon() ) break;
	    		}
	    		temporaryStr = " \"++++++++++++++++++ console(starts) ++++++++++++++++++\" ";
	    		println( temporaryStr );  
	    		if( ! switchToPlan("waitGUICommand").getGoon() ) break;
	    break;
	    }//while
	    return returnValue;
	    }catch(Exception e){
	    println( getName() + " ERROR " + e.getMessage() );
	    throw e;
	    }
	    }
	    public boolean waitGUICommand() throws Exception{	//public to allow reflection
	    try{
	    	curPlanInExec =  "waitGUICommand";
	    	boolean returnValue = suspendWork;
	    while(true){
	    nPlanIter++;
	    		temporaryStr = " \"++++++++++++++++++ WAIT COMMAND ++++++++++++++++++\" ";
	    		println( temporaryStr );  
	    		//senseEvent
	    		timeoutval = 999999999;
	    		aar = senseEvents( timeoutval,"local_gui_command","continue",
	    		"" , "",ActionExecMode.synch );
	    		if( ! aar.getGoon() || aar.getTimeRemained() <= 0 ){
	    			println("			WARNING: sense timeout");
	    			addRule("tout(senseevent,"+getName()+")");
	    			//break;
	    		}
	    		if( (guardVars = evalTheGuard( " ??tout(X,Y)" )) != null ){
	    		if( ! switchToPlan("handleTimeout").getGoon() ) break;
	    		}
	    		printCurrentEvent(false);
	    		memoCurrentEvent( false );
	    		//onEvent
	    		if( currentEvent.getEventId().equals("local_gui_command") ){
	    		 		String parg = "";
	    		 		parg = updateVars(null, Term.createTerm("local_gui_command(COMMAND)"), Term.createTerm("local_gui_command(loadexpmap(PATH))"), 
	    		 			    		  					Term.createTerm(currentEvent.getMsg()), parg);
	    		 			if( parg != null ){
	    		 				 if( ! switchToPlan("loadExpMap").getGoon() ) break; 
	    		 			}//else println("guard  fails");  //parg is null when there is no guard (onEvent)
	    		 }
	    		//onEvent
	    		if( currentEvent.getEventId().equals("local_gui_command") ){
	    		 		String parg = "";
	    		 		parg = updateVars(null, Term.createTerm("local_gui_command(COMMAND)"), Term.createTerm("local_gui_command(explore)"), 
	    		 			    		  					Term.createTerm(currentEvent.getMsg()), parg);
	    		 			if( parg != null ){
	    		 				 if( ! switchToPlan("exploration").getGoon() ) break; 
	    		 			}//else println("guard  fails");  //parg is null when there is no guard (onEvent)
	    		 }
	    		//onEvent
	    		if( currentEvent.getEventId().equals("local_gui_command") ){
	    		 		String parg = "";
	    		 		parg = updateVars(null, Term.createTerm("local_gui_command(COMMAND)"), Term.createTerm("local_gui_command(explore(START,BOUNDS))"), 
	    		 			    		  					Term.createTerm(currentEvent.getMsg()), parg);
	    		 			if( parg != null ){
	    		 				 if( ! switchToPlan("explorationDebug").getGoon() ) break; 
	    		 			}//else println("guard  fails");  //parg is null when there is no guard (onEvent)
	    		 }
	    		//onEvent
	    		if( currentEvent.getEventId().equals("local_gui_command") ){
	    		 		String parg = "";
	    		 		parg = updateVars(null, Term.createTerm("local_gui_command(COMMAND)"), Term.createTerm("local_gui_command(loadmap(PATH))"), 
	    		 			    		  					Term.createTerm(currentEvent.getMsg()), parg);
	    		 			if( parg != null ){
	    		 				 if( ! switchToPlan("loadMap").getGoon() ) break; 
	    		 			}//else println("guard  fails");  //parg is null when there is no guard (onEvent)
	    		 }
	    		//onEvent
	    		if( currentEvent.getEventId().equals("local_gui_command") ){
	    		 		String parg = "";
	    		 		parg = updateVars(null, Term.createTerm("local_gui_command(COMMAND)"), Term.createTerm("local_gui_command(findpath(START,GOAL))"), 
	    		 			    		  					Term.createTerm(currentEvent.getMsg()), parg);
	    		 			if( parg != null ){
	    		 				 if( ! switchToPlan("findPath").getGoon() ) break; 
	    		 			}//else println("guard  fails");  //parg is null when there is no guard (onEvent)
	    		 }
	    		//onEvent
	    		if( currentEvent.getEventId().equals("local_gui_command") ){
	    		 		String parg = "";
	    		 		parg = updateVars(null, Term.createTerm("local_gui_command(COMMAND)"), Term.createTerm("local_gui_command(navigate)"), 
	    		 			    		  					Term.createTerm(currentEvent.getMsg()), parg);
	    		 			if( parg != null ){
	    		 				 if( ! switchToPlan("navigation").getGoon() ) break; 
	    		 			}//else println("guard  fails");  //parg is null when there is no guard (onEvent)
	    		 }
	    		//onEvent
	    		if( currentEvent.getEventId().equals("local_gui_command") ){
	    		 		String parg = "";
	    		 		parg = updateVars(null, Term.createTerm("local_gui_command(COMMAND)"), Term.createTerm("local_gui_command(clear)"), 
	    		 			    		  					Term.createTerm(currentEvent.getMsg()), parg);
	    		 			if( parg != null ){
	    		 				 if( ! switchToPlan("clearGUI").getGoon() ) break; 
	    		 			}//else println("guard  fails");  //parg is null when there is no guard (onEvent)
	    		 }
	    		if( repeatPlan(0).getGoon() ) continue;
	    break;
	    }//while
	    return returnValue;
	    }catch(Exception e){
	    println( getName() + " ERROR " + e.getMessage() );
	    throw e;
	    }
	    }
	    public boolean loadExpMap() throws Exception{	//public to allow reflection
	    try{
	    	curPlanInExec =  "loadExpMap";
	    	boolean returnValue = suspendWork;
	    while(true){
	    nPlanIter++;
	    		if( (guardVars = evalTheGuard( " ??msg(local_gui_command, \"event\" ,SENDER,none,local_gui_command(loadexpmap(PATH)),MSGNUM)" )) != null ){
	    		{ String parg = "loadMap(PATH,exploration)";
	    		parg = substituteVars(guardVars,parg);
	    		  aar = solveGoal( parg , 210000000, "","" , "" );
	    		//println(getName() + " plan " + curPlanInExec  +  " interrupted=" + aar.getInterrupted() + " action goon="+aar.getGoon());
	    		if( aar.getInterrupted() ){
	    			curPlanInExec   = "loadExpMap";
	    			if( ! aar.getGoon() ) break;
	    		} 			
	    		if( aar.getResult().equals("failure")){
	    		if( ! switchToPlan("loadMapFailure").getGoon() ) break;
	    		}else if( ! aar.getGoon() ) break;
	    		}
	    		}
	    		temporaryStr = " \"++++++++++++++++++ EXPLORATION MAP LOADED ++++++++++++++++++\" ";
	    		println( temporaryStr );  
	    		returnValue = continueWork; //we must restore nPlanIter and curPlanInExec of the 'interrupted' plan
	    break;
	    }//while
	    return returnValue;
	    }catch(Exception e){
	    println( getName() + " ERROR " + e.getMessage() );
	    throw e;
	    }
	    }
	    public boolean exploration() throws Exception{	//public to allow reflection
	    try{
	    	curPlanInExec =  "exploration";
	    	boolean returnValue = suspendWork;
	    while(true){
	    nPlanIter++;
	    		temporaryStr = " \"++++++++++++++++++ EXPLORATION ++++++++++++++++++\" ";
	    		println( temporaryStr );  
	    		{ String parg = "showClearMap";
	    		  aar = solveGoal( parg , 60000, "","" , "" );
	    		//println(getName() + " plan " + curPlanInExec  +  " interrupted=" + aar.getInterrupted() + " action goon="+aar.getGoon());
	    		if( aar.getInterrupted() ){
	    			curPlanInExec   = "exploration";
	    			if( ! aar.getGoon() ) break;
	    		} 			
	    		if( aar.getResult().equals("failure")){
	    		if( ! switchToPlan("explorationFailure").getGoon() ) break;
	    		}else if( ! aar.getGoon() ) break;
	    		}
	    		temporaryStr = unifyMsgContent("explore","explore", guardVars ).toString();
	    		sendMsg("explore","robot", ActorContext.dispatch, temporaryStr ); 
	    		if( ! switchToPlan("waitEndOfExploration").getGoon() ) break;
	    break;
	    }//while
	    return returnValue;
	    }catch(Exception e){
	    println( getName() + " ERROR " + e.getMessage() );
	    throw e;
	    }
	    }
	    public boolean explorationDebug() throws Exception{	//public to allow reflection
	    try{
	    	curPlanInExec =  "explorationDebug";
	    	boolean returnValue = suspendWork;
	    while(true){
	    nPlanIter++;
	    		temporaryStr = " \"++++++++++++++++++ EXPLORATION DEBUG ++++++++++++++++++\" ";
	    		println( temporaryStr );  
	    		if( (guardVars = evalTheGuard( " !?msg(local_gui_command, \"event\" ,SENDER,none,local_gui_command(explore(START,BOUNDS)),MSGNUM)" )) != null ){
	    		{ String parg = "showClearMap(BOUNDS)";
	    		parg = substituteVars(guardVars,parg);
	    		  aar = solveGoal( parg , 60000, "","" , "" );
	    		//println(getName() + " plan " + curPlanInExec  +  " interrupted=" + aar.getInterrupted() + " action goon="+aar.getGoon());
	    		if( aar.getInterrupted() ){
	    			curPlanInExec   = "explorationDebug";
	    			if( ! aar.getGoon() ) break;
	    		} 			
	    		if( aar.getResult().equals("failure")){
	    		if( ! switchToPlan("explorationFailure").getGoon() ) break;
	    		}else if( ! aar.getGoon() ) break;
	    		}
	    		}
	    		temporaryStr = " \"mappa pulita\" ";
	    		println( temporaryStr );  
	    		if( (guardVars = evalTheGuard( " ??msg(local_gui_command, \"event\" ,SENDER,none,local_gui_command(explore(START,BOUNDS)),MSGNUM)" )) != null ){
	    		temporaryStr = unifyMsgContent("exploredebug(START,BOUNDS)","exploredebug(START,BOUNDS)", guardVars ).toString();
	    		sendMsg("exploredebug","robot", ActorContext.dispatch, temporaryStr ); 
	    		}
	    		if( ! switchToPlan("waitEndOfExploration").getGoon() ) break;
	    break;
	    }//while
	    return returnValue;
	    }catch(Exception e){
	    println( getName() + " ERROR " + e.getMessage() );
	    throw e;
	    }
	    }
	    public boolean waitEndOfExploration() throws Exception{	//public to allow reflection
	    try{
	    	curPlanInExec =  "waitEndOfExploration";
	    	boolean returnValue = suspendWork;
	    while(true){
	    nPlanIter++;
	    		//senseEvent
	    		timeoutval = 999999999;
	    		aar = senseEvents( timeoutval,"local_gui_command,expdata,end","continue,continue,continue",
	    		"" , "",ActionExecMode.synch );
	    		if( ! aar.getGoon() || aar.getTimeRemained() <= 0 ){
	    			println("			WARNING: sense timeout");
	    			addRule("tout(senseevent,"+getName()+")");
	    			//break;
	    		}
	    		memoCurrentEvent( false );
	    		printCurrentEvent(false);
	    		//onEvent
	    		if( currentEvent.getEventId().equals("expdata") ){
	    		 		String parg="updateMap(POS,STATE)";
	    		 		parg = updateVars(null, Term.createTerm("expdata(POS,STATE)"), Term.createTerm("expdata(POS,STATE)"), 
	    		 			    		  					Term.createTerm(currentEvent.getMsg()), parg);
	    		 			if( parg != null ) {
	    		 				aar = solveGoal( parg , 210000000, "","" , "");
	    		 				//println(getName() + " plan " + curPlanInExec  +  " interrupted=" + aar.getInterrupted() + " action goon="+aar.getGoon());
	    		 				if( aar.getInterrupted() ){
	    		 					curPlanInExec   = "waitEndOfExploration";
	    		 					if( ! aar.getGoon() ) break;
	    		 				} 			
	    		 				if( aar.getResult().equals("failure")){
	    		 					if( ! switchToPlan("explorationFailure").getGoon() ) break;
	    		 				}else if( ! aar.getGoon() ) break;
	    		 			}
	    		 }
	    		//onEvent
	    		if( currentEvent.getEventId().equals("local_gui_command") ){
	    		 		String parg = "";
	    		 		parg = updateVars(null, Term.createTerm("local_gui_command(COMMAND)"), Term.createTerm("local_gui_command(abort)"), 
	    		 			    		  					Term.createTerm(currentEvent.getMsg()), parg);
	    		 			if( parg != null ){
	    		 				 if( ! switchToPlan("abortCommand").getGoon() ) break; 
	    		 			}//else println("guard  fails");  //parg is null when there is no guard (onEvent)
	    		 }
	    		//onEvent
	    		if( currentEvent.getEventId().equals("end") ){
	    		 		String parg = "";
	    		 		parg = updateVars(null, Term.createTerm("end"), Term.createTerm("end"), 
	    		 			    		  					Term.createTerm(currentEvent.getMsg()), parg);
	    		 			if( parg != null ){
	    		 				 if( ! switchToPlan("endOfExploration").getGoon() ) break; 
	    		 			}//else println("guard  fails");  //parg is null when there is no guard (onEvent)
	    		 }
	    		if( repeatPlan(0).getGoon() ) continue;
	    		returnValue = continueWork;  
	    break;
	    }//while
	    return returnValue;
	    }catch(Exception e){
	    println( getName() + " ERROR " + e.getMessage() );
	    throw e;
	    }
	    }
	    public boolean endOfExploration() throws Exception{	//public to allow reflection
	    try{
	    	curPlanInExec =  "endOfExploration";
	    	boolean returnValue = suspendWork;
	    while(true){
	    nPlanIter++;
	    		temporaryStr = " \"Exploration ENDED\" ";
	    		println( temporaryStr );  
	    		temporaryStr = " \"Please Save the map, or clear and repeat\" ";
	    		println( temporaryStr );  
	    		if( ! switchToPlan("waitGUICommand").getGoon() ) break;
	    break;
	    }//while
	    return returnValue;
	    }catch(Exception e){
	    println( getName() + " ERROR " + e.getMessage() );
	    throw e;
	    }
	    }
	    public boolean loadMap() throws Exception{	//public to allow reflection
	    try{
	    	curPlanInExec =  "loadMap";
	    	boolean returnValue = suspendWork;
	    while(true){
	    nPlanIter++;
	    		if( (guardVars = evalTheGuard( " ??msg(local_gui_command, \"event\" ,SENDER,none,local_gui_command(loadmap(PATH)),MSGNUM)" )) != null ){
	    		{ String parg = "loadMap(PATH,navigation)";
	    		parg = substituteVars(guardVars,parg);
	    		  aar = solveGoal( parg , 210000000, "","" , "" );
	    		//println(getName() + " plan " + curPlanInExec  +  " interrupted=" + aar.getInterrupted() + " action goon="+aar.getGoon());
	    		if( aar.getInterrupted() ){
	    			curPlanInExec   = "loadMap";
	    			if( ! aar.getGoon() ) break;
	    		} 			
	    		if( aar.getResult().equals("failure")){
	    		if( ! switchToPlan("loadMapFailure").getGoon() ) break;
	    		}else if( ! aar.getGoon() ) break;
	    		}
	    		}
	    		temporaryStr = " \"++++++++++++++++++ MAP LOADED ++++++++++++++++++\" ";
	    		println( temporaryStr );  
	    		returnValue = continueWork;  
	    break;
	    }//while
	    return returnValue;
	    }catch(Exception e){
	    println( getName() + " ERROR " + e.getMessage() );
	    throw e;
	    }
	    }
	    public boolean findPath() throws Exception{	//public to allow reflection
	    try{
	    	curPlanInExec =  "findPath";
	    	boolean returnValue = suspendWork;
	    while(true){
	    nPlanIter++;
	    		if( (guardVars = evalTheGuard( " ??msg(local_gui_command, \"event\" ,SENDER,none,local_gui_command(findpath(START,GOAL)),MSGNUM)" )) != null ){
	    		{ String parg = "searchBestPath(START,GOAL)";
	    		parg = substituteVars(guardVars,parg);
	    		  aar = solveGoal( parg , 210000000, "","" , "" );
	    		//println(getName() + " plan " + curPlanInExec  +  " interrupted=" + aar.getInterrupted() + " action goon="+aar.getGoon());
	    		if( aar.getInterrupted() ){
	    			curPlanInExec   = "findPath";
	    			if( ! aar.getGoon() ) break;
	    		} 			
	    		if( aar.getResult().equals("failure")){
	    		if( ! switchToPlan("findpathFailure").getGoon() ) break;
	    		}else if( ! aar.getGoon() ) break;
	    		}
	    		}
	    		temporaryStr = " \"++++++++++++++++++ PATH FOUND ++++++++++++++++++\" ";
	    		println( temporaryStr );  
	    		returnValue = continueWork;  
	    break;
	    }//while
	    return returnValue;
	    }catch(Exception e){
	    println( getName() + " ERROR " + e.getMessage() );
	    throw e;
	    }
	    }
	    public boolean navigation() throws Exception{	//public to allow reflection
	    try{
	    	curPlanInExec =  "navigation";
	    	boolean returnValue = suspendWork;
	    while(true){
	    nPlanIter++;
	    		temporaryStr = " \"++++++++++++++++++ NAVIGATION ++++++++++++++++++\" ";
	    		println( temporaryStr );  
	    		{ String parg = "startNavigation";
	    		  aar = solveGoal( parg , 60000, "","" , "" );
	    		//println(getName() + " plan " + curPlanInExec  +  " interrupted=" + aar.getInterrupted() + " action goon="+aar.getGoon());
	    		if( aar.getInterrupted() ){
	    			curPlanInExec   = "navigation";
	    			if( ! aar.getGoon() ) break;
	    		} 			
	    		if( aar.getResult().equals("failure")){
	    		if( ! switchToPlan("navigationFailure").getGoon() ) break;
	    		}else if( ! aar.getGoon() ) break;
	    		}
	    		if( ! switchToPlan("waitEndOfNavigation").getGoon() ) break;
	    break;
	    }//while
	    return returnValue;
	    }catch(Exception e){
	    println( getName() + " ERROR " + e.getMessage() );
	    throw e;
	    }
	    }
	    public boolean waitEndOfNavigation() throws Exception{	//public to allow reflection
	    try{
	    	curPlanInExec =  "waitEndOfNavigation";
	    	boolean returnValue = suspendWork;
	    while(true){
	    nPlanIter++;
	    		//senseEvent
	    		timeoutval = 999999999;
	    		aar = senseEvents( timeoutval,"local_gui_command,update,end","continue,continue,continue",
	    		"" , "",ActionExecMode.synch );
	    		if( ! aar.getGoon() || aar.getTimeRemained() <= 0 ){
	    			println("			WARNING: sense timeout");
	    			addRule("tout(senseevent,"+getName()+")");
	    			//break;
	    		}
	    		printCurrentEvent(false);
	    		memoCurrentEvent( false );
	    		//onEvent
	    		if( currentEvent.getEventId().equals("local_gui_command") ){
	    		 		String parg = "";
	    		 		parg = updateVars(null, Term.createTerm("local_gui_command(COMMAND)"), Term.createTerm("local_gui_command(abort)"), 
	    		 			    		  					Term.createTerm(currentEvent.getMsg()), parg);
	    		 			if( parg != null ){
	    		 				 if( ! switchToPlan("abortCommand").getGoon() ) break; 
	    		 			}//else println("guard  fails");  //parg is null when there is no guard (onEvent)
	    		 }
	    		//onEvent
	    		if( currentEvent.getEventId().equals("update") ){
	    		 		String parg="updateMapAndReplan(OBJECT,CURRENT)";
	    		 		parg = updateVars(null, Term.createTerm("update(OBJECT,CURRENT)"), Term.createTerm("update(OBJECT,CURRENT)"), 
	    		 			    		  					Term.createTerm(currentEvent.getMsg()), parg);
	    		 			if( parg != null ) {
	    		 				aar = solveGoal( parg , 600000, "","" , "");
	    		 				//println(getName() + " plan " + curPlanInExec  +  " interrupted=" + aar.getInterrupted() + " action goon="+aar.getGoon());
	    		 				if( aar.getInterrupted() ){
	    		 					curPlanInExec   = "waitEndOfNavigation";
	    		 					if( ! aar.getGoon() ) break;
	    		 				} 			
	    		 				if( aar.getResult().equals("failure")){
	    		 					if( ! switchToPlan("alternativeFindpathFailure").getGoon() ) break;
	    		 				}else if( ! aar.getGoon() ) break;
	    		 			}
	    		 }
	    		//onEvent
	    		if( currentEvent.getEventId().equals("end") ){
	    		 		String parg = "";
	    		 		parg = updateVars(null, Term.createTerm("end"), Term.createTerm("end"), 
	    		 			    		  					Term.createTerm(currentEvent.getMsg()), parg);
	    		 			if( parg != null ){
	    		 				 if( ! switchToPlan("endOfNavigation").getGoon() ) break; 
	    		 			}//else println("guard  fails");  //parg is null when there is no guard (onEvent)
	    		 }
	    		if( repeatPlan(0).getGoon() ) continue;
	    break;
	    }//while
	    return returnValue;
	    }catch(Exception e){
	    println( getName() + " ERROR " + e.getMessage() );
	    throw e;
	    }
	    }
	    public boolean endOfNavigation() throws Exception{	//public to allow reflection
	    try{
	    	curPlanInExec =  "endOfNavigation";
	    	boolean returnValue = suspendWork;
	    while(true){
	    nPlanIter++;
	    		temporaryStr = " \"++++++++++++++++++ NAVIGATION ENDED :D ++++++++++++++++++\" ";
	    		println( temporaryStr );  
	    		if( ! switchToPlan("waitGUICommand").getGoon() ) break;
	    break;
	    }//while
	    return returnValue;
	    }catch(Exception e){
	    println( getName() + " ERROR " + e.getMessage() );
	    throw e;
	    }
	    }
	    public boolean abortCommand() throws Exception{	//public to allow reflection
	    try{
	    	curPlanInExec =  "abortCommand";
	    	boolean returnValue = suspendWork;
	    while(true){
	    nPlanIter++;
	    		temporaryStr = unifyMsgContent("abort","abort", guardVars ).toString();
	    		emit( "abort", temporaryStr );
	    		temporaryStr = " \"++++++++++++++++++ COMMAND ABORTED ++++++++++++++++++\" ";
	    		println( temporaryStr );  
	    		if( ! switchToPlan("waitGUICommand").getGoon() ) break;
	    break;
	    }//while
	    return returnValue;
	    }catch(Exception e){
	    println( getName() + " ERROR " + e.getMessage() );
	    throw e;
	    }
	    }
	    public boolean clearGUI() throws Exception{	//public to allow reflection
	    try{
	    	curPlanInExec =  "clearGUI";
	    	boolean returnValue = suspendWork;
	    while(true){
	    nPlanIter++;
	    		{ String parg = "clearGUI";
	    		  aar = solveGoal( parg , 10000, "","" , "" );
	    		//println(getName() + " plan " + curPlanInExec  +  " interrupted=" + aar.getInterrupted() + " action goon="+aar.getGoon());
	    		if( aar.getInterrupted() ){
	    			curPlanInExec   = "clearGUI";
	    			if( ! aar.getGoon() ) break;
	    		} 			
	    		if( aar.getResult().equals("failure")){
	    		if( ! aar.getGoon() ) break;
	    		}else if( ! aar.getGoon() ) break;
	    		}
	    		temporaryStr = " \"map cleared\" ";
	    		println( temporaryStr );  
	    		returnValue = continueWork;  
	    break;
	    }//while
	    return returnValue;
	    }catch(Exception e){
	    println( getName() + " ERROR " + e.getMessage() );
	    throw e;
	    }
	    }
	    public boolean handleTimeout() throws Exception{	//public to allow reflection
	    try{
	    	curPlanInExec =  "handleTimeout";
	    	boolean returnValue = suspendWork;
	    while(true){
	    nPlanIter++;
	    		temporaryStr = " \"timeout!! GOODBYE\" ";
	    		println( temporaryStr );  
	    break;
	    }//while
	    return returnValue;
	    }catch(Exception e){
	    println( getName() + " ERROR " + e.getMessage() );
	    throw e;
	    }
	    }
	    public boolean explorationFailure() throws Exception{	//public to allow reflection
	    try{
	    	curPlanInExec =  "explorationFailure";
	    	boolean returnValue = suspendWork;
	    while(true){
	    nPlanIter++;
	    		temporaryStr = " \"Explore FAILURE\" ";
	    		println( temporaryStr );  
	    break;
	    }//while
	    return returnValue;
	    }catch(Exception e){
	    println( getName() + " ERROR " + e.getMessage() );
	    throw e;
	    }
	    }
	    public boolean loadMapFailure() throws Exception{	//public to allow reflection
	    try{
	    	curPlanInExec =  "loadMapFailure";
	    	boolean returnValue = suspendWork;
	    while(true){
	    nPlanIter++;
	    		temporaryStr = " \"Load Map FAILURE\" ";
	    		println( temporaryStr );  
	    break;
	    }//while
	    return returnValue;
	    }catch(Exception e){
	    println( getName() + " ERROR " + e.getMessage() );
	    throw e;
	    }
	    }
	    public boolean findpathFailure() throws Exception{	//public to allow reflection
	    try{
	    	curPlanInExec =  "findpathFailure";
	    	boolean returnValue = suspendWork;
	    while(true){
	    nPlanIter++;
	    		temporaryStr = " \"Find Path FAILURE\" ";
	    		println( temporaryStr );  
	    break;
	    }//while
	    return returnValue;
	    }catch(Exception e){
	    println( getName() + " ERROR " + e.getMessage() );
	    throw e;
	    }
	    }
	    public boolean navigationFailure() throws Exception{	//public to allow reflection
	    try{
	    	curPlanInExec =  "navigationFailure";
	    	boolean returnValue = suspendWork;
	    while(true){
	    nPlanIter++;
	    		temporaryStr = " \"Navigation FAILURE\" ";
	    		println( temporaryStr );  
	    break;
	    }//while
	    return returnValue;
	    }catch(Exception e){
	    println( getName() + " ERROR " + e.getMessage() );
	    throw e;
	    }
	    }
	    public boolean alternativeFindpathFailure() throws Exception{	//public to allow reflection
	    try{
	    	curPlanInExec =  "alternativeFindpathFailure";
	    	boolean returnValue = suspendWork;
	    while(true){
	    nPlanIter++;
	    		temporaryStr = " \"Alternative Find Path FAILURE\" ";
	    		println( temporaryStr );  
	    break;
	    }//while
	    return returnValue;
	    }catch(Exception e){
	    println( getName() + " ERROR " + e.getMessage() );
	    throw e;
	    }
	    }
	    public boolean consultPrologFailure() throws Exception{	//public to allow reflection
	    try{
	    	curPlanInExec =  "consultPrologFailure";
	    	boolean returnValue = suspendWork;
	    while(true){
	    nPlanIter++;
	    		temporaryStr = " \"The consult is gone wrong, maybe there are errors in the prolog file\" ";
	    		println( temporaryStr );  
	    break;
	    }//while
	    return returnValue;
	    }catch(Exception e){
	    println( getName() + " ERROR " + e.getMessage() );
	    throw e;
	    }
	    }
	    public boolean generalPrologFailure() throws Exception{	//public to allow reflection
	    try{
	    	curPlanInExec =  "generalPrologFailure";
	    	boolean returnValue = suspendWork;
	    while(true){
	    nPlanIter++;
	    		temporaryStr = " \"Prolog goal FAILURE\" ";
	    		println( temporaryStr );  
	    break;
	    }//while
	    return returnValue;
	    }catch(Exception e){
	    println( getName() + " ERROR " + e.getMessage() );
	    throw e;
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
		* IACTIVITY
		* ------------------------------------------------------------
		*/
		    private String[] actions = new String[]{
		    	"println( STRING | TERM )", 
		    	"play( FILENAME ) ",
		"emit(EVID,EVCONTENT)  ",
		"move(MOVE,DURATION,ANGLE)  with MOVE=mf|mb|ml|mr|ms",
		"forward( DEST, MSGID, MSGCONTENTTERM)"
		    };
		    protected void doHelp(){
				println("  GOAL ");
				println("[ GUARD ], ACTION  ");
				println("[ GUARD ], ACTION, DURATION ");
				println("[ GUARD ], ACTION, DURATION, ENDEVENT ");
				println("[ GUARD ], ACTION, DURATION,'',E VENTS, PLANS ");
				println("Actions:");
				for( int i=0; i<actions.length; i++){
					println(" " + actions[i] );
				}
		    }
		@Override
		public void execAction(String cmd) {
			if( cmd.equals("HELP") ){
				doHelp();
				return;
			}
			if( cmd.equals("FIRE") ){
				platform.raiseEvent("input", "alarm", "alarm(fire)");
				return;
			}
			String input = env.readln();
			//input = "\""+input+"\"";
			input = it.unibo.qactors.web.GuiUiKb.buildCorrectPrologString(input);
			//println("input=" + input);
			try {
				Term.createTerm(input);
				String eventMsg=it.unibo.qactors.web.QActorHttpServer.inputToEventMsg(input);
				//println("QActor eventMsg " + eventMsg);
				platform.raiseEvent("input", "local_"+it.unibo.qactors.web.GuiUiKb.inputCmd, eventMsg);
	 		} catch (Exception e) {
		 		println("QActor input error " + e.getMessage());
			}
		}
	 	
		@Override
		public void execAction() {}
		@Override
		public void execAction(IIntent input) {}
		@Override
		public String execActionWithAnswer(String cmd) {return null;}
	  }
	
