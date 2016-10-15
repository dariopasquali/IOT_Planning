package it.unibo.explorer.gui;

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
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import it.unibo.domain.model.implmentation.Map;
import it.unibo.domain.model.implmentation.MapElement;
import it.unibo.domain.model.interfaces.IMapElement;
import it.unibo.explorer.algo.LeftHandExplorer;
import it.unibo.explorer.gui.MapViewerPanel.CellState;
import it.unibo.planning.astar.domain.State;
import it.unibo.planning.astar.enums.Algo;
import it.unibo.planning.astar.enums.Direction;

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
import javax.swing.JRadioButton;

public class ExplorerGUI extends Frame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JFrame frame;
	
	private JTextArea txtOut;
	private JScrollBar scrollBar;
	
	private JPanel panelMap;
	private JSplitPane splitMapAndOutput;
	private MapViewerPanel mapViewer;
	
	private Map map;
	private ArrayList<State> path;
	
	JButton btnExplore, btnLoad;
	
	private Algo searchAlgorithm;
	
	//private JFileChooser fileLoader, fileSaver;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ExplorerGUI window = new ExplorerGUI();
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
	public ExplorerGUI() {
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
		
		btnExplore = new JButton("Explore");
		GridBagConstraints gbc_btnExplore = new GridBagConstraints();
		gbc_btnExplore.insets = new Insets(0, 0, 5, 0);
		gbc_btnExplore.gridx = 0;
		gbc_btnExplore.gridy = 2;
		frame.getContentPane().add(btnExplore, gbc_btnExplore);
		btnExplore.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnExplore.addActionListener(new DefaultInputHandler());
		btnExplore.setEnabled(false);
		
		Component verticalStrut_1 = Box.createVerticalStrut(20);
		GridBagConstraints gbc_verticalStrut_1 = new GridBagConstraints();
		gbc_verticalStrut_1.insets = new Insets(0, 0, 5, 0);
		gbc_verticalStrut_1.gridx = 0;
		gbc_verticalStrut_1.gridy = 3;
		frame.getContentPane().add(verticalStrut_1, gbc_verticalStrut_1);
		
		splitMapAndOutput = new JSplitPane();
		splitMapAndOutput.setContinuousLayout(true);
		splitMapAndOutput.setOneTouchExpandable(true);
		GridBagConstraints gbc_splitMapAndOutput = new GridBagConstraints();
		gbc_splitMapAndOutput.gridheight = 2;
		gbc_splitMapAndOutput.fill = GridBagConstraints.BOTH;
		gbc_splitMapAndOutput.gridx = 0;
		gbc_splitMapAndOutput.gridy = 4;
		frame.getContentPane().add(splitMapAndOutput, gbc_splitMapAndOutput);
		
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
		
		searchAlgorithm = Algo.ONLY_TILED;
		
		/*
		fileLoader = new JFileChooser();
		fileLoader.setFileSelectionMode(JFileChooser.FILES_ONLY);
		
		fileSaver = new JFileChooser();
		fileSaver.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		*/
	}



	private class DefaultInputHandler implements ActionListener{

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
				println("QActor  ERROR " + e.getMessage());
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
				
				btnExplore.setEnabled(true);				
				break;
				
				
			case "Explore":
				
				//TODO
				
				MapElement start = mapViewer.getStart();				
				
				if(start == null)
				{
					println("Please select START position");
					return;
				}
				
				int sx = start.getX();
				int sy = start.getY();
				
				
				
				LeftHandExplorer explorer = new LeftHandExplorer(mapViewer, map);
				
				
				break;
				
			
			default:
				println("Invalid command");
			}
		}
		
	}

	
	public synchronized void clear(  ){
		txtOut.setText("");
		txtOut.validate();
	}//clear
	

	/*
	 * ROBA DELLE INTERFACCE, SERVE SOLO A FARE ANDARE IL FRAMEWORK
	 */

	public void setVisible(boolean state)
	{
		frame.setVisible(state);
	}


	public void println(String msg) {
		txtOut.append(msg+"\n");
		txtOut.validate();
		txtOut.setCaretPosition(txtOut.getDocument().getLength());
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
