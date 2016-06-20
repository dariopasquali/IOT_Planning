/* Generated by AN DISI Unibo */ 
/*
This code is generated only ONCE
*/
package it.unibo.console;
import it.unibo.console.gui.ConsoleGUI;
import it.unibo.is.interfaces.IOutputEnvView;
import it.unibo.qactors.ActorContext;

public class Console extends AbstractConsole { 	
	
	public Console(String actorId, ActorContext myCtx, IOutputEnvView outEnvView )  throws Exception{
		super(actorId, myCtx, outEnvView);
		((ConsoleGUI) env).setController(this);
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
