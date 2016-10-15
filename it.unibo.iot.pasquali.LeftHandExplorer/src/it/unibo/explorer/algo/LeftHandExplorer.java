package it.unibo.explorer.algo;

import java.util.ArrayList;

import it.unibo.explorer.domain.State;
import it.unibo.explorer.gui.MapViewerPanel;
import it.unibo.explorer.interfaces.IExplorerEngine;
import it.unibo.planning.astar.enums.Direction;

public class LeftHandExplorer {

	private MapViewerPanel mapViewer;
	private ExplorerEngine engine;
	
	private Direction dir;
	private State state;
	
	private ArrayList<State> clears;
	private ArrayList<State> obstacles;
	
	public LeftHandExplorer(MapViewerPanel mapViewer, IExplorerEngine engine) {

		this.mapViewer = mapViewer;
		this.engine = (ExplorerEngine) engine;
		this.clears = new ArrayList<State>();
		this.obstacles = new ArrayList<State>();
	}
		
	public void explore(int startX, int startY)
	{
		state = new State(startX, startY);
		this.dir = Direction.NORTH;
		

		
		
		
	}
	
	private void findLeftWall()
	{
		if(!engine.CheckObjectLeft(state, dir))
		{
			clears.add(engine.moveLeft(state, dir));
			
			dir = engine.turnLeft(dir);
			while(!engine.CheckObjectForward(state, dir) && !engine.CheckObjectLeft(state, dir))
			{
				clears.add(engine.moveLeft(state, dir));
				state = engine.moveForward(state, dir);
				clears.add(state);
			}
			
			if()
		}
	}

	
}
