package it.unibo.planning;

import java.util.ArrayList;
import java.util.List;

import it.unibo.domain.model.Action;
import it.unibo.domain.model.Fact;

public class Plan {
	
	private List<Action> steps;
	private List<Fact> openGoals;
	private List<CausalLink> links;
	private List<Order> orders;
	

	public Plan()
	{
		this.steps = new ArrayList<>();
		this.openGoals = new ArrayList<>();
		this.links = new ArrayList<>();
		this.orders = new ArrayList<>();
	}

	public Plan(Plan plan) {
		this.steps = new ArrayList<>(plan.getSteps());
		this.openGoals = new ArrayList<>(plan.getOpenGoals());
		this.links = new ArrayList<>(plan.getLinks());
		this.orders = new ArrayList<>(plan.getOrders());
	}

	public List<Action> getSteps() {
		return steps;
	}

	public List<Fact> getOpenGoals() {
		return openGoals;
	}

	public List<CausalLink> getLinks() {
		return links;
	}

	public List<Order> getOrders() {
		return orders;
	}
	
	public void addStep(Action step){
		steps.add(step);
		openGoals.addAll(0, step.getPre());
	}
	
	public void addGoal(Fact f){
		openGoals.add(f);
	}
	
	public void addCausalLink(CausalLink l){
		if(!links.contains(l))
			links.add(l);
	}
	
	public void addOrderConstraint(Order o){
		if(!orders.contains(o))
			orders.add(o);
	}

	public void init(Action start, Action stop) {
		
		steps.add(start);
		steps.add(stop);
		
		orders.add(new Order(start, stop));
		
		openGoals.addAll(stop.getPre());		
	}

	public boolean terminationTest() {
		return openGoals.isEmpty();
	}

	public Fact selectSubgoalToSolve() {
		return openGoals.remove(0);
	}

	public void addSubgoal(Fact subGoal) {
		openGoals.add(0, subGoal);		
	}
	
	public ArrayList<Action> numbering(){
		
		
		ArrayList<Action> ordered = new ArrayList<Action>();		
		ArrayList<PlanNode> nodes = new ArrayList<PlanNode>();
		
		for(Action a : steps)
		{
			PlanNode node = new PlanNode(a);
			
			for(Order o : orders)
			{
				if(o.getBefore().equals(a))
					node.next.add(o.getAfter());
				
				if(o.getAfter().equals(a))
					node.pre.add(o.getBefore());
			}
			
			nodes.add(node);
		}
		
		while(true)
		{
			PlanNode noPre = null;
			
			for(PlanNode n : nodes)
			{
				if(n.pre.isEmpty())
				{
					noPre = n;
					nodes.remove(n);
					break;
				}
			}
			
			if(noPre == null)
				break;
			
			ordered.add(noPre.action);
			
			for(PlanNode n : nodes)
			{
				if(n.pre.contains(noPre.action))
					n.pre.remove(noPre.action);
			}			
		}
		
		return ordered;
	}
	

}
