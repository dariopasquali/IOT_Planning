package it.unibo.console.gui;

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

/**
 * @see http://stackoverflow.com/questions/7702697
 */
public class GridButtonPanel {

	private JPanel p;
	
	public enum CellState{
		CLEAR,
		OBSTACLE,
		START,
		GOAL,
		PATH
	}
	
    private static final int N = 5;
    private JButton[][] matrix = null;
    

    private JButton getGridButton(int r, int c) {
        return matrix[r][c];
    }

    private JButton createGridButton(final int row, final int col) {
        final JButton b = new JButton("");        
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
    	
    	matrix = new JButton[rows][cols];
    	
        p = new JPanel(new GridLayout(rows, cols));
        
        for(int r = 0; r < rows; r ++)
        {
        	for(int c = 0; c < cols; c++)
        	{
        		JButton gb = createGridButton(r, c);
        		gb.setBackground(Color.WHITE);
                gb.setPreferredSize(new Dimension(20,20));
                matrix[r][c] = gb;
                p.add(gb);
        	}
        }
        
        return p;
    }
    
    private void display() {
        JFrame f = new JFrame("GridButton");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(createGridPanel(15,15));
        f.pack();
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                new GridButtonPanel().display();
            }
        });
    }

	public JPanel getPanel() {
		return p;
	}
}