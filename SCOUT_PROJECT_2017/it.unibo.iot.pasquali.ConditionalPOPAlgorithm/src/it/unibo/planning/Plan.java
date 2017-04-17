package it.unibo.planning;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import it.unibo.domain.model.Action;
import it.unibo.domain.model.Fact;
import it.unibo.domain.model.conditional.ConditionalAction;
import it.unibo.domain.model.conditional.ConditionalLabel;
import it.unibo.domain.model.conditional.Goal;

public class Plan implements Serializable{
	
	private List<ConditionalAction> steps;
	
	private List<Fact> openPreconditions;
	private List<Goal> openGoals;
	
	private List<CausalLink> links;
	private List<Order> orders;
	private List<ConditioningLink> conditioningLinks;
	

	public Plan()
	{
		this.steps = new ArrayList<>();
		
		this.openPreconditions = new ArrayList<Fact>();
		this.openGoals = new ArrayList<>();
		
		this.links = new ArrayList<>();
		this.orders = new ArrayList<>();
		this.conditioningLinks = new ArrayList<ConditioningLink>();
		
	}

	public Plan(Plan plan) {
		this.steps = new ArrayList<>(plan.getSteps());
		
		this.openPreconditions = new ArrayList<>(plan.getOpenPreconditions());
		this.openGoals = new ArrayList<>(plan.getOpenGoals());
		
		this.links = new ArrayList<>(plan.getLinks());
		this.orders = new ArrayList<>(plan.getOrders());
		this.conditioningLinks = new ArrayList<ConditioningLink>(plan.getConditioningLinks());
	}
	
	public void init(ConditionalAction start, ConditionalAction stop) {
		
		// ci pensa il planner a mettere stop in steps e preconditions
		
		steps.add(start);
		//steps.add(stop);
		
		orders.add(new Order(start, stop));
		
		//openPreconditions.addAll(stop.getPre());
		
		openGoals.add(new Goal(stop));
	}

	public List<ConditionalAction> getSteps() {
		return steps;
	}

	public List<Fact> getOpenPreconditions() {
		return openPreconditions;
	}

	public List<CausalLink> getLinks() {
		return links;
	}

	public List<Order> getOrders() {
		return orders;
	}
	
	public void addStep(ConditionalAction step){
		steps.add(step);
		//openPreconditions.addAll(0, step.getPre()); //depth first
		openPreconditions.addAll(step.getPre()); //breadth first
	}
	
	public void addPrecondition(Fact f){
		openPreconditions.add(f);
	}
	
	public void addGoal(Goal g){
		openGoals.add(g);
	}
	
	public void addCausalLink(CausalLink l){
		if(!links.contains(l))
			links.add(l);
	}
	
	public void addOrderConstraint(Order o){
		if(!orders.contains(o))
			orders.add(o);
	}

	public void addConditioningLink(ConditioningLink o){
		if(!conditioningLinks.contains(o))
			conditioningLinks.add(o);
	}

	public boolean hasGoalToSolve() {
		return !openGoals.isEmpty();
	}
	
	public boolean hasPreconditionToSolve(){
		return !openPreconditions.isEmpty();
	}

	public Fact selectPreconditionToSolve() {
		return openPreconditions.remove(0);
	}
	
	public Goal selectGoalToSolve() {
		return openGoals.remove(0);
	}

	public void addSubgoal(Fact subGoal) {
		openPreconditions.add(subGoal);	
	}
	
	public void restoreSubgoal(Fact subGoal){
		openPreconditions.add(0, subGoal);
	}
	
	
	
	public List<Goal> getOpenGoals() {
		return openGoals;
	}

	public List<ConditioningLink> getConditioningLinks() {
		return conditioningLinks;
	}

	public ArrayList<ConditionalPlanNode> numbering(){
		
		int ID = 0;
		
		ArrayList<ConditionalPlanNode> conditionalPlan = new ArrayList<ConditionalPlanNode>();		
		ArrayList<PlanNode> nodes = new ArrayList<PlanNode>();
		
		for(ConditionalAction a : steps)
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
		
		ArrayList<PlanNode> noPreList = new ArrayList<PlanNode>();
		
		for(PlanNode n : nodes)
		{
			if(n.pre.isEmpty())
			{
				noPreList.add(n);
				nodes.remove(n);
				break;
			}
		}
		
		while(!noPreList.isEmpty())
		{
			PlanNode noPre = noPreList.remove(0);
			
			conditionalPlan.add(new ConditionalPlanNode((ConditionalAction) noPre.action, ID)); //depth first
			ID ++;
			
			for(PlanNode n : nodes)
			{
				if(n.pre.contains(noPre.action))
				{
					n.pre.remove(noPre.action);
					if(n.pre.isEmpty())
						noPreList.add(0, n);
				}
			}			
		}
		
		for(ConditioningLink branch : conditioningLinks)
		{
			for(ConditionalPlanNode node : conditionalPlan)
			{
				if(node.getAction().equals(branch.getBefore()))
				{
					for(ConditionalPlanNode to : conditionalPlan)
					{
						if(to.getAction().equals(branch.getAfter()))
						{
							if(branch.getCondition().getID() == 1)
							{
								//clear branch
								node.setBranchClearID(to.getId());
							}
							else
							{
								//not clear branch
								node.setBranchNotClearID(to.getId());
							}
						}
					}
				}
			}
		}
		
		
		return conditionalPlan;
	}

	public void updateReasonAscending(ConditionalAction step, List<Goal> reason) {
		
		updateReason(step, reason);
		
		if(step.getName().contains("start"))
			return;
		
		ConditionalAction currentStep = step;
		
		for(int i=0; i<links.size(); i++){
			
			for(CausalLink link : links)
			{
				if(link.getTo().equals(currentStep)){
					
					currentStep = link.getFrom();
					
					updateReason(link.getTo(), reason);				
				}
			}
		}
	}
	
	public void updateReason(ConditionalAction step, List<Goal> reason){
		
		for(ConditionalAction a : steps){
			if(a.equals(step))
			{
				a.mergeReason(reason);
				return;
			}
		}
		
		
	}

	public Goal updateContextDescending(ConditionalAction step, ConditionalLabel context) {

		updateContext(step, context);
		if(step.getName().contains("stop"))
			return new Goal(step, step.getContext());
		
		Goal g = null;
		
		for(int i=0; i<links.size(); i++)
		{
			for(CausalLink link : links)
			{
				if(link.getFrom().equals(step))
				{
					g = updateContextDescending(link.getTo(), context);
				}				
			}	
		}
		
		return g;
	}

	private void updateContext(ConditionalAction currentStep, ConditionalLabel context) {

		for(ConditionalAction a : steps)
		{
			if(a.equals(currentStep))
				a.addConditionalLabel(context);
		}
		
	}

	public Goal descendingGoalLookup(ConditionalAction step) {
		
		ConditionalAction currentStep = step;
		
		if(currentStep.getName().contains("stop"))
			return new Goal(currentStep, currentStep.getContext());
		
		for(CausalLink link : links)
		{
			if(link.getFrom().equals(currentStep))
			{
				currentStep = link.getTo();						
				
				if(currentStep.getName().contains("stop"))
					return new Goal(currentStep, currentStep.getContext());
			}				
		}	
		
		return null;
	}
	

}
