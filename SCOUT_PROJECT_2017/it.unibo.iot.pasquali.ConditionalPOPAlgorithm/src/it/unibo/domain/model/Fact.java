package it.unibo.domain.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Fact implements Serializable{

	private String name;
	private ArrayList<String> params;
	
	private Action step;
	
	private String p;
	
	public Fact(String name, Action step){
		this.name = name;
		this.params = new ArrayList<String>();
		p = "";
		this.step = step;
	}
	
	public Action getStep() {
		return step;
	}

	public void addParam(String param){
		if(!params.contains(param))
		{
			if(!p.equals(""))
				p+=",";
			
			params.add(param);
			
			p += param;
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<String> getParams() {
		return params;
	}

	public void setParams(ArrayList<String> params) {
		this.params = params;
	}
	
	@Override
	public String toString(){
		return name + "("+ p +")";
	}

	public void setStep(Action s) {
		this.step = s;		
	}
	
	@Override
	public boolean equals(Object o)
	{
		if(!(o instanceof Fact))
			return false;
		else
			return ((Fact) o).toString().equals(this.toString());
	}
	
	public boolean getTruthValue(){
		return !name.contains("not ");
	}
	
	public String getKenrel(){
		if(!getTruthValue())
			return name.replace("not ", "")+ "("+ p +")";
		
		return toString();
	}
}
