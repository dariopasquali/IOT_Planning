package it.unibo.robot.planutils.exe;

import it.unibo.qactors.action.AsynchActionResult;
import it.unibo.robot.Robot;

public abstract class ExecutableRobotAction{
	
	protected Robot robot;
	
	public ExecutableRobotAction(Robot robot){
		this.robot = robot;
	}
	
	public abstract AsynchActionResult execute();	
}
