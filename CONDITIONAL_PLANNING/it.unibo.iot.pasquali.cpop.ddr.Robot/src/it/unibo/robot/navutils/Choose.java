package it.unibo.robot.navutils;

import java.util.ArrayDeque;

import it.unibo.execution.domain.CMove;

public class Choose{
	
	private int parent, ID;
	private ArrayDeque<CMove> reverse;
	private int alternativeID;
	
	public Choose(int parent, int ID, int aID){
		this.alternativeID = aID;
		this.ID = ID;
		this.parent = parent;
	}

	public int getId() {
		return ID;
	}

	public void setId(int id) {
		this.ID = id;
	}

	public ArrayDeque<CMove> getReverse() {
		return reverse;
	}

	public void setReverse(ArrayDeque<CMove> reverse) {
		this.reverse = reverse;
	}

	public int getAlternativeID() {
		return alternativeID;
	}

	public void setAlternativeID(int alternativeID) {
		this.alternativeID = alternativeID;
	}
	
	@Override
	public String toString()
	{
		return ID+" "+reverse.toString()+" "+ alternativeID;
	}
	
	@Override
	public boolean equals(Object o){
		
		if(!(o instanceof Choose))
			return false;
		
		return ((Choose)o).getId() == this.getId();
		
	}
	
	public int getParent()
	{
		return parent;
	}

}
