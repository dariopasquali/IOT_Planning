package it.unibo.console.ImageGui;

import java.awt.FileDialog;

import javax.swing.JFrame;
import java.awt.Component;

import javax.imageio.ImageIO;
import javax.swing.Box;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Panel;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;

import it.unibo.domain.model.implmentation.IntMap;
import it.unibo.domain.model.interfaces.IGUI;
import it.unibo.domain.model.interfaces.IMap;
import it.unibo.is.interfaces.IActivity;
import it.unibo.is.interfaces.IActivityBase;
import it.unibo.is.interfaces.IBasicEnvAwt;
import it.unibo.is.interfaces.IOutputEnvView;
import it.unibo.is.interfaces.IOutputView;

import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Frame;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.JScrollPane;
import javax.swing.JScrollBar;

public class ImageGUI extends Frame implements IOutputEnvView, IBasicEnvAwt, IGUI{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JFrame frame;
	private JFrame mapFrame;
	private ImagePanel imagePanel;
	
	private IActivityBase controller;	
	private JPanel panelMap;
	
	private IMap map;
	private ArrayList<Point> path;
	
	JButton btnLoad;

	private JButton btnFind;
	private JButton btnNavigate;
	private JButton btnAbort;
	private JScrollPane panelOut;
	private JTextArea txtOut;
	private JScrollBar scrollBar;
	
	/**
	 * Create the application.
	 */
	public ImageGUI(IActivityBase controller) {
		System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
		initialize();
		this.controller = controller;
	}
	
	public ImageGUI() {
		System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 662, 286);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{126, 97, 127, 135, 67, 0};
		gridBagLayout.rowHeights = new int[]{27, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		frame.getContentPane().setLayout(gridBagLayout);
		
		Box boxMapManager = Box.createHorizontalBox();
		GridBagConstraints gbc_boxMapManager = new GridBagConstraints();
		gbc_boxMapManager.anchor = GridBagConstraints.NORTHWEST;
		gbc_boxMapManager.insets = new Insets(0, 0, 5, 5);
		gbc_boxMapManager.gridx = 1;
		gbc_boxMapManager.gridy = 0;
		frame.getContentPane().add(boxMapManager, gbc_boxMapManager);
		
		btnLoad = new JButton("Load Map");
		GridBagConstraints gbc_btnLoad = new GridBagConstraints();
		gbc_btnLoad.insets = new Insets(0, 0, 5, 5);
		gbc_btnLoad.gridx = 0;
		gbc_btnLoad.gridy = 1;
		frame.getContentPane().add(btnLoad, gbc_btnLoad);
		btnLoad.addActionListener(new DefaultInputHandler());
		btnLoad.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		panelOut = new JScrollPane();
		GridBagConstraints gbc_panelOut = new GridBagConstraints();
		gbc_panelOut.gridheight = 5;
		gbc_panelOut.insets = new Insets(0, 0, 5, 0);
		gbc_panelOut.gridwidth = 4;
		gbc_panelOut.fill = GridBagConstraints.BOTH;
		gbc_panelOut.gridx = 1;
		gbc_panelOut.gridy = 1;
		frame.getContentPane().add(panelOut, gbc_panelOut);
		
		txtOut = new JTextArea();
		txtOut.setRows(21);
		txtOut.setFont(new Font("Monospaced", Font.PLAIN, 14));
		txtOut.setEditable(false);	
		panelOut.setViewportView(txtOut);
		
		scrollBar = new JScrollBar();
		panelOut.setRowHeaderView(scrollBar);
		panelOut.add(scrollBar);
		
		btnFind = new JButton("Find Best Path");
		btnFind.setEnabled(false);
		btnFind.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnFind.addActionListener(new DefaultInputHandler());
		GridBagConstraints gbc_btnFind = new GridBagConstraints();
		gbc_btnFind.anchor = GridBagConstraints.NORTH;
		gbc_btnFind.insets = new Insets(0, 0, 5, 5);
		gbc_btnFind.gridx = 0;
		gbc_btnFind.gridy = 2;
		frame.getContentPane().add(btnFind, gbc_btnFind);
		
		btnNavigate = new JButton("Start Navigation");
		btnNavigate.addActionListener(new DefaultInputHandler());
		btnNavigate.setEnabled(false);
		btnNavigate.setFont(new Font("Tahoma", Font.PLAIN, 15));
		GridBagConstraints gbc_btnNavigate = new GridBagConstraints();
		gbc_btnNavigate.anchor = GridBagConstraints.NORTH;
		gbc_btnNavigate.insets = new Insets(0, 0, 5, 5);
		gbc_btnNavigate.gridx = 0;
		gbc_btnNavigate.gridy = 3;
		frame.getContentPane().add(btnNavigate, gbc_btnNavigate);
		
		btnAbort = new JButton("Abort");
		btnAbort.addActionListener(new DefaultInputHandler());
		btnAbort.setEnabled(false);
		btnAbort.setFont(new Font("Tahoma", Font.PLAIN, 15));
		GridBagConstraints gbc_btnAbort = new GridBagConstraints();
		gbc_btnAbort.insets = new Insets(0, 0, 5, 5);
		gbc_btnAbort.anchor = GridBagConstraints.NORTH;
		gbc_btnAbort.gridx = 0;
		gbc_btnAbort.gridy = 4;
		frame.getContentPane().add(btnAbort, gbc_btnAbort);
				
	}

	
	

	private void initializeImagePanel(Mat image)
	{
		Mat colourImage = new Mat(image.size(), CvType.CV_8UC3);
		Imgproc.cvtColor(image, colourImage, Imgproc.COLOR_GRAY2RGB);
		
		MatOfByte matOfByte = new MatOfByte();
	    Highgui.imencode(".jpg", colourImage, matOfByte);
	    byte[] byteArray = matOfByte.toArray();
	    BufferedImage bufImage = null;
	    try {
	        InputStream in = new ByteArrayInputStream(byteArray);
	        bufImage = ImageIO.read(in);
	        mapFrame = new JFrame();
	        
//	        imageLabel = new JLabel(new ImageIcon(bufImage));


	        imagePanel = new ImagePanel(bufImage, (IntMap) map);
	        imagePanel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                	
                	Point panelPoint = e.getPoint();
                    Point imgContext = imagePanel.toImageContext(panelPoint);
                    println(imgContext.toString());
                	
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
	

	private class DefaultInputHandler implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			
			switch(e.getActionCommand())
			{
			case "Load Map":				
				FileDialog loadDialog = new FileDialog(frame, "Choose the Map", FileDialog.LOAD);
				loadDialog.setDirectory("C:\\");
				loadDialog.setFile("*.jpg");
				loadDialog.setVisible(true);
				String filename = loadDialog.getDirectory()+loadDialog.getFile();
				if(filename.contains("null"))
					break;
				
				btnFind.setEnabled(true);
				controller.execAction("LOAD "+filename);
				break;
				
				
			case "Find Best Path":
				
				Point goal = imagePanel.getGoal();
				Point start = imagePanel.getStart();				
				
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
				
				int sx = start.x;
				int sy = start.y;
				int gx = goal.x;
				int gy = goal.y;
				
				try
				{
					controller.execAction("FIND "
						+sx+","						
						+sy+","
						+gx+","
						+gy+","
						+"a"+","
						);
					
					btnNavigate.setEnabled(true);							
					btnLoad.setEnabled(false);
				}
				catch(NumberFormatException e1)
				{
					println(e1.getMessage());
				}
			
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
		
		imagePanel.clearPath();		
		this.path = (ArrayList<Point>) list;
		
		for(Point s : list)
		{
			imagePanel.addPathElement(s);			
		}		
	}
	
	public void setMap(IMap map, Mat image) {
		
		this.map = map;
		initializeImagePanel(image);		
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
