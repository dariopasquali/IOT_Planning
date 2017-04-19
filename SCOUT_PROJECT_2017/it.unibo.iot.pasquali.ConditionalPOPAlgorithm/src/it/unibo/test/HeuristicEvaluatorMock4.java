package it.unibo.test;

import java.awt.Point;
import java.util.HashMap;

import it.unibo.domain.graph.State;
import it.unibo.model.interfaces.IMap;
import it.unibo.planning.astar.algo.Path;
import it.unibo.utils.HeuristicEvaluator;

public class HeuristicEvaluatorMock4 extends HeuristicEvaluator{

	private HashMap<String, Double> values = new HashMap<String, Double>();
	
	
	public HeuristicEvaluatorMock4() {
		super(null);
		
		values.put("AA", 0D);
		values.put("BB", 0D);
		values.put("CC", 0D);
		values.put("DD", 0D);
		

		values.put("AB", 1D);
		values.put("AC", 2D);
		values.put("AD", 1D);
		
		values.put("BA", 1D);
		values.put("BC", 1D);
		values.put("BD", 1D);
		
		values.put("DA", 1D);
		values.put("DB", 1D);
		values.put("DC", 1D);
		
		values.put("CA", 2D);
		values.put("CB", 1D);
		values.put("CD", 1D);	
		
	}
	
	@Override
	public double evaluate(State from, State to)
	{
		AbcState s1 = (AbcState) from;
		AbcState s2 = (AbcState) to;
		
		return values.get(s1.getName()+s2.getName());
	}

}
