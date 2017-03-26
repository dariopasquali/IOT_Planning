package it.unibo.gui;

import java.awt.FileDialog;

import javax.swing.JFrame;
import java.awt.Component;
import javax.swing.Box;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Panel;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;

import it.unibo.gui.enums.CellState;
import it.unibo.is.interfaces.IActivity;
import it.unibo.is.interfaces.IActivityBase;
import it.unibo.is.interfaces.IBasicEnvAwt;
import it.unibo.is.interfaces.IOutputEnvView;
import it.unibo.is.interfaces.IOutputView;
import it.unibo.model.interfaces.IGUI;
import it.unibo.model.interfaces.IMap;
import it.unibo.model.interfaces.IMapElement;
import it.unibo.model.map.MapElement;

import java.awt.Font;
import java.awt.Frame;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JScrollBar;
import javax.swing.JCheckBox;

public class ConsoleGUI extends Frame implements IOutputEnvView, IBasicEnvAwt, IGUI{

	private static final long serialVersionUID = 1L;
	
	private JFrame frame;
	private JFrame controlFrame;
	private JFrame outputFrame;
	
	private IActivityBase controller;
	
	private JTextArea txtOut;
	private JScrollBar scrollBar;
	
	private JPanel panelMap;
	
	private MapViewer mapViewer;	
	private boolean mapLoaded = false;
	
	private boolean realRobot = false;
	private String robotMode = "simulated";
	
	private ArrayList<Point> path;
	
	JButton btnExplore, btnSave, btnLoad, btnSearch, btnNavigate, btnAbort;
	private Component verticalStrut;
	private JButton btnClear;
	private JCheckBox checkRealOrNot;
	private JButton btnClearPath;

	public ConsoleGUI(IActivityBase controller) {
		
		initializeFrame();
		initializeControlFrame();
		initializeOutputFrame();
		this.controller = controller;
	}
	
	public ConsoleGUI() {
		initializeFrame();
		initializeControlFrame();
		initializeOutputFrame();
	}
	
//{{ INITIALIZATION ------------------------------------------------------
	
	private void initializeFrame()
	{
		frame = new JFrame();
		frame.setBounds(50, 50, 800, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Map Frame");		
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{1056, 0};
		gridBagLayout.rowHeights = new int[]{20, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		frame.getContentPane().setLayout(gridBagLayout);
		
	}

	private void initializeControlFrame()
	{
		controlFrame = new JFrame();
		controlFrame.setBounds(100, 100, 148, 356);
		controlFrame.setTitle("Control Frame");		
		controlFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 123, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		controlFrame.getContentPane().setLayout(gridBagLayout);
		
		btnExplore = new JButton("Explore");
		GridBagConstraints gbc_btnExplore = new GridBagConstraints();
		gbc_btnExplore.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnExplore.insets = new Insets(0, 0, 5, 5);
		gbc_btnExplore.gridx = 1;
		gbc_btnExplore.gridy = 2;
		btnExplore.addActionListener(new DefaultInputHandler());
		
		btnLoad = new JButton("Load Map");
		GridBagConstraints gbc_btnNewButton_2 = new GridBagConstraints();
		gbc_btnNewButton_2.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnNewButton_2.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton_2.gridx = 1;
		gbc_btnNewButton_2.gridy = 1;
		btnLoad.addActionListener(new DefaultInputHandler());
		btnLoad.setEnabled(true);
		controlFrame.getContentPane().add(btnLoad, gbc_btnNewButton_2);
		btnExplore.setEnabled(false);
		controlFrame.getContentPane().add(btnExplore, gbc_btnExplore);
		
		
		btnSave = new JButton("Save Map");
		GridBagConstraints gbc_btnNewButton_1 = new GridBagConstraints();
		gbc_btnNewButton_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnNewButton_1.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton_1.gridx = 1;
		gbc_btnNewButton_1.gridy = 3;
		btnSave.addActionListener(new DefaultInputHandler());
		btnSave.setEnabled(false);
		controlFrame.getContentPane().add(btnSave, gbc_btnNewButton_1);
		
		verticalStrut = Box.createVerticalStrut(20);
		GridBagConstraints gbc_verticalStrut = new GridBagConstraints();
		gbc_verticalStrut.insets = new Insets(0, 0, 5, 5);
		gbc_verticalStrut.gridx = 1;
		gbc_verticalStrut.gridy = 4;
		controlFrame.getContentPane().add(verticalStrut, gbc_verticalStrut);
		
		btnSearch = new JButton("Search Path");
		GridBagConstraints gbc_btnNewButton_3 = new GridBagConstraints();
		gbc_btnNewButton_3.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnNewButton_3.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton_3.gridx = 1;
		gbc_btnNewButton_3.gridy = 5;
		btnSearch.addActionListener(new DefaultInputHandler());
		btnSearch.setEnabled(false);
		controlFrame.getContentPane().add(btnSearch, gbc_btnNewButton_3);
		
		btnNavigate = new JButton("Navigate");
		GridBagConstraints gbc_btnNavigate = new GridBagConstraints();
		gbc_btnNavigate.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnNavigate.insets = new Insets(0, 0, 5, 5);
		gbc_btnNavigate.gridx = 1;
		gbc_btnNavigate.gridy = 6;
		btnNavigate.addActionListener(new DefaultInputHandler());
		btnNavigate.setEnabled(false);
		controlFrame.getContentPane().add(btnNavigate, gbc_btnNavigate);
		
		btnAbort = new JButton("Abort");
		GridBagConstraints gbc_btnNewButton_4 = new GridBagConstraints();
		gbc_btnNewButton_4.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnNewButton_4.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton_4.gridx = 1;
		gbc_btnNewButton_4.gridy = 8;
		btnAbort.addActionListener(new DefaultInputHandler());
		btnAbort.setEnabled(true);
		controlFrame.getContentPane().add(btnAbort, gbc_btnNewButton_4);
		
		btnClear = new JButton("Clear Exploration");
		GridBagConstraints gbc_btnClear = new GridBagConstraints();
		gbc_btnClear.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnClear.insets = new Insets(0, 0, 5, 5);
		gbc_btnClear.gridx = 1;
		gbc_btnClear.gridy = 9;
		controlFrame.getContentPane().add(btnClear, gbc_btnClear);
		btnClear.addActionListener(new DefaultInputHandler());
		btnClear.setEnabled(false);
		
		btnClearPath = new JButton("Clear Path");
		btnClearPath.setEnabled(false);
		GridBagConstraints gbc_btnClearPath = new GridBagConstraints();
		gbc_btnClearPath.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnClearPath.insets = new Insets(0, 0, 5, 5);
		gbc_btnClearPath.gridx = 1;
		gbc_btnClearPath.gridy = 10;
		controlFrame.getContentPane().add(btnClearPath, gbc_btnClearPath);
		
		checkRealOrNot = new JCheckBox("Raspberry Pi Robot");
		GridBagConstraints gbc_checkRealOrNot = new GridBagConstraints();
		gbc_checkRealOrNot.insets = new Insets(0, 0, 5, 5);
		gbc_checkRealOrNot.gridx = 1;
		gbc_checkRealOrNot.gridy = 11;
		controlFrame.getContentPane().add(checkRealOrNot, gbc_checkRealOrNot);
		
		checkRealOrNot.setSelected(false);
		checkRealOrNot.addActionListener(new DefaultInputHandler());
		
	}
	
	private void initializeOutputFrame()
	{
		outputFrame = new JFrame();
		outputFrame.setBounds(300, 300, 700, 300);
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
		txtOut.setFont(new Font("Monospaced", Font.PLAIN, 14));
		panelOutput.setViewportView(txtOut);
		
		scrollBar = new JScrollBar();
		panelOutput.setRowHeaderView(scrollBar);
		panelOutput.add(scrollBar);		
	}

	@Override
	public void setController(IActivityBase controller) {
		this.controller = controller;		
	}
	
//}}	


//{{ COMMON --------------------------------------------------------------
	
	public void setCellState(int y, int x, CellState state)
	{
		mapViewer.setCellState(y, x, state);
		
		frame.revalidate();
		frame.repaint();
	}
	
	public void setMap(IMap map)
	{
		this.mapViewer = new MapViewer(true);
		
		mapViewer.createGridPanel(map.getYmax(), map.getXmax());
		List<IMapElement> elements = map.getElements();
		
		for(IMapElement e : elements)
		{
			mapViewer.setCellState(e.getY(), e.getX(), CellState.OBJECT);
		}
		
		panelMap = mapViewer.getPanel();
		
		GridBagConstraints gbcPanel = new GridBagConstraints();
		gbcPanel.fill = GridBagConstraints.BOTH;
		gbcPanel.gridheight = 5;
		gbcPanel.gridy = 0;	
		
		frame.getContentPane().removeAll();
		
		frame.getContentPane().add(panelMap, gbcPanel);
		panelMap.revalidate();
		panelMap.repaint();
		frame.revalidate();
		frame.repaint();
		
		this.mapLoaded = true;
	}
	
	public void clearGUI()
	{
		mapViewer.noneAll();
	}
	
	public void clearPath()
	{
		mapViewer.clearPath();
	}

//}}	

	
//{{ EXPLORATION -------------------------------------------------------	
	
	public void clearCurrentExplorationMap()
	{
		if(!mapLoaded)
		{
			println("Prima carica una mappa!!");
			return;
		}
		
		mapViewer.noneAll();
		
		frame.revalidate();		
		frame.repaint();		
	}

//}}	

	
//{{ NAVIGATION -----------------------------------------------------------------	
	
//	public void initNavigationViewer()
//	{
//		this.mapViewer = new NavigationViewer(true);
//		frame.getContentPane().removeAll();
//	}
	
	public void setPath(List<Point> list) {
		
		mapViewer.clearPath();		
		this.path = (ArrayList<Point>) list;
		
		for(Point p : list)
		{
			mapViewer.setCellState(p.y, p.x, CellState.PATH);			
		}
		mapViewer.showStartAndGoal();	
		
	}
	
//}}

	
//{{ CLICK HANDLER ----------------------------------------------------------------------	

	private class DefaultInputHandler implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			
			switch(e.getActionCommand())
			{
			
			case "Raspberry Pi Robot":
				
				realRobot = checkRealOrNot.isSelected();				
				robotMode = (realRobot) ? "simulated" : "robot";				
				String s = (!realRobot) ? "Simulation" : "Real Robot";
				println("I'm working with a "+s);				
				break;
			
			case "Load Map":
				
				FileDialog loadDialog = new FileDialog(frame, "Choose the Navigation Map", FileDialog.LOAD);
				loadDialog.setDirectory("C:\\");
				loadDialog.setFile("*.pl");
				loadDialog.setVisible(true);
				String filename = loadDialog.getDirectory()+loadDialog.getFile();
				if(filename.contains("null"))
					break;
				
				btnSearch.setEnabled(true);
				btnExplore.setEnabled(true);
				
				controller.execAction("LOAD "+filename);
				break;	
				
			case "Explore":
				
				MapElement expStart = mapViewer.getStart();
				
				if(expStart == null)
				{
					println("Please select start position");
					return;
				}
				controller.execAction("EXPLOREDEBUG "+expStart.getX()+","+expStart.getY()+","
										+mapViewer.getXMax()+","+mapViewer.getYMax()+","+robotMode);				
				
				btnSave.setEnabled(true);
				btnClear.setEnabled(true);
				btnSearch.setEnabled(true);
				break;
				
			case "Save Map":

				FileDialog storeDialog = new FileDialog(frame, "Create new Map", FileDialog.SAVE);
				storeDialog.setDirectory("C:\\");
				storeDialog.setFile(".pl");
				storeDialog.setVisible(true);
				String fname = storeDialog.getDirectory()+storeDialog.getFile();
				if(fname.contains("null"))
					break;
				
				storeMap(fname);
				
				btnLoad.setEnabled(true);
				break;
			
			case "Search Path":
				
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
						+gy+")"
						);
					
					btnNavigate.setEnabled(true);							
					btnLoad.setEnabled(false);
					btnClear.setEnabled(true);
				}
				catch(NumberFormatException e1)
				{
					println(e1.getMessage());
				}
				break;
			
			case "Navigate":
								
				controller.execAction("NAVIGATE "+robotMode);
				btnNavigate.setEnabled(false);
				btnAbort.setEnabled(true);
				break;
				
			case "Abort":
				controller.execAction("ABORT ");				
				btnAbort.setEnabled(false);
				
				btnNavigate.setEnabled(true);
				btnExplore.setEnabled(true);
				btnLoad.setEnabled(true);
				
				break;
				
			case "Clear Exploration":
				controller.execAction("CLEAR ");
				btnClear.setEnabled(false);
				
			case "Clear Path":
				controller.execAction("CLEARPATH ");
				btnClearPath.setEnabled(false);
				
				
			default:
				addOutput("Invalid command");
			}
		}
		
	}
	
	public void storeMap(String fname) {
		
		IMap map = mapViewer.getMap();
		
		try
		{
			FileOutputStream fsout = new FileOutputStream( new File(fname) );
			fsout.write(map.getDefaultRep().getBytes());
			fsout.close();
		} catch (Exception e) {
			System.out.println("QActor  ERROR " + e.getMessage());
 		}
				
	}
//}}	

	
//{{ OUTPUT ---------------------------------------------------------------
	
	@Override
	public void println(String msg) {
		txtOut.append(msg+"\n");
		txtOut.validate();
		txtOut.setCaretPosition(txtOut.getDocument().getLength());
//		System.out.println(msg);
	}
	

	public synchronized void clear(){
		txtOut.setText("");
		txtOut.validate();
	}//clear
	
	@Override
	public synchronized String getCurVal() {
		return txtOut.getText();
	}

	@Override
	public void addOutput(String msg) {
		txtOut.append(msg+"\n");
		txtOut.validate();
		txtOut.setCaretPosition(txtOut.getDocument().getLength());
//		System.out.println(msg);
	}

	@Override
	public void setOutput(String msg) {
		
		txtOut.setText(msg);
		txtOut.validate();
		txtOut.setCaretPosition(txtOut.getDocument().getLength());
	}
//}}
	
	
//{{ VISIBLE ----------------------------------------------------------	

	@Override
	public void setVisible(boolean state)
	{
		frame.setVisible(state);
		controlFrame.setVisible(state);
		outputFrame.setVisible(state);
	}
	
	@Override
	public void close() {
		frame.setVisible(false);		
	}
//}}
	
	
//{{  ROBA DELLE INTERFACCE, SERVE SOLO A FARE ANDARE IL FRAMEWORK
	
	@Override
	public IBasicEnvAwt getEnv() {
		return this;
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

	@Override
	public void addInputPanel(String msg) {
		// TODO Auto-generated method stub
		
	}

//}}	

	
	
	



	
	
	
}
