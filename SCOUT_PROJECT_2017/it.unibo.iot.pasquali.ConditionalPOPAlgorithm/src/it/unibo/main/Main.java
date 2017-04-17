package it.unibo.main;

import java.util.ArrayList;

import it.unibo.domain.graph.Graph;
import it.unibo.domain.graph.State;
import it.unibo.domain.model.Action;
import it.unibo.domain.model.conditional.ConditionalAction;
import it.unibo.domain.model.conditional.ConditionalLabel;
import it.unibo.model.interfaces.IMap;
import it.unibo.planning.ConditionalPlanNode;
import it.unibo.planning.ConditioningLink;
import it.unibo.planning.Order;
import it.unibo.planning.Plan;
import it.unibo.planning.algo.Planner;
import it.unibo.utils.HeuristicEvaluator;
import it.unibo.utils.MapFileManager;

public class Main{

	public static void main(String[] args) {

		MapFileManager loader = new MapFileManager();
		
		Graph simple = loader.loadGraph();
		IMap map = loader.loadMap();
		
		HeuristicEvaluator heuristic = new HeuristicEvaluator(map);
		
		Planner planner = new Planner(new State(1,1), new State(3,3), simple, heuristic);
		
		long starttime = System.currentTimeMillis();
		
		Plan plan = null;
		try {
			plan = planner.findPlan();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("SEARCH TIME -->>> "+(System.currentTimeMillis()-starttime)+"\n\n");
		
		String p = "STEPS------------\n";
		
		for(Action a : plan.getSteps())
		{
			p += a.toString() + " ";
			for(ConditionalLabel c : ((ConditionalAction) a).getContext())
				p += c.toString()+" ; ";
			
			p+="\n";
		}
		
		p += "\nORDER-------------\n";
		
		for(Order o : plan.getOrders())
			p += o.toString() + "\n";
		
		p += "\nCONDITION-------------\n";
		
		for(ConditioningLink o : plan.getConditioningLinks())
			p += o.toString() + "\n";
		
		System.out.println("plan----------\n\n"+p);
		
		
		System.out.println("********** ORDERED ACTIONS *******");
		ArrayList<ConditionalPlanNode> conditionalPlan = plan.numbering();
		
		for(ConditionalPlanNode a : conditionalPlan)
		{
			System.out.println(a.toString());
		}
		
	}

}
