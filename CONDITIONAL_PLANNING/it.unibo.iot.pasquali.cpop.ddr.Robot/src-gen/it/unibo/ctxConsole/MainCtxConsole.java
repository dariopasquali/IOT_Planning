/* Generated by AN DISI Unibo */ 
package it.unibo.ctxConsole;
import it.unibo.qactors.QActorContext;
import java.io.InputStream;
import alice.tuprolog.SolveInfo;
import alice.tuprolog.Term;
import alice.tuprolog.Var;
 
import it.unibo.is.interfaces.IBasicEnvAwt;
import it.unibo.is.interfaces.IIntent;
import it.unibo.is.interfaces.IOutputEnvView;
import it.unibo.system.SituatedSysKb;

public class MainCtxConsole   {
private IBasicEnvAwt env; 
private it.unibo.qactor.robot.RobotActor robot; 
 
 	
/*
* ----------------------------------------------
* MAIN
* ----------------------------------------------
*/
 
	public static void main(String[] args) throws Exception{
			IOutputEnvView outEnvView = SituatedSysKb.standardOutEnvView;
			it.unibo.qactors.QActorUtils.setRobotBase("robotScout" );  
		    String webDir = null;
			QActorContext.initQActorSystem(
				"ctxconsole", "./srcMore/it/unibo/ctxConsole/scout.pl", 
				"./srcMore/it/unibo/ctxConsole/sysRules.pl", outEnvView,webDir, false);
 	}
 	
}
