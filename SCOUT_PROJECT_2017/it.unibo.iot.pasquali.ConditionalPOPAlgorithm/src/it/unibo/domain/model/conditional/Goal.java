package it.unibo.domain.model.conditional;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import it.unibo.planning.ChoicePoint;

public class Goal implements Serializable{
	
	private ConditionalAction action;
	private List<ConditionalLabel> globalContext;
	private ChoicePoint otherChoice = null;
	private Check generativeCheck;
	
	public Goal(ConditionalAction action, List<ConditionalLabel> globalContext, ChoicePoint other, Check generative) {
		super();
		this.action = action;
		this.globalContext = globalContext;
		this.otherChoice = other;
		this.setGenerativeCheck(generative);
	}
	
	public Goal(ConditionalAction action, List<ConditionalLabel> globalContext) {
		super();
		this.action = action;
		this.globalContext = globalContext;
		this.otherChoice = null;
		this.setGenerativeCheck(null);
	}
	
	public Goal(ConditionalAction action) {
		super();
		this.action = action;
		this.globalContext = new ArrayList<>();
	}
	
	
	
	public ConditionalAction getAction() {
		return action;
	}
	
	public void setAction(ConditionalAction action) {
		this.action = action;
	}
	
	public List<ConditionalLabel> getGlobalContext() {
		return globalContext;
	}
	
	public void setGlobalContext(List<ConditionalLabel> globalContext) {
		this.globalContext = globalContext;
	}
	
	@Override
	public String toString(){
		return action.getName()+"\n"+globalContext.toString();
	}

	public ChoicePoint getAlternatives() {
		return otherChoice;
	}

	public Check getGenerativeCheck() {
		return generativeCheck;
	}

	public void setGenerativeCheck(Check generativeCheck) {
		this.generativeCheck = generativeCheck;
	}

}
