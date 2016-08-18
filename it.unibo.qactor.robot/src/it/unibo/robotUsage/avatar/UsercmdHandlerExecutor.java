package it.unibo.robotUsage.avatar;
import alice.tuprolog.Struct;
import alice.tuprolog.Term;
import it.unibo.contactEvent.interfaces.IEventItem;
import it.unibo.contactEvent.platform.EventHandlerComponent;
import it.unibo.is.interfaces.IOutputEnvView;
import it.unibo.qactors.ActorContext;

/*
 * This handler is useful when the RobotHttpServer is created in 'event' mode
 */
public class UsercmdHandlerExecutor extends EventHandlerComponent{
private CmdUilInterpreter cmdInterpreter;
	public UsercmdHandlerExecutor(String name, ActorContext myctx, String eventId,
			IOutputEnvView view) throws Exception {
		super(name, myctx, eventId, view);
		cmdInterpreter = new CmdUilInterpreter();
  	}
	@Override
	public void doJob() throws Exception {
		IEventItem event = getEventItem();
 		String msg = event.getEventId() + "|" + event.getMsg() + " from " + event.getSubj()   ;
  		showMsg( msg );	
  		Struct cmdMsgStruct = (Struct) Term.createTerm(event.getMsg());
  		//cmdMsg : usercmd("h-Low")
		char cmd = cmdMsgStruct.getArg(0).toString().replace("'","").charAt(0);
		cmdInterpreter.execute(cmd);
//		showMsg( "TerminalHandlerExecutor endofjob" );
	}
}
