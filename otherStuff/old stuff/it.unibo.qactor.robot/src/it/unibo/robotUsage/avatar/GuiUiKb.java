package it.unibo.robotUsage.avatar;
import it.unibo.iot.models.commands.baseRobot.IBaseRobotSpeed;
import it.unibo.qactor.robot.RobotSysKb;
 

public class GuiUiKb {
 	public static IBaseRobotSpeed  robotSpeed =  RobotSysKb.SPEED_LOW ;
 	public static final String chSpeed        = "SPEED";

	public static final String terminalCmd = "usercmd";
	public static final String speedCmd    = "speed";
	public static final String forwardCmd  = "w";
	public static final String backwardCmd = "s";
	public static final String leftCmd     = "a";
	public static final String rightCmd    = "d";
	public static final String stopCmd     = "q";

}
