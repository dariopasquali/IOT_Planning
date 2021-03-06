/* Generated by AN DISI Unibo */ 
package it.unibo.robot;
import alice.tuprolog.Struct;
import alice.tuprolog.Term;
import it.unibo.qactors.ActorContext;
import it.unibo.is.interfaces.IOutputEnvView;
import it.unibo.qactors.planned.QActorPlanned;
import it.unibo.qactors.action.ActionDummy;
import it.unibo.qactors.action.AsynchActionResult;
import it.unibo.qactors.action.IActorAction;
import it.unibo.qactors.action.IActorAction.ActionExecMode;
import it.unibo.iot.configurator.Configurator;
import it.unibo.iot.executors.baseRobot.IBaseRobot; 
import it.unibo.iot.models.sensorData.distance.IDistanceSensorData;
import it.unibo.iot.models.sensorData.line.ILineSensorData;
import it.unibo.iot.models.sensorData.magnetometer.IMagnetometerSensorData;
import it.unibo.iot.sensors.ISensor; 
import it.unibo.iot.sensors.ISensorObserver;
import it.unibo.iot.sensors.distanceSensor.DistanceSensor;
import it.unibo.iot.sensors.lineSensor.LineSensor;
import it.unibo.iot.sensors.magnetometerSensor.MagnetometerSensor;

public class AbstractRobot extends it.unibo.qactor.robot.RobotActor { 
protected AsynchActionResult aar = null;
protected boolean actionResult = true;
protected alice.tuprolog.SolveInfo sol;

		protected static IOutputEnvView setTheEnv(IOutputEnvView outEnvView ){
			return outEnvView;
		}


	public AbstractRobot(String actorId, ActorContext myCtx, IOutputEnvView outEnvView ,it.unibo.iot.executors.baseRobot.IBaseRobot baserobot)  throws Exception{
		super(actorId, myCtx, "./srcMore/it/unibo/robot/plans.txt", 
		"./srcMore/it/unibo/robot/WorldTheory.pl",
		setTheEnv( outEnvView ) ,baserobot , "init");		
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
    		{ String parg = "consult( \"robotTheory.pl\" )";
    		  aar = solveGoal( parg , 210000000, "","" , "" );
    		//println(getName() + " plan " + curPlanInExec  +  " interrupted=" + aar.getInterrupted() + " action goon="+aar.getGoon());
    		if( aar.getInterrupted() ){
    			curPlanInExec   = "init";
    			if( ! aar.getGoon() ) break;
    		} 			
    		if( aar.getResult().equals("failure")){
    		if( ! switchToPlan("prologFailure").getGoon() ) break;
    		}else if( ! aar.getGoon() ) break;
    		}
    		{ String parg = "consult( \"talkTheory.pl\" )";
    		  aar = solveGoal( parg , 210000000, "","" , "" );
    		//println(getName() + " plan " + curPlanInExec  +  " interrupted=" + aar.getInterrupted() + " action goon="+aar.getGoon());
    		if( aar.getInterrupted() ){
    			curPlanInExec   = "init";
    			if( ! aar.getGoon() ) break;
    		} 			
    		if( aar.getResult().equals("failure")){
    		if( ! switchToPlan("prologFailure").getGoon() ) break;
    		}else if( ! aar.getGoon() ) break;
    		}
    		temporaryStr = " \"++++++++++++++++++ robot(starts) ++++++++++++++++++\" ";
    		println( temporaryStr );  
    		if( ! switchToPlan("waitNavData").getGoon() ) break;
    break;
    }//while
    return returnValue;
    }catch(Exception e){
    println( getName() + " ERROR " + e.getMessage() );
    throw e;
    }
    }
    public boolean waitNavData() throws Exception{	//public to allow reflection
    try{
    	curPlanInExec =  "waitNavData";
    	boolean returnValue = suspendWork;
    while(true){
    nPlanIter++;
    		temporaryStr = " \"++++++++++++++++++ WAIT NAVIGATION DATA ++++++++++++++++++\" ";
    		println( temporaryStr );  
    		//ReceiveMsg
    		 		 aar = receiveAMsg(600000000, "" , "" ); 	//could block
    				if( aar.getInterrupted() ){
    					curPlanInExec   = "playTheGame";
    					if( ! aar.getGoon() ) break;
    				} 			
    				if( ! aar.getGoon() ){
    					System.out.println("			WARNING: receiveMsg in " + getName() + " TOUT " + aar.getTimeRemained() + "/" +  600000000);
    					addRule("tout(receive,"+getName()+")");
    				} 		 
    				//println(getName() + " received " + aar.getResult() );
    		printCurrentMessage(false);
    		//onMsg
    		if( currentMessage.msgId().equals("navdata") ){
    			String parg="loadNavigationData(PLAN,POS)";
    			parg = updateVars(null, Term.createTerm("navdata(PLAN,POS)"), Term.createTerm("navdata(PLAN,POS)"), 
    				    		  					Term.createTerm(currentMessage.msgContent()), parg);
    				if( parg != null ) {
    					aar = solveGoal( parg , 100000, "","" , "");
    					//println(getName() + " plan " + curPlanInExec  +  " interrupted=" + aar.getInterrupted() + " action goon="+aar.getGoon());
    					if( aar.getInterrupted() ){
    						curPlanInExec   = "waitNavData";
    						if( ! aar.getGoon() ) break;
    					} 			
    					if( aar.getResult().equals("failure")){
    						if( ! switchToPlan("prologFailure").getGoon() ) break;
    					}else if( ! aar.getGoon() ) break;
    				}
    		}if( ! switchToPlan("navigate").getGoon() ) break;
    break;
    }//while
    return returnValue;
    }catch(Exception e){
    println( getName() + " ERROR " + e.getMessage() );
    throw e;
    }
    }
    public boolean navigate() throws Exception{	//public to allow reflection
    try{
    	curPlanInExec =  "navigate";
    	boolean returnValue = suspendWork;
    while(true){
    nPlanIter++;
    		{ String parg = "loadThePlan( \"scout.txt\" )";
    		  aar = solveGoal( parg , 30000, "","" , "" );
    		//println(getName() + " plan " + curPlanInExec  +  " interrupted=" + aar.getInterrupted() + " action goon="+aar.getGoon());
    		if( aar.getInterrupted() ){
    			curPlanInExec   = "navigate";
    			if( ! aar.getGoon() ) break;
    		} 			
    		if( aar.getResult().equals("failure")){
    		if( ! aar.getGoon() ) break;
    		}else if( ! aar.getGoon() ) break;
    		}
    		temporaryStr = " \" \" ";
    		println( temporaryStr );  
    		temporaryStr = " \" \" ";
    		println( temporaryStr );  
    		temporaryStr = " \"++++++++++++++++++ Plan Loaded ++++++++++++++++++\" ";
    		println( temporaryStr );  
    		{ String parg = "showPlan(path)";
    		  aar = solveGoal( parg , 1000, "","" , "" );
    		//println(getName() + " plan " + curPlanInExec  +  " interrupted=" + aar.getInterrupted() + " action goon="+aar.getGoon());
    		if( aar.getInterrupted() ){
    			curPlanInExec   = "navigate";
    			if( ! aar.getGoon() ) break;
    		} 			
    		if( aar.getResult().equals("failure")){
    		if( ! aar.getGoon() ) break;
    		}else if( ! aar.getGoon() ) break;
    		}
    		temporaryStr = " \"++++++++++++++++++ ++++++++++++++++++ ++++++++++++++++++\" ";
    		println( temporaryStr );  
    		temporaryStr = " \" \" ";
    		println( temporaryStr );  
    		temporaryStr = " \" \" ";
    		println( temporaryStr );  
    		{ String parg = "runResumablePlan(path)";
    		  aar = solveGoal( parg , 100000000, "","" , "" );
    		//println(getName() + " plan " + curPlanInExec  +  " interrupted=" + aar.getInterrupted() + " action goon="+aar.getGoon());
    		if( aar.getInterrupted() ){
    			curPlanInExec   = "navigate";
    			if( ! aar.getGoon() ) break;
    		} 			
    		if( aar.getResult().equals("failure")){
    		if( ! aar.getGoon() ) break;
    		}else if( ! aar.getGoon() ) break;
    		}
    break;
    }//while
    return returnValue;
    }catch(Exception e){
    println( getName() + " ERROR " + e.getMessage() );
    throw e;
    }
    }
    public boolean notifyEndOfNavigation() throws Exception{	//public to allow reflection
    try{
    	curPlanInExec =  "notifyEndOfNavigation";
    	boolean returnValue = suspendWork;
    while(true){
    nPlanIter++;
    		temporaryStr = " \"� passato al piano successivo\" ";
    		println( temporaryStr );  
    		{ String parg = "notifyEnd";
    		  aar = solveGoal( parg , 10000, "","" , "" );
    		//println(getName() + " plan " + curPlanInExec  +  " interrupted=" + aar.getInterrupted() + " action goon="+aar.getGoon());
    		if( aar.getInterrupted() ){
    			curPlanInExec   = "notifyEndOfNavigation";
    			if( ! aar.getGoon() ) break;
    		} 			
    		if( aar.getResult().equals("failure")){
    		if( ! switchToPlan("prologFailure").getGoon() ) break;
    		}else if( ! aar.getGoon() ) break;
    		}
    		temporaryStr = " \"ma la chiama prolog fallisce per un motivo non ben definito\" ";
    		println( temporaryStr );  
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
    public boolean prologFailure() throws Exception{	//public to allow reflection
    try{
    	curPlanInExec =  "prologFailure";
    	boolean returnValue = suspendWork;
    while(true){
    nPlanIter++;
    		temporaryStr = " \"Prolog goal FAILURE\" ";
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
    /* 
    * ------------------------------------------------------------
    * SENSORS
    * ------------------------------------------------------------
    */
    protected void initSensorSystem(){		
    	try {
    		String goal = "consult( \"./src/it/unibo/robot/sensorTheory.pl\" )";
    		AsynchActionResult aar = solveGoal( goal , 0, "","" , "" );
    		if(  aar.getResult().contains("failure")){
    			//println( "avatar initSensorSystem attempt to load sensorTheory "  );
    			goal = "consult( \"./sensorTheory.pl\" )";
    			aar = solveGoal( goal , 0, "","" , "" );
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
    Composed component motors
    */
    protected void addSensorObservers(){
    	for (ISensor<?> sensor : Configurator.getInstance().getSensors()) {
    		//println( "robot sensor= "  + sensor.getDefStringRep() );
    		//println( "robot sensor class= "  + sensor.getClass().getName() );
        	if( sensor instanceof DistanceSensor){
        		DistanceSensor sensorDistance  = (DistanceSensor) sensor;
        		ISensorObserver<IDistanceSensorData> obs = new SensorObserver<IDistanceSensorData>(this,outView);
        //		println( "avatar add observer to  "  + sensorDistance.getDefStringRep() );
        		sensorDistance.addObserver(  obs  ) ;
        	}
        	if( sensor instanceof LineSensor){
        		LineSensor sensorLine = (LineSensor) sensor;
         		ISensorObserver<ILineSensorData> obs = new SensorObserver<ILineSensorData>(this,outView);
        //		println( "avatar add observer to  "  + sensorLine.getDefStringRep() );
        		sensorLine.addObserver(  obs  ) ;
        	}
         	if( sensor instanceof MagnetometerSensor){
        		MagnetometerSensor sensorMagneto = (MagnetometerSensor) sensor;
         		ISensorObserver<IMagnetometerSensorData> obs = new SensorObserver<IMagnetometerSensorData>(this,outView);
        //		println( "avatar add observer to  "  + sensorMagneto.getDefStringRep() );
        		sensorMagneto.addObserver(  obs  ) ;
        	}
    	//OLD	
    	}		
    }	
    
 
	/* 
	* ------------------------------------------------------------
	* APPLICATION ACTIONS
	* ------------------------------------------------------------
	*/
	
  }

