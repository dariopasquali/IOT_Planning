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
	
	protected List<ConditionalAction> successors;
	
	
	public ConditionalAction(String name)
	{
		super(name);
		reason = new ArrayList<Goal>();
		context = new ArrayList<ConditionalLabel>();
		successors = new ArrayList<ConditionalAction>();
	}
	
	public ConditionalAction(String name, double heuristic)
	{
		super(name, heuristic);
		context = new ArrayList<ConditionalLabel>();
		reason = new ArrayList<Goal>();
		successors = new ArrayList<ConditionalAction>();
	}

	public List<Goal> getReason() {
		return reason;
	}

	public List<ConditionalLabel> getContext() {
		return context;
	}
	
	protected void setContext(List<ConditionalLabel> context){
		this.context = new ArrayList<ConditionalLabel>(context);
	}
	
	protected void setReason(List<Goal> reason){
		this.reason = new ArrayList<Goal>(reason);
	}
	
	public void addReason(Goal r)
	{
		reason.add(r);
	}
	
	public void addConditionalLabel(ConditionalLabel l)
	{
		if(!context.contains(l))
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
	
	public void addReasons(List<Goal> goals) {
		
		for(Goal l : goals)
			if(!reason.contains(l))
				reason.add(l);
		
	}
	
	public ConditionalAction copy(){
		ConditionalAction a = new ConditionalAction(name);
		a.setContext(context);
		a.setReason(reason);
		return a;
	}
	
	public String getShortName(){
		return name;
	}
	
	@Override
	public boolean equals(Object o){
		
		if(!(o instanceof ConditionalAction))
			return false;
		
		return super.equals(o) &&
				((ConditionalAction)o).getContext().containsAll(context);
		
	}
	
	public void addSuccessor(ConditionalAction next)
	{
		this.successors.add(next);
	}
	
	public List<ConditionalAction> getSuccessors(){
		return successors;
	}

	public String getHash() {
		return "";
	}
	
	public String getGraphvizLabel()
	{
		String s = "";
		for(Goal l : reason)
		s+=""+l.getAction().getShortName().replace("stop", "");
		
//		for(ConditionalLabel l : context)
//			s+=""+l.getUniqueLabel();
		
		return getShortName()+s;
	}
}
