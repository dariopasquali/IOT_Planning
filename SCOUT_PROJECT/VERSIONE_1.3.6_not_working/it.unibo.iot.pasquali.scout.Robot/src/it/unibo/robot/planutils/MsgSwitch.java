package it.unibo.robot.planutils;

import java.util.ArrayList;

public class MsgSwitch {
	
	private int receiveTimeout;
	private ArrayList<String> msgList;
	private ArrayList<String> payloadList;
	private ArrayList<String> payloadBoundedList;
	private ArrayList<String> toDoList;
	
	public MsgSwitch(int receiveTimeout)
	{
		this.receiveTimeout = receiveTimeout;
	}
	
	public void addSolveCase(String msgID, String payload, String payloadBounded, String goal, int solveTimeout)
	{
		msgList.add(msgID);
		payloadList.add(payload);
		payloadBoundedList.add(payloadBounded);
		String solve = "solve("+goal+","+solveTimeout+")";
		toDoList.add(solve);
	}
	
	public void addSwitchCase(String msgID, String payload, String payloadBounded, String plan)
	{
		msgList.add(msgID);
		payloadList.add(payload);
		payloadBoundedList.add(payloadBounded);
		String solve = "switchTo("+plan+")";
		toDoList.add(solve);
	}
	
	public String getMove()
	{
		String pml = "[";
		String ppl = "[";
		String ppbl = "[";
		String ptdl = "[";
		
		for(int i = 0; i<msgList.size(); i++)
		{
			pml+=msgList.get(i);
			ppl+=payloadList.get(i);
			ppbl+=payloadBoundedList.get(i);
			ptdl+=toDoList.get(i);
			
			if(i!=msgList.size()-1)
			{
				pml+=",";
				ppl+=",";
				ppbl+=",";
				ptdl+=",";
			}
		}
		pml+="]";
		ppl+="]";
		ppbl+="]";
		ptdl+="]";
		
		return "msgSwitch("+pml+","+ppl+","+ppbl+","+ptdl+")";
	}

}
