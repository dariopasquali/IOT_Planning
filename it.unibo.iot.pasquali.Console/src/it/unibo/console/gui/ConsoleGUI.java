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
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import it.unibo.console.gui.GridButtonPanel.CellState;
import it.unibo.domain.model.implmentation.Map;
import it.unibo.domain.model.interfaces.IConsole;
import it.unibo.domain.model.interfaces.IMap;
import it.unibo.domain.model.interfaces.IMapElement;
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
import javax.swing.JScrollPane;
import javax.swing.JScrollBar;
import java.awt.GridLayout;

public class ConsoleGUI extends Frame implements IOutputEnvView, IBasicEnvAwt{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JFrame frame;
	
	protected String curVal = "";

	private IActivityBase controller;
	private JTextField txtStartX;
	private JTextField txtStartY;
	private JTextField txtGoalX;
	private JTextField txtGoalY;
	
	private JTextArea txtOut;
	
	private JPanel panelMap;
	private JSplitPane splitRight;
	private GridButtonPanel gbp;
	
	private Map map;
	
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
		gbc_bodyPanel.insets = new Insets(0, 0, 5, 0);
		gbc_bodyPanel.fill = GridBagConstraints.BOTH;
		gbc_bodyPanel.gridx = 0;
		gbc_bodyPanel.gridy = 3;
		frame.getContentPane().add(bodyPanel, gbc_bodyPanel);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Navigation", TitledBorder.LEADING, TitledBorder.TOP, new Font("Tahoma", Font.PLAIN, 13), null));
		bodyPanel.setLeftComponent(panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{136, 0, 46, 0};
		gbl_panel.rowHeights = new int[]{0, 14, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_panel.columnWeights = new double[]{0.0, 1.0, 1.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		Component verticalStrut_2 = Box.createVerticalStrut(10);
		GridBagConstraints gbc_verticalStrut_2 = new GridBagConstraints();
		gbc_verticalStrut_2.insets = new Insets(0, 0, 5, 5);
		gbc_verticalStrut_2.gridx = 1;
		gbc_verticalStrut_2.gridy = 0;
		panel.add(verticalStrut_2, gbc_verticalStrut_2);
		
		JLabel label = new JLabel("Start X");
		label.setFont(new Font("Tahoma", Font.PLAIN, 15));
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.insets = new Insets(0, 0, 5, 5);
		gbc_label.gridx = 0;
		gbc_label.gridy = 1;
		panel.add(label, gbc_label);
		
		txtStartX = new JTextField();
		txtStartX.setColumns(10);
		GridBagConstraints gbc_txtStartX = new GridBagConstraints();
		gbc_txtStartX.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtStartX.gridwidth = 2;
		gbc_txtStartX.insets = new Insets(0, 0, 5, 0);
		gbc_txtStartX.gridx = 1;
		gbc_txtStartX.gridy = 1;
		panel.add(txtStartX, gbc_txtStartX);
		
		JLabel label_1 = new JLabel("Start Y");
		label_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		GridBagConstraints gbc_label_1 = new GridBagConstraints();
		gbc_label_1.insets = new Insets(0, 0, 5, 5);
		gbc_label_1.gridx = 0;
		gbc_label_1.gridy = 2;
		panel.add(label_1, gbc_label_1);
		
		txtStartY = new JTextField();
		txtStartY.setColumns(10);
		GridBagConstraints gbc_txtStartY = new GridBagConstraints();
		gbc_txtStartY.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtStartY.gridwidth = 2;
		gbc_txtStartY.insets = new Insets(0, 0, 5, 0);
		gbc_txtStartY.gridx = 1;
		gbc_txtStartY.gridy = 2;
		panel.add(txtStartY, gbc_txtStartY);
		
		JLabel label_2 = new JLabel("Goal X");
		label_2.setFont(new Font("Tahoma", Font.PLAIN, 15));
		GridBagConstraints gbc_label_2 = new GridBagConstraints();
		gbc_label_2.insets = new Insets(0, 0, 5, 5);
		gbc_label_2.gridx = 0;
		gbc_label_2.gridy = 4;
		panel.add(label_2, gbc_label_2);
		
		txtGoalX = new JTextField();
		txtGoalX.setColumns(10);
		GridBagConstraints gbc_txtGoalX = new GridBagConstraints();
		gbc_txtGoalX.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtGoalX.gridwidth = 2;
		gbc_txtGoalX.insets = new Insets(0, 0, 5, 0);
		gbc_txtGoalX.gridx = 1;
		gbc_txtGoalX.gridy = 4;
		panel.add(txtGoalX, gbc_txtGoalX);
		
		JLabel label_3 = new JLabel("Goal Y");
		label_3.setFont(new Font("Tahoma", Font.PLAIN, 15));
		GridBagConstraints gbc_label_3 = new GridBagConstraints();
		gbc_label_3.insets = new Insets(0, 0, 5, 5);
		gbc_label_3.gridx = 0;
		gbc_label_3.gridy = 5;
		panel.add(label_3, gbc_label_3);
		
		txtGoalY = new JTextField();
		txtGoalY.setColumns(10);
		GridBagConstraints gbc_txtGoalY = new GridBagConstraints();
		gbc_txtGoalY.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtGoalY.gridwidth = 2;
		gbc_txtGoalY.insets = new Insets(0, 0, 5, 0);
		gbc_txtGoalY.gridx = 1;
		gbc_txtGoalY.gridy = 5;
		panel.add(txtGoalY, gbc_txtGoalY);
		
		btnFindPath = new JButton("Find Best Path");
		btnFindPath.setFont(new Font("Tahoma", Font.PLAIN, 15));
		GridBagConstraints gbc_btnFindPath = new GridBagConstraints();
		gbc_btnFindPath.gridwidth = 3;
		gbc_btnFindPath.insets = new Insets(0, 0, 5, 0);
		gbc_btnFindPath.gridx = 0;
		gbc_btnFindPath.gridy = 7;
		panel.add(btnFindPath, gbc_btnFindPath);
		btnFindPath.addActionListener(new DefaultInputHandler());
		btnFindPath.setEnabled(false);
		
		btnNavigate = new JButton("Start Navigation");
		btnNavigate.setFont(new Font("Tahoma", Font.PLAIN, 15));
		GridBagConstraints gbc_btnNavigate = new GridBagConstraints();
		gbc_btnNavigate.gridwidth = 3;
		gbc_btnNavigate.insets = new Insets(0, 0, 5, 0);
		gbc_btnNavigate.gridx = 0;
		gbc_btnNavigate.gridy = 9;
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
		gbc_btnManipulate.gridy = 11;
		panel.add(btnManipulate, gbc_btnManipulate);
		//btnManipulate.addActionListener(new DefaultInputHandler());
		btnManipulate.setEnabled(false);
		
		btnAbort = new JButton("Abort");
		btnAbort.setFont(new Font("Tahoma", Font.PLAIN, 15));
		GridBagConstraints gbc_btnAbort = new GridBagConstraints();
		gbc_btnAbort.anchor = GridBagConstraints.ABOVE_BASELINE;
		gbc_btnAbort.gridwidth = 3;
		gbc_btnAbort.insets = new Insets(0, 0, 0, 5);
		gbc_btnAbort.gridx = 0;
		gbc_btnAbort.gridy = 13;
		panel.add(btnAbort, gbc_btnAbort);
		btnAbort.addActionListener(new DefaultInputHandler());
		btnAbort.setEnabled(false);
		
		splitRight = new JSplitPane();
		bodyPanel.setRightComponent(splitRight);
		
		gbp = new GridButtonPanel();	
		
		JScrollPane scrollPane = new JScrollPane((Component) null);
		splitRight.setRightComponent(scrollPane);
		
		txtOut = new JTextArea();
		txtOut.setRows(21);
		txtOut.setFont(new Font("Monospaced", Font.PLAIN, 14));
		txtOut.setEditable(false);	
		scrollPane.setViewportView(txtOut);
		
		JScrollBar scrollBar = new JScrollBar();
		scrollPane.setRowHeaderView(scrollBar);
		scrollPane.add(scrollBar);
		
		
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
				
				String sx = txtStartX.getText();
				String sy = txtStartY.getText();
				String gx = txtGoalX.getText();
				String gy = txtGoalY.getText();
				
				if(!gx.equals("") && !gy.equals("") && !sx.equals("") && !sy.equals(""))
				{
					try
					{
						controller.execAction("FIND "
							+Integer.parseInt(sx)+","						
							+Integer.parseInt(sy)+","
							+Integer.parseInt(gx)+","
							+Integer.parseInt(gy)+","
							);
						
						btnNavigate.setEnabled(true);							
						btnLoad.setEnabled(false);
					}
					catch(NumberFormatException e1)
					{
						println(e1.getMessage());
					}
				}
				else
					println("INVALID COORDINATES!!");
				break;
			
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
	}

	@Override
	public void setOutput(String msg) {
		
		txtOut.setText(msg);
		txtOut.validate();
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
		
		gbp.createGridPanel(map.getYmax()+1, map.getXmax()+1);
		List<IMapElement> elements = map.getElements();
		
		for(IMapElement e : elements)
		{
			gbp.setCellState(e.getY(), e.getX(), CellState.OBSTACLE);
		}
		
		panelMap = gbp.getPanel();
		splitRight.setLeftComponent(panelMap);
	}

	
	
	



	
	
	
}
