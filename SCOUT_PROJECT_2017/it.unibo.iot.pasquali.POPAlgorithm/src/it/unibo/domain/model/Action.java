package it.unibo.domain.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Action implements Serializable, Comparable<Action>{

	protected String name;
	protected List<Fact> pre;
	protected List<Fact> eff;
	
	protected double heuristic;
	protected double cost;
	
	public Action(String name)
	{
		this.name = name;
		this.pre = new ArrayList<Fact>();
		this.eff = new ArrayList<Fact>();
		this.heuristic = -1;
		this.cost = -1;
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
	
	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}
	
	public double getDistance(){
		return cost + heuristic;
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
		
		double distance = cost + heuristic;
		
		if(distance > a.getDistance())
			return 1;
		
		if(distance == a.getDistance())
			return 0;
		
		return -1;
	}
	
}
