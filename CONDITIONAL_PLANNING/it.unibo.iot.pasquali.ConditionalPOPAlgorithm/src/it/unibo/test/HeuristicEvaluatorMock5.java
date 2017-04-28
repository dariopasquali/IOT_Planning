package it.unibo.test;

import java.awt.Point;
import java.util.HashMap;

import it.unibo.domain.graph.State;
import it.unibo.model.interfaces.IMap;
import it.unibo.utils.HeuristicEvaluator;

public class HeuristicEvaluatorMock5 extends HeuristicEvaluator{

	private HashMap<String, Double> values = new HashMap<String, Double>();
	
	
	public HeuristicEvaluatorMock5() {
		super(null);
		
		values.put("AA", 0D);
		values.put("BB", 0D);
		values.put("CC", 0D);
		values.put("DD", 0D);
		values.put("EE", 0D);
		

		values.put("AB", 1D);
		values.put("AC", 2D);
		values.put("AD", 2D);
		values.put("AE", 1D);
		
		values.put("BA", 1D);
		values.put("BC", 2D);
		values.put("BD", 2D);
		values.put("BE", 1D);
		
		values.put("DA", 2D);
		values.put("DB", 2D);
		values.put("DC", 1D);
		values.put("DE", 1D);
		
		values.put("CA", 2D);
		values.put("CB", 2D);
		values.put("CD", 1D);
		values.put("CE", 1D);
		
		values.put("EA", 1D);
		values.put("EB", 1D);
		values.put("EC", 1D);
		values.put("ED", 1D);
		
	}
	
	@Override
	public double evaluate(State from, State to)
	{
		AbcState s1 = (AbcState) from;
		AbcState s2 = (AbcState) to;
		
		return values.get(s1.getName()+s2.getName());
	}

}
