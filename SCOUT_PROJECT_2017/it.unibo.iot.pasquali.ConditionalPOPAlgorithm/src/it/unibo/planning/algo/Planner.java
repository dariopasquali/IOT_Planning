package it.unibo.planning.algo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeSet;

import it.unibo.domain.graph.Graph;
import it.unibo.domain.graph.State;
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
import it.unibo.test.AbcState;
import it.unibo.utils.HeuristicEvaluator;

public class Planner {
	
	HashMap<String, Double> heuristicMap;
	
	private State start, goal;
	private Graph graph;
	
	private List<ChoicePoint> openChoice = null;
	private ChoicePoint currentPoint = null;
	private ChoicePoint branchChoicePoint = null;
	
	private List<ConditionalAction> possibleMoves;
	
	private TreeSet<State> visited;
	private ConditionalAction lastStep;
	
	private Plan plan = null;
	
	private HeuristicEvaluator heuristic = null;
	
	private int currentGoalNumber;
	
	private List<ConditionalLabel> globalContext = null;
	
	private Check currentBranchGenerativeCheck = null;
	
	public Planner(State start, State goal, Graph graph, HeuristicEvaluator heuristic) {
		super();
		this.start = start;
		this.goal = goal;
		this.graph = graph;
		this.heuristic = heuristic;
		
		this.heuristicMap = new HashMap<String, Double>();
		
		this.lastStep = new Move(new State(-1,-1), new State(-1,-1));		
		
		visited = new TreeSet<State>();
		
		openChoice = new ArrayList<ChoicePoint>();
		globalContext = new ArrayList<ConditionalLabel>();
		possibleMoves = graph.getActions();
		
		evaluateStates();
		evaluateMoves();
	}
	
//{{ INITIALIZATION -------------------------------------------------------------	
	
	private void evaluateStates(){
		
		for(State s : graph.getStates())
		{			
			try
			{				
				heuristicMap.put(s.toString(), heuristic.evaluate(s, start));
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
				double h = heuristicMap.get(m.getFrom().toString());				
				m.setHeuristic(h);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		
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
	
//}}	
	

//{{ MAIN ALGORITHM -------------------------------------------------------------	
	
	public Plan findPlan() throws Exception{
		
		plan = new Plan();
		
		initPlan();
		
		while(plan.hasGoalToSolve())
		{
			Goal goal = plan.selectGoalToSolve();			
			
			System.out.println("new goal - "+goal.toString());
			
			globalContext = goal.getGlobalContext();			
			plan.addStep(goal.getAction());
			
			lastStep = new Move(new State(-1,-1), new State(-1,-1));
			
			visited = new TreeSet<>();
			
			openChoice = new ArrayList<ChoicePoint>();
			branchChoicePoint = goal.getAlternatives();
			
			while(plan.hasPreconditionToSolve())
			{
				Fact subgoal = plan.selectPreconditionToSolve();
				
				//System.out.println("new subgoal --- "+subgoal.toString());
				
				if(branchChoicePoint != null && subgoal.equals(branchChoicePoint.getSubGoal()))
				{
					branchChoicePoint.setState(new Plan(plan));
					branchChoicePoint.getSubGoal().setStep(lastStep);
					currentPoint = branchChoicePoint;
					branchChoicePoint = null;
					currentBranchGenerativeCheck = goal.getGenerativeCheck();
				}
				
				ChoicePoint choice = null;
				
				if(currentPoint == null)
				{
					
					if(!checkSubgoalContextCompayibility(subgoal))
					{
						failAndBacktrack(goal, subgoal);
						//solveThreats();
						continue;
					}
					
					choice = new ChoicePoint(subgoal, new Plan(plan), 1);
					choice.setVisited(new TreeSet<State>(visited));
					
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
									boolean found = false;
									for(State s : visited)
									{
										if(s.equals(((Move)a).getFrom()))
										{
											found = true;
											break;
										}
												
									}
									
									if(!found)
									{
										a.setCost(0);
										choice.addAction(a.copy());
									}									
								}
							}
							else
							{
								a.setCost(0);
								choice.addAction(a.copy());
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
					//solveThreats();
					continue;
				}
				
				openChoice.add(choice);
				
				ConditionalAction step = choice.getNext();
				
				plan.addCausalLink(new CausalLink(step, (ConditionalAction) subgoal.getStep(), subgoal));
				plan.addOrderConstraint(new Order(step, (ConditionalAction) subgoal.getStep()));
				
				step.addSuccessor((ConditionalAction) subgoal.getStep());
				
				if(choice.fromStep()) //addLink
				{			
					// update reason di tutti i gli ascendenti dello step aggiunto
					plan.updateReasonAscending(step, ((ConditionalAction)subgoal.getStep()).getReason());
					
					plan.updateContextDescending((ConditionalAction) subgoal.getStep(), step.getContext());
				}
				else //addStep
				{
					step.addConditionalLabels(globalContext);					
					
					if(step.getType().equals(ActionType.CHECK))
					{

						plan.updateContextDescending(
								(ConditionalAction)subgoal.getStep(), ((Check) step).getLabels().get(0));
					
						List<Goal> reason = new ArrayList<Goal>();
						reason.add(goal);
						step.mergeReason(reason);
						
						//create branch
						List<ConditionalLabel> context = new ArrayList<ConditionalLabel>(globalContext);
						context.add(((Check)step).getLabels().get(1));	
						
						plan.addConditioningLink(
								new ConditioningLink(step,
										(ConditionalAction) subgoal.getStep(),
										((Check) step).getLabels().get(0)));
						
						ChoicePoint alternative = findOpenChoiceFor((Check)step);						
						Goal branch = createNewGoal(context, alternative, (Check)step);
						plan.addGoal(branch);
					}
					else
					{
						//Goal g = plan.descendingGoalLookup(	(ConditionalAction)subgoal.getStep());
						
						List<Goal> reason = new ArrayList<Goal>();
						reason.add(goal);
						step.mergeReason(reason);
					}
					
					plan.addStep(step);
					
				}
				
				if(step instanceof Move || step.getName().contains("stop"))
				{
					lastStep = (ConditionalAction)step;
					visited.add(((Move)step).getTo());
				}
				
				//solveThreats();
				
			}	
			
			connectAlternatives(goal);
			
		}
		
		return plan;
	}
	
	private void failAndBacktrack(Goal g, Fact subgoal) {
		
		System.out.println("rollback");
		
		currentPoint = findRollbackOpenChoice(subgoal);		
		
		if(currentPoint == null)
		{
			String ID = g.getAction().getName().replace("stop", "");
			ConditionalAction fail = new ConditionalAction("fail"+ID);
			fail.addConditionalLabels(g.getGlobalContext());
			
			plan.addStep(fail);
			plan.addCausalLink(new CausalLink(fail, g.getAction(), subgoal));
			plan.addOrderConstraint(new Order(fail, g.getAction()));
			
			return;
		}
		
		clearSameLevelOpenChoice(currentPoint);
		
		plan = new Plan(currentPoint.getState());
		plan.restoreSubgoal(currentPoint.getSubGoal());
		visited = currentPoint.getVisited();
	}

	private void clearSameLevelOpenChoice(ChoicePoint choice) {

		try
		{			
			for(int i=0; i<openChoice.size(); i++)
			{
				if(openChoice.get(i).getSubGoal().getStep().equals(choice.getLastChoice()))
					openChoice.remove(i);
			}			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}		
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

	private ChoicePoint findOpenChoiceFor(Check step) {

		for(ChoicePoint cp : openChoice)
		{
			if(cp != null && cp.getSubGoal().toString().equals("at("+step.getTo()+")"))
				return cp;
		}
		
		return null;		
	}

//}}
	
	
//{{ CONTEXT COMPATIBILITY ----------------------------------------------------------
	
	private boolean checkLabelCompatibility(ConditionalLabel label1, ConditionalLabel label2){
		/*
		 * Two labels are compatible if have the same ID or come from different checks
		 * A1 !comp A2
		 * A1 comp A1
		 * A1 comp B1
		 * A1 comp B2
		 */
		
		return label1.equals(label2) || !label1.getRootName().equals(label2.getRootName());
	}
	
	private boolean checkContextCompatibility(List<ConditionalLabel> c1, List<ConditionalLabel> c2){
		
		/*
		 * Two context are compatible if each label of the first context
		 * is compatible with each label of the second context
		 */
		

		if(c1.isEmpty() || c2.isEmpty())
			return true;
	
		for(ConditionalLabel l1 : c1)
		{
			for(ConditionalLabel l2 : c2)
			{
				if(!checkLabelCompatibility(l1, l2))
					return false;
			}
		}
		
		
		return true;
	}
	
	private boolean checkSubgoalContextCompayibility(Fact subgoal) {

		for(ConditionalLabel label : globalContext)
		{
			if(factsInConflict(label.getValue(), subgoal))
				return false;
		}
		
		return true;		
	}

	private boolean checkContextCompatibilityGlobal(List<ConditionalLabel> context) {
		
		return checkContextCompatibility(context, globalContext);		
	}

//}}
	
	
//{{ BRANCH MANAGEMENT -----------------------------------------------------------------
	
	private void connectAlternatives(Goal goal) {
		
//		System.out.println("connect alternatives");
		
		if(goal.getGenerativeCheck() == null)
			return;
		
		/*
		 * devo connettere la check che ha generato il global context del goal
		 * con la prima action che possiede quel global context.
		 * 
		 * cioè la prima check connessa a start che possiede il global context
		 * e ha lo stesso from della check generatrice
		 * oppure un fail.
		 */
		
		ArrayList<ConditionalAction> subtree = new ArrayList<>();
		
		for(ConditionalAction step : plan.getSteps())
		{
			if(step.getContext().containsAll(goal.getGlobalContext()))
			{
				subtree.add(step);
			}
		}		
	
		/*
		 * Trova la prima check per la quale non esiste un ConditioningLink corrispondente
		 * alla Label 2 e che non appartiene al subtree appena generato.
		 * 
		 * Connettila alla check del subtree con lo stesso stato before
		 */
	
		/*
		 * Cioè trova il primo conditioning link,
		 * associato a una label positiva
		 * la cui check non appartiene al subtree
		 * per il quale non esiste un corrispettivo negativo
		 * 
		 * Se esiste, trova una check appartenente al subtree,
		 * che possiede lo stesso "from state" della check sopra.
		 * 
		 * Connetti la prima check alla seconda con un conditioning link basato sulla label negativa
		 */
		
		if(subtree.size() < 2)
			return;
		
		for(int i = 0; i<plan.getConditioningLinks().size(); i++)
		{
			ConditioningLink condition = plan.getConditioningLinks().get(i);			
			
			if(!condition.getCondition().getValue().getTruthValue())
				continue;
			
			if(subtree.contains(condition.getBefore()))
				continue;
						
			boolean found = false;
			
			for(ConditioningLink link : plan.getConditioningLinks())
			{
				if(factsInConflict(condition.getCondition().getValue(), link.getCondition().getValue()))
				{
					found = false;
					break;
				}
			}
			
			if(!found)
			{
				for(ConditionalAction a : subtree) //TODO dovrei anche aggiornare i contesti a cascata??? Ha senso come cosa?????
				{
					if((a instanceof Check) &&
							((Check)a).getFrom().equals(((Check)condition.getBefore()).getFrom()))
					{
						plan.addConditioningLink(new ConditioningLink(condition.getBefore(),
								a, ((Check)condition.getBefore()).getLabels().get(1)));
						
						plan.addOrderConstraint(new Order(condition.getBefore(), a));
					}
				}
			}
		}		
	}

	private Goal createNewGoal(List<ConditionalLabel> context, ChoicePoint alternative, Check generativeCheck){
		
		ConditionalAction stopStep = new ConditionalAction("stop"+currentGoalNumber);
		currentGoalNumber++;
		
		Fact goalPos = new Fact("at", stopStep);
		goalPos.addParam(goal.toString());
		
		stopStep.addPre(goalPos);
		stopStep.addConditionalLabels(context);
		
		return new Goal(stopStep, context, alternative, generativeCheck);		
	}

//}}
	
	
//{{ THREAT SOLVING ---------------------------------------------------------------------
	
	private void solveThreats() {
		
		for(ConditionalAction step : plan.getSteps())
		{
			for(CausalLink link : plan.getLinks())
			{
				Fact minaccia = checkThreat(step, link);
				
				if(minaccia != null)
				{
					System.out.println("SOLVE THREAT");
					
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
	
	private Fact checkThreat(ConditionalAction step, CausalLink link){
		
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
		
		System.out.println("PROMOTION");
		
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
		
		System.out.println("DEMOTION");
		
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
		
		System.out.println("CONDITION");
		
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
//				plan.addConditioningLink(new ConditioningLink(check, step, check.getLabels().get(0)));
//				plan.addConditioningLink(new ConditioningLink(check, to, check.getLabels().get(1)));
				
				plan.addOrderConstraint(new Order(check, step));
				plan.addOrderConstraint(new Order(check, to));
				
				plan.updateContextDescending(step, check.getLabels().get(0));
				plan.updateContextDescending(to, check.getLabels().get(1));
			}			
		}
		
		return false;		
	}
	
//}}	
	
}
