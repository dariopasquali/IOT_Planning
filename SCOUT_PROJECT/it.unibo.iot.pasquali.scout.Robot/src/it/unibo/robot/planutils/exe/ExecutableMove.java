package it.unibo.robot.planutils.exe;

import it.unibo.qactors.action.AsynchActionResult;
import it.unibo.robot.Robot;

public class ExecutableMove extends ExecutableRobotAction{

	private enum ExecutableMoveType{
		
		TILED("t", "forward"),
		DIAGONAL("d", "forward"),
		R45("r", "right"),
		L45("l", "left"),
		STOP("h", "stop");
		
		private String rep;
		private String mapping;
		
		private ExecutableMoveType(String rep, String mapping){
			this.rep = rep;
			this.mapping = mapping;
		}
		
		public String getMapping(){return mapping;}
		public String getRep(){ return rep; }
		
		public static ExecutableMoveType fromPlannerRep(String rep)
		{
			for(ExecutableMoveType e : ExecutableMoveType.values())
				if(e.getRep().equals(rep))
					return e;
			
			return null;
		}
	}
	
	
	private int speed;
	private int duration;
	private String events = "";
	private String plans = "";
	
	private ExecutableMoveType moveType;
	/*
	 * t = forward tiled
	 * d = forward diagonal
	 * r = spin right 45°
	 * l = spin left 45°
	 * dr = spin right 90°
	 * dl = spin left 90°
	 * h = stop
	 */
	
	public ExecutableMove(Robot robot, int speed, int duration, String rep) {
		super(robot);

		this.moveType = ExecutableMoveType.fromPlannerRep(rep);
		this.speed = speed;
		this.duration = duration;		
	}

	@Override
	public AsynchActionResult execute() {
		
		try
		{
			return robot.myExecute(moveType.getMapping(), speed, 0, duration, events, plans);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}		
	}
	
	
	

}
