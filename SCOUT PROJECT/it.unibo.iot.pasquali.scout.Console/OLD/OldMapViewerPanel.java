package it.unibo.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import it.unibo.model.implmentation.Map;
import it.unibo.model.implmentation.MapElement;
import it.unibo.model.interfaces.IMap;


public class OldMapViewerPanel {

	public enum CellState{
		CLEAR,
		OBSTACLE,
		START,
		GOAL,
		PATH;
		
		public static Color getColor(CellState state){
			
			switch (state){
	    	
	    	case CLEAR:
	    		return Color.WHITE;	    		
	    	
	    	case OBSTACLE:
	    		return Color.BLACK;
	    		
	    	case START:
	    		return Color.BLUE;
	    		
	    	case GOAL:
	    		return Color.RED;
	    		
	    	case PATH:
	    		return Color.RED;
	    		
	    	default:
	    		return Color.WHITE;
	    	}
		}
	}
	
	private class ClickHandler implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent e) {
			
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent e) {

			String[] name = ((JButton)e.getSource()).getActionCommand().split(" ");
			int r = Integer.parseInt(name[0]);
			int c = Integer.parseInt(name[1]);
			if(!map.isCellClear(c,r))
				return;
			
			if(SwingUtilities.isLeftMouseButton(e))
			{
				if(start != null)
					setCellState(start.getX(), start.getY(), CellState.CLEAR);
				
				setCellState(c,r,CellState.START);
				start = new MapElement(c,r);
			}
			else if(SwingUtilities.isRightMouseButton(e))
			{
				if(goal != null)
					setCellState(goal.getX(), goal.getY(), CellState.CLEAR);
				
				setCellState(c,r,CellState.GOAL);
				goal = new MapElement(c,r);
			}
			else if(SwingUtilities.isMiddleMouseButton(e))
			{
				if(getCell(c,r).getBackground().equals(CellState.getColor(CellState.START)))
				{
					start = null;
				}
				else if(getCell(c,r).getBackground().equals(CellState.getColor(CellState.START)))
				{
					goal = null;
				}
				setCellState(c,r,CellState.CLEAR);				
			}			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}		
	}
	
	private JPanel p;
	private IMap map;
	private int rows, cols;
    private JButton[][] matrix = null;
    
    private MapElement start = null, goal = null;
    
    public MapElement getStart()
    {
    	return start;
    }
    
    public MapElement getGoal()
    {
    	return goal;
    }

	public void setMap(IMap m){
		this.map = m;
		start = null;
		goal = null;
	}
	
	public IMap getMap()
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
    
    private JButton getCell(int r, int c) {
        return matrix[c][r];
    }

    private JButton createCell(final int row, final int col) {
        final JButton b = new JButton("");
        b.setActionCommand(row+" "+col);
        b.addMouseListener(new ClickHandler());
        return b;
    }
    
    public void setCellState(int col, int row, CellState state)
    {
    	getCell(row, col).setBackground(CellState.getColor(state));    
    }
    
    public JPanel createMapViewer(int rows, int cols) {
    	
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
	
	public void switchState(int r, int c) {
		
		JButton b = getCell(r, c);
		
		Color color = b.getBackground();
		if(color.equals(Color.WHITE))
		{
			getCell(r,c).setBackground(Color.BLACK);
			map.addElement(new MapElement(c,r));
		}
		else if(color.equals(Color.BLACK))
		{
			getCell(r,c).setBackground(Color.WHITE);
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

	public void clearPath() {
		for(int r = 0; r<rows; r++)
			for(int c = 0; c<cols; c++)
				if(!matrix[c][r].getBackground().equals(CellState.getColor(CellState.OBSTACLE)) &&
						!matrix[c][r].getBackground().equals(CellState.getColor(CellState.START)))
					matrix[c][r].setBackground(Color.WHITE);
	}
	
	
    /*
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                new MapViewerPanel().display();
            }
        });
    }
    
    private void display() {
        JFrame f = new JFrame("GridButton");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(createGridPanel(15,15));
        f.pack();
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }
    */
	
}