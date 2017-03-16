package it.unibo.domain;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

//import org.opencv.core.CvType;
//import org.opencv.core.Mat;
//import org.opencv.core.MatOfByte;
//import org.opencv.highgui.Highgui;

import it.unibo.domain.interfaces.IMapElement;



public class Map {

	private ArrayList<IMapElement> elements = new ArrayList<IMapElement>();	
	private int Xmax;
	private int Ymax;
	private Integer[][] intmap;
	
	
	public static Map createMapFromPrologRep(String map)
	{
		if(!map.contains("map"))
			return null;
		
		String[] s = map.split(",");
		String[] sh = s[0].split("\\(");
		String[] st = s[1].split("\\)");
		
		return new Map(Integer.parseInt(sh[1]), Integer.parseInt(st[0]));
	}
	
	public Map(int width, int heigh, Integer[][] intmap)
	{
		this.Xmax = width;
		this.Ymax = heigh;
		this.intmap = intmap;
		
		for(int i = 0; i<Xmax; i++)
			for(int j = 0; j<Ymax; j++)
				if(intmap[i][j]!=0)
					elements.add(new MapElement(i,j));
				
	}
	
	public Map(int x, int y) {
		this.Xmax = x;
		this.Ymax = y;
		
		intmap = new Integer[x+1][y+1];
		for(int k=0; k<=Xmax; k++)
		{
			for(int j = 0; j<=Ymax; j++)
			{
				intmap[k][j] = 0;
			}
		}
	}

	public Map() {
		// TODO Auto-generated constructor stub
	}

	public void addElement(IMapElement newElement) {
		
		if(!elements.contains(newElement))
			elements.add(newElement);		
	}

	public List<IMapElement> getElements() {
		return elements;
	}

	public String getDefaultRep() {
		return toString();
	}

	public String getJSONRep() {
		return toString();
	}
	
	public int getYmax() {
		return Ymax;
	}
	
	public int getXmax() {
		return Xmax;
	}

	public void removeElement(int c, int r) {
		MapElement toRemove = new MapElement(c,r);		
		elements.remove(toRemove);		
	}
	

	public void addElementsFromList(List<String> els) {
		
		for(String e : els)
		{
			String[] s = e.split(",");
			String[] sh = s[0].split("\\(");
			String[] st = s[1].split("\\)");
			
			MapElement me = new MapElement(Integer.parseInt(sh[1]), Integer.parseInt(st[0]));
			elements.add(me);
		}
	}	
	
	public Integer[][] getIntMap()
	{
		return intmap;
	}
	
//	public Mat getImage()
//	{
//		Mat image = new Mat(Xmax, Ymax, CvType.CV_8UC3);
//		
//		MatOfByte matOfByte = new MatOfByte();
//	    Highgui.imencode(".jpg", image, matOfByte);
//	    byte[] byteArray = matOfByte.toArray();
//	    BufferedImage bufImage = null;
//	    try 
//	    {
//	        InputStream in = new ByteArrayInputStream(byteArray);
//	        bufImage = ImageIO.read(in);
//	        
//	        for(int i=0; i<Xmax; i++)
//	        	for(int j=0; j<Ymax; j++)
//	        		if(intmap[i][j] == 0)
//	        			bufImage.setRGB(i, j, Color.white.getRGB());
//	        		else
//	        			bufImage.setRGB(i, j, Color.black.getRGB());	
//	        
//	        byte[] data = ((DataBufferByte) bufImage.getRaster().getDataBuffer()).getData();
//	        image.put(0, 0, data);
//	    }
//	    catch(Exception e)
//	    {
//	    	e.printStackTrace();
//	    }
//	    
//	    return image;
//	}
	
	
	public void addElementsFromString(String elem) {
				
		int i = 0;
		String[] els = elem.split(",");
		
		try
		{
			while(i < els.length)
			{
				String[] sh = els[i].split("\\(");
				String[] st = els[i+1].split("\\)");
				
				MapElement me = new MapElement(Integer.parseInt(sh[1]), Integer.parseInt(st[0]));
				intmap[Integer.parseInt(sh[1])][Integer.parseInt(st[0])] = 1;
				elements.add(me);
				i+=2;
			}	
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	
	@Override
	public String toString()
	{
		String s = "";
		
		s += "map("+Xmax+","+Ymax+")\n";
		
		for(int i = 0; i<elements.size(); i++)
		{
			s += "mapdata(" + (i+1) +", " + elements.get(i).toString() +")";
			if(i!=elements.size()-1)
				s+="\n";
		}
		return s;
	}

	public void clearAll() {
		elements = new ArrayList<IMapElement>();		
	}

	public boolean isCellClear(int x, int y) {

		MapElement me = new MapElement(x,y);
		
		if(elements.contains(me))
			return false;
		else
			return true;
	}

}
