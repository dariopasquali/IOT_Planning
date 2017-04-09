package it.unibo.domain.model.conditional;

import it.unibo.domain.model.Fact;
import it.unibo.planning.ChoicePoint;

public class ConditionalLabel {
	
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
	
	
}
