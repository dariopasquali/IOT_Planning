package it.unibo.navigation;

import java.util.List;

import it.unibo.domain.graph.Graph;
import it.unibo.domain.graph.State;
import it.unibo.domain.model.conditional.Check;
import it.unibo.domain.model.conditional.Move;
import it.unibo.enums.PlanningMode;
import it.unibo.gui.MapViewer;
import it.unibo.interfaces.IRunnablePopPlan;
import it.unibo.model.map.Map;
import it.unibo.planning.ConditionalPlanNode;
import it.unibo.planning.Plan;
import it.unibo.planning.algo.Planner;
import it.unibo.utils.HeuristicEvaluator;

public class Controller {

	private Map map;
	private PlanningMode planningMode;
	private List<ConditionalPlanNode> plan;
	
	
	
	public void setObstacle(int x, int y) {
		// TODO Auto-generated method stub
		
	}
	
	public void setPlanningMode(PlanningMode mode){
		this.planningMode = mode;
	}

	public void setMap(Map map) {
		if(map != null)
			this.map = map;				
	}

	public List<ConditionalPlanNode> createPlan(State start, State goal) {
		
		Graph graph = null;
		
		if(planningMode.equals(PlanningMode.WITH_OBJECTS))
		{
			graph = getPOPGraph(map.getIntMap(), map.getXmax(), map.getYmax());
		}
		else
		{
			graph = getPOPGraph(map.getNoObjects(), map.getXmax(), map.getYmax());
		}
		
		Planner planner = new Planner(start, goal, graph, new HeuristicEvaluator(map));		
		
		try
		{
			plan = planner.findPlan().numbering();
			return plan;
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		return null;
	}

	public void initNavigation(MapViewer mapViewer) {
		// TODO Auto-generated method stub
		
	}

	public void navigate() {
		// TODO Auto-generated method stub
		
	}

	public boolean nextStep() {
		// TODO Auto-generated method stub
		return false;
	}

	private Graph getPOPGraph(Integer[][] intmap, int xmax, int ymax) {

		String graph = "";
		String moves = "";
		
		Graph g = new Graph();
		
		for(int x=0; x< xmax; x++)
		{
			for(int y=0; y<ymax; y++)
			{
				if(intmap[y][x] == 0)
					g.addState(new State(x,y));
			}
		}				
		
		for(int y=0; y<ymax; y++)
		{
			for(int i=0; i<(xmax-1); i++)
			{
				if(intmap[y][i] == 0 && intmap[y][i+1] == 0)
				{
					State a = new State(i, y);
					State b = new State(i+1, y);
					
					g.addState(a);
					g.addState(b);
					
					Move m = new Move(a, b);										
					g.addMove(m);				
					m = new Move(b, a);					
					g.addMove(m);
					
					Check ch = new Check(a, b);										
					g.addCheck(ch);				
					ch = new Check(b, a);					
					g.addCheck(ch);					
				}
			}
		}
		
		
		for(int x=0; x<xmax; x++)
		{
			for(int j=0; j<(ymax-1); j++)
			{
				if(intmap[j][x] == 0 && intmap[j+1][x] == 0)
				{
					State a = new State(x, j);
					State b = new State(x, j+1);
					
					g.addConnection(a.toString(), b.toString());
					g.addConnection(b.toString(), a.toString());
					
					Move m = new Move(a, b);										
					g.addMove(m);				
					m = new Move(b, a);					
					g.addMove(m);
					
					Check ch = new Check(a, b);										
					g.addCheck(ch);				
					ch = new Check(b, a);					
					g.addCheck(ch);
					
		
				}
			}
		}		
		
		return g;
	}
	
}
