package it.unibo.algo;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;

import it.unibo.domain.Map;
import it.unibo.domain.State;
import it.unibo.engine.Engine;
import it.unibo.gui.MapViewerPanel;
import it.unibo.planning.astar.algo.AStarSearchAgent;
import it.unibo.planning.astar.algo.Path;
import it.unibo.planning.astar.engine.AStarEngine;
import it.unibo.planning.enums.PositionMove;

public class Explorer {

	private boolean explore;
	private Engine engine;

	public void startExploration(Point start, Map map, MapViewerPanel viewer) {

		engine = new Engine(start, map, viewer);
		explore = true;
		
		engine.setCurrentClear();
		
		findLeftWall();
	}

	private void findLeftWall() {
		if (!explore)
			endOfExploration();

		if (engine.checkObjFront()) {
			engine.turnDoubleRight();
			followWall();
		}

		if (explore && !engine.checkObjLeft()) {
			do {
				engine.moveForward();
				engine.addCurrentToVisited();
				
				if (engine.checkObjFront()) {
					engine.turnDoubleRight();
					break;
				}

				if (engine.checkObjLeft()) {
					break;
				}
			} while (true);
		}

		followWall();

	}

	private void followWall() {
		
		if(!explore)
			return;
		
		do{
			
			if(!engine.checkObjFront())
			{
				engine.moveForward();
				
				if(engine.isCurrentAlreadyVisited())
				{
					Point n = findNearestNotExploredCell();
					
					if(n == null)
					{
						explore = false;
						break;
					}
					
					Path path = computeBestPath(n);;
					
					engine.travel(path);
					
					findLeftWall();
				}
				
				engine.addCurrentToVisited();
			}
			else
			{
				if(!engine.checkObjLeft() && !engine.checkExploredLeft())
				{
					engine.turnDoubleLeft();
				}
				else
					engine.turnDoubleRight();
			}			
		}while(explore && engine.checkObjLeft());
		
		if(explore)
		{
			engine.turnDoubleLeft();
			findLeftWall();
		}
		
	}

	private Path computeBestPath(Point goal) {

		AStarSearchAgent agent = new AStarSearchAgent();
		
		long st = System.currentTimeMillis();
		
		AStarEngine ae = new AStarEngine();		
		
		Map astarMap = new Map(engine.getCurrentMap().getYMax(),
								engine.getCurrentMap().getXMax(),
								engine.getCurrentMap().getIntMap());
		
		astarMap.fillUnexploredCell();
		astarMap.setCellClear(goal.y, goal.x);
		
		System.out.println("SEARCH MAP ---- ");		
		ae.setIntMap(astarMap.getIntMap(), astarMap.getYMax(), astarMap.getXMax());
		
		//System.out.println("LET'S FIND BEST PATH");
		
		State current = engine.getCurrent();
		
		Path path = agent.searchBestPath(ae,
										current.getCoordinates(),
										PositionMove.fromDirection(current.getDirection().toString()),
										goal);		
		
		System.out.println("Search Time --> " + (System.currentTimeMillis() - st) +" ms");
		
		return path;
	}


	private Point findNearestNotExploredCell()
	{
		ArrayList<State> unexplored = engine.getUnexploredNeighbors();
		
		if(unexplored.isEmpty())
			return null;
		
		Collections.sort(unexplored);
		
		return unexplored.get(0).getCoordinates();
	}

	
	private void endOfExploration() {
		System.out.println("Si cazzo ce l'abbiamo fatta");
	}

	@Override
	public String toString()
	{
		return engine.toString();
	}
	
}
