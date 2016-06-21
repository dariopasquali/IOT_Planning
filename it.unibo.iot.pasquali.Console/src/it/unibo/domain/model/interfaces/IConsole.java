package it.unibo.domain.model.interfaces;

public interface IConsole {
	
	public IMap explore(String EnvType, long maxMilsTime); 							//modifier , primitive
	public void navigate(double goalX, double goalY); 								//modifier , primitive
	public void navigate(double startX, double startY, double goalX, double goalY); //modifier , primitive
	public void abort(); 															//modifier , primitive
	
	public void storeMap(String filepath) throws Exception; 								//modifier , primitive
	public IMap loadMap(String filepath); 											//modifier , primitive
	public void updateMap(IMapElement newElement);
	
	public IMap getMap(); 															//property , primitive
	public String getName(); 															//property , primitive
	
	public boolean isExploring();
	public boolean isNavigating();
		
	public String getDefaultRep(); 													// mapping , non-primitive
}
