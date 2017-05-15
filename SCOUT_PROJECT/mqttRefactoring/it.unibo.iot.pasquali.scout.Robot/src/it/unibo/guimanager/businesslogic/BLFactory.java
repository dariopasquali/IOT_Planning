package it.unibo.guimanager.businesslogic;

import it.unibo.guimanager.Guimanager;
import it.unibo.guimanager.interfaces.IGuiManagerBL;

public class BLFactory {

	public enum BLType{
		GUI, UNITY
	}
	
	public static IGuiManagerBL getBusinessLogic(Guimanager actor, BLType type){
		
		switch(type){
		case GUI: return new RobotGuiBL(actor);
		case UNITY: return new UnityBL(actor);
		default : return null;
		}		
	}
	
}
