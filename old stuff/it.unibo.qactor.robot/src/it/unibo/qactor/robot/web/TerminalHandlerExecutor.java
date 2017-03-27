package it.unibo.qactor.robot.web;
import it.unibo.contactEvent.interfaces.IEventItem;
import it.unibo.contactEvent.platform.EventHandlerComponent;
import it.unibo.is.interfaces.IOutputEnvView;
import it.unibo.qactors.ActorContext;

public class TerminalHandlerExecutor extends EventHandlerComponent{
private CmdUilInterpreter cmdInterpreter;
	public TerminalHandlerExecutor(String name, ActorContext myctx, String eventId,
			IOutputEnvView view) throws Exception {
		super(name, myctx, eventId, view);
		cmdInterpreter = new CmdUilInterpreter();
  	}
	@Override
	public void doJob() throws Exception {
		IEventItem event = getEventItem();
 		String msg = event.getEventId() + "|" + event.getMsg() + " from " + event.getSubj()   ;
  		showMsg( "Robot TerminalHandlerExecutor "+ msg );		
		char cmd = event.getMsg().charAt(0);
		cmdInterpreter.execute(cmd);
//		showMsg( "TerminalHandlerExecutor endofjob" );
	}
}
