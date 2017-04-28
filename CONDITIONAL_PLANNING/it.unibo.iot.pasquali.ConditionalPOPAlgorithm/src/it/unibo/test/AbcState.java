package it.unibo.test;

import it.unibo.domain.graph.State;

public class AbcState extends it.unibo.domain.graph.State{

	private String name;
	
	public AbcState(String name) {
		super(-1,-1);
		this.name = name;
	}
	
	@Override
	public boolean equals(Object o)
	{
		if(!(o instanceof AbcState))
			return false;
		
		return ((AbcState)o).getName() == name;
	}
	
	public String getName() {
		
		return name;
	}
	
	@Override
	public String toString(){
		return name;
	}

	@Override
	public int compareTo(State o) {
		
		if(!(o instanceof AbcState))
			return -1;
		
		if(((AbcState)o).getName().equals(name))
			return 0;
		
		return -1;
	}
	

}
