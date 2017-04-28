package it.unibo.robot.planutils;

import java.util.ArrayList;

public class Plan {
	
	private String planName;
	private String plan;
	private int moveCounter;
	
	public Plan(String planName)
	{
		this.planName = planName;
		plan = "";
		moveCounter = 1;
	}
	
	public void addSpinMove(String speed, String time, PlanSpinDirection spin, String event, String handler)
	{
		String move = ""+spin.getDirection()+","+speed+","+time+",0";
		plan += "plan("+moveCounter+","+planName+", sentence(true, move( robotmove,"+move+"), '"+event+"', '"+handler+"'))\n";
		moveCounter++;
	}	
	
	public void addSpinMove(String speed, String time, PlanSpinDirection spin)
	{
		addSpinMove(speed, time, spin, "","");
	}
	
	public void addForwardMove(String speed, String time, String event, String handler)
	{
		String move = "mf,"+speed+","+time+",0";
		plan += "plan("+moveCounter+","+planName+", sentence(true, move( robotmove,"+move+"), '"+event+"', '"+handler+"'))\n";
		moveCounter++;
	}
	
	public void addForwardMove(String speed, String time)
	{
		addForwardMove(speed, time, "","");
	}
	
	public void addRobotMove(String move, String event, String handler)
	{
		plan += "plan("+moveCounter+","+planName+", sentence(true, move( robotmove,"+move+"), '"+event+"', '"+handler+"'))\n";
		moveCounter++;
	}
	
	public void addRobotMove(String move)
	{
		addRobotMove(move, "","");
	}
	
	public void addPrint(String message, String event, String handler)
	{
		plan += "plan("+moveCounter+","+planName+", sentence(true, move( print,\""+message+"\"),'"+event+"', '"+handler+"' ))\n";
		moveCounter++;
	}
	
	public void addPrint(String message)
	{
		addPrint(message, "", "");
	}
	
	public void addSolve(String prologGoal, String time, String event, String handler)
	{
		plan += "plan("+moveCounter+","+planName+", sentence(true, move( solve,"+prologGoal+","+time+"),'"+event+"', '"+handler+"' ))\n";
		moveCounter++;
	}
	
	public void addSolve(String prologGoal, String time)
	{
		addSolve(prologGoal, time, "", "");
	}
		
	public void addResumeLastPlan()
	{
		plan += "plan("+moveCounter+","+planName+", sentence(true,move(resumeplan),'', '' ))\n";
		moveCounter++;
	}
	
	public void addSenseEvents(int timeout, ArrayList<String> events, ArrayList<String> plans)
	{
		
		if(events.size() != plans.size())
			return;
		
		String e = "[";
		String p = "[";
		for(int i=0; i<events.size(); i++ )
		{
			e+=events.get(i);
			p+=plans.get(i);
			
			if(i!=events.size()-1)
			{
				e+=",";
				p+=",";
			}
		}
		e+="]";
		p+="]";
		
		plan += "plan("+moveCounter+","+planName+", sentence(true,move(senseEvent"+e+","+p+"),'', '' ))\n";
		moveCounter++;
	}
	
	public void addSenseEvent(int timeout, String ee, String pp)
	{
		
		//String e = "[";
		//String p = "[";
		//e+=ee;
		//p+=pp;
		//e+="]";
		//p+="]";
		
		plan += "plan("+moveCounter+","+planName+", sentence(true,move(senseEvent,"+timeout+",\""+ee+"\",\""+pp+"\"),'', '' ))\n";
		moveCounter++;
	}
	
	public void addMsgSwitch(MsgSwitch msg)
	{
		
		plan += "plan("+moveCounter+","+planName+", sentence(true,move("+msg.getMove()+"),'', '' ))\n";
		moveCounter++;
	}
	
	public void addReceiveMsgAndSolve(int receiveTimeout,
			String msgList,
			String payloadList,
			String goalList,
			int solveTimeout)
	{
		String msg = "[";
		String pay = "[";
		String goal = "[";
		
		msg+=msgList;
		pay+=payloadList;
		goal+=goalList;

		msg+="]";
		pay+="]";
		goal+="]";
		
		plan += "plan("+moveCounter+","+planName+", sentence(true,move(receiveAndSolve,"+receiveTimeout+","+msg+","+pay+","+goal+","+solveTimeout+"),'', '' ))\n";
		moveCounter++;
	}
	
	public void addSwitchPlan(String planname)
	{
		
		plan += "plan("+moveCounter+","+planName+", sentence(true,move(switchplan , "+planname+"),'', '' ))\n";
		moveCounter++;
	}
	
	public String getPlan()
	{
		return plan;
	}
	
}
