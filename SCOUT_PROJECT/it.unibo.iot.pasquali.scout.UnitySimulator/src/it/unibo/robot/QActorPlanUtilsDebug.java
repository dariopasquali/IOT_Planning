package it.unibo.robot;

import akka.dispatch.sysmsg.Supervise;
import it.unibo.contactEvent.interfaces.IEventItem;
import it.unibo.is.interfaces.IOutputEnvView;
import it.unibo.qactors.action.AsynchActionResult;
import it.unibo.qactors.action.IActorAction.ActionExecMode;
import it.unibo.qactors.akka.QActor;
import it.unibo.qactors.akka.QActorActionUtils;
import it.unibo.qactors.akka.QActorPlanUtils;
import it.unibo.qactors.platform.EventItem;
import it.unibo.qactors.platform.LocalTime;
import it.unibo.robot.Robot;

public class QActorPlanUtilsDebug extends QActorPlanUtils{

	private boolean unitySimulation = false;
	
	public QActorPlanUtilsDebug(QActor actor, QActorActionUtils actionUtils, IOutputEnvView outEnvView) {
		
		super(actor, actionUtils, outEnvView);		
	}
	
	public void setUnitySimulation(boolean state)
	{
		unitySimulation = state;
	}
	
	public AsynchActionResult senseEvents(int tout, String events,
			String plans, String alarmEvents, String recoveryPlans,
			ActionExecMode mode) throws Exception {
		
		actor.println("sense("+tout+","+ events+","+ plans+","+ alarmEvents+","+ recoveryPlans+","+ mode+")");
		
		if(unitySimulation)
		{
			try
			{
				if(plans.contains("notifyUnexpectedObstacle"))
					System.out.println("eccolo");
				
				actor.emit("senseObstacle", "senseObstacle");
				return super.senseEvents(tout, events, plans, alarmEvents, recoveryPlans, mode);
			}
			catch(Exception e)
			{
				System.out.println(e.getMessage());
				e.printStackTrace();
				return null;
			}
			
		}
		else
		{
			return super.senseEvents(tout, events, plans, alarmEvents, recoveryPlans, mode);
		}			
		
	}

}
