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

import it.unibo.is.interfaces.IActivity;
import it.unibo.is.interfaces.IActivityBase;
import it.unibo.is.interfaces.IBasicEnvAwt;
import it.unibo.is.interfaces.IOutputEnvView;
import it.unibo.is.interfaces.IOutputView;
import it.unibo.model.implementation.MapElement;
import it.unibo.model.interfaces.IGUI;
import it.unibo.model.interfaces.IMap;
import it.unibo.model.interfaces.IMapElement;

import java.awt.Font;
import java.awt.Frame;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JScrollBar;

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
	
	private ArrayList<Point> path;
	
	JButton btnExplore, btnSave, btnLoad, btnSearch, btnNavigate, btnAbort;
	private Component verticalStrut;
	private Component verticalStrut_1;
	private JButton btnLoadExp;
	private GridBagConstraints gbc_btnLoadExp;
	private JButton btnClear;

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
	
	// INITIALIZATION ------------------------------------------------------
	
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
		controlFrame.setBounds(100, 100, 172, 333);
		controlFrame.setTitle("Control Frame");		
		controlFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 123, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		controlFrame.getContentPane().setLayout(gridBagLayout);
		
		btnExplore = new JButton("Explore");
		GridBagConstraints gbc_btnExplore = new GridBagConstraints();
		gbc_btnExplore.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnExplore.insets = new Insets(0, 0, 5, 5);
		gbc_btnExplore.gridx = 1;
		gbc_btnExplore.gridy = 2;
		btnExplore.addActionListener(new DefaultInputHandler());
		btnExplore.setEnabled(false);
		controlFrame.getContentPane().add(btnExplore, gbc_btnExplore);
		
		btnLoadExp = new JButton("Load Exploration Map");
		gbc_btnLoadExp = new GridBagConstraints();
		gbc_btnLoadExp.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnLoadExp.insets = new Insets(0, 0, 5, 5);
		gbc_btnLoadExp.gridx = 1;
		gbc_btnLoadExp.gridy = 1;
		controlFrame.getContentPane().add(btnLoadExp, gbc_btnLoadExp);
		btnLoadExp.addActionListener(new DefaultInputHandler());
		
		
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
		
		btnLoad = new JButton("Load Map");
		GridBagConstraints gbc_btnNewButton_2 = new GridBagConstraints();
		gbc_btnNewButton_2.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnNewButton_2.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton_2.gridx = 1;
		gbc_btnNewButton_2.gridy = 5;
		btnLoad.addActionListener(new DefaultInputHandler());
		btnLoad.setEnabled(true);
		controlFrame.getContentPane().add(btnLoad, gbc_btnNewButton_2);
		
		btnSearch = new JButton("Search Path");
		GridBagConstraints gbc_btnNewButton_3 = new GridBagConstraints();
		gbc_btnNewButton_3.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnNewButton_3.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton_3.gridx = 1;
		gbc_btnNewButton_3.gridy = 6;
		btnSearch.addActionListener(new DefaultInputHandler());
		btnSearch.setEnabled(false);
		controlFrame.getContentPane().add(btnSearch, gbc_btnNewButton_3);
		
		btnNavigate = new JButton("Navigate");
		GridBagConstraints gbc_btnNavigate = new GridBagConstraints();
		gbc_btnNavigate.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnNavigate.insets = new Insets(0, 0, 5, 5);
		gbc_btnNavigate.gridx = 1;
		gbc_btnNavigate.gridy = 7;
		btnNavigate.addActionListener(new DefaultInputHandler());
		btnNavigate.setEnabled(false);
		controlFrame.getContentPane().add(btnNavigate, gbc_btnNavigate);
		
		verticalStrut_1 = Box.createVerticalStrut(20);
		GridBagConstraints gbc_verticalStrut_1 = new GridBagConstraints();
		gbc_verticalStrut_1.insets = new Insets(0, 0, 5, 5);
		gbc_verticalStrut_1.gridx = 1;
		gbc_verticalStrut_1.gridy = 8;
		controlFrame.getContentPane().add(verticalStrut_1, gbc_verticalStrut_1);
		
		btnAbort = new JButton("Abort");
		GridBagConstraints gbc_btnNewButton_4 = new GridBagConstraints();
		gbc_btnNewButton_4.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnNewButton_4.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton_4.gridx = 1;
		gbc_btnNewButton_4.gridy = 9;
		btnAbort.addActionListener(new DefaultInputHandler());
		btnAbort.setEnabled(true);
		controlFrame.getContentPane().add(btnAbort, gbc_btnNewButton_4);
		
		btnClear = new JButton("Clear");
		GridBagConstraints gbc_btnClear = new GridBagConstraints();
		gbc_btnClear.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnClear.insets = new Insets(0, 0, 5, 5);
		gbc_btnClear.gridx = 1;
		gbc_btnClear.gridy = 10;
		controlFrame.getContentPane().add(btnClear, gbc_btnClear);
		btnClear.addActionListener(new DefaultInputHandler());
		btnClear.setEnabled(false);
		
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
	
	// COMMON --------------------------------------------------------------
	
	public void setCellState(int y, int x, CellState state)
	{
		mapViewer.setCellState(y, x, state);
		
		frame.revalidate();
		frame.repaint();
	}
	
	public void setMap(IMap map)
	{
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
		frame.getContentPane().add(panelMap, gbcPanel);
		panelMap.revalidate();
		panelMap.repaint();
		frame.revalidate();
		frame.repaint();
		
		this.mapLoaded = true;
	}
	
	public void clearGUI()
	{
		if(mapViewer instanceof ExplorationViewer)
		{
			((ExplorationViewer) mapViewer).noneAll();
		}
		else if(mapViewer instanceof NavigationViewer)
		{
			((NavigationViewer) mapViewer).clearPath();
		}
	}
	
// EXPLORATION -------------------------------------------------------	
	
	public void initExplorationViewer()
	{
		this.mapViewer = new ExplorationViewer(true);
		frame.getContentPane().removeAll();
	}
	
	public void clearCurrentExplorationMap()
	{
		if(!mapLoaded)
		{
			println("Prima carica una mappa!!");
			return;
		}
		
		((ExplorationViewer)mapViewer).noneAll();
		
		frame.revalidate();		
		frame.repaint();		
	}
	

// NAVIGATION -----------------------------------------------------------------	
	
	public void initNavigationViewer()
	{
		this.mapViewer = new NavigationViewer(true);
		frame.getContentPane().removeAll();
	}
	
	@Override
	public void setPath(List<Point> list) {
		
		((NavigationViewer) mapViewer).clearPath();		
		this.path = (ArrayList<Point>) list;
		
		for(Point p : list)
		{
			mapViewer.setCellState(p.y, p.x, CellState.PATH);			
		}
		((NavigationViewer) mapViewer).showStartAndGoal();	
		
	}
	


	
// CLICK HANDLER ----------------------------------------------------------------------	

	private class DefaultInputHandler implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			
			switch(e.getActionCommand())
			{
			
			case "Load Exploration Map": // Per la mappa di esplorazione
								
				FileDialog loadExpDialog = new FileDialog(frame, "Choose the Exploration Debug Map", FileDialog.LOAD);
				loadExpDialog.setDirectory("C:\\");
				loadExpDialog.setFile("*.pl");
				loadExpDialog.setVisible(true);
				String filename = loadExpDialog.getDirectory()+loadExpDialog.getFile();
				if(filename.contains("null"))
					break;
				
				controller.execAction("LOADEXP "+filename);
				
				btnExplore.setEnabled(true);
				
				break;
			
			case "Explore":
				
				MapElement expStart = mapViewer.getStart();
				
				if(expStart == null)
				{
					println("Please select start position");
					return;
				}
				controller.execAction("EXPLOREDEBUG "+expStart.getX()+","+expStart.getY()+","
										+mapViewer.getXMax()+","+mapViewer.getYMax());				
				
				btnSave.setEnabled(true);
				btnClear.setEnabled(true);
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
			
			case "Load Map":	// Per la Mappa di Navigazione
				
				FileDialog loadDialog = new FileDialog(frame, "Choose the Navigation Map", FileDialog.LOAD);
				loadDialog.setDirectory("C:\\");
				loadDialog.setFile("*.pl");
				loadDialog.setVisible(true);
				filename = loadDialog.getDirectory()+loadDialog.getFile();
				if(filename.contains("null"))
					break;
				
				btnSearch.setEnabled(true);				
				
				controller.execAction("LOAD "+filename);
				break;
				
				
			case "Search Path":
				
				MapElement goal = ((NavigationViewer)mapViewer).getGoal();
				MapElement start = ((NavigationViewer)mapViewer).getStart();				
				
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
				controller.execAction("NAVIGATE ");
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
				
			case "Clear":
				controller.execAction("CLEAR ");
				btnClear.setEnabled(false);
				
				
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
	

// OUTPUT ---------------------------------------------------------------
	
	@Override
	public synchronized void clear(  ){
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
	}

	@Override
	public void setOutput(String msg) {
		
		txtOut.setText(msg);
		txtOut.validate();
		txtOut.setCaretPosition(txtOut.getDocument().getLength());
	}
	
// ----------------------------------------------------------	

	/*
	 * ROBA DELLE INTERFACCE, SERVE SOLO A FARE ANDARE IL FRAMEWORK
	 */



	@Override
	public IBasicEnvAwt getEnv() {
		return this;
	}
	
	public void setVisible(boolean state)
	{
		frame.setVisible(state);
		controlFrame.setVisible(state);
		outputFrame.setVisible(state);
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

	

	
	
	



	
	
	
}
