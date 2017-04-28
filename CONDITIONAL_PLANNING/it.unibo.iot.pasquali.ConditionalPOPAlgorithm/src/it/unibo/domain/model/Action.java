package it.unibo.domain.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import it.unibo.domain.model.conditional.ConditionalAction;

public class Action implements Serializable, Comparable<Action>{

	protected String name;
	protected List<Fact> pre;
	protected List<Fact> eff;
	
	protected double heuristic;
	
	public Action(String name)
	{
		this.name = name;
		this.pre = new ArrayList<Fact>();
		this.eff = new ArrayList<Fact>();
		this.heuristic = -1;
	}
	
	public Action(String name, double heuristic)
	{
		this.name = name;
		this.pre = new ArrayList<Fact>();
		this.eff = new ArrayList<Fact>();
		this.heuristic = heuristic;
	}
	
	public void setHeuristic(double heuristic)
	{
		this.heuristic = heuristic;
	}
	
	public double getHeuristic(){
		return heuristic;
	}
	
	public double getDistance(){
		return heuristic;
	}

	public String getName() {
		return name;
	}

	public List<Fact> getPre() {
		return pre;
	}

	public List<Fact> getEff() {
		return eff;
	}

	public void addPre(Fact p)
	{
		if(!pre.contains(p))
			pre.add(p);
	}
	
	public void addEffect(Fact e)
	{
		if(!eff.contains(e))
			eff.add(e);
	}
	
	public boolean containsPre(Fact p){
		return pre.contains(p);
	}
	
	public boolean containsEff(Fact e){
		return eff.contains(e);
	}
	
	@Override
	public String toString(){
		return name;
	}

	@Override
	public int compareTo(Action a) {
		
		double distance = heuristic;
		
		if(distance > a.getDistance())
			return 1;
		
		if(distance == a.getDistance())
			return 0;
		
		return -1;
	}
	
	@Override
	public boolean equals(Object o){
		
		if(!(o instanceof Action))
			return false;
		
		return ((Action)o).getName().equals(name);		
	}
	
}
