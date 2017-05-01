package it.unibo.robot.navutils;

import java.awt.Point;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import it.unibo.execution.domain.*;
import it.unibo.execution.enums.ConditionalMoveType;
import it.unibo.planning.enums.SpinDirection;
import it.unibo.robot.Robot;
import it.unibo.robot.utility.Engine;

public class NavigationController {

	private Engine engine;
	private List<CMove> plan;
	
	public NavigationController(String[] plan, Engine engine) {

		this.plan = new ArrayList<CMove>();
		this.engine = engine;
		
		for(String s : plan)
			this.plan.add(CMove.fromStringToCMove(s));
	}
	
	public boolean navigate()
	{
		System.out.println("Robot will navigate autonomously from START to GOAL");
		
		HashMap<Integer, Choose> alternatives = new HashMap<Integer, Choose>();
		ArrayDeque<CMove> reverse = new ArrayDeque<CMove>();
		int parentID = -1;
		
		Choose choose = new Choose(parentID, -1, -1);
		
		int failID = findFailID();
		
		
		for(int i=0; i<plan.size(); i++)
		{
			CMove move = plan.get(i);			
			
			switch(move.getType()){
			
			case STEP:				
				engine.moveForward();
				
				reverse.push(((CStep)move).getReverse());				
				break;				
				
				
			case SPIN:
				
				if(((CSpin)move).getDirection().equals(SpinDirection.RIGHT))
					engine.turnDoubleRight();
				else
					engine.turnDoubleLeft();
				
				reverse.push(((CSpin)move).getReverse());
				break;
				
				
			case SENSE:				
				
				choose.setReverse(reverse);
				alternatives.put(choose.getId(), choose);
				reverse = new ArrayDeque<CMove>();
				
				if(engine.checkObjFront()) //next position is CLEAR
				{
					choose = new Choose(parentID, i, ((CSense)move).getBranchIDNotClear());
					parentID = i;
				}
				else
				{
					choose = new Choose(parentID, i, failID);
					parentID = i;
					i = ((CSense)move).getBranchIDNotClear()-1;
				}				
				break;
				
			
				
			case FAIL:
				
				Choose backtrack = alternatives.remove(parentID);
				
				if(!alternatives.isEmpty() && backtrack == null)
					backtrack = alternatives.remove(choose.getParent());
				
				if(backtrack == null)
				{
					System.out.println("************FAIL***************");
					return false;						
				}
				else
				{
					while(!backtrack.getReverse().isEmpty())
					{
						CMove m = backtrack.getReverse().pop();
						
						if(m.getType().equals(ConditionalMoveType.STEP))
						{
							if(m.toString().contains("t"))
								engine.moveForward();
							else
								engine.moveBackward();
						}
						else
						{
							if(m.toString().contains("r"))
								engine.turnDoubleRight();
							else
								engine.turnDoubleLeft();
						}
					}
					
					parentID = backtrack.getParent();
					i = backtrack.getAlternativeID()-1;					
				}				
				break;
				
				
				
			case STOP:				
				System.out.println("************END OF EXECUTION***************");
				return true;
				
			default:
				return true;			
			
			}	
			
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
		
		return true;
		
	}
	
	private int findFailID() {
		for(CMove m : plan)
			if(m.getType().equals(ConditionalMoveType.FAIL))
				return plan.indexOf(m);
		
		return -1;
	}

}
