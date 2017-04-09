package it.unibo.domain.model.conditional;

import java.util.ArrayList;
import java.util.List;

import it.unibo.domain.model.Action;
import it.unibo.enums.ActionType;

public class ConditionalAction extends Action{

	protected List<Goal> reason;
	protected List<ConditionalLabel> context;
	
	protected ActionType type;
	
	
	public ConditionalAction(String name)
	{
		super(name);
		reason = new ArrayList<Goal>();
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
	
	
}
