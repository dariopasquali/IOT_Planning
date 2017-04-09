package it.unibo.domain.model.conditional;

import it.unibo.domain.graph.State;
import it.unibo.domain.model.Fact;
import it.unibo.enums.ActionType;

public class Check extends ConditionalAction{

	/*
	 * check(From, To, PRElist, MutualEFFlist, 0).
	 * 
	 * check(From, To, [at(From)], [clear(From, To), not clear(From, To)], 0).
	 */
	
	private State from, to;
	
	public Check(State from, State to) {
		super("move");
		this.from = from;
		this.to = to;
		
		init();		
	}
	
	private void init(){
		
		this.type = ActionType.CHECK;
		
		Fact f = new Fact("at", this);
		
		f.addParam(from.toString());
		this.addPre(f);
		
		f = new Fact("clear", this);
		f.addParam(from.toString());
		f.addParam(to.toString());
		this.addEffect(f);
		
		f = new Fact("not clear", this);
		f.addParam(from.toString());
		f.addParam(to.toString());
		this.addEffect(f);
		
	}
	
	@Override
	public void addEffect(Fact f)
	{
		
	}
	
	
}
