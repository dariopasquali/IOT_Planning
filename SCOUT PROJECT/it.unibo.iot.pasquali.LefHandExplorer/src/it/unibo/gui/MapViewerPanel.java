package it.unibo.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JButton;
import javax.swing.JPanel;

import it.unibo.domain.Map;

/**
 * @see http://stackoverflow.com/questions/7702697
 */
public class MapViewerPanel {

	public enum CellState{
		CLEAR(Color.WHITE, Map.CLEAR),
		OBJECT(Color.BLACK, Map.OBJ),
		NONE(Color.GRAY, Map.NONE),
		START(Color.GREEN, Map.CLEAR);
		
		private Color color;
		private int mapState;
		
		private CellState(Color c, int mapState){this.color = c; this.mapState = mapState;}
		
		public Color getColor(){return color;}
		public int getMapState(){return mapState;}

	}
	
	
	
	private JPanel p;
	private Map map;
	private int rows, cols;
    private JButton[][] matrix = null;
    
    private Point start;

    
	private class ClickHandler implements MouseListener{

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
			
			if(e.getButton() == MouseEvent.BUTTON1)
			{
				if(start != null)
				{
					setCellState(start.y, start.x, CellState.NONE);
				}
				
				setCellState(r,c, CellState.START);
				start = new Point(c, r);
			}
		}				
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
        return b;
    }
    
    public void setCellState(int x, int y, CellState state)
    {
    	getGridButton(y, x).setBackground(state.getColor());
    	map.setElement(x, y, state.getMapState());
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
        		gb.setBackground(CellState.NONE.getColor());
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
	
	
	public void clearAll() {
		
		for(int r = 0; r<rows; r++)
			for(int c = 0; c<cols; c++)
				matrix[c][r].setBackground(Color.GRAY);
		
		map.clearAll();		
	}


	
	

	
}