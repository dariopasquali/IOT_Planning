package it.unibo.planning;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import it.unibo.domain.graph.State;
import it.unibo.domain.model.Fact;
import it.unibo.domain.model.conditional.Check;
import it.unibo.domain.model.conditional.ConditionalAction;
import it.unibo.domain.model.conditional.ConditionalLabel;
import it.unibo.domain.model.conditional.Goal;
import it.unibo.domain.model.conditional.Move;
import it.unibo.execution.domain.CFail;
import it.unibo.execution.domain.CMove;
import it.unibo.execution.domain.CSense;
import it.unibo.execution.domain.CSpin;
import it.unibo.execution.domain.CStep;
import it.unibo.execution.domain.CStop;
import it.unibo.execution.enums.CDirection;
import it.unibo.execution.enums.ConditionalMoveType;
import it.unibo.execution.enums.SpinAngle;
import it.unibo.interfaces.IRunnablePopPlan;
import it.unibo.planning.enums.SpinDirection;

public class Plan implements Serializable, IRunnablePopPlan{
	
	private List<ConditionalAction> steps;
	
	private List<Fact> openPreconditions;
	private List<Goal> openGoals;
	
	private List<CausalLink> links;
	private List<Order> orders;
	private List<ConditioningLink> conditioningLinks;
	
	private HashSet<String> updated;
	
//{{ INITIALIZATION ----------------------------------------------------------------
	
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
		
		steps.add(start);		
		orders.add(new Order(start, stop));		
		openGoals.add(new Goal(stop));
	}

//}}
	

//{{ GETTERS -----------------------------------------------------------------------	
	
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
	
	public List<Goal> getOpenGoals() {
		return openGoals;
	}

	public List<ConditioningLink> getConditioningLinks() {
		return conditioningLinks;
	}

	
	
//}}	
	
	
//{{ ADDERS ------------------------------------------------------------------------	
	
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
		//if(!links.contains(l))
			links.add(l);
	}
	
	public void addOrderConstraint(Order o){
		//if(!orders.contains(o))
			orders.add(o);
	}

	public void addConditioningLink(ConditioningLink o){
		//if(!conditioningLinks.contains(o))
			conditioningLinks.add(o);
	}

	public void addSubgoal(Fact subGoal) {
		openPreconditions.add(subGoal);	
	}
	
//}}
	
	
//{{ TERMINATION TEST --------------------------------------------------------------	
	
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

//}}	
	

// {{ BRANCH MANAGEMENT --------------------------------------------------------------
	
	public void restoreSubgoal(Fact subGoal){
		openPreconditions.add(0, subGoal);
	}
	
//}}
	
//{{ REASON & CONTEXT UPDATE ----------------------------------------------------------
	
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

	private void updateContextDescendingRecursive(ConditionalAction step, List<ConditionalLabel> context)
	{
		
		step.addConditionalLabels(context);
		
		for(ConditionalAction a : step.getSuccessors())
		{
			if(!updated.contains(a.getHash()))
			{
				updated.add(a.getHash());
				updateContextDescendingRecursive(a, context);
			}
		}
		
	}
	
	private void updateContextDescendingRecursive(ConditionalAction step,
			ConditionalLabel context)
	{
		
		step.addConditionalLabel(context);
		
		for(ConditionalAction a : step.getSuccessors())
		{
			if(!updated.contains(a.getHash()))
			{
				updated.add(a.getHash());
				updateContextDescendingRecursive(a, context);
			}
		}
	}
		
	
	
	public void updateContextDescending(ConditionalAction step, List<ConditionalLabel> context)
	{
		updated = new HashSet<String>();		
		updateContextDescendingRecursive(step, context);
		
	}	
	
	public void updateContextDescending(ConditionalAction step, ConditionalLabel context) {

		updated = new HashSet<String>();
		updateContextDescendingRecursive(step, context);		
	}
	
//}}
	

//{{ RUNNABLE PLAN METHOD -------------------------------------------------------
	
	@Override
	public List<ConditionalPlanNode> numbering(){
		
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
			
			ArrayList<PlanNode> toAdd = new ArrayList<PlanNode>();
			
			for(PlanNode n : nodes)
			{
				if(n.pre.contains(noPre.action))
				{
					n.pre.remove(noPre.action);
					if(n.pre.isEmpty())
						toAdd.add(n);
				}
			}
			
			noPreList.addAll(0, toAdd);
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
							if(branch.getCondition().getID() == 1) //true conditional label
							{
								//clear branch
								if(node.getBranchClearID() == -1) 
									node.setBranchClearID(to.getId());
							}
							else //not true conditional label
							{
								//not clear branch
								if(node.getBranchNotClearID() == -1) 
									node.setBranchNotClearID(to.getId());
							}
						}
					}
				}
			}
		}
		
		int failID;
		
		conditionalPlan.add(new ConditionalPlanNode(new ConditionalAction("fail"), ID));
		failID = ID;
		ID ++;
		conditionalPlan.add(new ConditionalPlanNode(new ConditionalAction("stop"), ID));
		ID ++;
		
				
		for(ConditionalPlanNode node : conditionalPlan)
		{
			if(node.getAction() instanceof Check)
			{
				if(node.getBranchNotClearID() == -1)
					node.setBranchNotClearID(failID);
				
				if(node.getBranchClearID() == -1)
					node.setBranchClearID(failID);
			}
					
		}
		
		return conditionalPlan;
	}

	
	public static List<CMove> expandPlan(List<ConditionalPlanNode> numbered, SpinAngle defSpinAngle){
		
		List<CMove> moves = new ArrayList<CMove>();		
		HashMap<Integer, Integer> mapping = new HashMap<>();
		HashMap<Integer, CDirection> branchStartDir = new HashMap<>();
		
		int ID = 0;
		CDirection currentDir = null;
		
		for(int i=1; i<numbered.size(); i++)
		{
			ConditionalPlanNode node = numbered.get(i);
			
			if(node.getAction().getShortName().contains("check"))
			{
				Check c = (Check)node.getAction();	
				
				if(!branchStartDir.containsKey(node.getId()))
				{
					if(currentDir == null)
						currentDir = CDirection.NORTH;
				}						
				else
					currentDir = branchStartDir.get(node.getId());
				
				mapping.put(node.getId(), ID);
				
				List<CMove> spins = fromStatetoMoves(ID, c.getFrom(), c.getTo(), currentDir, defSpinAngle);				
				currentDir = updateDirection(currentDir, spins, defSpinAngle);
				
				if(!spins.isEmpty())
				{
					ID = spins.get(spins.size()-1).getId();
					ID++;
					moves.addAll(spins);
				}				
				moves.add(new CSense(ID, node.getBranchClearID(), node.getBranchNotClearID()));	
				
				branchStartDir.put(node.getBranchNotClearID(), currentDir);
			}
			else if(node.getAction().getShortName().contains("move"))
			{
				moves.add(new CStep(ID));
				mapping.put(node.getId(), ID);
			}
			else if(node.getAction().getShortName().contains("stop"))
			{
				moves.add(new CStop(ID));
				mapping.put(node.getId(), ID);
			}
			else if(node.getAction().getShortName().contains("fail"))
			{
				moves.add(new CFail(ID));
				mapping.put(node.getId(), ID);
			}		
			
			ID++;
		}	
		
		
		for(CMove m : moves)
		{
			if(m.getType().equals(ConditionalMoveType.SENSE))
			{
				try
				{
					((CSense) m).setBranches(mapping.get(((CSense) m).getBranchIDClear()),
							mapping.get(((CSense) m).getBranchIDNotClear()));
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}				
			}
		}
		
		return moves;
	}
	
	
	private static CDirection updateDirection(CDirection currentDir, List<CMove> spins, SpinAngle defSpinAngle) {

		HashMap<CDirection, CDirection> rigth = new HashMap<>();
		rigth.put(CDirection.NORTH, CDirection.EAST);
		rigth.put(CDirection.EAST, CDirection.SOUTH);
		rigth.put(CDirection.SOUTH, CDirection.WEST);
		rigth.put(CDirection.WEST, CDirection.NORTH);
		
		
		HashMap<CDirection, CDirection> left = new HashMap<>();
		left.put(CDirection.NORTH, CDirection.WEST);
		left.put(CDirection.EAST, CDirection.NORTH);
		left.put(CDirection.SOUTH, CDirection.EAST);
		left.put(CDirection.WEST, CDirection.SOUTH);
		
		CDirection dir = currentDir;
		
		for(int i=0; i<spins.size(); i++)
		{
			if(spins.get(i).toString().contains("r"))
				dir = rigth.get(dir);
			else
				dir = left.get(dir);
			
			if(defSpinAngle.equals(SpinAngle.d45)) i++;
		}
		
		return dir;		
	}

	private static List<CMove> fromStatetoMoves(int startID, State from, State to, CDirection cdir, SpinAngle defSpinAngle)
	{
		List<CMove> moves = new ArrayList<CMove>();
		
		int spins = (int) (calcRotationAngleInDegrees(cdir.getPhase(), from,  to)/defSpinAngle.getAngle());
		
		if(spins > 4)
			spins = spins - 8;
		
		if(spins < -4)
			spins = 8 + spins;
		
		if(spins > 0)
		{	
			for(int i=0; i<spins; i++)
			{
				moves.add(new CSpin(startID, SpinDirection.RIGHT));
				startID++;
			}
		}
		else if(spins<0)
		{
			for(int i=0; i<Math.abs(spins); i++)
			{
				moves.add(new CSpin(startID, SpinDirection.LEFT));
				startID++;
			}
		}	
		
		return moves;
	}
	
	private static int calcRotationAngleInDegrees(int startDir, State from, State to)
	{

	    /*
	     * calcolo la posizione di to rispetto a from,
	     * gli associo uno sfasamento rispetto allo zero
	     * e lo sottraggo a startDir
	     */
		
		int deltaX = to.getX() - from.getX();
		int deltaY = to.getY() - from.getY();
		
		CDirection toEntraceDir = null;
		
		if(deltaX == 0 && deltaY == 1)
			toEntraceDir = CDirection.SOUTH;
		
		else if(deltaX == 0 && deltaY == -1)
			toEntraceDir = CDirection.NORTH;
		
		else if(deltaX == 1 && deltaY == 0)
			toEntraceDir = CDirection.EAST;
		
		else
			toEntraceDir = CDirection.WEST;
		
		return toEntraceDir.getPhase() - startDir;		
	}

	
//}}	
}
