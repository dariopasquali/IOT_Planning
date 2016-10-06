package it.unibo.robot.planutility;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class PlanSaver {
	
	private String planName;
	private String plan;
	private int moveCounter;
	
	public PlanSaver(String planName)
	{
		this.planName = planName;
		plan = "";
		moveCounter = 1;
	}
	
	public void addSpinMove(String speed, String time, String spin, String event, String handler)
	{
		String move = ""+spin+","+speed+","+time+",0";
		plan += "plan("+moveCounter+","+planName+", sentence(true, move( robotmove,"+move+"), '"+event+"', '"+handler+"'))\n";
		moveCounter++;
	}	
	
	public void addSpinMove(String speed, String time, String spin)
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
		plan += "plan("+moveCounter+","+planName+", sentence(true,move(print,\""+message+"\"),'"+event+"', '"+handler+"' ))\n";
		moveCounter++;
	}
	
	public void addPrint(String message)
	{
		addPrint(message, "", "");
	}
	
	public void addSolve(String prologGoal, String time, String event, String handler)
	{
		plan += "plan("+moveCounter+","+planName+", sentence(true,move( solve,"+prologGoal+","+time+"),'"+event+"', '"+handler+"' ))\n";
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
	
	
	public void storePlan()
	{
		File f = new File(planName+".txt");
		try
		{
			FileOutputStream fout = new FileOutputStream(f);
			fout.write(plan.getBytes());
			fout.close();
		}
		catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public String getPlan()
	{
		return plan;
	}

}
