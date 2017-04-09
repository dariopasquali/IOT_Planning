/* Generated by AN DISI Unibo */ 
/*
This code is generated only ONCE
*/
package it.unibo.console;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import it.unibo.gui.ConsoleGUI;
import it.unibo.gui.enums.CellState;
import it.unibo.is.interfaces.IActivity;
import it.unibo.is.interfaces.IIntent;
import it.unibo.is.interfaces.IOutputEnvView;
import it.unibo.model.interfaces.IGUI;
import it.unibo.model.interfaces.IMap;
import it.unibo.model.map.Map;
import it.unibo.planning.astar.algo.AStarSearchAgent;
import it.unibo.planning.astar.engine.AStarEngine;
import it.unibo.qactors.QActorContext;
import it.unibo.qactors.QActorUtils;

public class Console extends AbstractConsole implements IActivity{ 
	
	
	private IMap map;
	private it.unibo.planning.astar.algo.Path path;
	private static ConsoleGUI env = new ConsoleGUI();
	private int sx, sy, gx, gy;
	private String filename;
	private String robotMode;
	
	public Console(String actorId, QActorContext myCtx, IOutputEnvView outEnvView )  throws Exception
	{
		super(actorId, myCtx, env);
		((IGUI) env).setController(this);
		((ConsoleGUI) env).setVisible(true);
		path = null;
	}
	
	
//{{ EXPLORATION METHODS ------------------------------------------------
	
		public void showClearMap()
		{
			//TODO
		}
		
		
		/**
		 * Show a clear map defined by boundaries
		 * 
		 * @param xmax  max width
		 * @param ymax  max height
		 */
		public void showClearMap(int xmax, int ymax)
		{
			map = new Map(ymax, xmax);
			((ConsoleGUI)env).clearCurrentExplorationMap();
		}
		
		
		/**
		 * Set the state of a cell of the map, used during Exploration.
		 * 
		 * @param x  coordinate
		 * @param y  coordinate
		 * @param state  String representation of the map cell state.
		 * </br> String representation because the method is used by Prolog Engine. 
		 */
		public void updateMap(int x, int y, String state)
		{
			if(x<0 || y<0)
				return;
			
			map.setCell(y, x, Map.fromStringToState(state));
			((ConsoleGUI) env).setCellState(y, x, CellState.fromString(state));
		}
		
//}}

		
//{{ NAVIGATION METHODS -------------------------------------------------
		
	/**
	 * Used when the Robot hit an unexpected static object during Navigation.
	 * </br>Find the best path from the current state and the previous GOAL
	 * 
	 * @param cx  x coordinate of current state
	 * @param cy  y coordinate of current state
	 */
	public void searchNewBestPath(int cx, int cy)
	{
		println("search another path");
		
		searchBestPath(cx, cy, gx, gy);
	}
		
	/**
	 * Find the best path from a START position to a GOAL position.
	 * 
	 * @param sx  x coordinate of START state
	 * @param sy  y coordinate of START state
	 * @param gx  x coordinate of GOAL state
	 * @param gy  y coordinate of GOAL state
	 */
	public void searchBestPath(int sx, int sy, int gx, int gy)
	{		
		this.sx = sx;
		this.sy = sy;
		this.gx = gx;
		this.gy = gy;
			
		AStarSearchAgent agent = new AStarSearchAgent();
			
		long st = System.currentTimeMillis();
			
		AStarEngine engine = new AStarEngine();		
			
		engine.setIntMap(map.getIntMap(), map.getYmax(), map.getXmax());
			
		println("LET'S FIND BEST PATH");
			
		path = agent.searchBestPath(engine,new Point(sx,sy), new Point(gx,gy));		
			
		println("Search Time --> " + (System.currentTimeMillis() - st) +" ms");
	}

	
	/**
	 * Show the path found by the {@code searchBestPath} method on the GUI
	 */
	public void showPathOnGui()
	{
		((IGUI)env).setPath(path.getPoints());
	}

	
	/**
	 * @return  the plan as a prolog list of moves
	 */
	private String getPrologPlan()
	{
		String moves = "plan([";
		for(int i=0; i<path.getMoves().size(); i++)
		{
			moves += path.getMoves().get(i).toString();
			if(i!=path.getMoves().size()-1)
				moves+=",";
		}
		moves+="])";
		//println(moves);
		return moves;
	}

	
	/**
	 * @return  the START position in a prolog representation
	 */
	private String getPrologPosition()
	{
		return "position( "+sx+", "+sy+" )";		
	}

	
	/**
	 * Send plan, START position and, eventually filename, to the Robot
	 */
	public void sendNavigationData(){
		sendNavigationData(robotMode);
	}
		
	/**
	 * Send plan, START position and, eventually filename, to the Robot
	 * 
	 * @param mode  simulated or real Robot
	 */
	public void sendNavigationData(String mode)
	{	
		String pp = getPrologPlan();
		String po = getPrologPosition();
		this.robotMode = mode;
			
		println(pp);
		println(po);
		println(mode);
			
		if(mode.equals("robot"))
		{
			temporaryStr = QActorUtils.unifyMsgContent(pengine, "navigate(PLAN,POS)","navigate("+pp+","+po+")", guardVars ).toString();
			println("temp string "+temporaryStr);
			try
			{
				sendMsg("navigate","robot", QActorContext.dispatch, temporaryStr );
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		else
		{
			temporaryStr = QActorUtils.unifyMsgContent(pengine, "navigatefile(PLAN,POS,FILENAME)","navigatefile("+pp+","+po+",\""+filename+"\")", guardVars ).toString();
			println("temp string "+temporaryStr);
			try
			{
				sendMsg("navigatefile","robot", QActorContext.dispatch, temporaryStr );
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		}
			
				
	}

//}}	
	
	
//{{ GUI INTERACTION ---------------------------------------------------
	
	/**
	 * Load the map from the Prolog file and show it on GUI as a Button Matrix.
	 * 
	 * @param path  absolute filepath of the map
	 */
	public void loadMapButton(String path)
	{	
		Map m = null;
		List<String> data = new ArrayList<String>();						
		try
		{
			InputStream fs = new FileInputStream(path);
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
			
		
		((ConsoleGUI)env).setMap(m);
		this.filename = path; 
			
	}
	
	
	
	/**
	 *  Clear all the GUI, used before Exploration.
	 */
	public void myClearGUI()
	{
		((ConsoleGUI)env).clearGUI();
	}
		
	
	/**
	 * Clear only the path cell.
	 */
	public void myClearPath()
	{
		((ConsoleGUI)env).clearPath();
	}

//}}
	
	/* (non-Javadoc)
	 * @see it.unibo.is.interfaces.IActivityBase#execAction(java.lang.String)
	 */
	@Override
	public void execAction(String cmd) {
		
		String[] command = cmd.split(" ");		
		String[] params;
		switch (command[0]){
			
		case "EXPLORE":
			emit( "local_gui_command", "local_gui_command(explore))");
			break;
			
		case "EXPLOREDEBUG":
				
			params = command[1].split(",");
			
			//System.out.println(filename);
			
			emit( "local_gui_command", "local_gui_command(explore("
						
					+ "\""+filename+"\"" + ","
					
					+ "position(" + params[0] + "," + params[1]+")"
							+ ","
					+ "map(" + params[2] + "," + params[3]+")"
							+ ","
					+ params[4] + "))");
			break;
			
		case "LOAD":
			emit( "local_gui_command", "local_gui_command(loadmap(\""+command[1]+"\"))");
			break;
			
				
		case "NAVIGATE":
			emit( "local_gui_command", "local_gui_command(navigate("+command[1]+"))");
			break;
				
		case "FIND":
			params = command[1].split(",");
			
			emit( "local_gui_command", "local_gui_command(findpath("
						+ "position(" + params[0] + "," + params[1]+")"
								+ ","
						+ "position(" + params[2] + "," + params[3]+")"
								+ ")");
						
			break;
							
		case "ABORT":
			emit( "local_gui_command", "local_gui_command(abort)");
			break;
				
		case "CLEAR":
			emit( "local_gui_command", "local_gui_command(clear)");
			break;
			
		case "CLEARPATH":
			emit( "local_gui_command", "local_gui_command(clearpath)");
			break;
				
		default:
			println("Invalid command");
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
