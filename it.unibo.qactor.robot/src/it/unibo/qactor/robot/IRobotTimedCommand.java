package it.unibo.qactor.robot;
import it.unibo.iot.models.commands.baseRobot.IBaseRobotCommand;
 

public interface IRobotTimedCommand extends IBaseRobotCommand{
	public IBaseRobotCommand getRobotBaseCommand();
	public long getDuration();
	public String getCompletionEventId();
}
