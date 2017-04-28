package it.unibo.main;

import java.awt.FileDialog;
import java.awt.Frame;
import java.util.ArrayList;
import java.util.Map;

import it.unibo.domain.model.Action;
import it.unibo.domain.model.Fact;
import it.unibo.domain.model.Graph;
import it.unibo.domain.model.Move;
import it.unibo.domain.model.Node;
import it.unibo.domain.model.State;
import it.unibo.model.interfaces.IMap;
import it.unibo.planning.Order;
import it.unibo.planning.Plan;
import it.unibo.planning.Planner;
import it.unibo.utility.HeuristicEvaluator;
import it.unibo.utility.MapFileManager;

public class main{

	public static void main(String[] args) {
/*
		Graph simple = new Graph();
		
		simple.addNode(new Node("a",1));
		simple.addNode(new Node("b",1));
		simple.addNode(new Node("c",1));
		simple.addNode(new Node("d",1));
		
		simple.addConnection("a", "b");
		simple.addConnection("b", "a");
		
		simple.addConnection("d", "c");
		simple.addConnection("c", "d");
		
		simple.addConnection("b", "d");
		simple.addConnection("d", "b");
		
		//*************************************************
		
		Move m1 = new Move("a", "b");
		
		Fact f = new Fact("at", m1);
		
		f.addParam("a");
		m1.addPre(f);
		
		f = new Fact("connected", m1);
		f.addParam("a");
		f.addParam("b");
		m1.addPre(f);
		
		f = new Fact("at", m1);
		f.addParam("b");
		m1.addEffect(f);
		
		f = new Fact("not at", m1);
		f.addParam("a");
		m1.addEffect(f);
		simple.addMove(m1);

//**********************************************************************
		
		m1 = new Move("d", "c");
		
		f = new Fact("at", m1);
		
		f.addParam("d");
		m1.addPre(f);
		
		f = new Fact("connected", m1);
		f.addParam("d");
		f.addParam("c");
		m1.addPre(f);
		
		f = new Fact("at", m1);
		f.addParam("c");
		m1.addEffect(f);
		
		f = new Fact("not at", m1);
		f.addParam("d");
		m1.addEffect(f);
		simple.addMove(m1);		
		
//***************************************************************
		
		m1 = new Move("b", "a");
		
		f = new Fact("at", m1);
		
		f.addParam("b");
		m1.addPre(f);
		
		f = new Fact("connected", m1);
		f.addParam("b");
		f.addParam("a");
		m1.addPre(f);
		
		f = new Fact("at", m1);
		f.addParam("a");
		m1.addEffect(f);
		
		f = new Fact("not at", m1);
		f.addParam("b");
		m1.addEffect(f);
		simple.addMove(m1);

//************************************************************		

		m1 = new Move("c", "d");
		
		f = new Fact("at", m1);
		
		f.addParam("c");
		m1.addPre(f);
		
		f = new Fact("connected", m1);
		f.addParam("c");
		f.addParam("d");
		m1.addPre(f);
		
		f = new Fact("at", m1);
		f.addParam("d");
		m1.addEffect(f);
		
		f = new Fact("not at", m1);
		f.addParam("c");
		m1.addEffect(f);
		simple.addMove(m1);	

//**************************************************************		
		
		m1 = new Move("b", "d");
		
		f = new Fact("at", m1);
		
		f.addParam("b");
		m1.addPre(f);
		
		f = new Fact("connected", m1);
		f.addParam("b");
		f.addParam("d");
		m1.addPre(f);
		
		f = new Fact("at", m1);
		f.addParam("d");
		m1.addEffect(f);
		
		f = new Fact("not at", m1);
		f.addParam("b");
		m1.addEffect(f);
		simple.addMove(m1);
		
//**************************************************************		
		
		m1 = new Move("d", "b");
		
		f = new Fact("at", m1);
		
		f.addParam("d");
		m1.addPre(f);
		
		f = new Fact("connected", m1);
		f.addParam("d");
		f.addParam("b");
		m1.addPre(f);
		
		f = new Fact("at", m1);
		f.addParam("b");
		m1.addEffect(f);
		
		f = new Fact("not at", m1);
		f.addParam("d");
		m1.addEffect(f);
		simple.addMove(m1);

		
		System.out.println(simple.toString());
*/
		MapFileManager loader = new MapFileManager();
		
		Graph simple = loader.loadGraph();
		IMap map = loader.loadMap();
		
		HeuristicEvaluator heuristic = new HeuristicEvaluator(map);
		
		Planner planner = new Planner(new State(17,19), new State(30,28), simple, heuristic);
		
		long starttime = System.currentTimeMillis();
		
		Plan plan = planner.findPlan();
		
		System.out.println("SEARCH TIME -->>> "+(System.currentTimeMillis()-starttime)+"\n\n");
		
		String p = "STEPS------------\n";
		
		for(Action a : plan.getSteps())
			p += a.toString() + "\n";
		
		p += "\nORDER-------------\n";
		
		for(Order o : plan.getOrders())
			p += o.toString() + "\n";
		
		System.out.println("plan----------\n\n"+p);
		
		
		System.out.println("********** ORDERED ACTIONS *******");
		ArrayList<Action> ordered = plan.numbering();
		
		for(Action a : ordered)
		{
			System.out.println(a.toString());
		}
		
	}

}
