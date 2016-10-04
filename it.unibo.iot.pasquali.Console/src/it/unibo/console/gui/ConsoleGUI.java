package it.unibo.console.gui;

import java.awt.EventQueue;
import java.awt.FileDialog;

import javax.swing.JFrame;
import java.awt.Component;
import javax.swing.Box;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import it.unibo.console.AbstractConsole;
import it.unibo.console.gui.MapViewerPanel.CellState;
import it.unibo.domain.model.implmentation.Map;
import it.unibo.domain.model.implmentation.MapElement;
import it.unibo.domain.model.interfaces.IMapElement;
import it.unibo.is.interfaces.IActivity;
import it.unibo.is.interfaces.IActivityBase;
import it.unibo.is.interfaces.IBasicEnvAwt;
import it.unibo.is.interfaces.IOutputEnvView;
import it.unibo.is.interfaces.IOutputView;
import it.unibo.planning.astar.algo.Engine;
import it.unibo.planning.astar.domain.Move;
import it.unibo.planning.astar.domain.State;
import it.unibo.planning.astar.domain.State.Direction;

import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Frame;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JScrollBar;
import javax.swing.UIManager;
import java.awt.Color;

public class ConsoleGUI extends Frame implements IOutputEnvView, IBasicEnvAwt{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JFrame frame;
	
	private IActivityBase controller;
	
	private JTextArea txtOut;
	private JScrollBar scrollBar;
	
	private JPanel panelMap;
	private JSplitPane splitMapAndOutput;
	private MapViewerPanel mapViewer;
	
	private Map map;
	private ArrayList<State> path;
	
	JButton btnFindPath, btnNavigate, btnManipulate, btnAbort, btnLoad;
	
	//private JFileChooser fileLoader, fileSaver;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ConsoleGUI window = new ConsoleGUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ConsoleGUI(IActivityBase controller) {
		initialize();
		this.controller = controller;
	}
	
	public ConsoleGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1134, 623);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{1056, 0};
		gridBagLayout.rowHeights = new int[]{20, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
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
		btnLoad.addActionListener(new DefaultInputHandler());
		btnLoad.setFont(new Font("Tahoma", Font.PLAIN, 15));
		boxMapManager.add(btnLoad);
		
		Component verticalStrut_1 = Box.createVerticalStrut(20);
		GridBagConstraints gbc_verticalStrut_1 = new GridBagConstraints();
		gbc_verticalStrut_1.insets = new Insets(0, 0, 5, 0);
		gbc_verticalStrut_1.gridx = 0;
		gbc_verticalStrut_1.gridy = 2;
		frame.getContentPane().add(verticalStrut_1, gbc_verticalStrut_1);
		
		JSplitPane bodyPanel = new JSplitPane();
		bodyPanel.setContinuousLayout(true);
		bodyPanel.setOneTouchExpandable(true);
		GridBagConstraints gbc_bodyPanel = new GridBagConstraints();
		gbc_bodyPanel.gridheight = 2;
		gbc_bodyPanel.insets = new Insets(0, 0, 5, 0);
		gbc_bodyPanel.fill = GridBagConstraints.BOTH;
		gbc_bodyPanel.gridx = 0;
		gbc_bodyPanel.gridy = 3;
		frame.getContentPane().add(bodyPanel, gbc_bodyPanel);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Navigation", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		bodyPanel.setLeftComponent(panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{136, 0, 46, 0};
		gbl_panel.rowHeights = new int[]{0, 14, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_panel.columnWeights = new double[]{1.0, 1.0, 1.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		Component verticalStrut_2 = Box.createVerticalStrut(10);
		GridBagConstraints gbc_verticalStrut_2 = new GridBagConstraints();
		gbc_verticalStrut_2.insets = new Insets(0, 0, 5, 5);
		gbc_verticalStrut_2.gridx = 1;
		gbc_verticalStrut_2.gridy = 0;
		panel.add(verticalStrut_2, gbc_verticalStrut_2);
		
		JTextArea txtInstructions = new JTextArea();
		txtInstructions.setText("- Load The Map\r\n- Left Click = Set START\r\n- Right Click = Set GOAL\r\n- Middle Click = CLEAR\r\n- Search Best Path\r\n- Start Navigation");
		txtInstructions.setEditable(false);
		txtInstructions.setEnabled(false);
		GridBagConstraints gbc_txtInstructions = new GridBagConstraints();
		gbc_txtInstructions.gridheight = 2;
		gbc_txtInstructions.gridwidth = 3;
		gbc_txtInstructions.insets = new Insets(0, 0, 5, 0);
		gbc_txtInstructions.fill = GridBagConstraints.BOTH;
		gbc_txtInstructions.gridx = 0;
		gbc_txtInstructions.gridy = 1;
		panel.add(txtInstructions, gbc_txtInstructions);
		
		btnFindPath = new JButton("Find Best Path");
		btnFindPath.setFont(new Font("Tahoma", Font.PLAIN, 15));
		GridBagConstraints gbc_btnFindPath = new GridBagConstraints();
		gbc_btnFindPath.gridwidth = 3;
		gbc_btnFindPath.insets = new Insets(0, 0, 5, 0);
		gbc_btnFindPath.gridx = 0;
		gbc_btnFindPath.gridy = 5;
		panel.add(btnFindPath, gbc_btnFindPath);
		btnFindPath.addActionListener(new DefaultInputHandler());
		btnFindPath.setEnabled(false);
		
		btnNavigate = new JButton("Start Navigation");
		btnNavigate.setFont(new Font("Tahoma", Font.PLAIN, 15));
		GridBagConstraints gbc_btnNavigate = new GridBagConstraints();
		gbc_btnNavigate.gridwidth = 3;
		gbc_btnNavigate.insets = new Insets(0, 0, 5, 0);
		gbc_btnNavigate.gridx = 0;
		gbc_btnNavigate.gridy = 7;
		panel.add(btnNavigate, gbc_btnNavigate);
		btnNavigate.addActionListener(new DefaultInputHandler());
		btnNavigate.setEnabled(false);
		
		btnManipulate = new JButton("Manipulate");
		btnManipulate.setFont(new Font("Tahoma", Font.PLAIN, 15));
		GridBagConstraints gbc_btnManipulate = new GridBagConstraints();
		gbc_btnManipulate.anchor = GridBagConstraints.SOUTH;
		gbc_btnManipulate.gridwidth = 3;
		gbc_btnManipulate.insets = new Insets(0, 0, 5, 0);
		gbc_btnManipulate.gridx = 0;
		gbc_btnManipulate.gridy = 9;
		panel.add(btnManipulate, gbc_btnManipulate);
		//btnManipulate.addActionListener(new DefaultInputHandler());
		btnManipulate.setEnabled(false);
		
		btnAbort = new JButton("Abort");
		btnAbort.setFont(new Font("Tahoma", Font.PLAIN, 15));
		GridBagConstraints gbc_btnAbort = new GridBagConstraints();
		gbc_btnAbort.anchor = GridBagConstraints.ABOVE_BASELINE;
		gbc_btnAbort.gridwidth = 3;
		gbc_btnAbort.gridx = 0;
		gbc_btnAbort.gridy = 13;
		panel.add(btnAbort, gbc_btnAbort);
		btnAbort.addActionListener(new DefaultInputHandler());
		btnAbort.setEnabled(false);
		
		splitMapAndOutput = new JSplitPane();
		bodyPanel.setRightComponent(splitMapAndOutput);
		
		mapViewer = new MapViewerPanel();	
		
		JScrollPane panelOutput = new JScrollPane((Component) null);
		splitMapAndOutput.setRightComponent(panelOutput);
		splitMapAndOutput.setLeftComponent(new JPanel());
		
		txtOut = new JTextArea();
		txtOut.setRows(21);
		txtOut.setFont(new Font("Monospaced", Font.PLAIN, 14));
		txtOut.setEditable(false);	
		panelOutput.setViewportView(txtOut);
		
		scrollBar = new JScrollBar();
		panelOutput.setRowHeaderView(scrollBar);
		panelOutput.add(scrollBar);
		
		
		/*
		fileLoader = new JFileChooser();
		fileLoader.setFileSelectionMode(JFileChooser.FILES_ONLY);
		
		fileSaver = new JFileChooser();
		fileSaver.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		*/
	}



	private class DefaultInputHandler implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			
			switch(e.getActionCommand())
			{
			case "Load Map":				
				FileDialog loadDialog = new FileDialog(frame, "Choose the Map", FileDialog.LOAD);
				loadDialog.setDirectory("C:\\");
				loadDialog.setFile("*.pl");
				loadDialog.setVisible(true);
				String filename = loadDialog.getDirectory()+loadDialog.getFile();
				if(filename.contains("null"))
					break;
				
				btnFindPath.setEnabled(true);
				controller.execAction("LOAD "+filename);
				break;
				
				
			case "Find Best Path":
				
				MapElement goal = mapViewer.getGoal();
				MapElement start = mapViewer.getStart();				
				
				if(goal == null)
				{
					println("Please select GOAL position");
					return;
				}
				
				if(start == null)
				{
					println("Please select START position");
					return;
				}
				
				int sx = start.getX();
				int sy = start.getY();
				int gx = goal.getX();
				int gy = goal.getY();
				
				try
				{
					controller.execAction("FIND "
						+sx+","						
						+sy+","
						+gx+","
						+gy+","
						);
					
					btnNavigate.setEnabled(true);							
					btnLoad.setEnabled(false);
				}
				catch(NumberFormatException e1)
				{
					println(e1.getMessage());
				}
			
			case "Start Navigation":
				controller.execAction("NAVIGATE ");
				btnNavigate.setEnabled(false);
				btnAbort.setEnabled(true);
				
			case "Abort":
				controller.execAction("ABORT ");				
				btnAbort.setEnabled(false);
				
				btnNavigate.setEnabled(true);
				btnLoad.setEnabled(true);
				
				break;
			default:
				addOutput("Invalid command");
			}
		}
		
	}

	
	public void setController(IActivityBase controller) {
		this.controller = controller;		
	}
	
	
	public synchronized void clear(  ){
		txtOut.setText("");
		txtOut.validate();
	}//clear
	

	/*
	 * ROBA DELLE INTERFACCE, SERVE SOLO A FARE ANDARE IL FRAMEWORK
	 */

	@Override
	public synchronized String getCurVal() {
		return txtOut.getText();
	}

	@Override
	public void addOutput(String msg) {
		txtOut.append(msg+"\n");
		txtOut.validate();
		txtOut.setCaretPosition(txtOut.getDocument().getLength());
	}

	@Override
	public void setOutput(String msg) {
		
		txtOut.setText(msg);
		txtOut.validate();
		txtOut.setCaretPosition(txtOut.getDocument().getLength());
	}

	@Override
	public IBasicEnvAwt getEnv() {
		return this;
	}
	
	public void setVisible(boolean state)
	{
		frame.setVisible(state);
	}

	@Override
	public void init() {		
	}

	@Override
	public String readln() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IOutputView getOutputView() {		
		return this;
	}

	@Override
	public void println(String msg) {
		txtOut.append(msg+"\n");
		txtOut.validate();
		txtOut.setCaretPosition(txtOut.getDocument().getLength());
	}

	@Override
	public void close() {
		frame.setVisible(false);		
	}

	@Override
	public void initNoFrame() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public IOutputEnvView getOutputEnvView() {
		return this;
	}

	@Override
	public void writeOnStatusBar(String s, int size) {
		// TODO Auto-generated method stub		
	}

	@Override
	public boolean isStandAlone() {
		return true;
	}

	@Override
	public void addInputPanel(int size) {
		// TODO Auto-generated method stub		
	}

	@Override
	public void addPanel(Panel p) {
		// TODO Auto-generated method stub		
	}

	@Override
	public void addPanel(Component p) {
		// TODO Auto-generated method stub		
	}

	@Override
	public Panel addCmdPanel(String name, String[] commands, IActivity activity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Panel addCmdPanel(String name, String[] commands, IActivityBase activity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removePanel(Panel p) {
		// TODO Auto-generated method stub		
	}

	@Override
	public int getNumOfPanels() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setEnvVisible(boolean b) {
		setVisible(b);	
	}

	public void setMap(Map map) {

		this.map = map;
		mapViewer.setMap(map);
		
		mapViewer.createMapViewer(map.getYmax()+1, map.getXmax()+1);
		List<IMapElement> elements = map.getElements();
		
		for(IMapElement e : elements)
		{
			mapViewer.setCellState(e.getX(), e.getY(), CellState.OBSTACLE);
		}
		
		panelMap = mapViewer.getPanel();
		splitMapAndOutput.setLeftComponent(panelMap);
	}

	public void setPath(ArrayList<State> path) {
		
		mapViewer.clearPath();
		
		this.path = path;
		
		MapElement start = mapViewer.getStart();
		MapElement goal = mapViewer.getGoal();
		
		it.unibo.planning.astar.domain.State current = 
				new it.unibo.planning.astar.domain.State(start.getX(), start.getY(), Direction.NORTH, null, 0,0);
		
		//it.unibo.planning.astar.domain.State goalState = 
		//		new it.unibo.planning.astar.domain.State(goal.getX(), goal.getY(), Direction.NORTH, null, 0);
		
		for(State s : path)
		{
			mapViewer.setCellState(s.getX(), s.getY(), CellState.PATH);			
		}		
	}

	
	
	



	
	
	
}
