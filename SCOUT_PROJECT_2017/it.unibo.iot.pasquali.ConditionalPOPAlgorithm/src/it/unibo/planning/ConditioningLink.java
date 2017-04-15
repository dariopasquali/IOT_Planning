package it.unibo.planning;

import java.io.Serializable;

import it.unibo.domain.model.conditional.ConditionalAction;
import it.unibo.domain.model.conditional.ConditionalLabel;

public class ConditioningLink implements Serializable{
	
	private ConditionalAction before;
	private ConditionalAction after;
	private ConditionalLabel condition;
	
	public ConditioningLink(ConditionalAction before, ConditionalAction after, ConditionalLabel condition) {
		super();
		this.before = before;
		this.after = after;
		this.condition = condition;
	}

	public ConditionalAction getBefore() {
		return before;
	}

	public void setBefore(ConditionalAction before) {
		this.before = before;
	}

	public ConditionalAction getAfter() {
		return after;
	}

	public void setAfter(ConditionalAction after) {
		this.after = after;
	}

	public ConditionalLabel getCondition() {
		return condition;
	}

	public void setCondition(ConditionalLabel condition) {
		this.condition = condition;
	}
	
	@Override
	public String toString(){
		return "conditioning{"+before.toString()+","+condition.toString()+","+after.toString()+"}";
	}
	

}
