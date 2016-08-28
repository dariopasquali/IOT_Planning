package it.unibo.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
	
	private class ClickHandler implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {

			String[] name = e.getActionCommand().split(" ");
			System.out.println(e.getActionCommand());
			int r = Integer.parseInt(name[0]);
			int c = Integer.parseInt(name[1]);
			
			switchState(r,c);			
		}		
	}
	
	private JPanel p;
	private Map map;
	private int rows, cols;
    private JButton[][] matrix = null;
    

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
        return matrix[r][c];
    }

    private JButton createCell(final int row, final int col) {
        final JButton b = new JButton("");
        b.setActionCommand(row+" "+col);
        b.addActionListener(new ClickHandler());
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
    	matrix = new JButton[rows][cols];
    	
        p = new JPanel(new GridLayout(rows, cols));
        
        for(int r = 0; r < rows; r ++)
        {
        	for(int c = 0; c < cols; c++)
        	{
        		JButton gb = createCell(r, c);
        		gb.setBackground(Color.WHITE);
                gb.setPreferredSize(new Dimension(20,20));
                matrix[r][c] = gb;
                p.add(gb);
        	}
        }
        
        return p;
    }
    
	public JPanel getPanel() {
		return p;
	}
	
	public void switchState(int r, int c) {
		
		JButton b = getGridButton(r, c);
		
		Color color = b.getBackground();
		if(color.equals(Color.WHITE))
		{
			getGridButton(r,c).setBackground(Color.BLACK);
			map.addElement(new MapElement(c,r));
		}
		else if(color.equals(Color.BLACK))
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