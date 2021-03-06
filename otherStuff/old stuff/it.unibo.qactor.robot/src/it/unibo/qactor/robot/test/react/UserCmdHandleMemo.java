/* Generated by AN DISI Unibo */ 
package it.unibo.qactor.robot.test.react;
import it.unibo.contactEvent.interfaces.IEventItem;
import it.unibo.contactEvent.platform.EventHandlerComponent;
import it.unibo.is.interfaces.IOutputEnvView;
import it.unibo.qactors.ActorContext;
 
public class UserCmdHandleMemo extends EventHandlerComponent { //implements IActionHandler
protected IEventItem event;
	public UserCmdHandleMemo(String name, ActorContext myCtx, IOutputEnvView outEnvView, String[] eventIds ) throws Exception {
		super(name, myCtx, eventIds, outEnvView);
  	}
	@Override
	public void doJob() throws Exception {
		event = getEventItem();
		if( event == null ) return;
		showMsg( "---------------------------------------------------------------------" );	
		showMsg( event.getPrologRep()  );				 
		showMsg( "---------------------------------------------------------------------" );	
		myCtx.getActor("robot").addRule("stored("+event.getEventId()+","+event.getMsg()+")");
	
	}
	

 
}
