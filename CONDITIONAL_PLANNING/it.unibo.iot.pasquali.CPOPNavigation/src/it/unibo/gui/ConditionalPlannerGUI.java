package it.unibo.gui;

import java.awt.EventQueue;
import java.awt.FileDialog;

import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;

import it.unibo.domain.graph.State;
import it.unibo.enums.PlanningMode;
import it.unibo.gui.enums.CellState;
import it.unibo.model.interfaces.IMapElement;
import it.unibo.model.map.Map;
import it.unibo.model.map.MapElement;
import it.unibo.navigation.Controller;
import it.unibo.planning.ConditionalPlanNode;
import it.unibo.planning.Plan;

import java.awt.Font;
import java.awt.Frame;
import javax.swing.JTextArea;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;


import java.awt.Component;
import javax.swing.Box;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
public class ConditionalPlannerGUI extends Frame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JFrame frame;
	public JFrame outputFrame;

	private MapViewer mapViewer;
	
	private JButton btnLoad;
	
	private Controller controller;
	private JPanel panelMap;	
	private JButton btnSavePlan;
	private JButton btnCreatePlan;
	private JButton btnNavigate;
	private Component horizontalStrut_5;
	private JCheckBox checkOnlyBorders;
	private Component horizontalStrut_6;



	private JTextArea txtOut;
	private JScrollBar scrollBar;
	
	private Plan plan;
	private ClickHandler handler;
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ConditionalPlannerGUI window = new ConditionalPlannerGUI();
					window.frame.setVisible(true);
					window.outputFrame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ConditionalPlannerGUI() {
		
		handler = new ClickHandler();
		
		initialize();
		initializeOutputFrame();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
			
		frame = new JFrame();
		frame.setBounds(100, 100, 911, 485);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{1056, 0};
		gridBagLayout.rowHeights = new int[]{20, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		frame.getContentPane().setLayout(gridBagLayout);
		
		Component verticalStrut = Box.createVerticalStrut(20);
		GridBagConstraints gbc_verticalStrut = new GridBagConstraints();
		gbc_verticalStrut.insets = new Insets(0, 0, 5, 0);
		gbc_verticalStrut.anchor = GridBagConstraints.NORTH;
		gbc_verticalStrut.fill = GridBagConstraints.HORIZONTAL;
		gbc_verticalStrut.gridx = 0;
		gbc_verticalStrut.gridy = 0;
		frame.getContentPane().add(verticalStrut, gbc_verticalStrut);
		
		Box boxMapManager = Box.createHorizontalBox();
		GridBagConstraints gbc_boxMapManager = new GridBagConstraints();
		gbc_boxMapManager.insets = new Insets(0, 0, 5, 0);
		gbc_boxMapManager.gridx = 0;
		gbc_boxMapManager.gridy = 1;
		frame.getContentPane().add(boxMapManager, gbc_boxMapManager);
		
		btnLoad = new JButton("Load Map");
		btnLoad.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnLoad.addActionListener(handler);
		boxMapManager.add(btnLoad);
		
		btnCreatePlan = new JButton("Create Plan");
		btnCreatePlan.setEnabled(false);
		btnCreatePlan.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnCreatePlan.addActionListener(handler);
		boxMapManager.add(btnCreatePlan);
		
		checkOnlyBorders = new JCheckBox("Only Borders");
		checkOnlyBorders.setSelected(false);
		checkOnlyBorders.addChangeListener(new ChangeListener(){

			@Override
			public void stateChanged(ChangeEvent e) {
				
				if(checkOnlyBorders.isSelected())
					controller.setPlanningMode(PlanningMode.ONLY_BORDERS);
				else
					controller.setPlanningMode(PlanningMode.WITH_OBJECTS);
				
			}
			
		});;
		boxMapManager.add(checkOnlyBorders);
		
		horizontalStrut_6 = Box.createHorizontalStrut(20);
		boxMapManager.add(horizontalStrut_6);
		
		btnSavePlan = new JButton("Save Plan");
		btnSavePlan.setEnabled(false);
		btnSavePlan.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnSavePlan.addActionListener(handler);
		boxMapManager.add(btnSavePlan);
		
		horizontalStrut_5 = Box.createHorizontalStrut(20);
		boxMapManager.add(horizontalStrut_5);
		
		btnNavigate = new JButton("Navigate");
		btnNavigate.setEnabled(false);
		btnNavigate.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnNavigate.addActionListener(handler);
		
		boxMapManager.add(btnNavigate);
		
		panelMap = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.insets = new Insets(0, 0, 5, 0);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 2;
		frame.getContentPane().add(panelMap, gbc_panel);
		GridBagLayout gbl_panelMap = new GridBagLayout();
		gbl_panelMap.columnWidths = new int[]{0};
		gbl_panelMap.rowHeights = new int[]{0};
		gbl_panelMap.columnWeights = new double[]{Double.MIN_VALUE};
		gbl_panelMap.rowWeights = new double[]{Double.MIN_VALUE};
		panelMap.setLayout(gbl_panelMap);
		
		
		controller = new Controller();		
		controller.setPlanningMode(PlanningMode.WITH_OBJECTS);
		
		mapViewer = new MapViewer(controller);
	}

	private void initializeOutputFrame()
	{
		outputFrame = new JFrame();
		outputFrame.setBounds(600, 600, 700, 300);
		outputFrame.setTitle("Output Frame");		
		outputFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		GridBagLayout gridBagOutput = new GridBagLayout();
		gridBagOutput.columnWidths = new int[]{1056, 0};
		gridBagOutput.rowHeights = new int[]{20, 0, 0, 0, 0, 0};
		gridBagOutput.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagOutput.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		outputFrame.getContentPane().setLayout(gridBagOutput);
		
		JScrollPane panelOutput = new JScrollPane((Component) null);
		GridBagConstraints gbc_panelOutput = new GridBagConstraints();
		gbc_panelOutput.fill = GridBagConstraints.BOTH;
		gbc_panelOutput.gridheight = 5;
		gbc_panelOutput.gridy = 0;
		outputFrame.getContentPane().add(panelOutput, gbc_panelOutput);
		
		txtOut = new JTextArea();
		txtOut.setEditable(false);
		txtOut.setFont(new Font("Monospaced", Font.PLAIN, 17));
		panelOutput.setViewportView(txtOut);
		
		scrollBar = new JScrollBar();
		panelOutput.setRowHeaderView(scrollBar);
		panelOutput.add(scrollBar);		
		
		controller.setStdOutput(txtOut);
	}
	
	private class ClickHandler implements ActionListener{

		boolean initDone = false;
		
		@Override
		public void actionPerformed(ActionEvent e) {

			switch(e.getActionCommand()){
			
			case "Load Map":
				
				Map m = loadMap();	
				
				if(m == null)
					return;
				
				controller.setMap(m);
				setMap(m);
				
				btnCreatePlan.setEnabled(true);
				
				println("Left Click --> select START position");
				println("Right Click --> select GOAL position");
				println("Wheel Click --> plance obstacle and erase position");
				println("If you tick the 'Only Borders' checkbox,\n the system creates a more generic plan, but it requires more time");
				
				break;
				
			case "Create Plan":
				
				MapElement start = mapViewer.getStart();
				if(start == null)
				{
					println("START non selezionato");
					break;
				}
				
				MapElement goal = mapViewer.getGoal();
				if(goal == null)
				{
					println("GOAL non selezionato");
					break;
				}
				
				
				long s = System.currentTimeMillis();
				
				plan = controller.createPlan(new State(start.getX(), start.getY()), new State(goal.getX(), goal.getY()));
				
				println("TIME ---> "+(System.currentTimeMillis()-s));
				
				
				showPlan(plan.numbering());
				
				btnSavePlan.setEnabled(true);
				btnNavigate.setEnabled(true);
				
				break;
				
			case "Save Plan":
				
				storePlan(plan);
				
				break;
				
			case "Navigate":
				
				mapViewer.setMapState(MapViewer.NAVIGATION);
				
				println("The agent will navigate autonomously in the map");
				
				controller.initNavigation(mapViewer);
				
				controller.navigate();
				mapViewer.setMapState(MapViewer.PLANNING);
				
				break;
				
//			case "Next Step (manual)":
//				
//				if(!initDone)
//				{
//					println("the agent will navigate in the map following the generated plan");
//					println("click this button for every step to navigate");
//					println("You can click a CLEAR cell in order to create a new obstacle");
//					
//					btnNavigate.setEnabled(false);
//					mapViewer.setMapState(MapViewer.NAVIGATION);
//					
//					controller.initNavigation(mapViewer);
//					
//					initDone = true;
//				}
//				
//				initDone = controller.nextStep();				
//				break;			
			}
			
			
		}
		
	}
	
	
	
	
	public Map loadMap(){
		
		FileDialog loadDialog = new FileDialog(this, "Choose the map", FileDialog.LOAD);
		loadDialog.setDirectory("C:\\");
		loadDialog.setFile("");
		loadDialog.setVisible(true);
		String filename = loadDialog.getDirectory()+loadDialog.getFile();
		
		if(filename.contains("null"))
			return null;
		
		Map m = null;
		List<String> data = new ArrayList<String>();						
		try
		{
			InputStream fs = new FileInputStream(filename);
			InputStreamReader inpsr = new InputStreamReader(fs);
			BufferedReader br       = new BufferedReader(inpsr);
			Iterator<String> lsit   = br.lines().iterator();

		while(lsit.hasNext())
		{
			data.add(lsit.next());
		}
		br.close();
			
		} catch (Exception e)
		{
			System.out.println("QActor  ERROR " + e.getMessage());
		}
				
		for(int i=0; i<data.size(); i++)
		{
			if(i == 0)
			{
				m = Map.createMapFromPrologRep(data.get(i));
			}
			else
			{
				String s[] = data.get(i).split(" ");
				m.addElementFromString(s[1]);
			}
		}
		
		return m;			
	}	
	
	public void storePlan(Plan plan){
		
		FileDialog storeDialog = new FileDialog(frame, "Create new Plan", FileDialog.SAVE);
		storeDialog.setDirectory("C:\\");
		storeDialog.setVisible(true);
		String fname = storeDialog.getDirectory()+storeDialog.getFile();
		if(fname.contains("null"))
			return;
		
		String head = "/*"
				+ "\n*"
				+ "\n* A GraphViz DOT FSM description"
				+ "\n* Please use a GraphViz visualizer (like http://www.webgraphviz.com)"
				+ "\n*"
				+ "\n* Generated by DP"
				+ "\n*"
				+ "\n*/";
		
		String numbered = "";
		for(ConditionalPlanNode n : plan.numbering())
			numbered += n.toString()+"\n";
		
		String graphviz = head + "\n\n\n" + plan.getGraphvizRepresentation();
		
		
		try
		{
			FileOutputStream fsout = new FileOutputStream( new File(fname+".txt") );
			fsout.write(numbered.getBytes());
			fsout.close();		
			
			fsout = new FileOutputStream( new File(fname+".gviz") );
			fsout.write(graphviz.getBytes());
			fsout.close();
			
		} catch (Exception e) {
			e.printStackTrace();
 		}
	}
	
	public void showPlan(List<ConditionalPlanNode> plan) {

		if(plan == null)
		{
			println("ERROR! plan is NULL");
			return;
		}
		
		println("\n---------------------------\n");
		println("CONDITIONAL PLAN");
		
		for(ConditionalPlanNode n : plan)
			println(n.toString());		
	}

	public void setMap(Map map) {

		mapViewer.createGridPanel(map.getYmax(), map.getXmax());
		List<IMapElement> elements = map.getElements();
		
		for(IMapElement e : elements)
		{
			mapViewer.setCellState(e.getY(), e.getX(), CellState.OBJECT);
		}
		
		panelMap.removeAll();
		
		GridBagConstraints contraint = new GridBagConstraints();
		contraint.fill = GridBagConstraints.BOTH;
		panelMap.add(mapViewer.getPanel(), contraint);
		panelMap.setVisible(true);
		
		frame.revalidate();
		frame.repaint();
		
		mapViewer.setMap(map);
	}	
	
	public void println(String msg) {
		txtOut.append(msg+"\n");
		txtOut.validate();
		txtOut.setCaretPosition(txtOut.getDocument().getLength());
	}
}
