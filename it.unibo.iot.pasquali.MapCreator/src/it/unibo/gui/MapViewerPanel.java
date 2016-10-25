package it.unibo.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import it.unibo.domain.Map;
import it.unibo.domain.MapElement;

/**
 * @see http://stackoverflow.com/questions/7702697
 */
public class MapViewerPanel {

	public enum CellState{
		CLEAR,
		OBSTACLE,
		START,
		GOAL,
		PATH
	}
	
	
	
	private JPanel p;
	private Map map;
	private int rows, cols;
    private JButton[][] matrix = null;
	private int brushSize;
	private Color brushColor;
	
	private boolean paintFlag = false;
	private boolean isPainting = false;
	
	private class ClickHandler implements MouseListener, MouseMotionListener{

		private int r;
		private int c;
		
		public ClickHandler(int r, int c)
		{
			this.r = r;
			this.c = c;
		}		
		
		@Override
		public void mouseClicked(MouseEvent e) {
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
						
		}

		@Override
		public void mousePressed(MouseEvent e) {
			
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			
			if(paintFlag)
			{
				isPainting = !isPainting;
				return;
			}
				
			
			int splitBrush = (int)brushSize/2;
			
			if(e.getButton() == MouseEvent.BUTTON1)
			{
				if(splitBrush == 0)
					setState(r,c, brushColor);
				else
				{
					for(int row=r-splitBrush; row<=r+splitBrush; row++)
						for(int col=c-splitBrush; col<=c+splitBrush; col++)
						{
							if(row>=0 && col>=0 && row<getRows() && col<getCols())
								setState(row,col, brushColor);
						}
				}
			}
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			
			
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			
			if(!paintFlag || !isPainting)
				return;
			
			System.out.println("MOVED");
			
			int splitBrush = (int)brushSize/2;
			
			if(splitBrush == 0)
				setState(r,c, brushColor);
			else
			{
				for(int row=r-splitBrush; row<=r+splitBrush; row++)
					for(int col=c-splitBrush; col<=c+splitBrush; col++)
					{
						if(row>=0 && col>=0 && row<getRows() && col<getCols())
							setState(row,col, brushColor);
					}
			}
		}		
	}
	
	
	public void setPaintFlag(boolean value)
	{
		this.paintFlag = value;
		System.out.println("let's paint");
	}
	
	public void setBrushColor(Color color)
	{
		this.brushColor = color;
	}
    

	public void setMap(Map m){
		this.map = m;
	}
	
	public Map getMap()
	{
		return map;
	}
	
	public int getRows()
	{
		return rows;
	}
	
	public int getCols()
	{
		return cols;
	}
    
    private JButton getGridButton(int r, int c) {
        return matrix[c][r];
    }

    private JButton createCell(final int row, final int col) {
        final JButton b = new JButton("");
        
        ClickHandler handler = new ClickHandler(row, col);
        
        b.addMouseListener(handler);
        b.addMouseMotionListener(handler);
        return b;
    }
    
    public void setCellState(int x, int y, CellState state)
    {
    	Color color;    	
    	switch (state){
    	
    	case CLEAR:
    		color = Color.WHITE;
    		break;
    	
    	case OBSTACLE:
    		color = Color.BLACK;
    		break;
    		
    	case START:
    		color = Color.BLUE;
    		break;
    		
    	case GOAL:
    		color = Color.RED;
    		break;
    		
    	case PATH:
    		color = Color.YELLOW;
    		break;
    		
    	default:
    		color = Color.WHITE;
    		break;
    	}
    	
    	getGridButton(y, x).setBackground(color);    
    }
    
    public JPanel createGridPanel(int rows, int cols) {
    	
    	this.rows = rows;
    	this.cols = cols;
    	matrix = new JButton[cols][rows];
    	
        p = new JPanel(new GridLayout(rows, cols));
        
        for(int r = 0; r < rows; r ++)
        {
        	for(int c = 0; c < cols; c++)
        	{
        		JButton gb = createCell(r, c);
        		gb.setBackground(Color.WHITE);
                gb.setPreferredSize(new Dimension(20,20));
                matrix[c][r] = gb;
                p.add(gb);
        	}
        }
        
        return p;
    }
    
	public JPanel getPanel() {
		return p;
	}
	
	public void setState(int r, int c, Color color) {
		
		//JButton b = getGridButton(r, c);
		//Color color = b.getBackground();
		
		if(color.equals(Color.BLACK))
		{
			getGridButton(r,c).setBackground(Color.BLACK);
			map.addElement(new MapElement(c,r));
		}
		else if(color.equals(Color.WHITE))
		{
			getGridButton(r,c).setBackground(Color.WHITE);
			map.removeElement(c,r);
		}
		
		System.out.println("Dimensione mappa "+map.getElements().size());
		
	}

	public void clearAll() {
		
		for(int r = 0; r<rows; r++)
			for(int c = 0; c<cols; c++)
				matrix[c][r].setBackground(Color.WHITE);
		
		map.clearAll();
		
	}

	public void setBrushSize(int size) {
		this.brushSize = size;
		System.out.println("Brush size: "+brushSize);
	}
	
	

	
}