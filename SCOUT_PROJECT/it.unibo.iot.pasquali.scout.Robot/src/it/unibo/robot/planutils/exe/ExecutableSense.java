package it.unibo.robot.planutils.exe;

import alice.tuprolog.Term;
import it.unibo.qactors.action.AsynchActionResult;
import it.unibo.qactors.action.IActorAction.ActionExecMode;
import it.unibo.robot.Robot;

public class ExecutableSense extends ExecutableRobotAction{

	private String events;
	private String plans;
	private int tout;
	
	public ExecutableSense(Robot robot, int tout, String events, String plans) {
		super(robot);
		this.tout = tout;
		this.events = events;
		this.plans = plans;
	}

	@Override
	public AsynchActionResult execute() {
		try
		{
			//return robot.getPlanUtils().senseEvents(tout, events, plans, "", "", ActionExecMode.synch);
			
			
			
			AsynchActionResult aar = 
					robot.getPlanUtils().senseEvents( tout,events,"continue","" , "",ActionExecMode.synch );
		
			if( ! aar.getGoon() || aar.getTimeRemained() <= 0 ){
    			//println("			WARNING: sense timeout");
    			robot.addRule("tout(senseevent,"+robot.getName()+")");
    		}
    		robot.printCurrentEvent(false);
    		//onEvent
    		if( robot.getCurrentEvent().getEventId().equals(events) ){
    			
    			return robot.getPlanUtils().switchToPlan(plans);    			
    		 }
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
		return null;
	}
	

}
