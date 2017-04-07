package it.unibo.planning;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import it.unibo.domain.model.Action;
import it.unibo.domain.model.Fact;
import it.unibo.domain.model.Graph;
import it.unibo.domain.model.Move;
import it.unibo.domain.model.Node;
import it.unibo.domain.model.State;
import it.unibo.utility.HeuristicEvaluator;

public class Planner {
	
	HashMap<String, Double> heuristicMap;
	
	private State start, goal;
	private Graph graph;
	
	private Deque<ChoicePoint> openChoice = null;
	private ChoicePoint currentPoint = null;
	
	private List<Move> possibleMoves;
	
	private Action lastStep;
	
	private Plan plan = null;
	
	private double stepCost;
	
	private HeuristicEvaluator heuristic = null;
	
	public Planner(State start, State goal, Graph graph, HeuristicEvaluator heuristic) {
		super();
		this.start = start;
		this.goal = goal;
		this.graph = graph;
		this.heuristic = heuristic;
		
		this.heuristicMap = new HashMap<String, Double>();
		
		this.stepCost = 1;
		
		this.lastStep = new Move(new State(-1,-1), new State(-1,-1));
		
		openChoice = new ArrayDeque<ChoicePoint>();
		possibleMoves = graph.getMoves();
		
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
		
		
		for(Move m : possibleMoves)
		{	
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
		
		while(!plan.terminationTest())
		{
			Fact subgoal = plan.selectSubgoalToSolve();
			
			// CHOICE OPERATOR *************************
			
			ChoicePoint choice = null;
			
			if(currentPoint == null)
			{
				choice = new ChoicePoint(subgoal, new Plan(plan), 1);
				
				List<Action> steps = plan.getSteps();			
				
				for(Action a : steps)
				{
					if(a.getEff().contains(subgoal))
					{
						a.setCost(0);
						choice.addStep(a);
					}
				}
				
				for(Action a : possibleMoves)
				{
					if(a.getEff().contains(subgoal))
					{
						if((lastStep instanceof Move) && !((Move)a).isOppositeTo((Move)lastStep))
						{
							a.setCost(0);
							choice.addAction(a);
						}
					}
				}
				
				if(choice.noMoreActions())
				{
					failAndBacktrack();
					continue;
				}
				
				choice.sort();				
				//stepCost++;
			}
			else
			{
				choice = currentPoint;
				currentPoint = null;
			}
			
			openChoice.push(choice);
			
			Action step = choice.getNext();
			
			plan.addCausalLink(new CausalLink(step, subgoal.getStep(), subgoal));
			plan.addOrderConstraint(new Order(step, subgoal.getStep()));
			
			if(!choice.fromStep())
			{
				plan.addStep(step);				
			}
			
			lastStep = (Action)step;
			
			//*****************************************
			
			solveThreat();			
		}		
		
		
		return plan;
	}
	

	private void solveThreat() {
		// TODO Auto-generated method stub
		
	}

	private void failAndBacktrack() {
		
		System.out.println("rollback");
		
		currentPoint = openChoice.pop();
		this.stepCost = currentPoint.getStepCost();
		plan = currentPoint.getState();
		plan.addSubgoal(currentPoint.getSubGoal());
	}

	private void initPlan() {
		
		// START STEP CONFIG **************************************
		
		Action startStep = new Action("start");		
		List<Fact> initState = graph.getInitialState();
		
		for(Fact f : initState){
			f.setStep(startStep);
			startStep.addEffect(f);
		}
		
		Fact startPos = new Fact("at", startStep);
		startPos.addParam(start.toString());
		
		startStep.addEffect(startPos);
		
		// STOP STEP CONFIG **************************************
		
		Action stopStep = new Action("stop");
		
		Fact goalPos = new Fact("at", stopStep);
		goalPos.addParam(goal.toString());
		
		stopStep.addPre(goalPos);
		
		//*********************************************************
		
		plan.init(startStep, stopStep);		
	}
	

}
