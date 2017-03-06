package it.unibo.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import it.unibo.model.implementation.Map;
import it.unibo.model.implementation.MapElement;

public class NavigationViewer {
	
	public enum CellState{
		CLEAR(Color.WHITE, Map.CLEAR),
		OBJECT(Color.BLACK, Map.OBJ),
		NONE(Color.GRAY, Map.NONE),
		START(Color.GREEN, Map.NONE),
		GOAL(Color.RED, Map.NONE),
		PATH(Color.RED, Map.NONE);
		
		private Color color;
		private int mapState;
		
		private CellState(Color c, int mapState){this.color = c; this.mapState = mapState;}
		
		public Color getColor(){return color;}
		public int getMapState(){return mapState;}

	}
	
	private JPanel p;
	private Map map;
	
	private int ymax, // RIGHE
				xmax; // COLONNE
	
    private JButton[][] matrix = null;
    private boolean enableClick;
    
    private MapElement start, goal;

    
	private class ClickHandler implements MouseListener{

		private int y;
		private int x;
		
		public ClickHandler(int y, int x)
		{
			this.y = y;
			this.x = x;
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
			
			if(!map.isCellClear(y,x))
				return;
			
			if(SwingUtilities.isLeftMouseButton(e))
			{
				if(start != null)
					setCellClear(start.getY(), start.getX());
				
				start = new MapElement(y, x);
			}
			else if(SwingUtilities.isRightMouseButton(e))
			{
				if(goal != null)
					setCellClear(goal.getY(), goal.getX());
				
				goal = new MapElement(y, x);
			}
			else if(SwingUtilities.isMiddleMouseButton(e))
			{
				if(getGridButton(y, x).getBackground().equals(CellState.START.getColor()))
				{
					start = null;
				}
				else if(getGridButton(y, x).getBackground().equals(CellState.GOAL.getColor()))
				{
					goal = null;
				}
				setCellClear(y, x);				
			}	
		}

		//TODO
		@Override
		public void mouseReleased(MouseEvent e) {
			
		}	
	}
	
	public NavigationViewer(boolean enableClick)
	{
		this.enableClick = enableClick;
	}
	
	
	public void setMap(Map m){
		this.map = m;
	}
	
	public Map getMap()
	{
		return map;
	}
	
	public int getYMax()
	{
		return ymax;
	}
	
	public int getXMax()
	{
		return xmax;
	}
    
    private JButton getGridButton(int y, int x) {
        return matrix[y][x];
    }

    private JButton createCell(final int y, final int x) {
        final JButton b = new JButton("");
        
        ClickHandler handler = new ClickHandler(y, x);
        
        if(enableClick)
        	b.addMouseListener(handler);
        return b;
    }
    
    public void setCellState(int y, int x, CellState state)
    {
    	getGridButton(y, x).setBackground(state.getColor());
    	p.revalidate();
    	p.repaint();
    }
    
    public void setCellClear(int y, int x)
    {
    	if(!(y>=0 &&
				y<=ymax &&
				x>=0 &&
				x<=xmax))
			return;
    	
    	getGridButton(y, x).setBackground(CellState.CLEAR.getColor());
    	p.revalidate();
    	p.repaint();
    	
    }
    
    public void setCellObj(int y, int x)
    {
    	if(!(y>=0 &&
				y<=ymax &&
				x>=0 &&
				x<=xmax))
			return;
    	
    	getGridButton(y, x).setBackground(CellState.OBJECT.getColor());
    	getGridButton(y, x).setEnabled(false);
    	p.revalidate();
    	p.repaint();
    	
    }
    
    public void setCellNone(int y, int x)
    {
    	if(!(y>=0 &&
				y<=ymax &&
				x>=0 &&
				x<=xmax))
			return;
    	
    	getGridButton(y, x).setBackground(CellState.NONE.getColor());
    	p.repaint();
    	
    }
    
	public void setCellPos(int y, int x) {
    	
		if(!(y>=0 &&
				y<=ymax &&
				x>=0 &&
				x<=xmax))
			return;
    	
    	getGridButton(y, x).setBackground(CellState.START.getColor());
    	p.repaint();
	}
    
    public JPanel createGridPanel(int ymax, int xmax) {
    	
    	this.ymax = ymax;
    	this.xmax = xmax;
    	matrix = new JButton[ymax][xmax];
    	
        p = new JPanel(new GridLayout(ymax, xmax));
        
        for(int y = 0; y < ymax; y ++)
        {
        	for(int x = 0; x < xmax; x++)
        	{
        		JButton gb = createCell(y, x);
        		gb.setBackground(CellState.NONE.getColor());
        		gb.setPreferredSize(new Dimension(20,20));
                matrix[y][x] = gb;
                p.add(gb);
        	}
        }
        
        return p;
    }
    
	public JPanel getPanel() {
		return p;
	}
	
	
	public void clearAll() {
		
		for(int y = 0; y<ymax; y++)
			for(int x = 0; x<xmax; x++)
				matrix[y][x].setBackground(Color.GRAY);
		
		map.clearAll();		
	}
	
	public void clearPath()
	{
		for(int y=0; y<ymax; y++)
		{
			for(int x=0; x<xmax; x++)
			{
				if(!matrix[y][x].getBackground().equals(CellState.OBJECT.getColor()))
					setCellClear(y, x);
			}
		}
	}


	public MapElement getStart() {
		return start;
	}
	
	public MapElement getGoal() {
		return goal;
	}

}
