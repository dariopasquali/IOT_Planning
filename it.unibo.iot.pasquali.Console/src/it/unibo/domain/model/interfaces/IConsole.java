package it.unibo.domain.model.interfaces;

public interface IConsole {
	
	public IMap explore(String EnvType, long maxMilsTime); 							//modifier , primitive
	public void navigate(double goalX, double goalY); 								//modifier , primitive
	public void navigate(double startX, double startY, double goalX, double goalY); //modifier , primitive
	public void adbort(); 															//modifier , primitive
	
	public void storeMap(IMap map, String filepath); 								//modifier , primitive
	public IMap loadMap(String filepath); 											//modifier , primitive
	
	public IMap getMap(); 															//property , primitive
	public String getName(); 															//property , primitive
		
	public String getDefaultRep(); 													// mapping , non-primitive
}
