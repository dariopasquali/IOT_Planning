package it.unibo.interfaces;

import java.util.List;

import it.unibo.execution.domain.CMove;
import it.unibo.planning.ConditionalPlanNode;

public interface IRunnablePopPlan {

	public List<ConditionalPlanNode> numbering();	
	
}
