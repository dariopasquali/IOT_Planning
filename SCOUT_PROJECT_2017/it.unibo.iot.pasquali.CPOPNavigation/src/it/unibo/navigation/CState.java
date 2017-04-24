package it.unibo.navigation;

import java.awt.Point;

import it.unibo.execution.enums.CDirection;

public class CState {

	public CState(Point position, CDirection direction) {
		this.position = position;
		this.direction = direction;
	}
	public Point position;
	public CDirection direction;
}
