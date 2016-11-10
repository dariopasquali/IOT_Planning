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
		
		Thread.sleep(7000);
		
		assertTrue(!console.isExploring());		
	}
	
	
	@Test
	public void abortTest()
	{
		console.explore("aperto", 999999999);
		assertTrue(console.isExploring());
		
		console.abort();
		
		assertTrue(!console.isExploring());	
	}
	
	@Test
	public void storeMapTest() throws Exception
	{
		console.explore("aperto", 6000);
		Thread.sleep(6000);
		
		console.storeMap("D:\\Desktop\\PLANNING\\GIT\\mapTest.json");
		IMap map = console.loadMap("D:\\Desktop\\PLANNING\\GIT\\mapTest.json");
		assertTrue(map!=null);
	}
	
	@Test
	public void loadMapTest() throws Exception
	{
		console.explore("aperto", 6000);
		Thread.sleep(6000);
		
		console.storeMap("D:\\Desktop\\PLANNING\\GIT\\mapTest.json");
		IMap map = console.loadMap("D:\\Desktop\\PLANNING\\GIT\\mapTest.json");
		assertTrue(map!=null);
	}
	
	@Test
	public void updateMapTest()
	{
		
	}
	
	@Test
	public void getNameTest()
	{
		assertTrue(console.getName().equals("console"));
	}	

}
