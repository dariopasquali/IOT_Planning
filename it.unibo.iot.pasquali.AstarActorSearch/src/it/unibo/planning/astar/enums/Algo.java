package it.unibo.planning.astar.enums;

public enum Algo {
	ONLY_TILED("tiled"),
	TILED_DIAGONAL("diago");
	
	private String value;
	private Algo(String v){
		this.value = v;
	}
	
	public String getValue(){
		return value;
	}
	
	public static Algo fromStringValue(String value)
	{
		if(value.equalsIgnoreCase("tiled"))
			return Algo.ONLY_TILED;
		else if(value.equalsIgnoreCase("diago"))
			return Algo.TILED_DIAGONAL;
		else
			return null;
	}
}
