package it.unibo.qactor.robot.action;
import it.unibo.is.interfaces.IOutputEnvView;
import it.unibo.qactors.action.ActorAction;
 
 

public class ActionDummy extends ActorAction{
 
 	public ActionDummy(String name, boolean cancompensate,
			String terminationEvId, String answerEvId, IOutputEnvView outView,
			long maxduration, String fName) throws Exception {
		super(name, cancompensate, terminationEvId, answerEvId, outView, maxduration);
  	}
	@Override
	protected void execTheAction() throws Exception {
		Thread.sleep(maxduration);		
	}
	@Override
	protected String getApplicationResult() throws Exception {
		println("ActionDummy getApplicationResult TODO ");
		return "TODO";
	}
  
}
