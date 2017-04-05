package it.unibo.planning;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import it.unibo.domain.model.Action;
import it.unibo.domain.model.Fact;

public class ChoicePoint {

	private Fact root;	
	private List<Action> stepSuccessors;
	private List<Action> newSuccessor;
	
	private Plan state;
	
	private boolean onStep;
	
	private double stepCost;
	
	public ChoicePoint(Fact root, Plan state, double stepCost) {
		super();
		this.state = state;
		this.root = root;
		this.stepSuccessors = new ArrayList<>();
		this.newSuccessor = new ArrayList<>();
		this.stepCost = stepCost;
		
		onStep = true;
	}
	
	public Action getNext(){
		
		Action a = null;
		
		if(onStep){
			
			a = getFromStep();
			
			if(a != null)
				return a;			
		}
		
		return getFromAction();
	}
	
	private Action getFromStep(){
		
		if(stepSuccessors.isEmpty())
		{
			onStep = false;
			return null;
		}
		
		return stepSuccessors.remove(0);
		
	}
	
	private Action getFromAction(){
		
		if(newSuccessor.isEmpty())
		{
			return null;
		}
		
		return newSuccessor.remove(0);
	}

	public void addStep(Action a) {
		stepSuccessors.add(a);
	}

	public void addAction(Action a) {
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
	
}
