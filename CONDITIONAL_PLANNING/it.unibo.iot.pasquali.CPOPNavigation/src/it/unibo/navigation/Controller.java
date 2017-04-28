package it.unibo.navigation;

import java.awt.Point;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.swing.JTextArea;
import javax.swing.SwingWorker;

import it.unibo.domain.graph.Graph;
import it.unibo.domain.graph.State;
import it.unibo.domain.model.conditional.Check;
import it.unibo.domain.model.conditional.ConditionalAction;
import it.unibo.domain.model.conditional.Move;
import it.unibo.enums.PlanningMode;
import it.unibo.execution.domain.CMove;
import it.unibo.execution.domain.CSense;
import it.unibo.execution.domain.CSpin;
import it.unibo.execution.domain.CStep;
import it.unibo.execution.enums.CDirection;
import it.unibo.execution.enums.ConditionalMoveType;
import it.unibo.execution.enums.SpinAngle;
import it.unibo.gui.MapViewer;
import it.unibo.model.map.Map;
import it.unibo.planning.ConditionalPlanNode;
import it.unibo.planning.Plan;
import it.unibo.planning.algo.Planner;
import it.unibo.utils.HeuristicEvaluator;

public class Controller {

	private Map map;
	private PlanningMode planningMode;
	private List<ConditionalPlanNode> plan;
	
	private List<CMove> moves;
	private Engine engine;
	
	private MapViewer view;
	private JTextArea txtOut;
	
	private State start;
	
	private Agent navigationAgent;
	
	
	private HashMap<Integer, Choose> alternatives;
	private ArrayDeque<CMove> reverse;
	int parentID = -1;	
	private Choose choose;
	private boolean keepNav;
	private int nextStepPointer;
	private int failID;
	
	
	public void setPlanningMode(PlanningMode mode){
		this.planningMode = mode;
	}

	public void setMap(Map map) {
		if(map != null)
			this.map = map;				
	}

	public List<ConditionalPlanNode> createPlan(State start, State goal) {
		
		this.start = start;
		
		Graph graph = null;
		Map m = map;
		
		if(planningMode.equals(PlanningMode.WITH_OBJECTS))
		{
			graph = getPOPGraph(map.getIntMap(), map.getXmax(), map.getYmax());
		}
		
		else
		{
			graph = getPOPGraphNoObjects(map.getNoObjects(), map.getXmax(), map.getYmax());
			m = new Map(map.getYmax(), map.getXmax(), map.getNoObjects());
		}
		
		printGraph(graph);
		
		Planner planner = new Planner(start, goal, graph, new HeuristicEvaluator(m));		
		
		try
		{
			plan = planner.findPlan().numbering();
			return plan;
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		return null;
	}

	private void printGraph(Graph graph) {
		
		for(ConditionalAction a : graph.getActions())
			System.out.println(a.getShortName());
		
	}

	public void initNavigation(MapViewer mapViewer) {

		this.view = mapViewer;
		
		moves = Plan.expandPlan(plan, SpinAngle.d90);
		
		println("\n-------------------------------------------\n");
		println("EXPANDED RUNNABLE CONDITIONAL PLAN\n");
		
		for(CMove m : moves)
			println(m.toString());	
		
		engine = new Engine(new Point(start.getX(), start.getY()), CDirection.NORTH);
		
		navigationAgent = new Agent(engine, map, view, moves);
		
		alternatives = new HashMap<Integer, Choose>();
		reverse = new ArrayDeque<CMove>();
		choose = new Choose(parentID, -1, -1);
		keepNav = true;
		nextStepPointer = 0;
		failID = findFailID();
		
//		setObstacle(2, 3);
//		view.setCellObj(3, 2);
	}

	
	public void navigate() {
		navigationAgent.execute();		
	}

	
	public boolean nextStep() {
		
		if(nextStepPointer < moves.size() && keepNav)
		{
			keepNav = navigationStep(nextStepPointer);
			nextStepPointer++;
		}
		
		return keepNav;		
	}

	
	
	private boolean navigationStep(int i) {

		CMove move = moves.get(i);			
		
		switch(move.getType()){
		
		case STEP:				
			Point next = engine.makeMove((CStep) move);				
			view.updateCurrentPosition(next.y, next.x, engine.getDirection().toString());				
			reverse.push(((CStep)move).getReverse());
			
			System.out.println("STEP");
			break;				
			
			
		case SPIN:
			CDirection ndir = engine.makeSpin((CSpin) move);				
			view.updateCurrentPosition(engine.getPosition().y, engine.getPosition().x, ndir.toString());				
			reverse.push(((CSpin)move).getReverse());
			
			System.out.println("SPIN "+ move.toString());
			break;
			
			
		case SENSE:
			
			System.out.println("SENSE");
			
			choose.setReverse(reverse);
			alternatives.put(choose.getId(), choose);
			reverse = new ArrayDeque<CMove>();
			
			if(sense(engine.getPosition(), engine.getDirection())) //next position is CLEAR
			{
				choose = new Choose(parentID, i, ((CSense)move).getBranchIDNotClear());
				parentID = i;
			}
			else
			{
				choose = new Choose(parentID, i, failID);
				parentID = i;
				nextStepPointer = ((CSense)move).getBranchIDNotClear()-1;
			}				
			break;
			
		
			
		case FAIL:
			
			Choose backtrack = alternatives.remove(parentID);
			
			if(!alternatives.isEmpty() && backtrack == null)
				backtrack = alternatives.remove(choose.getParent());
			
			if(backtrack == null || choose.getParent() == -1)
			{
				println("FAIL");
				return false;						
			}
			else
			{
				while(!backtrack.getReverse().isEmpty())
				{
					CMove m = backtrack.getReverse().pop();
					
					if(m.getType().equals(ConditionalMoveType.STEP))
					{
						engine.makeMove(m);
						view.updateCurrentPosition(engine.getPosition().y, engine.getPosition().x, engine.getDirection().toString());
					}
					else
					{
						engine.makeSpin(m);
						view.updateCurrentPosition(engine.getPosition().y, engine.getPosition().x, engine.getDirection().toString());
					}
				}
				
				parentID = backtrack.getParent();
				nextStepPointer = backtrack.getAlternativeID()-1;					
			}				
			break;
			
			
			
		case STOP:				
			println("END OF EXECUTION");
			return false;
			
		default:
			return true;			
		
		}	
		
		
//		try {
//			Thread.sleep(700);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
		
		return true;
	}
	
	private boolean sense(Point position, CDirection direction) {

		Point next = null;
		
		switch(direction)
		{
		case NORTH:
			next = new Point(position.x, position.y-1);
			break;
			
		case EAST:
			next = new Point(position.x+1, position.y);
			break;
			
		case SOUTH:
			next = new Point(position.x, position.y+1);
			break;
			
		case WEST:
			next = new Point(position.x-1, position.y);
			break;
			
		default:
			break;
		}
		
		return map.isCellClear(next.y, next.x);		
		
	}
	
	
	public void setObstacle(int x, int y) {
		map.setCellObj(y, x);
	}
	
	
	
	
	private class Agent extends SwingWorker<Boolean, CState>{

		private Engine engine;
		private Map map;
		private MapViewer view;
		private List<CMove> moves;
		
		public Agent(Engine engine, Map map, MapViewer view, List<CMove> moves)
		{
			this.engine = engine;
			this.map = map;
			this.view = view;
			this.moves = moves;
		}
		
		
		public void navigate() {

			println("Robot will navigate autonomously from START to GOAL");
			
			HashMap<Integer, Choose> alternatives = new HashMap<Integer, Choose>();
			ArrayDeque<CMove> reverse = new ArrayDeque<CMove>();
			int parentID = -1;
			
			Choose choose = new Choose(parentID, -1, -1);
			
			int failID = findFailID();
			
			
			for(int i=0; i<moves.size(); i++)
			{
				CMove move = moves.get(i);			
				
				switch(move.getType()){
				
				case STEP:				
					Point next = engine.makeMove((CStep) move);				
					//view.updatePosition(next);
					
					publish(new CState(engine.getPosition(), engine.getDirection()));
					
					reverse.push(((CStep)move).getReverse());				
					break;				
					
					
				case SPIN:
					CDirection ndir = engine.makeSpin((CSpin) move);				
					//view.updateDirection(ndir);
					
					publish(new CState(engine.getPosition(), engine.getDirection()));
					
					reverse.push(((CSpin)move).getReverse());
					break;
					
					
				case SENSE:
					
					
					choose.setReverse(reverse);
					alternatives.put(choose.getId(), choose);
					reverse = new ArrayDeque<CMove>();
					
					if(sense(engine.getPosition(), engine.getDirection())) //next position is CLEAR
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
						println("FAIL");
						return;						
					}
					else
					{
						while(!backtrack.getReverse().isEmpty())
						{
							CMove m = backtrack.getReverse().pop();
							
							if(m.getType().equals(ConditionalMoveType.STEP))
							{
								engine.makeMove(m);
								publish(new CState(engine.getPosition(), engine.getDirection()));
							}
							else
							{
								engine.makeSpin(m);
								publish(new CState(engine.getPosition(), engine.getDirection()));
							}
						}
						
						parentID = backtrack.getParent();
						i = backtrack.getAlternativeID()-1;					
					}				
					break;
					
					
					
				case STOP:				
					println("END OF EXECUTION");
					return;
					
				default:
					return;			
				
				}	
				
				
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
			}
			
		}
		



		private boolean sense(Point position, CDirection direction) {

			Point next = null;
			
			switch(direction)
			{
			case NORTH:
				next = new Point(position.x, position.y-1);
				break;
				
			case EAST:
				next = new Point(position.x+1, position.y);
				break;
				
			case SOUTH:
				next = new Point(position.x, position.y+1);
				break;
				
			case WEST:
				next = new Point(position.x-1, position.y);
				break;
				
			default:
				break;
			}
			
			return map.isCellClear(next.y, next.x);		
			
		}


		@Override
		protected void process(List<CState> chunks) {
			super.process(chunks);
			
			for(CState s : chunks)
				view.updateCurrentPosition(s.position.y, s.position.x, s.direction.toString());
		}
		
		@Override
		protected Boolean doInBackground() throws Exception {
			navigate();
			return true;
		}
		
		
		
	}
	
	
	private int findFailID() {
		for(CMove m : moves)
			if(m.getType().equals(ConditionalMoveType.FAIL))
				return moves.indexOf(m);
		
		return -1;
	}
	
	private Graph getPOPGraph(Integer[][] intmap, int xmax, int ymax) {

		String graph = "";
		String moves = "";
		
		Graph g = new Graph();
		
		for(int x=0; x< xmax; x++)
		{
			for(int y=0; y<ymax; y++)
			{
				if(intmap[y][x] == 0)
					g.addState(new State(x,y));
			}
		}				
		
		for(int y=0; y<ymax; y++)
		{
			for(int i=0; i<(xmax-1); i++)
			{
				if(intmap[y][i] == 0 && intmap[y][i+1] == 0)
				{
					State a = new State(i, y);
					State b = new State(i+1, y);
					
					g.addState(a);
					g.addState(b);
					
					Move m = new Move(a, b);										
					g.addMove(m);				
					m = new Move(b, a);					
					g.addMove(m);
					
					Check ch = new Check(a, b);										
					g.addCheck(ch);				
					ch = new Check(b, a);					
					g.addCheck(ch);					
				}
			}
		}
		
		
		for(int x=0; x<xmax; x++)
		{
			for(int j=0; j<(ymax-1); j++)
			{
				if(intmap[j][x] == 0 && intmap[j+1][x] == 0)
				{
					State a = new State(x, j);
					State b = new State(x, j+1);
					
					g.addConnection(a.toString(), b.toString());
					g.addConnection(b.toString(), a.toString());
					
					Move m = new Move(a, b);										
					g.addMove(m);				
					m = new Move(b, a);					
					g.addMove(m);
					
					Check ch = new Check(a, b);										
					g.addCheck(ch);				
					ch = new Check(b, a);					
					g.addCheck(ch);
					
		
				}
			}
		}		
		
		return g;
	}
	
	private Graph getPOPGraphNoObjects(Integer[][] intmap, int xmax, int ymax) {

		Graph g = new Graph();
		
		for(int x=0; x< xmax; x++)
		{
			for(int y=0; y<ymax; y++)
			{
				if(intmap[y][x] == 0)
					g.addState(new State(x,y));
			}
		}				
		
		for(int y=0; y<ymax; y++)
		{
			for(int i=0; i<(xmax-1); i++)
			{
//				if(intmap[y][i] == 0 && intmap[y][i+1] == 0)
//				{
					State a = new State(i, y);
					State b = new State(i+1, y);
					
					g.addState(a);
					g.addState(b);
					
					Move m = new Move(a, b);										
					g.addMove(m);				
					m = new Move(b, a);					
					g.addMove(m);
					
					Check ch = new Check(a, b);										
					g.addCheck(ch);				
					ch = new Check(b, a);					
					g.addCheck(ch);					
//				}
			}
		}
		
		
		for(int x=0; x<xmax; x++)
		{
			for(int j=0; j<(ymax-1); j++)
			{
//				if(intmap[j][x] == 0 && intmap[j+1][x] == 0)
//				{
					State a = new State(x, j);
					State b = new State(x, j+1);
					
					g.addConnection(a.toString(), b.toString());
					g.addConnection(b.toString(), a.toString());
					
					Move m = new Move(a, b);										
					g.addMove(m);				
					m = new Move(b, a);					
					g.addMove(m);
					
					Check ch = new Check(a, b);										
					g.addCheck(ch);				
					ch = new Check(b, a);					
					g.addCheck(ch);
					
		
//				}
			}
		}		
		
		return g;
	}
	
	public void setStdOutput(JTextArea txtOut) {
		this.txtOut = txtOut;		
	}
	
	public void println(String msg) {
		txtOut.append(msg+"\n");
		txtOut.validate();
		txtOut.setCaretPosition(txtOut.getDocument().getLength());
	}
	
}
