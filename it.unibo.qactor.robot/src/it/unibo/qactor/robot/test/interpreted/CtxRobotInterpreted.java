package it.unibo.qactor.robot.test.interpreted;
import java.io.FileInputStream;
import java.io.InputStream;

import it.unibo.iot.executors.baseRobot.IBaseRobot;
import it.unibo.is.interfaces.IOutputEnvView;
import it.unibo.qactor.robot.RobotSysKb;
import it.unibo.qactor.robot.web.RobotHttpServer;
import it.unibo.qactor.robot.web.TerminalHandlerExecutor;
import it.unibo.qactors.ActorContext;
import it.unibo.qactors.web.GuiUiKb;
import it.unibo.system.SituatedSysKb;
 

public class CtxRobotInterpreted extends ActorContext{

	public CtxRobotInterpreted(String name, IOutputEnvView outView,
			InputStream sysKbStream, InputStream sysRulesStream)
			throws Exception {
		super(name, outView, sysKbStream, sysRulesStream);
 	}

	@Override
	public void configure() {
		try {
			IBaseRobot baseRobot = RobotSysKb.setRobotBase(this, "rbase");
			RobotInterpreted robot     = new RobotInterpreted("mock", this, "./plans.txt","./srcMoreWorldTheory.pl", outEnvView,baseRobot, "init");
// 			new AlarmSource("alarmSource", this, outEnvView);
// 	 		new UserCmdEvHandler("ucevh1", this , robot, "usercmd", outView);
 	 		 //		new UserCmdEvHandler("ucevh2", this , robot, "usercmd", outView);
 	 		initThesServer();
 		} catch (Exception e) {
 			e.printStackTrace();
		} 		
	}
	protected void initThesServer() throws Exception{
// 		new RobotHttpServer(outView,8080).start();		
  		new TerminalHandlerExecutor("th", this, GuiUiKb.terminalCmd, outEnvView);
	}
	public static void main(String[] args) throws Exception{
		InputStream sysKbStream    = new FileInputStream("robotNaiveKb.pl");
		InputStream sysRulesStream = new FileInputStream("sysRules.pl");
		new CtxRobotInterpreted("ctxrobot", SituatedSysKb.standardOutEnvView, sysKbStream, sysRulesStream ).configure();
 	}

}