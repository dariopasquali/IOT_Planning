package it.unibo.qactor.robot.test.interpreted;

import it.unibo.is.interfaces.IOutputEnvView;
import it.unibo.qactors.ActorContext;
import it.unibo.qactors.QActor;

public class AlarmSource extends QActor{

	public AlarmSource(String actorId, ActorContext myCtx,
			IOutputEnvView outEnvView) {
		super(actorId, myCtx, outEnvView);
 	}

	@Override
	protected void doJob() throws Exception {
		Thread.sleep(4000);
		println(getName() + " emits alarm");
		emit("alarm", "alarm( "+ getName()+ ")");
	}

}
