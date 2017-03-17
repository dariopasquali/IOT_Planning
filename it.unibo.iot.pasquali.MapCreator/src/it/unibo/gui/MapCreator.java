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
import it.unibo.domain.MapElement;
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
import javax.swing.UIManager;
import javax.swing.JSpinner;
import javax.imageio.ImageIO;
import javax.swing.SpinnerListModel;
import javax.swing.JCheckBox;
import javax.swing.SwingUtilities;

public class MapCreator extends Frame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JFrame frame;
	
	protected String curVal = "";

	private JTextField txtW;
	private JTextField txtH;
	private JSpinner spinBrush;
	
	private MapViewerPanel gbp;
	
	private Map map;
	private Map imageMap;	
	
	private boolean haveMap, haveImage;
	
	private JPanel panelMap;
	private JSplitPane bodyPanel;
	
	JButton btnGenerate, btnLoad, btnClear, btnSaveMap;
	private JRadioButton radioBlack;
	private JRadioButton radioWhite;
	private JRadioButton rdbtnSingleCellPaint;
	private JRadioButton rdbtnDragPaint;
	private JCheckBox checkBound;
	private JButton btnLoadImage;
	private JButton btnSaveImage;

	public JFrame mapFrame;

	public it.unibo.gui.ImagePanel imagePanel;
	private JButton btnIsEqual;
	
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
		
		btnLoadImage = new JButton("Load Image");
		btnLoadImage.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnLoadImage.addActionListener(new DefaultInputHandler());
		boxMapManager.add(btnLoadImage);
		
		Component horizontalStrut = Box.createHorizontalStrut(20);
		boxMapManager.add(horizontalStrut);
		
		btnSaveMap = new JButton("Save Map");
		btnSaveMap.addActionListener(new DefaultInputHandler());
		btnSaveMap.setEnabled(false);
		btnSaveMap.setFont(new Font("Tahoma", Font.PLAIN, 15));
		boxMapManager.add(btnSaveMap);
		
		btnSaveImage = new JButton("Save Image");
		btnSaveImage.setEnabled(false);
		btnSaveImage.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnSaveImage.addActionListener(new DefaultInputHandler());
		boxMapManager.add(btnSaveImage);
		
		btnIsEqual = new JButton("Check Equality");
		boxMapManager.add(btnIsEqual);
		btnIsEqual.addActionListener(new DefaultInputHandler());
		
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
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Instruction", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		bodyPanel.setLeftComponent(panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{136, 0, 46, 0};
		gbl_panel.rowHeights = new int[]{0, 14, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_panel.columnWeights = new double[]{1.0, 1.0, 1.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
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
		
		checkBound = new JCheckBox("Insert External Boundaries");
		GridBagConstraints gbc_checkBound = new GridBagConstraints();
		gbc_checkBound.gridwidth = 4;
		gbc_checkBound.insets = new Insets(0, 0, 5, 5);
		gbc_checkBound.gridx = 0;
		gbc_checkBound.gridy = 4;
		panel.add(checkBound, gbc_checkBound);
		
		btnGenerate = new JButton("Generate");
		btnGenerate.setFont(new Font("Tahoma", Font.PLAIN, 15));
		GridBagConstraints gbc_btnGenerate = new GridBagConstraints();
		gbc_btnGenerate.gridwidth = 4;
		gbc_btnGenerate.insets = new Insets(0, 0, 5, 0);
		gbc_btnGenerate.gridx = 0;
		gbc_btnGenerate.gridy = 5;
		panel.add(btnGenerate, gbc_btnGenerate);
		btnGenerate.addActionListener(new DefaultInputHandler());
		
		btnClear = new JButton("Clear");
		btnClear.addActionListener(new DefaultInputHandler());
		btnClear.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnClear.setEnabled(false);
		GridBagConstraints gbc_btnClear = new GridBagConstraints();
		gbc_btnClear.gridwidth = 4;
		gbc_btnClear.insets = new Insets(0, 0, 5, 0);
		gbc_btnClear.gridx = 0;
		gbc_btnClear.gridy = 6;
		panel.add(btnClear, gbc_btnClear);
		
		JLabel lblSelectPrecision = new JLabel("Brush Size");
		GridBagConstraints gbc_lblSelectPrecision = new GridBagConstraints();
		gbc_lblSelectPrecision.anchor = GridBagConstraints.BELOW_BASELINE;
		gbc_lblSelectPrecision.insets = new Insets(0, 0, 5, 5);
		gbc_lblSelectPrecision.gridx = 0;
		gbc_lblSelectPrecision.gridy = 9;
		panel.add(lblSelectPrecision, gbc_lblSelectPrecision);
		
		spinBrush = new JSpinner();
		spinBrush.setModel(new SpinnerListModel(new String[] {"1", "3", "5", "7", "9", "13", "15", "17"}));
		GridBagConstraints gbc_spinBrush = new GridBagConstraints();
		gbc_spinBrush.fill = GridBagConstraints.BOTH;
		gbc_spinBrush.gridwidth = 3;
		gbc_spinBrush.insets = new Insets(0, 0, 5, 0);
		gbc_spinBrush.gridx = 1;
		gbc_spinBrush.gridy = 9;
		panel.add(spinBrush, gbc_spinBrush);
		spinBrush.addChangeListener(new ChangeListener(){
			@Override
			public void stateChanged(ChangeEvent arg0) {
				gbp.setBrushSize(Integer.parseInt((String)spinBrush.getValue()));				
			}			
		});
		
		radioBlack = new JRadioButton("Paint Black");
		radioBlack.setSelected(true);
		GridBagConstraints gbc_radioBlack = new GridBagConstraints();
		gbc_radioBlack.insets = new Insets(0, 0, 5, 5);
		gbc_radioBlack.gridx = 0;
		gbc_radioBlack.gridy = 10;
		radioBlack.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				gbp.setBrushColor(Color.BLACK);
				radioWhite.setSelected(false);
				radioBlack.setSelected(true);
			}
			
		});
		panel.add(radioBlack, gbc_radioBlack);
		
		radioWhite = new JRadioButton("Paint White");
		GridBagConstraints gbc_radioWhite = new GridBagConstraints();
		gbc_radioWhite.insets = new Insets(0, 0, 5, 5);
		gbc_radioWhite.gridx = 2;
		gbc_radioWhite.gridy = 10;
		radioWhite.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				gbp.setBrushColor(Color.WHITE);
				radioWhite.setSelected(true);
				radioBlack.setSelected(false);
			}
			
		});
		panel.add(radioWhite, gbc_radioWhite);
		
		rdbtnSingleCellPaint = new JRadioButton("Single Cell Paint");
		rdbtnSingleCellPaint.setSelected(true);
		GridBagConstraints gbc_rdbtnSingleCellPaint = new GridBagConstraints();
		gbc_rdbtnSingleCellPaint.insets = new Insets(0, 0, 5, 5);
		gbc_rdbtnSingleCellPaint.gridx = 0;
		gbc_rdbtnSingleCellPaint.gridy = 11;
		rdbtnSingleCellPaint.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				gbp.setPaintFlag(false);
				rdbtnSingleCellPaint.setSelected(true);
				rdbtnDragPaint.setSelected(false);
			}
			
		});
		panel.add(rdbtnSingleCellPaint, gbc_rdbtnSingleCellPaint);
		
		rdbtnDragPaint = new JRadioButton("Drag Paint");
		GridBagConstraints gbc_rdbtnDragPaint = new GridBagConstraints();
		gbc_rdbtnDragPaint.insets = new Insets(0, 0, 5, 5);
		gbc_rdbtnDragPaint.gridx = 2;
		gbc_rdbtnDragPaint.gridy = 11;
		rdbtnDragPaint.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				gbp.setPaintFlag(true);
				rdbtnSingleCellPaint.setSelected(false);
				rdbtnDragPaint.setSelected(true);
			}
			
		});
		panel.add(rdbtnDragPaint, gbc_rdbtnDragPaint);
		
		JTextArea txtInstructions = new JTextArea();
		txtInstructions.setEditable(false);
		txtInstructions.setText("- Insert Width\r\n- Insert Height\r\n- Click Generate\r\n- Click cell = Clear/Obstacle\r\n- Save Map");
		GridBagConstraints gbc_txtInstructions = new GridBagConstraints();
		gbc_txtInstructions.insets = new Insets(0, 0, 5, 5);
		gbc_txtInstructions.gridheight = 3;
		gbc_txtInstructions.gridwidth = 3;
		gbc_txtInstructions.fill = GridBagConstraints.BOTH;
		gbc_txtInstructions.gridx = 0;
		gbc_txtInstructions.gridy = 12;
		panel.add(txtInstructions, gbc_txtInstructions);
		
		panelMap = new JPanel();
		bodyPanel.setRightComponent(panelMap);		
		gbp = new MapViewerPanel();
		haveMap = false;
		haveImage = false;
		
		//System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		
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
				btnSaveImage.setEnabled(true);
				haveMap = true;
				break;
			
			case "Load Image":
				
				loadDialog = new FileDialog(frame, "Choose the Image", FileDialog.LOAD);
				loadDialog.setDirectory("C:\\");
				loadDialog.setFile("*.jpg");
				loadDialog.setVisible(true);
				filename = loadDialog.getDirectory()+loadDialog.getFile();
				if(filename.contains("null"))
					break;
				
				panelMap = new JPanel();
				
				//loadImage(filename);				
				
				btnGenerate.setEnabled(true);
				btnClear.setEnabled(true);
				btnSaveMap.setEnabled(true);
				btnSaveImage.setEnabled(false);
				haveImage = true;
				
				
				break;
				
			case "Generate":
				
				String w = txtW.getText();
				String h = txtH.getText();
				
				if(w.equals("") || h.equals(""))
					break;
				
				Map m = new Map(Integer.parseInt(w), Integer.parseInt(h));
				
				if(checkBound.isSelected())
				{
					for(int x = 0; x<m.getXmax(); x++)
						m.addElement(new MapElement(x,0));
					
					for(int y = 0; y<m.getYmax(); y++)
						m.addElement(new MapElement(0,y));
					
					for(int x = 0; x<m.getXmax(); x++)
						m.addElement(new MapElement(x,m.getYmax()));
					
					for(int y = 0; y<=m.getYmax(); y++)
						m.addElement(new MapElement(m.getXmax(),y));
				}
				
				setMap(m,1);
				
				btnSaveMap.setEnabled(true);
				btnSaveImage.setEnabled(true);
				btnClear.setEnabled(true);
				haveMap = true;
				haveImage = false;
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
				
			case "Save Image":
				
				storeDialog = new FileDialog(frame, "Create new Image", FileDialog.SAVE);
				storeDialog.setDirectory("C:\\");
				storeDialog.setFile(".jpg");
				storeDialog.setVisible(true);
				fname = storeDialog.getDirectory()+storeDialog.getFile();
				if(fname.contains("null"))
					break;
				
				storeMap(fname);
				
				break;
				
			case "Check Equality":
				
				if(!haveMap)
				{
					System.out.println("please load a map");
					break;
				}
				
				if(!haveImage)
				{
					System.out.println("please load an image");
					break;
				}
				
				Integer[][] map1 = map.getIntMap();
				Integer[][] map2 = imageMap.getIntMap();
				
				if(map1.length-1 != map2.length)
				{
					System.out.println("column number is different!");
					break;
				}
				
				if(map1[0].length-1 != map2[0].length)
				{
					System.out.println("row number is different");
					break;
				}
				
				boolean isEqual = true;
				
				for(int i=0; i<map1.length-1 && isEqual; i++)
					for(int j=0; j<map1[i].length-1 && isEqual; j++)
						isEqual = (map1[i][j] == map2[i][j]);
				
				if(isEqual)
					System.out.println("YES");
				else
					System.out.println("NOPE");
				
				break;
				
			default:
				break;
			}
		}

		/*
		private Integer[][] matToIntMatrix(Mat image, int range)
		{
			MatOfByte matOfByte = new MatOfByte();
		    Highgui.imencode(".jpg", image, matOfByte);
		    byte[] byteArray = matOfByte.toArray();
		    BufferedImage bufImage = null;
		    try 
		    {
		        InputStream in = new ByteArrayInputStream(byteArray);
		        bufImage = ImageIO.read(in);
		        int width = bufImage.getWidth(null);
			    int height = bufImage.getHeight(null);
			    Integer[][] pixels = new Integer[width][height];
			    for (int i = 0; i < width; i++) {
			        for (int j = 0; j < height; j++) {
			            pixels[i][j] = Math.abs(bufImage.getRGB(i, j)/range);
			        }
			    }

			    return pixels;
		    }
		    catch (Exception e) {
		        e.printStackTrace();
		    }
		    return null;
		}
		
		private void loadImage(String filename)
		{
			Mat image = Highgui.imread(filename, Highgui.CV_LOAD_IMAGE_GRAYSCALE);
		    System.out.println(image.size());
		    System.out.println(CvType.channels(image.type()));
		    
		    int cleanSize = 3;
		    Mat element = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new  Size(cleanSize + 1, cleanSize+1));
		    Imgproc.erode(image, image, element);
		    Imgproc.dilate(image, image, element);
		    
		    Mat tresh = new Mat();
		    Imgproc.threshold(image, tresh, 180, 255, Imgproc.ADAPTIVE_THRESH_MEAN_C);
		    
		    List<MatOfPoint> contours = new ArrayList<MatOfPoint>();
		    
		    
//		    Imgproc.findContours(tresh, contours, new Mat(), Imgproc.RETR_LIST , Imgproc.CHAIN_APPROX_SIMPLE);
//		    Mat cMat = new Mat(tresh.size(), CvType.CV_8UC3, new Scalar(255,255,255));
//		    
//		    double area = Double.MIN_VALUE;
//		    for(int i=0; i<contours.size(); i++)
//		    	if(Imgproc.contourArea(contours.get(i)) > area)
//		    		area = Imgproc.contourArea(contours.get(i));
//		    
//		    for(int i=0; i<contours.size(); i++)
//		        if(Imgproc.contourArea(contours.get(i)) != area)
//		        	Imgproc.drawContours(cMat, contours, i, new Scalar(0,0,0));
		    
		    Integer[][] pixels = matToIntMatrix(tresh, 16777215);
		    imageMap = new Map(tresh.width(), tresh.height(), pixels);
		    loadedImage = tresh;
		    initializeImagePanel(loadedImage);
		}
		
		private void initializeImagePanel(Mat image)
		{
			Mat colourImage = new Mat(image.size(), CvType.CV_8UC3);
			
			if(image.channels() == 1)
				Imgproc.cvtColor(image, colourImage, Imgproc.COLOR_GRAY2RGB);
			else
				image.copyTo(colourImage);
			
			MatOfByte matOfByte = new MatOfByte();
		    Highgui.imencode(".jpg", colourImage, matOfByte);
		    byte[] byteArray = matOfByte.toArray();
		    BufferedImage bufImage = null;
		    try {
		        InputStream in = new ByteArrayInputStream(byteArray);
		        bufImage = ImageIO.read(in);
		        mapFrame = new JFrame();
		        
		        imagePanel = new ImagePanel(bufImage, (Map) imageMap);
		        imagePanel.addMouseListener(new MouseAdapter() {
	                @Override
	                public void mouseClicked(MouseEvent e) {
	                	
	                	Point panelPoint = e.getPoint();
	                    Point imgContext = imagePanel.toImageContext(panelPoint);
	                    System.out.println(imgContext.toString());
	                	
	                	if(SwingUtilities.isLeftMouseButton(e))
	        			{
	        				imagePanel.setStart(imgContext);
	        			}
	        			else if(SwingUtilities.isRightMouseButton(e))
	        			{
	        				imagePanel.setGoal(imgContext);
	        			}                   
	                }
	            });
		        
		        mapFrame.setSize(image.width(), image.height());
		        mapFrame.setResizable(false);
		        mapFrame.getContentPane().add(imagePanel);
		        mapFrame.pack();
		        mapFrame.setVisible(true);    
		        

		        
		        
		 
		    } catch (Exception e) {
		        e.printStackTrace();
		    }
		}
		*/
		
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
		}
		
	}

	
		
	
	public void setMap(Map map, int precision) {

		this.map = map;
		
		gbp.createGridPanel(map.getYmax()+precision, map.getXmax()+precision);
		List<IMapElement> elements = map.getElements();
		
		for(IMapElement e : elements)
		{
			gbp.setCellState(e.getX(), e.getY(), CellState.OBSTACLE);
		}
		
		panelMap = gbp.getPanel();
		bodyPanel.setRightComponent(panelMap);
		
		gbp.setMap(map);
		gbp.setBrushSize(Integer.parseInt((String)spinBrush.getValue()));
		gbp.setBrushColor(Color.BLACK);
	}

	public void storeMap(String fname) {
		
		if(haveMap)
		{
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
		else if(haveImage)
		{
			try
			{
				FileOutputStream fsout = new FileOutputStream( new File(fname) );
				fsout.write(imageMap.toString().getBytes());
				fsout.close();
			} catch (Exception e) {
				System.out.println("QActor  ERROR " + e.getMessage());
	 		}
		}		
	}
	/*
	public void storeImage(String fname) {
		
		if(haveMap)
		{
			imageMap = gbp.getMap();			
		}
		
		Mat toWrite = imageMap.getImage();		
		Highgui.imwrite(fname, toWrite);
		
	}
	*/

	
	
	



	
	
	
}
