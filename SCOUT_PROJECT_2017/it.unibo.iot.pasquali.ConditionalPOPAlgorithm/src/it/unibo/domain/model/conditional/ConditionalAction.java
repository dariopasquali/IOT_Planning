package it.unibo.domain.model.conditional;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import it.unibo.domain.model.Action;
import it.unibo.enums.ActionType;

public class ConditionalAction extends Action implements Serializable{

	protected List<Goal> reason;
	protected List<ConditionalLabel> context;
	
	protected ActionType type;
	
	
	public ConditionalAction(String name)
	{
		super(name);
		reason = new ArrayList<Goal>();
		context = new ArrayList<ConditionalLabel>();
	}
	
	public ConditionalAction(String name, double heuristic)
	{
		super(name, heuristic);
		context = new ArrayList<ConditionalLabel>();
	}

	public List<Goal> getReason() {
		return reason;
	}

	public List<ConditionalLabel> getContext() {
		return context;
	}
	
	public void addReason(Goal r)
	{
		reason.add(r);
	}
	
	public void addConditionalLabel(ConditionalLabel l)
	{
		context.add(l);
	}

	public ActionType getType() {
		return type;
	}

	public void setType(ActionType type) {
		this.type = type;
	}

	public void mergeReason(List<Goal> toMerge) {
		
		for(Goal g : toMerge)
		{
			if(!reason.contains(g))
				reason.add(g);
		}
		
	}

	public void addConditionalLabels(List<ConditionalLabel> globalContext) {
		
		for(ConditionalLabel l : globalContext)
			if(!context.contains(l))
				context.add(l);
		
	}
	
	
}
