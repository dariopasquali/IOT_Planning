package it.unibo.qactor.robot.action;
import it.unibo.qactors.action.IActorAction.ActorActionType;
import it.unibo.qactors.action.IAsynchAction;
 
public class ActorActionDescr  {
private	ActorActionType ActorActionType;
private int duration;
private String  endEvent;
private String actionInfo;
private String actionName;
private String answerEvent;
protected IAsynchAction action;
	public ActorActionDescr( 
			ActorActionType ActorActionType, String actionName, IAsynchAction action,
			int duration, String endEvent, String answerEvent, String actionInfo){
 		this.ActorActionType  = ActorActionType;
 		this.action = action;
		this.duration    = duration;
		this.endEvent    = endEvent;
		this.answerEvent = answerEvent;
		this.actionName  = actionName.trim().replace("'", "");
		this.actionInfo  = actionInfo.trim();
// 		System.out.println("%%% CREATED " + actionName + " ActorActionType=" + this.ActorActionType);
 	}
 	 
	public String getDefStringRep() {
 		return "action( " + actionName + ", duration(" + duration + ") , endEvent(" + endEvent + ") , actionInfo(" + actionInfo + "))";
	}
 
	 
	public String getActionName() {
 		return actionName;
	}
 }
