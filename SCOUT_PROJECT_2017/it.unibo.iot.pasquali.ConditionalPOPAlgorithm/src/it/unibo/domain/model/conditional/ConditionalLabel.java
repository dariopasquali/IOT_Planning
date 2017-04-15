package it.unibo.domain.model.conditional;

import java.io.Serializable;

import it.unibo.domain.model.Fact;
import it.unibo.planning.ChoicePoint;

public class ConditionalLabel implements Serializable{
	
	private String rootName;
	
	private int ID;
	
	private Fact value;
	
	private ChoicePoint choice;

	public ConditionalLabel(String rootName,int ID, Fact value, ChoicePoint choice) {
		super();
		this.rootName = rootName;
		this.ID = ID;
		this.value = value;
		this.choice = choice;
	}

	

	public String getRootName() {
		if(rootName.contains("not "))
			return rootName.replace("not ", "");
		
		return rootName;
	}



	public void setRootName(String rootName) {
		this.rootName = rootName;
	}



	public int getID() {
		return ID;
	}



	public void setID(int iD) {
		ID = iD;
	}



	public Fact getValue() {
		return value;
	}

	public void setValue(Fact value) {
		this.value = value;
	}

	public ChoicePoint getChoice() {
		return choice;
	}

	public void setChoice(ChoicePoint choice) {
		this.choice = choice;
	}
	
	@Override
	public String toString()
	{
		return rootName+"-"+ID;
	}


	@Override
	public boolean equals(Object o){
		if(!(o instanceof ConditionalLabel))
			return false;
		
		ConditionalLabel l = (ConditionalLabel)o;
		
		return l.getRootName().equals(rootName) && l.getID() == ID;				
	}

	public boolean sameRootDifferentID(ConditionalLabel g) {
		//TODO
		String rg = g.getRootName();
		
		if(rg.equals(rootName))
			if(g.getID() != ID)
				return true;
		
		return false;	
	}
	
}
