package it.unibo.domain.model.conditional;

import java.util.List;

public class Goal {
	
	private String name;
	private List<ConditionalLabel> globalContext;
	
	public Goal(String name, List<ConditionalLabel> globalContext) {
		super();
		this.name = name;
		this.globalContext = globalContext;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public List<ConditionalLabel> getGlobalContext() {
		return globalContext;
	}
	
	public void setGlobalContext(List<ConditionalLabel> globalContext) {
		this.globalContext = globalContext;
	}
	
	

}
