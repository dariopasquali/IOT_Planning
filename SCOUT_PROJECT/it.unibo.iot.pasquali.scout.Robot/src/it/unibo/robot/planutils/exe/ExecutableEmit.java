package it.unibo.robot.planutils.exe;

import it.unibo.qactors.action.AsynchActionResult;
import it.unibo.robot.Robot;

public class ExecutableEmit extends ExecutableRobotAction{
	
	private String evId, evContent;
	
	public ExecutableEmit(Robot robot, String evId, String evContent) {
		super(robot);
		this.evId = evId;
		this.evContent = evContent;
	}

	@Override
	public AsynchActionResult execute() {
		try
		{
			robot.emit(evId, evContent);
			return new AsynchActionResult(null, 1, true, true, "", robot.getCurrentEvent());
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}

}
