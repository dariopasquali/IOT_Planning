package it.unibo.robot.utility;

import akka.dispatch.sysmsg.Supervise;
import it.unibo.contactEvent.interfaces.IEventItem;
import it.unibo.domain.model.implementation.State;
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

	private SensingMode sensingMode = null;
	private FileEngine engine = null;
	private boolean unitySimulation = false;
	
	public QActorPlanUtilsDebug(QActor actor, QActorActionUtils actionUtils, IOutputEnvView outEnvView) {
		
		super(actor, actionUtils, outEnvView);		
	}
	
	public void enableFileSensingMode()
	{
		this.sensingMode = SensingMode.FILE;
	}
	
	public void disableFileSensingMode()
	{
		this.sensingMode = SensingMode.ROBOT;
	}
	
	public void setUnitySimulation(boolean state)
	{
		unitySimulation = state;
	}
	
	public void setEngine(FileEngine engine){
		this.engine = engine;
	}
	
	public AsynchActionResult senseEvents(int tout, String events,
			String plans, String alarmEvents, String recoveryPlans,
			ActionExecMode mode) throws Exception {
		
		AsynchActionResult aar = null;
		actor.println("sense("+tout+","+ events+","+ plans+","+ alarmEvents+","+ recoveryPlans+","+ mode+")");
		
		if(sensingMode.equals(SensingMode.ROBOT))
		{
			return super.senseEvents(tout, events, plans, alarmEvents, recoveryPlans, mode);
		}
		else
		{
			if(events.contains("obstacle"))
			{
				if(unitySimulation)
				{
					try
					{
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
					boolean obj = false;
					
					if(events.contains("obstaclefront"))
					{
						obj = engine.checkObjFront();
					}
					else
					{
						obj = engine.checkObjLeft();
					}
					
					if(!obj)
					{
						events = "noev";
					}
					else
					{
						System.out.println("Event "+events+" received!!!!");
						super.switchToPlan(plans);
					}
					
					IEventItem ev = new EventItem(events, events, new LocalTime(tout-1), "sensor");
								
					super.actor.setCurrentEvent(ev);
					aar = new AsynchActionResult(null, tout-1, true, true, "", ev);			
					return aar;
				}			
			}
			else
			{
				return super.senseEvents(tout, events, plans, alarmEvents, recoveryPlans, mode);
			}			
		}		
	}

}
