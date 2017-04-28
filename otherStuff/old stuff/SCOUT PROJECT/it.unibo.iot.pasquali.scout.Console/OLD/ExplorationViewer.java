package it.unibo.gui;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JButton;

import it.unibo.model.implementation.MapElement;


/**
 * @see http://stackoverflow.com/questions/7702697
 */
public class ExplorationViewer extends MapViewer{

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
			
			if(e.getButton() == MouseEvent.BUTTON1)
			{
				if(getCellState(y, x).equals(CellState.OBJECT))
					return;
				
				if(start != null)
				{
					setCellState(start.getY(), start.getX(), CellState.CLEAR);
				}
				
				setCellState(y,x, CellState.START);
				start = new MapElement(y,x);
			}
			else 
			{
				
			}
		}				
	}
	
	public ExplorationViewer(boolean enableClick)
	{
		super(enableClick);
	}
	
	
	@Override
    protected JButton createCell(final int y, final int x) {
        
    	final JButton b = new JButton("");
                        
        if(super.enableClick)
        	b.addMouseListener(new ClickHandler(y, x));
        return b;
    }
	
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
    
    





	
	
	

	
}