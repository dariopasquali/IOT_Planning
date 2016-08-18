package it.unibo.qactor.robot.test.base;
import java.io.FileInputStream;
import java.io.InputStream;

import it.unibo.iot.executors.baseRobot.IBaseRobot;
import it.unibo.is.interfaces.IOutputEnvView;
import it.unibo.qactor.robot.RobotSysKb;
import it.unibo.qactors.ActorContext;
import it.unibo.system.SituatedSysKb;
 

public class CtxRobotBase extends ActorContext{

	public CtxRobotBase(String name, IOutputEnvView outView,
			InputStream sysKbStream, InputStream sysRulesStream)
			throws Exception {
		super(name, outView, sysKbStream, sysRulesStream);
 	}

	@Override
	public void configure() {
		try {
			IBaseRobot baseRobot = RobotSysKb.setRobotBase(this, "rbase");
			RobotActorBaseUsage robot     = new RobotActorBaseUsage("mock", this, "./plans.txt", "./srcMoreWorldTheory.pl",outEnvView,baseRobot, "init");
  		} catch (Exception e) {
 			e.printStackTrace();
		} 		
	}
 	public static void main(String[] args) throws Exception{
		InputStream sysKbStream    = new FileInputStream("robotNaiveKb.pl");
		InputStream sysRulesStream = new FileInputStream("sysRules.pl");
		new CtxRobotBase("ctxrobot", SituatedSysKb.standardOutEnvView, sysKbStream, sysRulesStream ).configure();
 	}

}