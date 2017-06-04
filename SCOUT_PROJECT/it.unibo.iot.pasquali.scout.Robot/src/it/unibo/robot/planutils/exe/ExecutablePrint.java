package it.unibo.robot.planutils.exe;

import it.unibo.qactors.action.AsynchActionResult;
import it.unibo.robot.Robot;

public class ExecutablePrint extends ExecutableRobotAction{
	
	private String msg;
	
	public ExecutablePrint(Robot robot, String msg) {
		super(robot);
		this.msg = msg;
	}

	@Override
	public AsynchActionResult execute() {
		try
		{
			robot.println(msg);			
			return new AsynchActionResult(null, 1, true, true, "", robot.getCurrentEvent());	
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}

}
