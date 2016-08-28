package it.unibo.gui;

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
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import it.unibo.domain.Map;
import it.unibo.domain.interfaces.IMapElement;
import it.unibo.gui.MapViewerPanel.CellState;

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
import javax.swing.UIManager;

public class MapCreator extends Frame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JFrame frame;
	
	protected String curVal = "";

	private JTextField txtW;
	private JTextField txtH;
	
	private MapViewerPanel gbp;
	
	private Map map;
	private JPanel panelMap;
	private JSplitPane bodyPanel;
	
	JButton btnGenerate, btnLoad, btnClear, btnSaveMap;
	
	//private JFileChooser fileLoader, fileSaver;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MapCreator window = new MapCreator();
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
	public MapCreator() {
		initialize();
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
		
		Component horizontalStrut = Box.createHorizontalStrut(20);
		boxMapManager.add(horizontalStrut);
		
		btnSaveMap = new JButton("Save Map");
		btnSaveMap.addActionListener(new DefaultInputHandler());
		btnSaveMap.setEnabled(false);
		btnSaveMap.setFont(new Font("Tahoma", Font.PLAIN, 15));
		boxMapManager.add(btnSaveMap);
		
		Component verticalStrut_1 = Box.createVerticalStrut(20);
		GridBagConstraints gbc_verticalStrut_1 = new GridBagConstraints();
		gbc_verticalStrut_1.insets = new Insets(0, 0, 5, 0);
		gbc_verticalStrut_1.gridx = 0;
		gbc_verticalStrut_1.gridy = 2;
		frame.getContentPane().add(verticalStrut_1, gbc_verticalStrut_1);
		
		bodyPanel = new JSplitPane();
		bodyPanel.setContinuousLayout(true);
		bodyPanel.setOneTouchExpandable(true);
		GridBagConstraints gbc_bodyPanel = new GridBagConstraints();
		gbc_bodyPanel.insets = new Insets(0, 0, 5, 0);
		gbc_bodyPanel.fill = GridBagConstraints.BOTH;
		gbc_bodyPanel.gridx = 0;
		gbc_bodyPanel.gridy = 3;
		frame.getContentPane().add(bodyPanel, gbc_bodyPanel);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Instruction", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		bodyPanel.setLeftComponent(panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{136, 0, 46, 0};
		gbl_panel.rowHeights = new int[]{0, 14, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_panel.columnWeights = new double[]{1.0, 1.0, 1.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		Component verticalStrut_2 = Box.createVerticalStrut(10);
		GridBagConstraints gbc_verticalStrut_2 = new GridBagConstraints();
		gbc_verticalStrut_2.insets = new Insets(0, 0, 5, 5);
		gbc_verticalStrut_2.gridx = 1;
		gbc_verticalStrut_2.gridy = 0;
		panel.add(verticalStrut_2, gbc_verticalStrut_2);
		
		JLabel lblWidth = new JLabel("Width");
		lblWidth.setFont(new Font("Tahoma", Font.PLAIN, 15));
		GridBagConstraints gbc_lblWidth = new GridBagConstraints();
		gbc_lblWidth.insets = new Insets(0, 0, 5, 5);
		gbc_lblWidth.gridx = 0;
		gbc_lblWidth.gridy = 2;
		panel.add(lblWidth, gbc_lblWidth);
		
		txtW = new JTextField();
		txtW.setColumns(10);
		GridBagConstraints gbc_txtW = new GridBagConstraints();
		gbc_txtW.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtW.gridwidth = 3;
		gbc_txtW.insets = new Insets(0, 0, 5, 0);
		gbc_txtW.gridx = 1;
		gbc_txtW.gridy = 2;
		panel.add(txtW, gbc_txtW);
		
		JLabel lblHeight = new JLabel("Height");
		lblHeight.setFont(new Font("Tahoma", Font.PLAIN, 15));
		GridBagConstraints gbc_lblHeight = new GridBagConstraints();
		gbc_lblHeight.insets = new Insets(0, 0, 5, 5);
		gbc_lblHeight.gridx = 0;
		gbc_lblHeight.gridy = 3;
		panel.add(lblHeight, gbc_lblHeight);
		
		txtH = new JTextField();
		txtH.setColumns(10);
		GridBagConstraints gbc_txtH = new GridBagConstraints();
		gbc_txtH.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtH.gridwidth = 3;
		gbc_txtH.insets = new Insets(0, 0, 5, 0);
		gbc_txtH.gridx = 1;
		gbc_txtH.gridy = 3;
		panel.add(txtH, gbc_txtH);
		
		btnGenerate = new JButton("Generate");
		btnGenerate.setFont(new Font("Tahoma", Font.PLAIN, 15));
		GridBagConstraints gbc_btnGenerate = new GridBagConstraints();
		gbc_btnGenerate.gridwidth = 3;
		gbc_btnGenerate.insets = new Insets(0, 0, 5, 0);
		gbc_btnGenerate.gridx = 0;
		gbc_btnGenerate.gridy = 4;
		panel.add(btnGenerate, gbc_btnGenerate);
		btnGenerate.addActionListener(new DefaultInputHandler());
		
		btnClear = new JButton("Clear");
		btnClear.addActionListener(new DefaultInputHandler());
		btnClear.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnClear.setEnabled(false);
		GridBagConstraints gbc_btnClear = new GridBagConstraints();
		gbc_btnClear.gridwidth = 3;
		gbc_btnClear.insets = new Insets(0, 0, 5, 0);
		gbc_btnClear.gridx = 0;
		gbc_btnClear.gridy = 5;
		panel.add(btnClear, gbc_btnClear);
		
		JTextArea txtInstructions = new JTextArea();
		txtInstructions.setEditable(false);
		txtInstructions.setText("- Insert Width\r\n- Insert Height\r\n- Click Generate\r\n- Click cell = Clear/Obstacle\r\n- Save Map");
		GridBagConstraints gbc_txtInstructions = new GridBagConstraints();
		gbc_txtInstructions.gridheight = 8;
		gbc_txtInstructions.gridwidth = 3;
		gbc_txtInstructions.fill = GridBagConstraints.BOTH;
		gbc_txtInstructions.gridx = 0;
		gbc_txtInstructions.gridy = 6;
		panel.add(txtInstructions, gbc_txtInstructions);
		
		panelMap = new JPanel();
		bodyPanel.setRightComponent(panelMap);		
		gbp = new MapViewerPanel();
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
				
				panelMap = new JPanel();
				
				loadMap(filename);				
				
				btnGenerate.setEnabled(true);
				btnClear.setEnabled(true);
				btnSaveMap.setEnabled(true);
				break;
				
			case "Generate":
				
				String w = txtW.getText();
				String h = txtH.getText();
				
				if(w.equals("") || h.equals(""))
					break;
				
				Map m = new Map(Integer.parseInt(w), Integer.parseInt(h));
				setMap(m);
				
				btnSaveMap.setEnabled(true);
				btnClear.setEnabled(true);
				
				break;
			
			case "Clear":
				
				gbp.clearAll();
				map = gbp.getMap();
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
				
				break;
				
			default:
				
			}
		}

		private void loadMap(String filename) {
			
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
					m.addElementsFromString(s[1]);
				}
			}
			setMap(m);
		}
		
	}

	
		
	
	/*
	 * ROBA DELLE INTERFACCE, SERVE SOLO A FARE ANDARE IL FRAMEWORK
	 */

	

	public void setMap(Map map) {

		this.map = map;
		
		gbp.createGridPanel(map.getYmax()+1, map.getXmax()+1);
		List<IMapElement> elements = map.getElements();
		
		for(IMapElement e : elements)
		{
			gbp.setCellState(e.getX(), e.getY(), CellState.OBSTACLE);
		}
		
		panelMap = gbp.getPanel();
		bodyPanel.setRightComponent(panelMap);
		
		gbp.setMap(map);
	}

	public void storeMap(String fname) {
		
		map = gbp.getMap();
		
		try
		{
			FileOutputStream fsout = new FileOutputStream( new File(fname) );
			fsout.write(map.toString().getBytes());
			fsout.close();
		} catch (Exception e) {
			System.out.println("QActor  ERROR " + e.getMessage());
 		}
		
	}

	
	
	



	
	
	
}
