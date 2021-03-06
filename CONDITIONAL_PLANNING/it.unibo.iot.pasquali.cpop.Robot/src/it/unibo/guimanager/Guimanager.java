/* Generated by AN DISI Unibo */ 
/*
This code is generated only ONCE
*/
package it.unibo.guimanager;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import it.unibo.gui.RobotGUI;
import it.unibo.is.interfaces.IActivity;
import it.unibo.is.interfaces.IIntent;
import it.unibo.is.interfaces.IOutputEnvView;
import it.unibo.model.map.Map;
import it.unibo.qactors.QActorContext;

public class Guimanager extends AbstractGuimanager implements IActivity
{
	private Map map;
	
	private static RobotGUI gui = new RobotGUI();
	
	public Guimanager(String actorId, QActorContext myCtx, IOutputEnvView outEnvView )
			throws Exception
	{
		super(actorId, myCtx, gui);
		((RobotGUI) env).setController(this);
	}
	
	
	public void showMap(int startX, int startY, String filename)
	{
		((RobotGUI) env).setVisible(true);
		
		Map m = null;
		
		List<String> data = new ArrayList<String>();						
		try
		{
			InputStream fs = new FileInputStream(filename);
			InputStreamReader inpsr = new InputStreamReader(fs);
			BufferedReader br       = new BufferedReader(inpsr);
			Iterator<String> lsit   = br.lines().iterator();

			while(lsit.hasNext())
			{
				data.add(lsit.next());
			}
			br.close();
			
		} catch (Exception e)
		{
			System.out.println("QActor  ERROR " + e.getMessage());
		}
			
		for(int i=0; i<data.size(); i++)
		{
			if(i == 0)
			{
				m = Map.createMapFromPrologRep(data.get(i));
			}
			else
			{
				String s[] = data.get(i).split(" ");
				m.addElementFromString(s[1]);
			}
		}
		this.map = m;
		
		println(map.toString());
		
		((RobotGUI)env).setMap(m);
		((RobotGUI)env).setCurrentPosition(startY, startX, "N");
	}
	
	public void updateState(int x, int y, String direction)
	{
		((RobotGUI) env).setCurrentPosition(y, x, direction.toUpperCase());
	}
	
	


	@Override
	public void execAction(String cmd) {
		
		String[] command = cmd.split(" ");		
		String[] params;
		switch (command[0]){
		
		case "WORLDCHANGED":
			
			params = command[1].split(",");
			emit( "updateSimulation", "updateSimulation(position("+params[0]+","+params[1]+"))");
			break;
		}
		
	}


	@Override
	public void execAction() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void execAction(IIntent input) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public String execActionWithAnswer(String cmd) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
