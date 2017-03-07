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
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import it.unibo.gui.NavigationViewer.CellState;
import it.unibo.is.interfaces.IActivity;
import it.unibo.is.interfaces.IActivityBase;
import it.unibo.is.interfaces.IBasicEnvAwt;
import it.unibo.is.interfaces.IOutputEnvView;
import it.unibo.is.interfaces.IOutputView;
import it.unibo.model.implementation.Map;
import it.unibo.model.implementation.MapElement;
import it.unibo.model.interfaces.IGUI;
import it.unibo.model.interfaces.IMap;
import it.unibo.model.interfaces.IMapElement;

import javax.swing.JSplitPane;
import java.awt.Font;
import java.awt.Frame;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JScrollBar;
import javax.swing.UIManager;
import java.awt.Color;
import java.awt.FlowLayout;

public class ConsoleGUI extends Frame implements IOutputEnvView, IBasicEnvAwt, IGUI{

	private static final long serialVersionUID = 1L;
	
	private JFrame frame;
	private JFrame controlFrame;
	private JFrame outputFrame;
	
	private IActivityBase controller;
	
	private JTextArea txtOut;
	private JScrollBar scrollBar;
	
	private JPanel panelMap;
	
	private NavigationViewer navViewer;
	private ExplorationViewer expViewer;
	
	private IMap map;
	private ArrayList<Point> path;
	
	JButton btnExplore, btnSave, btnLoad, btnSearch, btnNavigate, btnAbort;
	private Component verticalStrut;
	private Component verticalStrut_1;

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
	
	private void initializeFrame()
	{
		frame = new JFrame();
		frame.setBounds(200, 200, 1800, 700);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Map Frame");		
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{1056, 0};
		gridBagLayout.rowHeights = new int[]{20, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		frame.getContentPane().setLayout(gridBagLayout);
		
		navViewer = new NavigationViewer(true);	
		expViewer = new ExplorationViewer(true);
		
	}

	private void initializeControlFrame()
	{
		controlFrame = new JFrame();
		controlFrame.setBounds(100, 100, 136, 333);
		controlFrame.setTitle("Control Frame");		
		controlFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 123, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		controlFrame.getContentPane().setLayout(gridBagLayout);
		
		btnExplore = new JButton("Explore");
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnNewButton.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton.gridx = 1;
		gbc_btnNewButton.gridy = 1;
		btnExplore.addActionListener(new DefaultInputHandler());
		btnExplore.setEnabled(false);
		controlFrame.getContentPane().add(btnExplore, gbc_btnNewButton);
		
		btnSave = new JButton("Save Map");
		GridBagConstraints gbc_btnNewButton_1 = new GridBagConstraints();
		gbc_btnNewButton_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnNewButton_1.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton_1.gridx = 1;
		gbc_btnNewButton_1.gridy = 2;
		btnSave.addActionListener(new DefaultInputHandler());
		btnSave.setEnabled(false);
		controlFrame.getContentPane().add(btnSave, gbc_btnNewButton_1);
		
		verticalStrut = Box.createVerticalStrut(20);
		GridBagConstraints gbc_verticalStrut = new GridBagConstraints();
		gbc_verticalStrut.insets = new Insets(0, 0, 5, 5);
		gbc_verticalStrut.gridx = 1;
		gbc_verticalStrut.gridy = 3;
		controlFrame.getContentPane().add(verticalStrut, gbc_verticalStrut);
		
		btnLoad = new JButton("Load Map");
		GridBagConstraints gbc_btnNewButton_2 = new GridBagConstraints();
		gbc_btnNewButton_2.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnNewButton_2.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton_2.gridx = 1;
		gbc_btnNewButton_2.gridy = 4;
		btnLoad.addActionListener(new DefaultInputHandler());
		btnLoad.setEnabled(true);
		controlFrame.getContentPane().add(btnLoad, gbc_btnNewButton_2);
		
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
		GridBagConstraints gbc_button = new GridBagConstraints();
		gbc_button.fill = GridBagConstraints.HORIZONTAL;
		gbc_button.insets = new Insets(0, 0, 5, 5);
		gbc_button.gridx = 1;
		gbc_button.gridy = 6;
		btnNavigate.addActionListener(new DefaultInputHandler());
		btnNavigate.setEnabled(false);
		controlFrame.getContentPane().add(btnNavigate, gbc_button);
		
		verticalStrut_1 = Box.createVerticalStrut(20);
		GridBagConstraints gbc_verticalStrut_1 = new GridBagConstraints();
		gbc_verticalStrut_1.insets = new Insets(0, 0, 5, 5);
		gbc_verticalStrut_1.gridx = 1;
		gbc_verticalStrut_1.gridy = 7;
		controlFrame.getContentPane().add(verticalStrut_1, gbc_verticalStrut_1);
		
		btnAbort = new JButton("Abort");
		GridBagConstraints gbc_btnNewButton_4 = new GridBagConstraints();
		gbc_btnNewButton_4.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnNewButton_4.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton_4.gridx = 1;
		gbc_btnNewButton_4.gridy = 8;
		btnAbort.addActionListener(new DefaultInputHandler());
		btnAbort.setEnabled(true);
		controlFrame.getContentPane().add(btnAbort, gbc_btnNewButton_4);


		
	}
	
	private void initializeOutputFrame()
	{
		outputFrame = new JFrame();
		outputFrame.setBounds(100, 100, 700, 300);
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
	
	@Override
	public synchronized void clear(  ){
		txtOut.setText("");
		txtOut.validate();
	}//clear
	
	
	@Override
	public void setPath(List<Point> list) {
		
		navViewer.clearPath();		
		this.path = (ArrayList<Point>) list;
		
		for(Point p : list)
		{
			navViewer.setCellState(p.x, p.y, CellState.PATH);			
		}		
	}

	private class DefaultInputHandler implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			
			switch(e.getActionCommand())
			{
			
			case "Explore":
				//TODO
				btnSave.setEnabled(true);
				break;
				
			case "Save Map":
				//TODO
				btnLoad.setEnabled(true);
				break;
			
			case "Load Map":	
				
				//TODO
				
				/*
				 * lo uso prima di tutto epr caricare la mappa de esplorare
				 * in questo modo posso sapere i BOUNDS e posso attendere che l'utente clicchi
				 * la posizione di START
				 * 
				 * quindi lo utilizzo per la mappa di navigazione
				 */
				
				FileDialog loadDialog = new FileDialog(frame, "Choose the Map", FileDialog.LOAD);
				loadDialog.setDirectory("C:\\");
				loadDialog.setFile("*.pl");
				loadDialog.setVisible(true);
				String filename = loadDialog.getDirectory()+loadDialog.getFile();
				if(filename.contains("null"))
					break;
				
				btnSearch.setEnabled(true);
				controller.execAction("LOAD "+filename);
				break;
				
				
			case "Find Path":
				
				MapElement goal = navViewer.getGoal();
				MapElement start = navViewer.getStart();				
				
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
						+"t"+","
						);
					
					btnNavigate.setEnabled(true);							
					btnLoad.setEnabled(false);
				}
				catch(NumberFormatException e1)
				{
					println(e1.getMessage());
				}
			
			case "Navigate":
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
	
	
	public void setMap(IMap map) {

		this.map = map;
		navViewer.createGridPanel(map.getHeight()+1, map.getWidth()+1);
		List<IMapElement> elements = map.getElements();
		
		for(IMapElement e : elements)
		{
			navViewer.setCellState(e.getX(), e.getY(), CellState.OBJECT);
		}
		
		panelMap = navViewer.getPanel();
		frame.getContentPane().add(panelMap);
	}

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
