package it.unibo.gui;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.SwingUtilities;

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
				getGridButton(y, x).setBackground(CellState.START.getColor());
			}
			else if(SwingUtilities.isRightMouseButton(e))
			{
				if(goal != null)
					setCellClear(goal.getY(), goal.getX());
				
				goal = new MapElement(y, x);
				getGridButton(y, x).setBackground(CellState.GOAL.getColor());
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
                
        if(enableClick)
        	b.addMouseListener(new ClickHandler(y, x));
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


	public void showStartAndGoal() {

		getGridButton(start.getY(), start.getX()).setBackground(CellState.START.getColor());
		getGridButton(goal.getY(), goal.getX()).setBackground(CellState.GOAL.getColor());
		
	}

}
