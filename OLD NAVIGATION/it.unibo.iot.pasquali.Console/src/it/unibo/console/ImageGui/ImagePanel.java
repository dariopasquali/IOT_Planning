package it.unibo.console.ImageGui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JPanel;

import it.unibo.domain.model.implmentation.IntMap;

public class ImagePanel extends JPanel{
	
	private BufferedImage img;
	private Point start;
	private Point goal;
	private IntMap intmap;
	private ArrayList<Point> path;

    public ImagePanel(BufferedImage img, IntMap intmap) {
        this.img = img;
        this.intmap = intmap;
    }

    @Override
    public Dimension getPreferredSize() {
        return img == null ? super.getPreferredSize() : new Dimension(img.getWidth(), img.getHeight());
    }

    protected Point getImageLocation() {

        Point p = null;
        if (img != null) {
            int x = (getWidth() - img.getWidth()) / 2;
            int y = (getHeight() - img.getHeight()) / 2;
            p = new Point(x, y);
        }        
        return p;

    }

    public void setStart(Point imagePoint)
    {
    	if(intmap.getIntMap()[imagePoint.x][imagePoint.y] == 1)
    		return;
    	
    	if(this.start != null)
    	{
    		img.setRGB(start.x, start.y,Color.white.getRGB());
    	}
    	
    	this.start = imagePoint;
    	img.setRGB(start.x, start.y,Color.blue.getRGB());
    	this.repaint();
    }
    
    public void setGoal(Point imagePoint)
    {
    	if(intmap.getIntMap()[imagePoint.x][imagePoint.y] == 1)
    		return;
    	
    	if(this.goal != null)
    	{
        	img.setRGB(goal.x, goal.y,Color.white.getRGB());
    	}
    	
    	this.goal = imagePoint;
    	img.setRGB(goal.x, goal.y,Color.red.getRGB());
    	this.repaint();
    }
    
    public Point getStart()
    {
    	return start;
    }
    
    public Point getGoal()
    {
    	return goal;
    }
    
    public void clearPath()
    {
    	if(path == null)
    	{
    		path = new ArrayList<Point>();
    		return;
    	}
    	
    	for(Point p : path)
    	{
    		img.setRGB(p.x, p.y,Color.green.getRGB());
    	}
    	path.clear();
    }
    
    
    public Point toImageContext(Point p) {
        Point imgLocation = getImageLocation();
        Point relative = new Point(p);
        relative.x -= imgLocation.x;
        relative.y -= imgLocation.y;
        return relative;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (img != null) {
            Point p = getImageLocation();
            g.drawImage(img, p.x, p.y, this);
        }
    }

	public void addPathElement(Point point) {
		if(path == null)
			path = new ArrayList<Point>();
		
		path.add(point);
		img.setRGB(point.x, point.y,Color.black.getRGB());
		this.repaint();
	}

}
