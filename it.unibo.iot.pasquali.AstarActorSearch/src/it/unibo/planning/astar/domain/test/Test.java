package it.unibo.planning.astar.domain.test;

import java.util.ArrayList;
import java.util.TreeMap;

import it.unibo.planning.astar.domain.State;
import it.unibo.planning.astar.enums.Direction;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		State est = new State(0,0,Direction.EAST, null, 0,5);
		State west = new State(0,0,Direction.WEST, null, 0,5);
		
		System.out.println("hashcode est? "+(est.hashCode()));
		System.out.println("hashcode west? "+(west.hashCode()));
		
		System.out.println("== ? "+(est==west));
		System.out.println("equals ? "+(est.equals(west)));
		
		TreeMap<State, Double> map = new TreeMap<State,Double>();		
		map.put(est, est.getF());
		
		System.out.println("contains key maptree? "+(map.containsKey(west)));
		
		ArrayList<State> list = new ArrayList<State>();		
		list.add(est);
		
		System.out.println("contains list? "+(list.contains(west)));
	}

}
