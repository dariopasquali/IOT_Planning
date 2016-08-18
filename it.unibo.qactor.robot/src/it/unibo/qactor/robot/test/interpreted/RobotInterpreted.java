package it.unibo.qactor.robot.test.interpreted;
import it.unibo.iot.executors.baseRobot.IBaseRobot;
import it.unibo.is.interfaces.IOutputEnvView;
import it.unibo.qactor.robot.RobotActor;
import it.unibo.qactors.ActorContext;
import it.unibo.qactors.action.IActorAction;
 
public class RobotInterpreted extends RobotActor{
 	private	int userCmd;	
	public RobotInterpreted( String id, ActorContext myCtx, String planPath, String worldTheoryPath, IOutputEnvView outView,  IBaseRobot baseRobot, String defaultPlan ) throws Exception{
		super( id,myCtx, planPath, worldTheoryPath, outView, baseRobot, defaultPlan  ); 
   	}
	@Override
	protected void doJob() throws Exception {
		do{
			/*
			 * Add some fact to enable the guards
			 */
			this.addRule("domove(left,20)");
			this.addRule("domove(backward,80)");
			buildPlanTable();
			executeThePlan(defaultPlan);
			/*
			 * Do not terminate to allow plan modification
			 */
			userCmd = waitForUserCommand( );
		}while(userCmd != 'e' )	;
		println("bye bye");
		System.exit(1);
 	} 	
	public boolean reactToUserCmd(){
		println(getName() + " ==== reactToUserCmd called by reflection.");	
		try {
			executeThePlan("reactToUserCmd");
		} catch (Exception e) {
			e.printStackTrace();
	} 
		return IActorAction.suspendPlan;
	}
}
