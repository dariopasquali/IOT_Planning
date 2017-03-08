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

public class NavigationViewer extends MapViewer{
	
    private MapElement goal;

    
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
		super(enableClick);
	}
	
	
	@Override
	protected JButton createCell(final int y, final int x) {
        final JButton b = new JButton("");
        
        ClickHandler handler = new ClickHandler(y, x);
        
        if(enableClick)
        	b.addMouseListener(handler);
        return b;
    }
    
    
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

}
