package it.unibo.model.interfaces;

import java.awt.Point;
import java.util.List;

import it.unibo.is.interfaces.IActivityBase;

public interface IGUI {

	public void setController(IActivityBase controller);
	public void setPath(List<Point> path);
}
