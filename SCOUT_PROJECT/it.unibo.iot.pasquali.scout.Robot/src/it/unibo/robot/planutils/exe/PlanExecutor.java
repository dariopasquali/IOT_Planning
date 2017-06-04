package it.unibo.robot.planutils.exe;

import java.util.ArrayList;
import java.util.List;

import it.unibo.robot.Robot;

public class PlanExecutor {
	
	private Robot robot;
	
	private int defSpeed, defTime, defDTime;
	
	private List<ExecutableRobotAction> exePlan;
	
	public PlanExecutor(Robot robot, int defaultSpeed, int defaultTime, int defaultDiagoTime){
		
		this.robot = robot;
		this.defSpeed = defaultSpeed;
		this.defTime = defaultTime;
		this.defDTime = defaultDiagoTime;
		
		exePlan = new ArrayList<ExecutableRobotAction>();
	}
	
	public void addPrint(String msg)
	{
		exePlan.add(new ExecutablePrint(robot, msg));
	}
	
	public void addSense(int tout, String events, String plans)
	{
		exePlan.add(new ExecutableSense(robot, tout, events, plans));
	}
	
	public void addEmit(String evId, String evContent)
	{
		exePlan.add(new ExecutableEmit(robot, evId, evContent));
	}
	
	public void addMove(String move) //in planner representation
	{
		if(move.contains("d"))
			exePlan.add(new ExecutableMove(robot, defSpeed, defDTime, move));
		else
			exePlan.add(new ExecutableMove(robot, defSpeed, defTime, move));
	}
	
	public void execute(){
		
		for(ExecutableRobotAction action : exePlan){
			action.execute();
		}
		
	}
	

}
