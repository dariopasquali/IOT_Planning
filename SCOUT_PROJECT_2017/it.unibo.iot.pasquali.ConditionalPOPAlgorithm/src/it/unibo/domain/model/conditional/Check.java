package it.unibo.domain.model.conditional;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import it.unibo.domain.graph.State;
import it.unibo.domain.model.Fact;
import it.unibo.enums.ActionType;
import it.unibo.utils.LabelNameGenerator;

public class Check extends ConditionalAction implements Serializable{

	/*
	 * check(From, To, PRElist, MutualEFFlist, 0).
	 * 
	 * check(From, To, [at(From)], [clear(From, To), not clear(From, To)], 0).
	 */
	
	private State from, to;
	
	private List<ConditionalLabel> labels;
	
	public Check(State from, State to) {
		super("check");
		this.from = from;
		this.to = to;
		
		init(false);		
	}
	
	public Check(State from, State to, List<ConditionalLabel> labels) {
		super("check");
		this.from = from;
		this.to = to;
		this.labels = new ArrayList<ConditionalLabel>(labels);
		
		init(true);		
	}
	
	private void init(boolean clone){
		
		if(!clone) labels = new ArrayList<ConditionalLabel>();
		String label = LabelNameGenerator.getUniqueLabel();
		
		this.type = ActionType.CHECK;
		
		Fact f = new Fact("at", this);
		
		f.addParam(from.toString());
		this.addPre(f);
		
		f = new Fact("clear", this);
		f.addParam(from.toString());
		f.addParam(to.toString());
		this.addEffect(f);		
		if(!clone) labels.add(new ConditionalLabel(f.toString()+"-"+label, 1, f, null));
		
		
		f = new Fact("not clear", this);
		f.addParam(from.toString());
		f.addParam(to.toString());
		this.addEffect(f);
		if(!clone) labels.add(new ConditionalLabel(f.toString()+"-"+label, 2, f, null));
		
	}
	
	public List<ConditionalLabel> getLabels(){
		return labels;
	}
	
	
	public State getFrom() {
		return from;
	}

	public void setFrom(State from) {
		this.from = from;
	}

	public State getTo() {
		return to;
	}

	public void setTo(State to) {
		this.to = to;
	}

	
	@Override
	public boolean equals(Object o)
	{
		if(!(o instanceof Check))
			return false;
		
		Check m = (Check)o;
		
		return super.equals(o) && m.getShortName().equalsIgnoreCase(this.getShortName());		
	}
	
	@Override
	public String toString()
	{
		String ret = "check(";
		
		ret += from+","+to+",[";
		
		for(int i=0; i<pre.size(); i++)
		{
			ret+=pre.get(i).toString();
			if(i != (pre.size()-1))
				ret+=",";
		}
		
		ret += "],[";
		
		for(int i=0; i<eff.size(); i++)
		{
			ret+=eff.get(i).toString();
			if(i != (eff.size()-1))
				ret+=";";
		}
		
		ret += "],";
		ret += (heuristic+cost);
		ret += ").";
		
		return ret;
	}

	@Override
	public ConditionalAction copy() {
		Check cm = new Check(from, to, labels);
		cm.setContext(this.getContext());
		cm.setReason(this.getReason());	
		return cm;
	}
	
	@Override
	public String getShortName(){
		
		return "check( "+from+" , "+to+" )";
		
	}
}
