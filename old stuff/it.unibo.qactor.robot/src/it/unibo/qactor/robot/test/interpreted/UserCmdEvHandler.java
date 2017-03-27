package it.unibo.qactor.robot.test.interpreted;

import it.unibo.contactEvent.interfaces.IEventItem;
import it.unibo.contactEvent.platform.EventHandlerComponent;
import it.unibo.is.interfaces.IOutputEnvView;
import it.unibo.qactors.ActorContext;
import it.unibo.qactors.QActor;

public class UserCmdEvHandler extends EventHandlerComponent{
private QActor qa;
	public UserCmdEvHandler(String name, ActorContext myctx, QActor qa, String eventId,
			IOutputEnvView view) throws Exception {
		super(name, myctx, eventId, view);
		this.qa = qa;
	}

	@Override
	public void doJob() throws Exception {
		IEventItem ev = this.getEventItem();
 		println("%%% UserCmdEvHandler " + getName() + " doJob "  + ev.getDefaultRep() );
		qa.addRule(ev.getDefaultRep());		
	}

}
