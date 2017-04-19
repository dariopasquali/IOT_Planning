package it.unibo.main;

import java.util.ArrayList;

import it.unibo.domain.graph.Graph;
import it.unibo.domain.graph.State;
import it.unibo.domain.model.Action;
import it.unibo.domain.model.conditional.Check;
import it.unibo.domain.model.conditional.ConditionalAction;
import it.unibo.domain.model.conditional.ConditionalLabel;
import it.unibo.domain.model.conditional.Move;
import it.unibo.model.interfaces.IMap;
import it.unibo.planning.ConditionalPlanNode;
import it.unibo.planning.ConditioningLink;
import it.unibo.planning.Order;
import it.unibo.planning.Plan;
import it.unibo.planning.algo.Planner;
import it.unibo.test.AbcState;
import it.unibo.test.HeuristicEvaluatorMock3;
import it.unibo.test.HeuristicEvaluatorMock4;
import it.unibo.test.HeuristicEvaluatorMock5;
import it.unibo.utils.HeuristicEvaluator;
import it.unibo.utils.MapFileManager;

public class Main{

	public static void main(String[] args) {

		MapFileManager loader = new MapFileManager();
		
		Graph simple = loader.loadGraph();
		
		//Graph simple = init4NodeGraph();
		
		IMap map = loader.loadMap();
		
		HeuristicEvaluator heuristic = new HeuristicEvaluator(map);
		
		//HeuristicEvaluator heuristic = new HeuristicEvaluatorMock4();
		
		Planner planner = new Planner(new State(1,1), new State(6,6), simple, heuristic);
		
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
		
		for(ConditionalAction a : plan.getSteps())
		{
			p += a.getShortName() + " ";
			for(ConditionalLabel c : a.getContext())
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
			System.out.println(a.toString()+ " --- " + a.getAction().getContext().toString()+"\n");
		}
		
	}

	private static Graph init3NodeGraph() {
		
		Graph g = new Graph();
		
		g.addState(new AbcState("A"));
		g.addState(new AbcState("B"));
		g.addState(new AbcState("C"));
		
		g.addMove(new Move(new AbcState("A"), new AbcState("B")));
		g.addMove(new Move(new AbcState("B"), new AbcState("A")));
		
		g.addMove(new Move(new AbcState("A"), new AbcState("C")));
		g.addMove(new Move(new AbcState("C"), new AbcState("A")));
		
		g.addMove(new Move(new AbcState("B"), new AbcState("C")));
		g.addMove(new Move(new AbcState("C"), new AbcState("B")));
		

		
		g.addCheck(new Check(new AbcState("A"), new AbcState("B")));
		g.addCheck(new Check(new AbcState("B"), new AbcState("A")));
		
		g.addCheck(new Check(new AbcState("A"), new AbcState("C")));
		g.addCheck(new Check(new AbcState("C"), new AbcState("A")));
		
		g.addCheck(new Check(new AbcState("B"), new AbcState("C")));
		g.addCheck(new Check(new AbcState("C"), new AbcState("B")));
		
		return g;		
	}
	
	private static Graph init4NodeGraph() {
		
		Graph g = new Graph();
		
		g.addState(new AbcState("A"));
		g.addState(new AbcState("B"));
		g.addState(new AbcState("C"));
		g.addState(new AbcState("D"));
		
		g.addMove(new Move(new AbcState("A"), new AbcState("B")));
		g.addMove(new Move(new AbcState("B"), new AbcState("A")));
		
		g.addMove(new Move(new AbcState("A"), new AbcState("D")));
		g.addMove(new Move(new AbcState("D"), new AbcState("A")));
		
		g.addMove(new Move(new AbcState("B"), new AbcState("C")));
		g.addMove(new Move(new AbcState("C"), new AbcState("B")));
		
		g.addMove(new Move(new AbcState("B"), new AbcState("D")));
		g.addMove(new Move(new AbcState("D"), new AbcState("B")));
		
		g.addMove(new Move(new AbcState("D"), new AbcState("C")));
		g.addMove(new Move(new AbcState("C"), new AbcState("D")));
		

		
		g.addCheck(new Check(new AbcState("A"), new AbcState("B")));
		g.addCheck(new Check(new AbcState("B"), new AbcState("A")));
		
		g.addCheck(new Check(new AbcState("A"), new AbcState("D")));
		g.addCheck(new Check(new AbcState("D"), new AbcState("A")));
		
		g.addCheck(new Check(new AbcState("B"), new AbcState("C")));
		g.addCheck(new Check(new AbcState("C"), new AbcState("B")));
		
		g.addCheck(new Check(new AbcState("B"), new AbcState("D")));
		g.addCheck(new Check(new AbcState("D"), new AbcState("B")));
		
		g.addCheck(new Check(new AbcState("D"), new AbcState("C")));
		g.addCheck(new Check(new AbcState("C"), new AbcState("D")));
		
		return g;		
	}

	
	private static Graph init5NodeGraph() {
		
		Graph g = new Graph();
		
		g.addState(new AbcState("A"));
		g.addState(new AbcState("B"));
		g.addState(new AbcState("C"));
		g.addState(new AbcState("D"));
		g.addState(new AbcState("E"));
		
		g.addMove(new Move(new AbcState("A"), new AbcState("B")));
		g.addMove(new Move(new AbcState("B"), new AbcState("A")));
		
		g.addMove(new Move(new AbcState("A"), new AbcState("E")));
		g.addMove(new Move(new AbcState("E"), new AbcState("A")));
		
		g.addMove(new Move(new AbcState("B"), new AbcState("E")));
		g.addMove(new Move(new AbcState("E"), new AbcState("B")));
		
		g.addMove(new Move(new AbcState("E"), new AbcState("D")));
		g.addMove(new Move(new AbcState("D"), new AbcState("E")));
		
		g.addMove(new Move(new AbcState("D"), new AbcState("C")));
		g.addMove(new Move(new AbcState("C"), new AbcState("D")));
		
		g.addMove(new Move(new AbcState("E"), new AbcState("C")));
		g.addMove(new Move(new AbcState("C"), new AbcState("E")));
		

		
		g.addCheck(new Check(new AbcState("A"), new AbcState("B")));
		g.addCheck(new Check(new AbcState("B"), new AbcState("A")));
		
		g.addCheck(new Check(new AbcState("A"), new AbcState("E")));
		g.addCheck(new Check(new AbcState("E"), new AbcState("A")));
		
		g.addCheck(new Check(new AbcState("B"), new AbcState("E")));
		g.addCheck(new Check(new AbcState("E"), new AbcState("B")));
		
		g.addCheck(new Check(new AbcState("E"), new AbcState("D")));
		g.addCheck(new Check(new AbcState("D"), new AbcState("E")));
		
		g.addCheck(new Check(new AbcState("D"), new AbcState("C")));
		g.addCheck(new Check(new AbcState("C"), new AbcState("D")));
		
		g.addCheck(new Check(new AbcState("E"), new AbcState("C")));
		g.addCheck(new Check(new AbcState("C"), new AbcState("E")));
		
		return g;		
	}
	
}
