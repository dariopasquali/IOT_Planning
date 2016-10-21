package it.unibo.search.algofactory;

public enum Algo {
	INCLUDE_DIRECTION("dir"),
	ONLY_POSITION("pos");
	
	private String value;
	private Algo(String v){
		this.value = v;
	}
	
	public String getValue(){
		return value;
	}
	
	public static Algo fromStringValue(String value)
	{
		if(value.equalsIgnoreCase("dir"))
			return Algo.INCLUDE_DIRECTION;
		else if(value.equalsIgnoreCase("pos"))
			return Algo.ONLY_POSITION;
		else
			return null;
	}
}
