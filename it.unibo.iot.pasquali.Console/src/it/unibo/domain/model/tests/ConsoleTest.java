package it.unibo.domain.model.tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import it.unibo.domain.model.implmentation.ConsolePOJO;
import it.unibo.domain.model.interfaces.IConsole;
import it.unibo.domain.model.interfaces.IMap;
import it.unibo.domain.model.interfaces.IMapElement;

public class ConsoleTest {

	private IConsole console;
	
	@Before
	public void init() {
		console = new ConsolePOJO("console");
	}
	
	@Test
	public void exploreTest() throws InterruptedException
	{
		console.explore("aperto", 6000);
		assertTrue(console.isExploring());
		
		Thread.sleep(6000);
		
		assertTrue(!console.isExploring());		
	}
	
	
	//public void navigate(double goalX, double goalY); 								//modifier , primitive
	//public void navigate(double startX, double startY, double goalX, double goalY); //modifier , primitive
	
	@Test
	public void abortTest()
	{
		console.explore("aperto", 999999999);
		assertTrue(console.isExploring());
		
		console.abort();
		
		assertTrue(!console.isExploring());	
	}
	
	@Test
	public void storeMapTest() throws InterruptedException
	{
		console.explore("aperto", 6000);
		Thread.sleep(6000);
		
		console.storeMap("\\mapTest.json");
		IMap map = console.loadMap("\\mapTest.json");
		assertTrue(map!=null);
	}
	
	@Test
	public void loadMapTest() throws InterruptedException
	{
		console.explore("aperto", 6000);
		Thread.sleep(6000);
		
		console.storeMap("\\mapTest.json");
		IMap map = console.loadMap("\\mapTest.json");
		assertTrue(map!=null);
	}
	
	@Test
	public void updateMapTest()
	{
		
	}
	
	public IMap getMap(); 															//property , primitive
	public String getName(); 															//property , primitive
	
	public boolean isExploring();
	public boolean isNavigating();
		
	public String getDefaultRep(); 													// mapping , non-primitive

}
