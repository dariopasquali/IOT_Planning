/* Generated by AN DISI Unibo */ 
/*
This code is generated only ONCE
*/
package it.unibo.console;
import java.util.ArrayList;

import it.unibo.console.gui.ConsoleGUI;
import it.unibo.is.interfaces.IOutputEnvView;
import it.unibo.planning.astar.algo.Engine;
import it.unibo.planning.astar.algo.SearchAgent;
import it.unibo.qactors.ActorContext;
import it.unibo.qactors.action.AsynchActionResult;
import it.unibo.planning.astar.domain.Move;
import it.unibo.planning.astar.domain.State.Direction;

public class Console extends AbstractConsole { 	
	
	private ArrayList<String> bestPath;
	
	
	public Console(String actorId, ActorContext myCtx, IOutputEnvView outEnvView )  throws Exception{
		super(actorId, myCtx, outEnvView);
		((ConsoleGUI) env).setController(this);
		Engine.actor = this;
		bestPath = new ArrayList<String>();
	}
	
	public AsynchActionResult solveGoal( String goal, int duration, String answerEv, String events, String plans) throws Exception
	{
		return super.solveGoal(goal, duration, answerEv, events, plans);
	}
	
	public void searchBestPath(int sx, int sy, int gx, int gy)
	{
		it.unibo.planning.astar.domain.State start = new it.unibo.planning.astar.domain.State(sx, sy, Direction.NORTH, null, 0);
		it.unibo.planning.astar.domain.State goal = new it.unibo.planning.astar.domain.State(gx, gy, Direction.NONE, null, 0);
	
		SearchAgent agent = new SearchAgent();
		ArrayList<Move> movePath = agent.searchBestPath(start, goal);
		
		int moveCounter = 1;
		
		for(int i=movePath.size()-1; i>=0; i--)
		{			
			String plan = "plan("+moveCounter+",returnToBase, sentence(true, "+movePath.get(i).toString()+"), 'abort', 'abortCommand')) ";
			bestPath.add(plan);
			moveCounter++;
		}	
	}
	
	public void sendPlan()
	{
		
	}
	
	
	@Override
	public void execAction(String cmd) {
		
		String[] command = cmd.split(" ");
		
		
		
		switch (command[0]){
		case "LOAD":
			String[] params = command[1].split(",");
			platform.raiseEvent("input", "local_gui_command", "command(load("+params[0]+")");
			break;
			
		case "STORE":
			params = command[1].split(",");
			platform.raiseEvent("input", "local_gui_command", "command(store("+params[0]+")");
			break;
			
		case "NAVIGATE":
			params = command[1].split(",");
			if(params.length == 4)
				platform.raiseEvent("input", "local_gui_command", "command(navigate("
						+ "position(" + params[0] + "," + params[1]+")"
								+ ","
						+ "position(" + params[2] + "," + params[3]+")"
								+ "))");
			else
				platform.raiseEvent("input", "local_gui_command", "command(navigate("
						+ "position(" + params[0] + "," + params[1]+")"
								+ "))");
			
			break;
			
			
		case "EXPLORE":
			params = command[1].split(",");
			platform.raiseEvent("input", "local_gui_command", "command(explore(" + params[0] + ","+ params[1] +"))");
			break;
			
			
		case "ABORT":
			platform.raiseEvent("input", "local_gui_command", "command(abort)");
			break;
		default:
			println("Invalid command");
		}
	}	
}
