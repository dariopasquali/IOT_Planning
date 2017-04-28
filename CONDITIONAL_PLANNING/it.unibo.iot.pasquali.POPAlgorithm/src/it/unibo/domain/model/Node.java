package it.unibo.domain.model;

import java.io.Serializable;

public class Node implements Serializable{
	
	private String name;
	private double heuristic;
	
	
	public Node(String name, double heuristic) {
		super();
		this.name = name;
		this.heuristic = heuristic;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public double getHeuristic() {
		return heuristic;
	}


	public void setHeuristic(double heuristic) {
		this.heuristic = heuristic;
	}
	
	@Override
	public boolean equals(Object o){
		
		if(!(o instanceof Node))
			return false;
		
		Node n = (Node)o;
		return n.toString().equals(this.toString());
	}
	
	
	@Override
	public String toString(){
		return "node("+name+").";
	}
	

}
