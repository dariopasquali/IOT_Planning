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

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import it.unibo.domain.model.interfaces.IConsole;
import it.unibo.domain.model.interfaces.IMap;
import it.unibo.is.interfaces.IActivity;
import it.unibo.is.interfaces.IActivityBase;
import it.unibo.is.interfaces.IBasicEnvAwt;
import it.unibo.is.interfaces.IBasicUniboEnv;
import it.unibo.is.interfaces.IOutputEnvView;
import it.unibo.is.interfaces.IOutputView;

import java.awt.Color;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Frame;
import javax.swing.JTextArea;
import javax.swing.JRadioButton;
import java.awt.Choice;

public class ConsoleGUI extends Frame implements IOutputEnvView, IBasicEnvAwt, IConsole{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JFrame frame;
	private JTextField txtExploreTime;
	private JTextField txtStartX;
	private JTextField txtStartY;
	private JTextField txtGoalX;
	private JTextField txtGoalY;
	private JTextArea txtDefaultOutput;
	
	private JButton btnLoad;
	private JButton btnStore;
	private JButton btnExplore;
	private JButton btnNavigate;
	private JButton btnAbort;
	
	
	private Choice envTypeChoice;
	
	protected String curVal = "";

	private IActivityBase controller;
	
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
		frame.setBounds(100, 100, 1072, 566);
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
		
		Component horizontalStrut = Box.createHorizontalStrut(10);
		boxMapManager.add(horizontalStrut);
		
		btnStore = new JButton("Store Map");
		btnStore.addActionListener(new DefaultInputHandler());
		btnStore.setFont(new Font("Tahoma", Font.PLAIN, 15));
		boxMapManager.add(btnStore);
		
		Component horizontalStrut_1 = Box.createHorizontalStrut(10);
		boxMapManager.add(horizontalStrut_1);
		
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
		gbc_bodyPanel.insets = new Insets(0, 0, 5, 0);
		gbc_bodyPanel.fill = GridBagConstraints.BOTH;
		gbc_bodyPanel.gridx = 0;
		gbc_bodyPanel.gridy = 3;
		frame.getContentPane().add(bodyPanel, gbc_bodyPanel);
		
		JSplitPane leftPanel = new JSplitPane();
		leftPanel.setOrientation(JSplitPane.VERTICAL_SPLIT);
		bodyPanel.setLeftComponent(leftPanel);
		
		JPanel explorePanel = new JPanel();
		explorePanel.setBorder(new TitledBorder(null, "Exploration", TitledBorder.LEFT, TitledBorder.TOP, new Font("Tahoma", Font.PLAIN, 13), Color.BLACK));
		leftPanel.setLeftComponent(explorePanel);
		GridBagLayout gbl_explorePanel = new GridBagLayout();
		gbl_explorePanel.columnWidths = new int[]{126, 86, 89, 0};
		gbl_explorePanel.rowHeights = new int[]{0, 23, 0, 0, 0};
		gbl_explorePanel.columnWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_explorePanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		explorePanel.setLayout(gbl_explorePanel);
		
		Component verticalStrut_3 = Box.createVerticalStrut(10);
		GridBagConstraints gbc_verticalStrut_3 = new GridBagConstraints();
		gbc_verticalStrut_3.insets = new Insets(0, 0, 5, 5);
		gbc_verticalStrut_3.gridx = 1;
		gbc_verticalStrut_3.gridy = 0;
		explorePanel.add(verticalStrut_3, gbc_verticalStrut_3);
		
		JLabel lblMaximumExplorationTime = new JLabel("Maximum Exploration Time");
		lblMaximumExplorationTime.setFont(new Font("Tahoma", Font.PLAIN, 17));
		GridBagConstraints gbc_lblMaximumExplorationTime = new GridBagConstraints();
		gbc_lblMaximumExplorationTime.gridwidth = 3;
		gbc_lblMaximumExplorationTime.anchor = GridBagConstraints.WEST;
		gbc_lblMaximumExplorationTime.insets = new Insets(0, 0, 5, 0);
		gbc_lblMaximumExplorationTime.gridx = 0;
		gbc_lblMaximumExplorationTime.gridy = 1;
		explorePanel.add(lblMaximumExplorationTime, gbc_lblMaximumExplorationTime);
		
		txtExploreTime = new JTextField();
		GridBagConstraints gbc_txtExploreTime = new GridBagConstraints();
		gbc_txtExploreTime.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtExploreTime.gridwidth = 3;
		gbc_txtExploreTime.insets = new Insets(0, 0, 5, 0);
		gbc_txtExploreTime.gridx = 0;
		gbc_txtExploreTime.gridy = 2;
		explorePanel.add(txtExploreTime, gbc_txtExploreTime);
		txtExploreTime.setColumns(10);
		
		btnExplore = new JButton("Explore");
		btnExplore.addActionListener(new DefaultInputHandler());
		btnExplore.setFont(new Font("Tahoma", Font.PLAIN, 15));
		GridBagConstraints gbc_btnExplore = new GridBagConstraints();
		gbc_btnExplore.insets = new Insets(0, 0, 0, 5);
		gbc_btnExplore.anchor = GridBagConstraints.NORTHWEST;
		gbc_btnExplore.gridx = 0;
		gbc_btnExplore.gridy = 3;
		explorePanel.add(btnExplore, gbc_btnExplore);
		
		envTypeChoice = new Choice();
		envTypeChoice.setFont(new Font("Dialog", Font.PLAIN, 15));
		envTypeChoice.add("open");
		envTypeChoice.add("close");
		
		JLabel lblEnvType = new JLabel("Env Type");
		lblEnvType.setFont(new Font("Tahoma", Font.PLAIN, 15));
		GridBagConstraints gbc_lblEnvType = new GridBagConstraints();
		gbc_lblEnvType.insets = new Insets(0, 0, 0, 5);
		gbc_lblEnvType.gridx = 1;
		gbc_lblEnvType.gridy = 3;
		explorePanel.add(lblEnvType, gbc_lblEnvType);
		GridBagConstraints gbc_envTypeChoice = new GridBagConstraints();
		gbc_envTypeChoice.gridx = 2;
		gbc_envTypeChoice.gridy = 3;
		explorePanel.add(envTypeChoice, gbc_envTypeChoice);
		
		JPanel navigatePanel = new JPanel();
		navigatePanel.setBorder(new TitledBorder(null, "Navigation", TitledBorder.LEADING, TitledBorder.TOP, new Font("Tahoma", Font.PLAIN, 13), null));
		leftPanel.setRightComponent(navigatePanel);
		GridBagLayout gbl_navigatePanel = new GridBagLayout();
		gbl_navigatePanel.columnWidths = new int[]{136, 0, 46, 0};
		gbl_navigatePanel.rowHeights = new int[]{0, 14, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_navigatePanel.columnWeights = new double[]{0.0, 1.0, 1.0, Double.MIN_VALUE};
		gbl_navigatePanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		navigatePanel.setLayout(gbl_navigatePanel);
		
		Component verticalStrut_2 = Box.createVerticalStrut(10);
		GridBagConstraints gbc_verticalStrut_2 = new GridBagConstraints();
		gbc_verticalStrut_2.insets = new Insets(0, 0, 5, 5);
		gbc_verticalStrut_2.gridx = 1;
		gbc_verticalStrut_2.gridy = 0;
		navigatePanel.add(verticalStrut_2, gbc_verticalStrut_2);
		
		JLabel lblNewLabel = new JLabel("Start X");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 1;
		navigatePanel.add(lblNewLabel, gbc_lblNewLabel);
		
		txtStartX = new JTextField();
		GridBagConstraints gbc_txtStartX = new GridBagConstraints();
		gbc_txtStartX.gridwidth = 2;
		gbc_txtStartX.insets = new Insets(0, 0, 5, 0);
		gbc_txtStartX.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtStartX.gridx = 1;
		gbc_txtStartX.gridy = 1;
		navigatePanel.add(txtStartX, gbc_txtStartX);
		txtStartX.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Start Y");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.gridx = 0;
		gbc_lblNewLabel_1.gridy = 2;
		navigatePanel.add(lblNewLabel_1, gbc_lblNewLabel_1);
		
		txtStartY = new JTextField();
		GridBagConstraints gbc_txtStartY = new GridBagConstraints();
		gbc_txtStartY.gridwidth = 2;
		gbc_txtStartY.insets = new Insets(0, 0, 5, 0);
		gbc_txtStartY.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtStartY.gridx = 1;
		gbc_txtStartY.gridy = 2;
		navigatePanel.add(txtStartY, gbc_txtStartY);
		txtStartY.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("Goal X");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 15));
		GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
		gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_2.gridx = 0;
		gbc_lblNewLabel_2.gridy = 4;
		navigatePanel.add(lblNewLabel_2, gbc_lblNewLabel_2);
		
		txtGoalX = new JTextField();
		GridBagConstraints gbc_txtGoalX = new GridBagConstraints();
		gbc_txtGoalX.gridwidth = 2;
		gbc_txtGoalX.insets = new Insets(0, 0, 5, 0);
		gbc_txtGoalX.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtGoalX.gridx = 1;
		gbc_txtGoalX.gridy = 4;
		navigatePanel.add(txtGoalX, gbc_txtGoalX);
		txtGoalX.setColumns(10);
		
		JLabel lblNewLabel_3 = new JLabel("Goal Y");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 15));
		GridBagConstraints gbc_lblNewLabel_3 = new GridBagConstraints();
		gbc_lblNewLabel_3.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_3.gridx = 0;
		gbc_lblNewLabel_3.gridy = 5;
		navigatePanel.add(lblNewLabel_3, gbc_lblNewLabel_3);
		
		txtGoalY = new JTextField();
		GridBagConstraints gbc_txtGoalY = new GridBagConstraints();
		gbc_txtGoalY.insets = new Insets(0, 0, 5, 0);
		gbc_txtGoalY.gridwidth = 2;
		gbc_txtGoalY.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtGoalY.gridx = 1;
		gbc_txtGoalY.gridy = 5;
		navigatePanel.add(txtGoalY, gbc_txtGoalY);
		txtGoalY.setColumns(10);
		
		btnNavigate = new JButton("Navigate");
		btnNavigate.addActionListener(new DefaultInputHandler());
		btnNavigate.setFont(new Font("Tahoma", Font.PLAIN, 15));
		GridBagConstraints gbc_btnNavigate = new GridBagConstraints();
		gbc_btnNavigate.anchor = GridBagConstraints.WEST;
		gbc_btnNavigate.insets = new Insets(0, 0, 5, 5);
		gbc_btnNavigate.gridx = 0;
		gbc_btnNavigate.gridy = 6;
		navigatePanel.add(btnNavigate, gbc_btnNavigate);
		
		btnAbort = new JButton("Abort");
		btnAbort.setEnabled(false);
		btnAbort.addActionListener(new DefaultInputHandler());
		btnAbort.setFont(new Font("Tahoma", Font.PLAIN, 15));
		GridBagConstraints gbc_btnAbort = new GridBagConstraints();
		gbc_btnAbort.gridwidth = 3;
		gbc_btnAbort.gridx = 0;
		gbc_btnAbort.gridy = 8;
		navigatePanel.add(btnAbort, gbc_btnAbort);
		
		txtDefaultOutput = new JTextArea();
		txtDefaultOutput.setText("This i the default output");
		txtDefaultOutput.setRows(20);
		bodyPanel.setRightComponent(txtDefaultOutput);
		
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
				loadDialog.setFile("*.json");
				loadDialog.setVisible(true);
				String filename = loadDialog.getDirectory()+loadDialog.getFile();				
				
				controller.execAction("LOAD "+filename);			
				break;
				
			case "Store Map":				
				FileDialog storeDialog = new FileDialog(frame, "Create a File", FileDialog.SAVE);
				storeDialog.setDirectory("C:\\");
				storeDialog.setFile("*.json");
				storeDialog.setVisible(true);
				filename = storeDialog.getDirectory()+storeDialog.getFile();
				
				controller.execAction("STORE "+filename);
				break;
				
			case "Navigate":
				
				String sx = txtStartX.getText();
				String sy = txtStartY.getText();
				String gx = txtGoalX.getText();
				String gy = txtGoalY.getText();
				
				if(!gx.equals("") && !gy.equals(""))
				{
					if(!sx.equals("") && !sy.equals(""))
					{
						try
						{
							controller.execAction("NAVIGATE "
								+Integer.parseInt(sx)+","						
								+Integer.parseInt(sy)+","
								+Integer.parseInt(gx)+","
								+Integer.parseInt(gy)+","
								);
							
							btnAbort.setEnabled(true);
							
							btnExplore.setEnabled(false);
							btnLoad.setEnabled(false);
							btnStore.setEnabled(false);
						}
						catch(NumberFormatException e1)
						{
							println(e1.getMessage());
						}
					}
					else
					{
						try
						{
							controller.execAction("NAVIGATE "
								+Integer.parseInt(gx)+","
								+Integer.parseInt(gy)+","
								);
							
							btnAbort.setEnabled(true);
							
							btnExplore.setEnabled(false);
							btnNavigate.setEnabled(false);
							btnLoad.setEnabled(false);
							btnStore.setEnabled(false);
						}
						catch(NumberFormatException e1)
						{
							println(e1.getMessage());
						}
					}
				}
				else
					println("ERROR: invalid GOAL position");
				break;
				
			case "Explore":				

				String mils = txtExploreTime.getText();
				
				if(mils.equals(""))
				{
					println("please insert max explore time");
				}
				else
				{
					String envType = envTypeChoice.getSelectedItem();
					
					try
					{
						controller.execAction("EXPLORE "
							+envType+","
							+Long.parseLong(mils)
							);
						
						btnAbort.setEnabled(true);
						
						btnNavigate.setEnabled(false);
						btnExplore.setEnabled(false);
						btnLoad.setEnabled(false);
						btnStore.setEnabled(false);						
					}
					catch(NumberFormatException e1)
					{
						println(e1.getMessage());
					}
				}				
				break;
				
			case "Abort":
				controller.execAction("ABORT ");
				
				btnAbort.setEnabled(false);
				
				btnNavigate.setEnabled(true);
				btnExplore.setEnabled(true);
				btnLoad.setEnabled(true);
				btnStore.setEnabled(true);
				
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
		txtDefaultOutput.setText("");
		txtDefaultOutput.validate();
	}//clear
	

	
	
	
	/*
	 * ROBA DELLE INTERFACCE, SERVE SOLO A FARE ANDARE IL FRAMEWORK
	 */

	@Override
	public synchronized String getCurVal() {
		return txtDefaultOutput.getText();
	}

	@Override
	public void addOutput(String msg) {
		txtDefaultOutput.append(msg+"\n");
		txtDefaultOutput.validate();
	}

	@Override
	public void setOutput(String msg) {
		
		txtDefaultOutput.setText(msg);
		txtDefaultOutput.validate();
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
		txtDefaultOutput.append(msg+"\n");
		txtDefaultOutput.validate();
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

	
	
	/*
	 *=============================================================
	 * EREDITATE DA IConsole
	 *=============================================================
	 */
	
	
	@Override
	public IMap explore(String EnvType, long maxMilsTime) {
		return null;
	}

	@Override
	public void navigate(double goalX, double goalY) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void navigate(double startX, double startY, double goalX, double goalY) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void adbort() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void storeMap(IMap map, String filepath) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public IMap loadMap(String filepath) {
		return null;
	}

	@Override
	public IMap getMap() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
		return curVal;
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getDefaultRep() {
		// TODO Auto-generated method stub
		return null;
	}



	
	
	
}
