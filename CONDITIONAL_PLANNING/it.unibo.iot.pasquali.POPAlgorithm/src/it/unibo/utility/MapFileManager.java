package it.unibo.utility;

import java.awt.FileDialog;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JFrame;

import it.unibo.domain.model.Graph;
import it.unibo.model.map.Map;

public class MapFileManager extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Map loadMap()
	{	
		FileDialog loadDialog = new FileDialog(this, "Choose the map", FileDialog.LOAD);
		loadDialog.setDirectory("C:\\");
		loadDialog.setFile("");
		loadDialog.setVisible(true);
		String filename = loadDialog.getDirectory()+loadDialog.getFile();
		if(filename.contains("null"))
			return null;
		
		Map m = null;
		List<String> data = new ArrayList<String>();						
		try
		{
			InputStream fs = new FileInputStream(filename);
			InputStreamReader inpsr = new InputStreamReader(fs);
			BufferedReader br       = new BufferedReader(inpsr);
			Iterator<String> lsit   = br.lines().iterator();

		while(lsit.hasNext())
		{
			data.add(lsit.next());
		}
		br.close();
			
		} catch (Exception e)
		{
			System.out.println("QActor  ERROR " + e.getMessage());
		}
				
		for(int i=0; i<data.size(); i++)
		{
			if(i == 0)
			{
				m = Map.createMapFromPrologRep(data.get(i));
			}
			else
			{
				String s[] = data.get(i).split(" ");
				m.addElementFromString(s[1]);
			}
		}
		
		return m;			
	}
	
	
	
	public Graph loadGraph() {
		
		String filename;
		
		FileDialog loadDialog = new FileDialog(this, "Choose the map", FileDialog.LOAD);
		loadDialog.setDirectory("C:\\");
		loadDialog.setFile("*.pop");
		loadDialog.setVisible(true);
		filename = loadDialog.getDirectory()+loadDialog.getFile();
		if(filename.contains("null"))
			return null;
		
		Graph g = new Graph();		
								
		try
		{
			FileInputStream fin = new FileInputStream(filename);
			ObjectInputStream ois = new ObjectInputStream(fin);

			return (Graph) ois.readObject();
			
		} catch (Exception e)
		{
			System.out.println(e.getMessage());
		}	

		return g;
	}
}
