package it.unibo.guimanager.businesslogic;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import it.unibo.gui.RobotGUI;
import it.unibo.guimanager.Guimanager;
import it.unibo.guimanager.interfaces.IGuiManagerBL;
import it.unibo.model.map.Map;

public class RobotGuiBL implements IGuiManagerBL{

	protected Map map;	
	private static RobotGUI gui = new RobotGUI();
	
	private int startX, startY;
	
	private Guimanager actor;
	
	public RobotGuiBL(Guimanager actor){
		
		this.actor = actor;
		((RobotGUI) gui).setController(actor);
		actor.setEnv(gui);
	}
	
	@Override
	public void showMap(int startX, int startY, String filename) {
		
		gui.setVisible(true);
		
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
		
		actor.println(map.toString());
		
		gui.setMap(m);
		this.startX = startX;
		this.startY = startY;
	}

	@Override
	public void createActor(){

		gui.setCurrentPosition(startY, startX, "N");
	}
	
	@Override
	public void updateState(int x, int y, String direction) {

		gui.setCurrentPosition(y, x, direction.toUpperCase());		
	}

}
