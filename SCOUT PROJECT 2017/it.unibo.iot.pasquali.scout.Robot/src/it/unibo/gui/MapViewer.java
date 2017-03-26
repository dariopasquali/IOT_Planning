package it.unibo.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import it.unibo.gui.enums.CellState;
import it.unibo.is.interfaces.IActivityBase;
import it.unibo.model.map.Map;
import it.unibo.model.map.MapElement;

public class MapViewer {
	
	protected JPanel p;
	protected Map map;
	
	protected int ymax, // RIGHE
				xmax; // COLONNE
	
    protected JButton[][] matrix = null;
    protected boolean enableClick;
    
    protected MapElement start, goal;
    
    protected MapElement currentPosition;
    protected CellState lastCurrentCellState;
	private IActivityBase controller;
 
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
			
			
		}

		//TODO
		@Override
		public void mouseReleased(MouseEvent e) {
			
			if(!map.isCellClear(y,x))
				return;
			
			getGridButton(y, x).setBackground(CellState.OBJECT.getColor());
			map.setCellObj(y, x);
			controller.execAction("WORLDCHANGED "+x+","+y);			
		}				
	}
    
    public MapViewer(boolean enableClick, IActivityBase controller)
    {
    	this.enableClick = enableClick;
    	this.controller = controller;
    }
 
// ACCESSOR --------------------------------------------------------    
    
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
	
    protected JButton getGridButton(int y, int x) {
        return matrix[y][x];
    }
    
    public MapElement getStart() {
		return start;
	}
    
    public JPanel getPanel() {
		return p;
	}

// CREATION ---------------------------------------------------------	

	protected JButton createCell(final int y, final int x) {
        final JButton b = new JButton("");
        b.setEnabled(true);        
        if(enableClick)
        	b.addMouseListener(new ClickHandler(y, x));
        return b;
    }

    public JPanel createGridPanel(int ymax, int xmax) {
    	
    	this.ymax = ymax;
    	this.xmax = xmax;
    	matrix = new JButton[ymax+1][xmax+1];
    	map = new Map(ymax, xmax);
    	map.clearAll();
    	
        p = new JPanel(new GridLayout(ymax+1, xmax+1));
        
        for(int y = 0; y <=ymax; y ++)
        {
        	for(int x = 0; x <=xmax; x++)
        	{
        		JButton gb = createCell(y, x);
        		gb.setBackground(CellState.CLEAR.getColor());
        		gb.setPreferredSize(new Dimension(20,20));
                matrix[y][x] = gb;
                p.add(gb);
        	}
        }
        
        return p;
    }
	
	
// CELL STATE MANAGEMENT -------------------------------------------
	
    public CellState getCellState(int y, int x)
    {
    	return CellState.fromColor(getGridButton(y, x).getBackground());
    }
	
	public void setCellState(int y, int x, CellState state)
    {
    	getGridButton(y, x).setBackground(state.getColor());
    	getGridButton(y, x).setEnabled(true);;
    	map.setCell(y, x, state.getMapState());
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
    	map.setCellClear(y, x);
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
    	map.setCellObj(y, x);
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
    	map.setCellNone(y, x);
    	p.repaint();
    	
    }
    
    public void clearAll() {
		
		for(int y = 0; y<=ymax; y++)
			for(int x = 0; x<=xmax; x++)
				matrix[y][x].setBackground(Color.GRAY);
		
		map.clearAll();		
	}
    
	public void setCurrentPosition(int y, int x, String direction) {
		
		//CellState newCellState = getCellState(y, x);
		setCellState(y, x, CellState.START);
		getGridButton(y, x).setText(direction);
		
		if(currentPosition != null && !(currentPosition.getX() == x && currentPosition.getY() == y))
		{			
			setCellState(currentPosition.getY(), currentPosition.getX(), lastCurrentCellState);
			getGridButton(currentPosition.getY(), currentPosition.getX()).setText("");
		}
				
		currentPosition = new MapElement(x, y);
		this.lastCurrentCellState = CellState.CLEAR;		
	}
    
    
// EXPLORATION SPECIFIC ------------------------------------------------
    
	public void noneAll()
	{
		for(int y=0; y<=ymax; y++)
		{
			for(int x=0; x<=xmax; x++)
			{
				getGridButton(y, x).setBackground(CellState.NONE.getColor());
			}
		}
	}
	
// NAVIGATION SPECIFIC ----------------------------------------------------
	
	public void clearPath()
	{
		for(int y=0; y<=ymax; y++)
		{
			for(int x=0; x<=xmax; x++)
			{
				if(!matrix[y][x].getBackground().equals(CellState.OBJECT.getColor()))
					setCellClear(y, x);
			}
		}
	}

	
	public MapElement getGoal() {
		return goal;
	}


	public void showStartAndGoal() {

		getGridButton(start.getY(), start.getX()).setBackground(CellState.START.getColor());
		getGridButton(goal.getY(), goal.getX()).setBackground(CellState.GOAL.getColor());
		
	}



}
