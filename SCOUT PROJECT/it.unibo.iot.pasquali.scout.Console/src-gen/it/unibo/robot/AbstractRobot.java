/* Generated by AN DISI Unibo */ 
package it.unibo.robot;
import alice.tuprolog.Term;
import alice.tuprolog.Struct;
import it.unibo.qactors.ActorContext;
import it.unibo.is.interfaces.IOutputEnvView;
import it.unibo.qactors.planned.QActorPlanned;
import it.unibo.qactors.action.ActionDummy;
import it.unibo.qactors.action.AsynchActionResult;
import it.unibo.qactors.action.IActorAction;
import it.unibo.qactors.action.IActorAction.ActionExecMode;

public abstract class AbstractRobot extends QActorPlanned { 
	protected AsynchActionResult aar = null;
	protected boolean actionResult = true;
	protected alice.tuprolog.SolveInfo sol;
	
			protected static IOutputEnvView setTheEnv(IOutputEnvView outEnvView ){
				return outEnvView;
			}
	
	
		public AbstractRobot(String actorId, ActorContext myCtx, IOutputEnvView outEnvView )  throws Exception{
			super(actorId, myCtx, "./srcMore/it/unibo/robot/plans.txt", 
			"./srcMore/it/unibo/robot/WorldTheory.pl",
			setTheEnv( outEnvView )  , "init");		
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
	    		temporaryStr = " \"Exploration interactivity Test\" ";
	    		println( temporaryStr );  
	    		//ReceiveMsg
	    		 		 aar = receiveAMsg(1000000000, "" , "" ); 	//could block
	    				if( aar.getInterrupted() ){
	    					curPlanInExec   = "playTheGame";
	    					if( ! aar.getGoon() ) break;
	    				} 			
	    				if( ! aar.getGoon() ){
	    					System.out.println("			WARNING: receiveMsg in " + getName() + " TOUT " + aar.getTimeRemained() + "/" +  1000000000);
	    					addRule("tout(receive,"+getName()+")");
	    				} 		 
	    				//println(getName() + " received " + aar.getResult() );
	    		memoCurrentMessage( false );
	    		printCurrentMessage(false);
	    		//onMsg
	    		if( currentMessage.msgId().equals("explore") ){
	    			String parg = "";
	    			parg = updateVars(null, Term.createTerm("explore"), Term.createTerm("explore(START,BOUNDS)"), 
	    				    		  					Term.createTerm(currentMessage.msgContent()), parg);
	    				if( parg != null ){
	    					 if( ! switchToPlan("simulateExploration").getGoon() ) break; 
	    				}//else println("guard  fails");  //parg is null when there is no guard (onEvent)
	    		}if( repeatPlan(0).getGoon() ) continue;
	    break;
	    }//while
	    return returnValue;
	    }catch(Exception e){
	    println( getName() + " ERROR " + e.getMessage() );
	    throw e;
	    }
	    }
	    public boolean simulateExploration() throws Exception{	//public to allow reflection
	    try{
	    	curPlanInExec =  "simulateExploration";
	    	boolean returnValue = suspendWork;
	    while(true){
	    nPlanIter++;
	    		temporaryStr = " \"let's explore!!\" ";
	    		println( temporaryStr );  
	    		//delay
	    		aar = delayReactive(500,"" , "");
	    		if( aar.getInterrupted() ) curPlanInExec   = "simulateExploration";
	    		if( ! aar.getGoon() ) break;
	    		temporaryStr = unifyMsgContent("expdata(POS,STATE)","expdata(position(0,3),clear)", guardVars ).toString();
	    		emit( "expdata", temporaryStr );
	    		//delay
	    		aar = delayReactive(500,"" , "");
	    		if( aar.getInterrupted() ) curPlanInExec   = "simulateExploration";
	    		if( ! aar.getGoon() ) break;
	    		temporaryStr = unifyMsgContent("expdata(POS,STATE)","expdata(position(0,2),clear)", guardVars ).toString();
	    		emit( "expdata", temporaryStr );
	    		//delay
	    		aar = delayReactive(500,"" , "");
	    		if( aar.getInterrupted() ) curPlanInExec   = "simulateExploration";
	    		if( ! aar.getGoon() ) break;
	    		temporaryStr = unifyMsgContent("expdata(POS,STATE)","expdata(position(0,1),clear)", guardVars ).toString();
	    		emit( "expdata", temporaryStr );
	    		//delay
	    		aar = delayReactive(500,"" , "");
	    		if( aar.getInterrupted() ) curPlanInExec   = "simulateExploration";
	    		if( ! aar.getGoon() ) break;
	    		temporaryStr = unifyMsgContent("expdata(POS,STATE)","expdata(position(0,0),object)", guardVars ).toString();
	    		emit( "expdata", temporaryStr );
	    		//delay
	    		aar = delayReactive(500,"" , "");
	    		if( aar.getInterrupted() ) curPlanInExec   = "simulateExploration";
	    		if( ! aar.getGoon() ) break;
	    		temporaryStr = unifyMsgContent("expdata(POS,STATE)","expdata(position(1,1),clear)", guardVars ).toString();
	    		emit( "expdata", temporaryStr );
	    		//delay
	    		aar = delayReactive(500,"" , "");
	    		if( aar.getInterrupted() ) curPlanInExec   = "simulateExploration";
	    		if( ! aar.getGoon() ) break;
	    		temporaryStr = unifyMsgContent("expdata(POS,STATE)","expdata(position(1,0),clear)", guardVars ).toString();
	    		emit( "expdata", temporaryStr );
	    		//delay
	    		aar = delayReactive(500,"" , "");
	    		if( aar.getInterrupted() ) curPlanInExec   = "simulateExploration";
	    		if( ! aar.getGoon() ) break;
	    		temporaryStr = unifyMsgContent("expdata(POS,STATE)","expdata(position(2,0),clear)", guardVars ).toString();
	    		emit( "expdata", temporaryStr );
	    		//delay
	    		aar = delayReactive(500,"" , "");
	    		if( aar.getInterrupted() ) curPlanInExec   = "simulateExploration";
	    		if( ! aar.getGoon() ) break;
	    		temporaryStr = unifyMsgContent("expdata(POS,STATE)","expdata(position(3,0),clear)", guardVars ).toString();
	    		emit( "expdata", temporaryStr );
	    		//delay
	    		aar = delayReactive(500,"" , "");
	    		if( aar.getInterrupted() ) curPlanInExec   = "simulateExploration";
	    		if( ! aar.getGoon() ) break;
	    		temporaryStr = unifyMsgContent("expdata(POS,STATE)","expdata(position(3,1),clear)", guardVars ).toString();
	    		emit( "expdata", temporaryStr );
	    		//delay
	    		aar = delayReactive(500,"" , "");
	    		if( aar.getInterrupted() ) curPlanInExec   = "simulateExploration";
	    		if( ! aar.getGoon() ) break;
	    		temporaryStr = unifyMsgContent("expdata(POS,STATE)","expdata(position(3,2),clear)", guardVars ).toString();
	    		emit( "expdata", temporaryStr );
	    		//delay
	    		aar = delayReactive(500,"" , "");
	    		if( aar.getInterrupted() ) curPlanInExec   = "simulateExploration";
	    		if( ! aar.getGoon() ) break;
	    		temporaryStr = unifyMsgContent("expdata(POS,STATE)","expdata(position(3,3),object)", guardVars ).toString();
	    		emit( "expdata", temporaryStr );
	    		//delay
	    		aar = delayReactive(500,"" , "");
	    		if( aar.getInterrupted() ) curPlanInExec   = "simulateExploration";
	    		if( ! aar.getGoon() ) break;
	    		temporaryStr = unifyMsgContent("expdata(POS,STATE)","expdata(position(2,2),object)", guardVars ).toString();
	    		emit( "expdata", temporaryStr );
	    		//delay
	    		aar = delayReactive(500,"" , "");
	    		if( aar.getInterrupted() ) curPlanInExec   = "simulateExploration";
	    		if( ! aar.getGoon() ) break;
	    		temporaryStr = unifyMsgContent("expdata(POS,STATE)","expdata(position(2,1),object)", guardVars ).toString();
	    		emit( "expdata", temporaryStr );
	    		//delay
	    		aar = delayReactive(500,"" , "");
	    		if( aar.getInterrupted() ) curPlanInExec   = "simulateExploration";
	    		if( ! aar.getGoon() ) break;
	    		temporaryStr = unifyMsgContent("expdata(POS,STATE)","expdata(position(1,2),clear)", guardVars ).toString();
	    		emit( "expdata", temporaryStr );
	    		//delay
	    		aar = delayReactive(500,"" , "");
	    		if( aar.getInterrupted() ) curPlanInExec   = "simulateExploration";
	    		if( ! aar.getGoon() ) break;
	    		temporaryStr = unifyMsgContent("expdata(POS,STATE)","expdata(position(1,3),clear)", guardVars ).toString();
	    		emit( "expdata", temporaryStr );
	    		//delay
	    		aar = delayReactive(500,"" , "");
	    		if( aar.getInterrupted() ) curPlanInExec   = "simulateExploration";
	    		if( ! aar.getGoon() ) break;
	    		temporaryStr = unifyMsgContent("expdata(POS,STATE)","expdata(position(2,3),clear)", guardVars ).toString();
	    		emit( "expdata", temporaryStr );
	    		//delay
	    		aar = delayReactive(1000,"" , "");
	    		if( aar.getInterrupted() ) curPlanInExec   = "simulateExploration";
	    		if( ! aar.getGoon() ) break;
	    		temporaryStr = unifyMsgContent("end","end", guardVars ).toString();
	    		emit( "end", temporaryStr );
	    		returnValue = continueWork; //we must restore nPlanIter and curPlanInExec of the 'interrupted' plan
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
		
	  }
	
