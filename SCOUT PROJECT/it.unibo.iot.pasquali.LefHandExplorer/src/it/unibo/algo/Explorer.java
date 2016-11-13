package it.unibo.algo;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import it.unibo.domain.Map;
import it.unibo.domain.Move;
import it.unibo.domain.State;
import it.unibo.engine.Engine;
import it.unibo.planning.astar.algo.AStarSearchAgent;
import it.unibo.planning.astar.algo.Path;
import it.unibo.planning.astar.engine.AStarEngine;
import it.unibo.planning.astar.enums.PositionMove;

public class Explorer {

	private boolean explore;
	private Engine engine;

	public void startExploration(Point start, Map map) {

		Engine engine = new Engine(start, map);

	}

	private void findLeftWall() {
		if (!explore)
			endOfExploration();

		if (engine.checkObjFront()) {
			engine.turnRight();
			followWall();
		}

		if (!engine.checkObjLeft()) {
			do {
				engine.moveForward();

				if (engine.checkObjFront()) {
					engine.turnRight();
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
		
		do{
			
			if(!engine.checkObjFront())
			{
				engine.moveForward();
				
				if(engine.currentAlreadyVisited())
				{
					if(engine.moreToExplore())
					{
						Point n = findNearestNotExploredCell();
						
						ArrayList<Move> path = computeBestPath(n);;
						
						engine.travel(path);
						
						findLeftWall();
					}
					else
						explore = false;
				}
			}
			else
			{
				if(!engine.checkObjLeft() && !engine.checkExploredLeft())
				{
					engine.turnRight();
				}
				else
					engine.turnLeft();
			}			
		}while(engine.checkObjLeft());
		
		if(explore)
		{
			engine.turnLeft();
			findLeftWall();
		}
		
	}

	private ArrayList<Move> computeBestPath(Point goal) {

		AStarSearchAgent agent = new AStarSearchAgent();
		
		long st = System.currentTimeMillis();
		
		AStarEngine ae = new AStarEngine();		
		
		Map m = engine.getCurrentMap();
		m.fillUnexploredCell();
		
		ae.setIntMap(m.getIntMap(), m.getWidth(), m.getHeight());
		
		System.out.println("LET'S FIND BEST PATH");
		
		State current = engine.getCurrent();
		
		Path path = agent.searchBestPath(ae, current.getCoordinates(),
				PositionMove.valueOf(current.getDirection().toString()),goal);		
		
		System.out.println("Search Time --> " + (System.currentTimeMillis() - st) +" ms");
		
		List<it.unibo.planning.astar.domain.Move> moves = path.getMoves();		
		ArrayList<Move> myMoves = new ArrayList<Move>();
		
		for(it.unibo.planning.astar.domain.Move mm : moves)
		{
			myMoves.add(new Move(mm));
		}
		
		return myMoves;
	}

	private Point findNearestNotExploredCell() {
		ArrayList<State> neighbours = engine.getUnexploredNeighbours();
		Collections.sort(neighbours);
		
		return neighbours.get(0).getCoordinates();
	}

	
	private void endOfExploration() {
		System.out.println("Si cazzo ce l'abbiamo fatta");
	}

}
