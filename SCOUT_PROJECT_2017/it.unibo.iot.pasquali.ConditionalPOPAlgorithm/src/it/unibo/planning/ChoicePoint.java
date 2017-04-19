package it.unibo.planning;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.TreeSet;

import it.unibo.domain.graph.State;
import it.unibo.domain.model.Action;
import it.unibo.domain.model.Fact;
import it.unibo.domain.model.conditional.ConditionalAction;

public class ChoicePoint implements Serializable{

	// Sempre associato alle move, quando faccio check non ho alcuna scelta
	// quando faccio check apro un branch che risolvo poi al prossimo loop
	
	private Fact root;	
	private List<ConditionalAction> stepSuccessors;
	private List<ConditionalAction> newSuccessor;
	
	private ConditionalAction lastChoice = null;
	
	private Plan state;
	
	private boolean onStep;
	
	private double stepCost;
	
	private TreeSet<State> visited = new TreeSet<State>();
	
	public ChoicePoint(Fact root, Plan state, double stepCost) {
		super();
		this.state = state;
		this.root = root;
		this.stepSuccessors = new ArrayList<>();
		this.newSuccessor = new ArrayList<>();
		this.stepCost = stepCost;
		
		onStep = true;
	}
	
	public ConditionalAction getNext(){
		
		if(onStep){
			
			lastChoice = getFromStep();
			
			if(lastChoice != null)
				return lastChoice;			
		}
		
		return lastChoice = getFromAction();
	}
	
	private ConditionalAction getFromStep(){
		
		if(stepSuccessors.isEmpty())
		{
			onStep = false;
			return null;
		}
		
		return stepSuccessors.remove(0);
		
	}
	private ConditionalAction getFromAction(){
		
		if(newSuccessor.isEmpty())
		{
			return null;
		}
		
		return newSuccessor.remove(0);
	}

	public void addStep(ConditionalAction a) {
		stepSuccessors.add(a);
	}

	public void addAction(ConditionalAction a) {
		newSuccessor.add(a);
	}
	
	public void sort(){
		Collections.sort(stepSuccessors);
		Collections.sort(newSuccessor);
	}

	public Fact getSubGoal() {
		return root;
	}
	
	public boolean fromStep(){
		return onStep;
	}
	
	public boolean noMoreActions(){
		return stepSuccessors.isEmpty() && newSuccessor.isEmpty();
	}

	public Plan getState() {
		return state;
	}

	public double getStepCost() {
		return stepCost;
	}

	public void setStepCost(double stepCost) {
		this.stepCost = stepCost;
	}

	
	@Override
	public String toString()
	{
		return root.toString();
	}

	public void setState(Plan state) {
		this.state = state;		
	}

	public ConditionalAction getLastChoice() {
		return lastChoice;
	}

	public TreeSet<State> getVisited() {
		return visited;
	}

	public void setVisited(TreeSet<State> visited) {
		this.visited = visited;
	}
	
	
	
}
