package it.unibo.guimanager.businesslogic;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import it.unibo.gui.RobotGUI;
import it.unibo.guimanager.Guimanager;
import it.unibo.guimanager.interfaces.IGuiManagerBL;
import it.unibo.model.map.Map;
import it.unibo.mqtt.MqttUtils;

public class RobotGuiBL implements IGuiManagerBL{

	protected Map map;	
	private static RobotGUI gui = new RobotGUI();
	
	private int startX, startY;
	
	private Guimanager actor;
	
	private String mqttTopic;
	private MqttUtils mqtt;
	private String mqttServer = "tcp://m2m.eclipse.org:1883";
	private boolean haveMap = false;
	private String mqttData = "";
	
	public RobotGuiBL(Guimanager actor){
		
		this.actor = actor;
		((RobotGUI) gui).setController(actor);
		actor.setEnv(gui);
	}
	
	@Override
	public void showMap(int startX, int startY, String topic) {
		
		gui.setVisible(true);
		
//		Map m = null;
//		
//		List<String> data = new ArrayList<String>();						
//		try
//		{
//			InputStream fs = new FileInputStream(filename);
//			InputStreamReader inpsr = new InputStreamReader(fs);
//			BufferedReader br       = new BufferedReader(inpsr);
//			Iterator<String> lsit   = br.lines().iterator();
//
//			while(lsit.hasNext())
//			{
//				data.add(lsit.next());
//			}
//			br.close();
//			
//		} catch (Exception e)
//		{
//			System.out.println("QActor  ERROR " + e.getMessage());
//		}
//			
//		for(int i=0; i<data.size(); i++)
//		{
//			if(i == 0)
//			{
//				m = Map.createMapFromPrologRep(data.get(i));
//			}
//			else
//			{
//				String s[] = data.get(i).split(" ");
//				m.addElementFromString(s[1]);
//			}
//		}
//		this.map = m;
		
		this.map = loadMapfromMqtt(topic);
		
		actor.println(map.toString());
		
		gui.setMap(map);
		this.startX = startX;
		this.startY = startY;
	}
	
	private Map loadMapfromMqtt(String topic){
		
		haveMap = false;
		
		this.mqttTopic = topic;
		actor.println("ROBOT GUI -------> LOAD MAP FROM TOPIC");
		
		mqtt = new MqttUtils();
		
		try
		{
			System.out.println("AAAAAAAAAA "+mqtt.connect(actor, mqttServer, mqttTopic));
			mqtt.subscribe(actor, mqttTopic, new MqttHandler());
			
			Thread.sleep(1000);
			
			while(!haveMap);
			
			haveMap = false;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		Map m = null;
		
		System.out.println("ROBOT GUI HA RICEVUTO "+mqttData);
		
		String[] d = mqttData.split("\n");
		mqttData = "";
		
		for(int i=0; i<d.length; i++)
		{
			if(i == 0)
			{
				m = Map.createMapFromPrologRep(d[i]);
			}
			else
			{
				String s[] = d[i].split(" ");
				m.addElementFromString(s[1]);
			}
		}
		
		return m;
	}

	@Override
	public void createActor(){

		gui.setCurrentPosition(startY, startX, "N");
	}
	
	@Override
	public void updateState(int x, int y, String direction) {

		gui.setCurrentPosition(y, x, direction.toUpperCase());		
	}


	
	private class MqttHandler implements MqttCallback{
		
		@Override
		public void connectionLost(Throwable cause) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void messageArrived(String topic, MqttMessage message) throws Exception {

			if(mqttData.equals(""))
				mqttData = message.toString();
			haveMap = true;		
		}

		@Override
		public void deliveryComplete(IMqttDeliveryToken token) {
			// TODO Auto-generated method stub
			
		}
	}

}
