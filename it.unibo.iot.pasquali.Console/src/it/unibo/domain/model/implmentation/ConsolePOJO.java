package it.unibo.domain.model.implmentation;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Observable;

import org.json.JSONObject;

import com.google.gson.Gson;

import it.unibo.domain.model.interfaces.IConsole;
import it.unibo.domain.model.interfaces.IMap;
import it.unibo.domain.model.interfaces.IMapElement;

public class ConsolePOJO extends Observable implements IConsole{
	
	private String name;	
	private IMap map;
	
	private boolean exploring;
	private boolean navigating;
	
	private Gson jsonConverter = new Gson();
	
	/*
	 * ============================================================
	 * CONSTRUCTOR
	 * ============================================================
	 */
	
	public ConsolePOJO(String name)
	{
		this.name = name;
	}	
	
	/*
	 *=============================================================
	 * EREDITATE DA IConsole
	 *=============================================================
	 */
	
	
	@Override
	public IMap explore(String EnvType, long maxMilsTime) {
		
		map = new Map();
		this.exploring = true;
		
		Thread t = new Thread(new Runnable(){

			@Override
			public void run() {

				try
				{
					Thread.sleep(maxMilsTime);
					exploring = false;
				}
				catch (InterruptedException e)
				{
					e.printStackTrace();
				}				
			}			
		});
		t.start();
		
		
		return map;
	}

	@Override
	public void navigate(double goalX, double goalY) {
		this.navigating = true;		
	}

	@Override
	public void navigate(double startX, double startY, double goalX, double goalY) {
		this.navigating = true;		
	}

	@Override
	public void abort() {
		this.navigating = false;
		this.exploring = false;		
	}

	@Override
	public void storeMap(String filepath) throws Exception {
		
		if(map == null)
			throw new Exception("Map is Null");
		
		try
		{
			jsonConverter.toJson(map, new FileWriter(filepath));
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public IMap loadMap(String filepath) {

		try
		{
			map = jsonConverter.fromJson(new FileReader(filepath), Map.class);			
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		setChanged();
		notifyObservers();
		return map;
	}
	
	@Override
	public void updateMap(IMapElement newElement) {
		map.addElement(newElement);
		setChanged();
		notifyObservers();		
	}
	
	

	@Override
	public IMap getMap() {
		return map;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getDefaultRep() {		
		return name+"\n----\n"+map.getDefaultRep();
	}

	@Override
	public boolean isExploring() {
		return exploring;
	}

	@Override
	public boolean isNavigating() {
		return navigating;
	}
}
