package it.unibo.gui;

import java.awt.FileDialog;

import javax.swing.JFrame;
import java.awt.Component;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Panel;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;

import it.unibo.domain.model.interfaces.IMap;
import it.unibo.domain.model.interfaces.IMapElement;
import it.unibo.is.interfaces.IActivity;
import it.unibo.is.interfaces.IActivityBase;
import it.unibo.is.interfaces.IBasicEnvAwt;
import it.unibo.is.interfaces.IOutputEnvView;
import it.unibo.is.interfaces.IOutputView;

import java.awt.Font;
import java.awt.Frame;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JScrollBar;

public class RobotGUI extends Frame implements IOutputEnvView, IBasicEnvAwt{

	private static final long serialVersionUID = 1L;
	
	private MapType type;
	
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
	
	private Component verticalStrut;
	private Component verticalStrut_1;
	private GridBagConstraints gbc_btnLoadExp;
	public RobotGUI(IActivityBase controller) {
		
		initializeFrame();
		initializeOutputFrame();
		this.controller = controller;
	}
	
	public RobotGUI() {
		initializeFrame();
		initializeOutputFrame();
		
		type = null;
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
		this.mapViewer = new MapViewer(false);
		
		mapViewer.createGridPanel(map.getXmax(), map.getXmax());
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
		if(type.equals(MapType.EXPLORATION))
		{
			mapViewer.noneAll();
		}
		else if(type.equals(MapType.NAVIGATION))
		{
			mapViewer.clearPath();
		}
	}
	
	public void setCurrentPosition(int y, int x, String direction) {
		mapViewer.setCurrentPosition(y, x, direction);		
	}
	
	
// OUTPUT ---------------------------------------------------------------
	
	@Override
	public void println(String msg) {
		txtOut.append(msg+"\n");
		txtOut.validate();
		txtOut.setCaretPosition(txtOut.getDocument().getLength());
	}
	
	
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
	}
	
	public void setOutputVisible(boolean state) {
		
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

	@Override
	public void addInputPanel(String msg) {
		// TODO Auto-generated method stub
		
	}







	

	
	
	



	
	
	
}
