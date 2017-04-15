package it.unibo.planning.algo;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import it.unibo.domain.graph.Graph;
import it.unibo.domain.graph.State;
import it.unibo.domain.model.Action;
import it.unibo.domain.model.Fact;
import it.unibo.domain.model.conditional.Check;
import it.unibo.domain.model.conditional.ConditionalAction;
import it.unibo.domain.model.conditional.ConditionalLabel;
import it.unibo.domain.model.conditional.Goal;
import it.unibo.domain.model.conditional.Move;
import it.unibo.enums.ActionType;
import it.unibo.planning.CausalLink;
import it.unibo.planning.ChoicePoint;
import it.unibo.planning.ConditioningLink;
import it.unibo.planning.Order;
import it.unibo.planning.Plan;
import it.unibo.utils.HeuristicEvaluator;

public class Planner {
	
	HashMap<String, Double> heuristicMap;
	
	private State start, goal;
	private Graph graph;
	
	private List<ChoicePoint> openChoice = null;
	private ChoicePoint currentPoint = null;
	private ChoicePoint branchChoicePoint = null;
	
	private List<ConditionalAction> possibleMoves;
	
	private ConditionalAction lastStep;
	
	private Plan plan = null;
	
	private double stepCost;
	
	private HeuristicEvaluator heuristic = null;
	
	private int currentGoalNumber;
	
	private List<ConditionalLabel> globalContext = null;
	
	public Planner(State start, State goal, Graph graph, HeuristicEvaluator heuristic) {
		super();
		this.start = start;
		this.goal = goal;
		this.graph = graph;
		this.heuristic = heuristic;
		
		this.heuristicMap = new HashMap<String, Double>();
		
		this.stepCost = 1;
		
		this.lastStep = new Move(new State(-1,-1), new State(-1,-1));
		
		openChoice = new ArrayList<ChoicePoint>();
		globalContext = new ArrayList<ConditionalLabel>();
		possibleMoves = graph.getActions();
		
		evaluateStates();
		evaluateMoves();
	}
	
	private void evaluateStates(){
		
		for(State s : graph.getStates())
		{
			//s.setHeuristic(heuristic.evaluate(s, start));
			try
			{
				//System.out.print(s.toString());
				heuristicMap.put(s.toString(), heuristic.evaluate(s, start));
				//System.out.println(heuristicMap.size());
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}	
	
	private void evaluateMoves() {
		
		List<State> states = graph.getStates();
		
		for(Entry<String, Double> e : heuristicMap.entrySet())
			System.out.println(e.getKey() +"-->"+ e.getValue());			
		
		
		for(ConditionalAction m1 : possibleMoves)
		{	
			if(!(m1 instanceof Move))
				continue;
			
			Move m = (Move) m1;
			
			try
			{
				//System.out.println(m.getFrom().toString());
				
				double h = heuristicMap.get(m.getFrom().toString());
				
				//System.out.print(" "+h+"\n");
				
				m.setHeuristic(h);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		
	}

	public Plan findPlan(){
		
		plan = new Plan();
		
		initPlan();
		
		while(plan.hasGoalToSolve())
		{
			Goal goal = plan.selectGoalToSolve();			
			globalContext = goal.getGlobalContext();			
			plan.addStep(goal.getAction());
			
			lastStep = new Move(new State(-1,-1), new State(-1,-1));
			openChoice = new ArrayList<ChoicePoint>();
			branchChoicePoint = goal.getAlternatives();
			
			while(plan.hasPreconditionToSolve())
			{
				Fact subgoal = plan.selectPreconditionToSolve();
				
				if(branchChoicePoint != null && subgoal.equals(branchChoicePoint.getSubGoal()))
				{
					branchChoicePoint.setState(new Plan(plan));
					branchChoicePoint.getSubGoal().setStep(goal.getAction());
					currentPoint = branchChoicePoint;
					branchChoicePoint = null;
				}
				
				ChoicePoint choice = null;
				
				if(currentPoint == null)
				{
					choice = new ChoicePoint(subgoal, new Plan(plan), 1);
					
					List<ConditionalAction> steps = plan.getSteps();			
					
					for(ConditionalAction a : steps)
					{
						if(a.getEff().contains(subgoal) &&
								checkContextCompatibilityGlobal(a.getContext()))
						{
							a.setCost(0);
							choice.addStep(a);
						}
					}
					
					for(ConditionalAction a : possibleMoves)
					{
						if(a.getEff().contains(subgoal))
						{
							if((lastStep instanceof Move) && (a instanceof Move))
							{
								if(!((Move)a).isOppositeTo((Move)lastStep))
								{
									a.setCost(0);
									choice.addAction(a);
								}
							}
							else
							{
								a.setCost(0);
								choice.addAction(a);
							}
						}
					}				
					
					choice.sort();	
				}
				else
				{
					// ho appena fatto backtrack per strada inutile
					choice = currentPoint;
					currentPoint = null;					
				}
				
				
				if(choice.noMoreActions())
				{
					failAndBacktrack(goal, subgoal);
					solveThreats();
					continue;
				}
				
				openChoice.add(choice);
				
				ConditionalAction step = choice.getNext();
				
				plan.addCausalLink(new CausalLink(step, (ConditionalAction) subgoal.getStep(), subgoal));
				plan.addOrderConstraint(new Order(step, (ConditionalAction) subgoal.getStep()));
				
				if(choice.fromStep()) //addLink
				{			
					// update reason di tutti i gli ascendenti dello step aggiunto
					plan.updateReasonAscending(step, ((ConditionalAction)subgoal.getStep()).getReason());
				}
				else //addStep
				{
					step.addConditionalLabels(globalContext);
					plan.addStep(step);
					
					if(step.getType().equals(ActionType.CHECK))
					{
						// aggiorna i contesti degli step discendenti da quello
						//del quale si è soddisfatto il prerequisito
						Goal g = plan.updateContextDescending(
								(ConditionalAction)subgoal.getStep(), ((Check) step).getLabels().get(0));
						
						if(g != null)
						{
							// se arrivo a un goal, aggiorna la reason dello step aggiunto con il goal
							List<Goal> reason = new ArrayList<Goal>();
							reason.add(g);
							plan.updateReason(step,reason);
						}
						
						//create branch
						List<ConditionalLabel> context = new ArrayList<ConditionalLabel>(globalContext);
						context.add(((Check)step).getLabels().get(1));	
						
						ChoicePoint alternative = findOpenChoiceFor((Check)step);						
						Goal branch = createNewGoal(context, alternative);
						plan.addGoal(branch);
					}
					else
					{
						Goal g = plan.descendingGoalLookup(	(ConditionalAction)subgoal.getStep());
						
						if(g != null)
						{
							// se arrivo a un goal, aggiorna la reason dello step aggiunto con il goal
							List<Goal> reason = new ArrayList<Goal>();
							reason.add(g);
							plan.updateReason(step,reason);
						}
					}
					
				}
				
				if(step instanceof Move)
					lastStep = (ConditionalAction)step;
				
				solveThreats();
				
			}			
		}
		
		return plan;
	}
	

	private ChoicePoint findOpenChoiceFor(Check step) {

		for(ChoicePoint cp : openChoice)
		{
			if(cp != null && cp.getSubGoal().toString().equals("at("+step.getTo()+")"))
				return cp;
		}
		
		return null;		
	}

	private boolean checkContextCompatibilityGlobal(List<ConditionalLabel> context) {
		
		boolean keep = false;
		
		for(ConditionalLabel l : context)
		{
			for(ConditionalLabel g : globalContext)
			{
				if(l.sameRootDifferentID(g))
					return false;
				
				if(l.equals(g)){
					keep = true;
					break;
				}
				
			}
			
			if(!keep)
				return false;
		}
		
		return true;
	}

	private void solveThreats() {
		
		for(ConditionalAction step : plan.getSteps())
		{
			for(CausalLink link : plan.getLinks())
			{
				Fact minaccia = checkThreat(step, link);
				
				if(minaccia != null)
				{
					/*//TODO threat solving
					 * PROMOTION
					 * DEMOTION
					 * CONDITION
					 */
					
					if(!promotion(step, link.getTo()))
					{
						if(!demotion(step, link.getFrom()))
						{
							condition(step, link.getTo());
						}
					}
					
					
				}
			}
		}		
	}
	
	private Fact checkThreat(ConditionalAction step, CausalLink link)
	{
		if(checkContextCompatibility(step.getContext(), link.getTo().getContext()))
		{
			if(isPossiblyAfter(step, link.getFrom()) && isPossiblyBefore(step, link.getTo()))
			{
				for(Fact eff : step.getEff())
				{
					if(factsInConflict(eff, link.getCondition()))
						return eff;
				}
			}
		}
		return null;
	}
	
	private boolean checkContextCompatibility(List<ConditionalLabel> c1, List<ConditionalLabel> c2){
		
		boolean keepSearch = false;
		
		for(ConditionalLabel l : c1)
		{
			for(ConditionalLabel g : c2)
			{
				if(l.sameRootDifferentID(g))
					return false;
				
				if(l.equals(g))
				{
					keepSearch = true;
					break;
				}
			}
			
			if(!keepSearch)
				return false;
		}
		
		return true;
	}
	
	private boolean isPossiblyBefore(ConditionalAction step, ConditionalAction b){
		
		Order stepAfterB = new Order(b, step);
		Order bAfterStep = new Order(step, b);
				
		
		
		for(Order o : plan.getOrders()){
			if(o.equals(stepAfterB))
			{
				// ho ordine del tipo step > B
				return false;
			}
			
			if(o.equals(bAfterStep)) //check se ho step < B
				return true;
		}
		
		//non ho ordine ben definito, controllo se esiste catena

		ConditionalAction before = step;
		
		for(int i=0; i<plan.getOrders().size(); i++)
		{
			for(Order o : plan.getOrders())
			{
				if(o.getBefore().equals(before)){
					if(o.getAfter().equals(b))
						return true;
					
					before = o.getAfter();
				}
			}
		}
		
		return false;
		
	}
	
	private boolean isPossiblyAfter(ConditionalAction step, ConditionalAction a){
		
		Order stepBeforeA = new Order(step, a);
		Order aBeforeStep = new Order(a, step);		
		
		for(Order o : plan.getOrders()){
			if(o.equals(stepBeforeA))
			{
				// ho ordine del tipo step < A
				return false;
			}
			
			if(o.equals(aBeforeStep)) //check se ho step > A
				return true;
		}
		
		//non ho ordine definito, controllo se esiste catena

		ConditionalAction before = a;
		
		for(int i=0; i<plan.getOrders().size(); i++)
		{
			for(Order o : plan.getOrders())
			{
				if(o.getBefore().equals(before)){
					if(o.getAfter().equals(step))
						return true;
					
					before = o.getAfter();
				}
			}
		}
		
		return false;
		
	}
	
	private boolean factsInConflict(Fact f1, Fact f2){
		
		return f1.getKenrel().equals(f2.getKenrel()) && f1.getTruthValue() != f2.getTruthValue();
		
	}

	private boolean promotion(ConditionalAction step, ConditionalAction b){
		
		/*
		 * se B non è stop & step può essere after B
		 * vincola step > B
		 */
		
		if(!b.getName().contains("stop") &&
				isPossiblyAfter(step, b))
		{
			plan.addOrderConstraint(new Order(b, step));
			return true;
		}
		
		return false;		
	}
	
	private boolean demotion(ConditionalAction step, ConditionalAction a){
		
		/*
		 * se A non è start & step può essere before A
		 * vincola step < A
		 */
		
		if(!a.getName().contains("start") &&
				isPossiblyBefore(step, a))
		{
			plan.addOrderConstraint(new Order(step, a));
			return true;
		}
		
		return false;		
	}
	
	private boolean condition(ConditionalAction step, ConditionalAction to){
		
		ArrayList<Check> possible = new ArrayList<Check>();
		
		for(ConditionalAction check : plan.getSteps())
		{
			if(check instanceof Check && isPossiblyBefore(check, step) && isPossiblyBefore(check, to))
				possible.add((Check)check);
		}
		
		//scegli in modo non deterministico
		//ora prendo il primo e fottesega
		
		for(Check check : possible)
		{
			List<ConditionalLabel> c1 = new ArrayList<ConditionalLabel>(check.getContext()),
					c2 = new ArrayList<ConditionalLabel>(check.getContext());
			
			c1.add(check.getLabels().get(0));
			c2.add(check.getLabels().get(1));
			
			if(checkContextCompatibility(c1, step.getContext()) &&
					checkContextCompatibility(c2, to.getContext()))
			{
				plan.addConditioningLink(new ConditioningLink(check, step, check.getLabels().get(0)));
				plan.addConditioningLink(new ConditioningLink(check, to, check.getLabels().get(1)));
				
				plan.addOrderConstraint(new Order(check, step));
				plan.addOrderConstraint(new Order(check, to));
				
				plan.updateContextDescending(step, check.getLabels().get(0));
				plan.updateContextDescending(to, check.getLabels().get(1));
			}			
		}
		
		return false;		
	}
	
	private void failAndBacktrack(Goal g, Fact subgoal) {
		
		System.out.println("rollback");
		
		currentPoint = findRollbackOpenChoice(subgoal);		
		
		if(openChoice.size() == 0 || currentPoint == null)
		{
			String ID = g.getAction().getName().replace("stop", "");
			ConditionalAction fail = new ConditionalAction("fail"+ID);
			fail.addConditionalLabels(g.getGlobalContext());
			
			plan.addStep(fail);
			plan.addCausalLink(new CausalLink(fail, g.getAction(), subgoal));
			plan.addOrderConstraint(new Order(fail, g.getAction()));			
			return;
		}
		
		this.stepCost = currentPoint.getStepCost();
		plan = currentPoint.getState();
		plan.restoreSubgoal(currentPoint.getSubGoal());
		
	}

	private ChoicePoint findRollbackOpenChoice(Fact subgoal) {
		
		ConditionalAction step = (ConditionalAction) subgoal.getStep();
		
		for(int i=0; i<openChoice.size(); i++)
		{					
			ConditionalAction lastChoice = (ConditionalAction) openChoice.get(i).getLastChoice();
			
			if(step.equals(lastChoice) &&
					checkContextCompatibility(step.getContext(), lastChoice.getContext()))
				return openChoice.remove(i);
		}
		
		return null;
	}

	private void initPlan() {
		
		currentGoalNumber = 0;
		
		// START STEP CONFIG **************************************
		
		ConditionalAction startStep = new ConditionalAction("start");		
		List<Fact> initState = graph.getInitialState();
		
		for(Fact f : initState){
			f.setStep(startStep);
			startStep.addEffect(f);
		}
		
		Fact startPos = new Fact("at", startStep);
		startPos.addParam(start.toString());
		
		startStep.addEffect(startPos);
		
		// STOP STEP CONFIG **************************************
		
		ConditionalAction stopStep = new ConditionalAction("stop"+currentGoalNumber);
		
		Fact goalPos = new Fact("at", stopStep);
		goalPos.addParam(goal.toString());
		
		stopStep.addPre(goalPos);
		
		//*********************************************************
		
		plan.init(startStep, stopStep);
		
		currentGoalNumber++;
	}
	
	private Goal createNewGoal(List<ConditionalLabel> context, ChoicePoint alternative){
		
		ConditionalAction stopStep = new ConditionalAction("stop"+currentGoalNumber);
		currentGoalNumber++;
		
		Fact goalPos = new Fact("at", stopStep);
		goalPos.addParam(goal.toString());
		
		stopStep.addPre(goalPos);
		stopStep.addConditionalLabels(context);
		
		return new Goal(stopStep, context, alternative);		
	}

}
