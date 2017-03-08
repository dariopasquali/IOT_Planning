package it.unibo.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import it.unibo.model.implementation.Map;
import it.unibo.model.implementation.MapElement;

public abstract class MapViewer {
	
	protected JPanel p;
	protected Map map;
	
	protected int ymax, // RIGHE
				xmax; // COLONNE
	
    protected JButton[][] matrix = null;
    protected boolean enableClick;
    
    protected MapElement start;
    
    public MapViewer(boolean enableClick)
    {
    	this.enableClick = enableClick;
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

	protected abstract JButton createCell(final int y, final int x);

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

}
