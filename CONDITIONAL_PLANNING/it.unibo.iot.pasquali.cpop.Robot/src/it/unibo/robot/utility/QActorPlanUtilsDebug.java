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
	
	public void setEngine(FileEngine engine){
		this.engine = engine;
	}
	
	public AsynchActionResult senseEvents(int tout, String events,
			String plans, String alarmEvents, String recoveryPlans,
			ActionExecMode mode) throws Exception {
		
		AsynchActionResult aar = null;
		
		if(sensingMode.equals(SensingMode.ROBOT))
		{
			return super.senseEvents(tout, events, plans, alarmEvents, recoveryPlans, mode);
		}
		else
		{
			/*
			// Not to sense the world but the system
			if(!events.contains("obstaclefront") && !events.contains("obstacleleft"))
			{
				AsynchActionResult ares = super.senseEvents(tout, events, plans, alarmEvents, recoveryPlans, mode);
				
				String msg = actor.getCurrentEvent().getMsg();
				
				if(!msg.contains("updateSimulation"))
					return ares;
				
				msg = msg.replace('(', ' ');
				msg = msg.replace(')', ' ');
				msg = msg.replace(',', ' ');
				msg = msg.trim();
				
				String[] params = msg.split(" ");
				
				((FileEngine)engine).setObject(
						new State(Integer.parseInt(params[3]), Integer.parseInt(params[2])));
				
				return ares;
			}
			*/

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
				((Robot)actor).switchPlan(plans);
			}
			
			IEventItem ev = new EventItem(events, events, new LocalTime(tout-1), "sensor");
						
			super.actor.setCurrentEvent(ev);
			aar = new AsynchActionResult(null, tout-1, true, true, "", ev);			
			return aar;
		}
		
	}

}
