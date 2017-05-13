package it.unibo.gui.enums;

public enum PlanningMode {

	ONLY_BORDERS, WITH_OBJECTS;
	
	@Override
	public String toString(){
		switch(this){
		case ONLY_BORDERS: return "clear";
		case WITH_OBJECTS: return "objects";
		default: return "";
		}
	}
}
