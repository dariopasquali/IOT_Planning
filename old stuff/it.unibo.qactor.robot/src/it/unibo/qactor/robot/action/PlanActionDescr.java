package it.unibo.qactor.robot.action;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.Vector;
import alice.tuprolog.Struct;
import alice.tuprolog.Term;
import it.unibo.qactors.action.IActorAction.ActorActionType;
import it.unibo.qactors.action.IAsynchAction;

public class PlanActionDescr  {
private String planName;
private String guard;		//guard(consume,g(a,X)) guard(stable,g(a,X))
private ActorActionType actionType;
private String command; 
private String actionArgs;
private String duration;
private String events;
private String plans;

protected IAsynchAction action;
	public PlanActionDescr( 
			ActorActionType actionType, String planName, String guard, String command,
			String actionArgs, String duration, String events, String plans){
 		this.actionType  = actionType;
 		this.guard       = guard;
 		this.command     = command;
 		this.actionArgs  = actionArgs;
		this.duration    = duration;
		this.events      = events;
 		this.planName    = planName.trim().replace("'", "");
		this.plans       = plans.trim();
// 		System.out.println("%%% CREATED " + actionName + " ActorActionType=" + this.ActorActionType);
 	} 	 
	public String getDefStringRep() {
		String  evStr   = Term.createTerm( getEventListRep(events) ).toString();
 		String  planStr = Term.createTerm( getEventListRep(plans) ).toString();
  		return "pa(" + planName + 
 				",guard(" + guard + 				
 				",type(" + actionType +
 				"),time(" + duration + 
 				"),cmd(" + command + 
 				"),args(" + actionArgs + 
 				"),events(" + evStr + 
				"),plans(" + planStr + 
 				"))";
	}
	
	public static PlanActionDescr createDescr( String defaultRep) throws Exception{
 		Term pat = Term.createTerm(defaultRep);
 		return createDescr( (Struct) pat );
	}
	public static PlanActionDescr createDescr( Struct pat ){
		String planName      = pat.getArg(0).toString();
//		String guardRemove   = ((Struct) pat.getArg(1)).getArg(0).toString();
		String guard         = ((Struct) pat.getArg(1)).toString();
		ActorActionType type = getType( ((Struct) pat.getArg(2)).getArg(0).toString() );
		String duration      = ((Struct) pat.getArg(3)).getArg(0).toString();
		String command       = ((Struct) pat.getArg(4)).getArg(0).toString();
 		String actionArgs    = ((Struct) pat.getArg(5)).getArg(0).toString().replaceAll("'", "");		
		String  evStr        = ((Struct) pat.getArg(6)).getArg(0).toString();
		evStr                = evStr.substring(1, evStr.length()-1);
		String  planStr      = ((Struct) pat.getArg(7)).getArg(0).toString();
		planStr              = planStr.substring(1, planStr.length()-1);
		return new PlanActionDescr(type,planName,guard,command,actionArgs,duration,evStr,planStr);
	}
	public static ActorActionType getType(String typeRep){
 		if( typeRep.equals(ActorActionType.move.toString()) ) return ActorActionType.move;
 		else if( typeRep.equals(ActorActionType.photo.toString()) ) return ActorActionType.photo;
 		else if( typeRep.equals(ActorActionType.receive.toString()) ) return ActorActionType.receive;
 		else if( typeRep.equals(ActorActionType.sound.toString()) ) return ActorActionType.sound;
 		else if( typeRep.equals(ActorActionType.userdef.toString()) ) return ActorActionType.userdef;
		return null;
	}
  	public static String[] getEventListArray(String events){
  		if( events == null ) return new String[]{};
  		if( events.length() == 0 ) return new String[0];
		Vector<String>  vs  = new Vector<String>();
 		if( events.contains(",")){
			int i = 0;
			StringTokenizer st = new StringTokenizer(events, ",");
			while(st.hasMoreTokens()){
				String t = st.nextToken();
				t.replaceAll("'", ""); 
 				vs.add( t );
			}
 		}else vs.add( events );
 		String[] vsa =  new String[ vs.size() ];
 		for( int i=0; i<vs.size(); i++)	vsa[i] = vs.elementAt(i);
 		return vsa;
  	}
  	/*
  	 * Retruns a Prolog list like []  or [usercmd,alarm]
  	 */
  	public static String getEventListRep(String events){
  		String outS = "";
   		if( events.length() == 0 ) return"[]";
		Vector<String>  vs  = new Vector<String>();
 		if( events.contains(",")){
			int i = 0;
			StringTokenizer st = new StringTokenizer(events, ",");
			while(st.hasMoreTokens()){
				String t = st.nextToken();
				t.replaceAll("'", ""); 
				vs.add( t );
 			}
 		}else vs.add( events );
 		Iterator<String> it = vs.iterator();
 		while( it.hasNext() ){
 			outS = outS + it.next();
 			if( it.hasNext() ) outS = outS + ",";
 		} 		 
 		return "["+outS+"]";
  	}
 
	public ActorActionType getType() {
 		return actionType;
	}
	public String getPlanName() {
 		return planName;
	}
	public String getGuard() {
 		return guard;
	}
	public String getCommand() {
 		return command;
	}
	public void setInCommand(String varName, String varValue) {
 		if(command.contains(varName)){
			command = command.replaceAll(varName, varValue);
 		}
	}
	public String getArgs() {
// 		System.out.println("actionArgs " + actionArgs );
 		return actionArgs;
	}
	public void setInArgs(String varName, String varValue) {
 		if(actionArgs.contains(varName)){
	 		actionArgs = actionArgs.replaceAll(varName, varValue);
 		}
	}
	public String getDuration() {
//		System.out.println("duration " + duration );
 		return duration;
	}
	public String getEvents() {
//		System.out.println("getEvents " + events );
 		return events;
	}
	public String getPlans() {
//		System.out.println("plans " + plans );
 		return plans;
	}
 }
