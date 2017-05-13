package it.unibo.guimanager.interfaces;

public interface IGuiManagerBL {
	
	public void showMap(int startX, int startY, String filename);
	public void createActor();
	public void updateState(int x, int y, String direction);	

}
