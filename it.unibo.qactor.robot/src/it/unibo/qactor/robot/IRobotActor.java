package it.unibo.qactor.robot;
import it.unibo.iot.executors.baseRobot.IBaseRobot;
import it.unibo.is.interfaces.IBasicUniboEnv;
import it.unibo.qactors.action.AsynchActionResult;

public interface IRobotActor {
	public String getName();
	public IBaseRobot getBaseRobot();
 	public AsynchActionResult execute(String command, int speed, int millisec, int angle, String  events,String plans) throws Exception;
	public void terminate(); 
	public void setEnv(IBasicUniboEnv env);
	public void sendMsg(String msgID, String destActorId, String msgType, String msg) throws Exception;
}
