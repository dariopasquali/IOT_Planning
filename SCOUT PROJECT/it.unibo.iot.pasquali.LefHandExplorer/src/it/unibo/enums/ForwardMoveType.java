package it.unibo.enums;

public enum ForwardMoveType {
	
		TILED ("T", 10),
		DIAGONAL ("D", 15);
		
		private final String direction;
		private final int cost;
		
	    private ForwardMoveType(String s, int cost) { direction = s; this.cost = cost; }
	    public boolean equalsDirection(String otherDir){ return otherDir == direction; }
	    public int getCost(){return cost;}
	    public String toString(){switch(direction){
	    case "T": return "t";
	    case "D": return "d";
	    default: return "";
	    }
    }
}
