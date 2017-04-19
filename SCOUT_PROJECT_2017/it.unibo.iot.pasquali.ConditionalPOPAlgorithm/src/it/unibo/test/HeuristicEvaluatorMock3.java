package it.unibo.test;

import java.awt.Point;

import it.unibo.domain.graph.State;
import it.unibo.model.interfaces.IMap;
import it.unibo.planning.astar.algo.Path;
import it.unibo.utils.HeuristicEvaluator;

public class HeuristicEvaluatorMock3 extends HeuristicEvaluator{

	public HeuristicEvaluatorMock3() {
		super(null);
		
		//evaluator for the 3 node simple map
		/*
		 * A -------- B
		 * \		 /
		 * 	--- C ---
		 * A = 0,0
		 * B = 0,1
		 * C = 1,0
		 */		
	}
	
	@Override
	public double evaluate(State from, State to)
	{
		return 1D;
	}

}
