package it.unibo.domain;

import java.util.ArrayList;
import java.util.List;

import it.unibo.domain.graph.Graph;
import it.unibo.domain.graph.State;

//import org.opencv.core.CvType;
//import org.opencv.core.Mat;
//import org.opencv.core.MatOfByte;
//import org.opencv.highgui.Highgui;

import it.unibo.domain.interfaces.IMapElement;
import it.unibo.domain.model.Fact;
import it.unibo.domain.model.conditional.Check;
import it.unibo.domain.model.conditional.Move;



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
		
		intmap = new Integer[x][y];
		for(int k=0; k<Xmax; k++)
		{
			for(int j = 0; j<Ymax; j++)
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
		{
			elements.add(newElement);
			intmap[newElement.getX()][newElement.getY()] = 1;
		}
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
		intmap[c][r] = 0;
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

	public Graph getPOPGraph() {

		String graph = "";
		String moves = "";
		
		Graph g = new Graph();
		
		for(int c=0; c<Xmax; c++)
		{
			for(int r=0; r<Ymax; r++)
			{
				if(intmap[c][r] == 0)
					g.addState(new State(c,r));
			}
		}				
		
		for(int c=0; c<Xmax; c++)
		{
			for(int i=0; i<(Ymax-1); i++)
			{
				if(intmap[c][i] == 0 && intmap[c][i+1] == 0)
				{
					State a = new State(c,i);
					State b = new State(c,i+1);
					
//					g.addConnection(a.toString(), b.toString());
//					g.addConnection(b.toString(), a.toString());
					
					g.addState(a);
					g.addState(b);
					
					Move m = new Move(a, b);										
					g.addMove(m);				
					m = new Move(b, a);					
					g.addMove(m);
					
					Check ch = new Check(a, b);										
					g.addCheck(ch);				
					ch = new Check(b, a);					
					g.addCheck(ch);
					
/*					
					graph += "connected(p("+c+","+i+"),p("+c+","+(i+1)+")).\n";
					graph += "connected(p("+c+","+(i+1)+"),p("+c+","+i+")).\n";
					
					moves += "move(p("+c+","+i+"),"
							+ "p("+c+","+(i+1)+"),"
									+ "[at(p("+c+","+i+")), connected(p("+c+","+i+"),p("+c+","+(i+1)+"))],"
											+ "[at(p("+c+","+(i+1)+")), not at(p("+c+","+i+"))]).\n";
					
					moves += "move(p("+c+","+(i+1)+"),"
							+ "p("+c+","+i+"),"
									+ "[at(p("+c+","+(i+1)+")), connected(p("+c+","+(i+1)+"),p("+c+","+i+"))],"
											+ "[at(p("+c+","+i+")), not at(p("+c+","+(i+1)+"))]).\n";
*/
				}
			}
		}
		
		
		for(int r=0; r<Ymax; r++)
		{
			for(int j=0; j<(Xmax-1); j++)
			{
				if(intmap[j][r] == 0 && intmap[j+1][r] == 0)
				{
					State a = new State(j,r);
					State b = new State(j+1,r);
					
					g.addConnection(a.toString(), b.toString());
					g.addConnection(b.toString(), a.toString());
					
					Move m = new Move(a, b);										
					g.addMove(m);				
					m = new Move(b, a);					
					g.addMove(m);
					
					Check ch = new Check(a, b);										
					g.addCheck(ch);				
					ch = new Check(b, a);					
					g.addCheck(ch);
					
/*					
					graph += "connected(p("+j+","+r+"),p("+(j+1)+","+r+")).\n";
					graph += "connected(p("+(j+1)+","+r+"),p("+j+","+r+")).\n";
					
					moves += "move(p("+j+","+r+"),"
							+ "p("+(j+1)+","+r+"),"
									+ "[at(p("+j+","+r+")), connected(p("+j+","+r+"),p("+(j+1)+","+r+"))],"
											+ "[at(p("+(j+1)+","+r+")), not at(p("+j+","+r+"))]).\n";
					
					moves += "move(p("+(j+1)+","+r+"),"
							+ "p("+j+","+r+"),"
									+ "[at(p("+(j+1)+","+r+")), connected(p("+(j+1)+","+r+"),p("+j+","+r+"))],"
											+ "[at(p("+j+","+r+")), not at(p("+(j+1)+","+r+"))]).\n";
*/				
				}
			}
		}		
		
		return g;
	}

}
