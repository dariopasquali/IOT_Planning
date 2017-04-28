package it.unibo.domain.model.interfaces;

public interface IRobot {
	
	public void move(String dir, double speed, double mils);	//modifier , priitive
	public void move(String dir, int steps);	//modifier , priitive
	public void turn(double angle, double speed);	//modifier , priitive
	public void stop();		//modifier , priitive
	
	public void setMap(IMap map);	// property , primitive
	public IMap getMap();	// property , primitive
	
	public void navigateTo(double goalX, double goalY);
	public void navigate(double startX, double startY, double goalX, double goalY); //modifier , priitive
	
	public IMap explore(String EnvType, long maxMilsTime);	//modifier , priitive
	
	public String getName();		// property , primitive
	public String getDefaultRep();	// mapping , non-primitive

}
