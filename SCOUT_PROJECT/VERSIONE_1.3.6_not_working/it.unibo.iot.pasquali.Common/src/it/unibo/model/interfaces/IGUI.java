package it.unibo.model.interfaces;

import java.awt.Point;
import java.util.List;

import it.unibo.is.interfaces.IActivityBase;

public interface IGUI {

	
	/**
	 * Set the executor of the GUI action, usually is a QActor.
	 * 
	 * @param  controller the QActor that implment the {@code IActivityBase} interface
	 */
	public void setController(IActivityBase controller);
	
	
	/**
	 * Show the path on the GUI
	 * 
	 * @param path  a list of {@code Point} from a location START to the GOAL
	 */
	public void setPath(List<Point> path);
}
