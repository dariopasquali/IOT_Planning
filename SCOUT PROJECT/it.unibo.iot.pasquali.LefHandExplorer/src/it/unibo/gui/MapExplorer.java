package it.unibo.gui;

import java.awt.EventQueue;
import java.awt.FileDialog;

import javax.swing.JFrame;
import java.awt.Component;
import javax.swing.Box;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import it.unibo.domain.Map;
import it.unibo.gui.MapViewerPanel.CellState;

import java.awt.Color;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Frame;
import javax.swing.JTextArea;
import javax.swing.JRadioButton;
import javax.swing.UIManager;
import javax.swing.JSpinner;
import javax.imageio.ImageIO;
import javax.swing.SpinnerListModel;
import javax.swing.JCheckBox;
import javax.swing.SwingUtilities;
import java.awt.FlowLayout;

public class MapExplorer extends Frame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JFrame frame;
	
	protected String curVal = "";
	
	
	
	private Map map;
	private Map unexploredMap;
		
	private JPanel panelMap;
	private JPanel panelExploredMap;
	
	private MapViewerPanel mapViewer;
	private MapViewerPanel unexploredMapViewer;
	
	private JSplitPane bodyPanel;
	
	JButton btnLoad, btnSaveMap;

	public JFrame mapFrame;
	
	private JButton btnExplore;
	
	//private JFileChooser fileLoader, fileSaver;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MapExplorer window = new MapExplorer();
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
	public MapExplorer() {
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
		gridBagLayout.rowHeights = new int[]{20, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
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
		
		btnSaveMap = new JButton("Save Explored Map");
		btnSaveMap.addActionListener(new DefaultInputHandler());
		btnSaveMap.setEnabled(false);
		btnSaveMap.setFont(new Font("Tahoma", Font.PLAIN, 15));
		boxMapManager.add(btnSaveMap);
		
		btnExplore = new JButton("EXPLORE");
		btnExplore.setEnabled(false);
		btnExplore.setFont(new Font("Tahoma", Font.PLAIN, 15));
		boxMapManager.add(btnExplore);
		
		Component verticalStrut_1 = Box.createVerticalStrut(20);
		GridBagConstraints gbc_verticalStrut_1 = new GridBagConstraints();
		gbc_verticalStrut_1.insets = new Insets(0, 0, 5, 0);
		gbc_verticalStrut_1.gridx = 0;
		gbc_verticalStrut_1.gridy = 3;
		frame.getContentPane().add(verticalStrut_1, gbc_verticalStrut_1);
		
		bodyPanel = new JSplitPane();
		bodyPanel.setContinuousLayout(true);
		bodyPanel.setOneTouchExpandable(true);
		GridBagConstraints gbc_bodyPanel = new GridBagConstraints();
		gbc_bodyPanel.insets = new Insets(0, 0, 5, 0);
		gbc_bodyPanel.fill = GridBagConstraints.BOTH;
		gbc_bodyPanel.gridx = 0;
		gbc_bodyPanel.gridy = 4;
		frame.getContentPane().add(bodyPanel, gbc_bodyPanel);
		
		panelMap = new JPanel();
		panelMap.setBorder(null);
		bodyPanel.setLeftComponent(panelMap);
		panelMap.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		panelExploredMap = new JPanel();
		bodyPanel.setRightComponent(panelExploredMap);
		
		mapViewer = new MapViewerPanel();
		unexploredMapViewer = new MapViewerPanel();
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
				
				panelExploredMap = new JPanel();
				
				loadMap(filename);				
				
				btnSaveMap.setEnabled(true);
				btnExplore.setEnabled(true);
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
				break;
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
			setMap(m,1);
			setUnexploredMap();
		}
		
	}

	
		
	
	public void setMap(Map map, int precision) {

		this.map = map;
		
		mapViewer.createGridPanel(map.getHeight()+precision, map.getWidth()+precision);
		Integer[][] intmap = map.getIntMap();
		
		for(int k=0; k<=map.getWidth(); k++)
		{
			for(int j = 0; j<=map.getHeight(); j++)
			{
				if(intmap[k][j] == Map.OBJ)
					mapViewer.setCellState(k, j, CellState.OBJECT);
				
			}
		}
		
		panelMap = mapViewer.getPanel();
		bodyPanel.setLeftComponent(panelMap);
		
		mapViewer.setMap(map);
	}
	
	public void setUnexploredMap() {

		unexploredMapViewer.createGridPanel(map.getHeight(), map.getWidth());
		
		this.unexploredMap = new Map(map.getWidth(), map.getHeight());
		
		for(int k=0; k<=map.getWidth(); k++)
		{
			for(int j = 0; j<=map.getHeight(); j++)
			{
				mapViewer.setCellState(k, j, CellState.OBJECT);
				
			}
		}
		
		panelExploredMap = unexploredMapViewer.getPanel();
		bodyPanel.setRightComponent(panelExploredMap);
		
		unexploredMapViewer.setMap(unexploredMap);
	}

	public void storeMap(String fname) {
		
		unexploredMap = unexploredMapViewer.getMap();
		
		try
		{
			FileOutputStream fsout = new FileOutputStream( new File(fname) );
			fsout.write(unexploredMap.toString().getBytes());
			fsout.close();
		} catch (Exception e) {
			System.out.println("QActor  ERROR " + e.getMessage());
 		}
		
	}
	
	
}
