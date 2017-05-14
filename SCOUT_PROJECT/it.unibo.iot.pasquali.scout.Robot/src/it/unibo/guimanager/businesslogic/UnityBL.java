package it.unibo.guimanager.businesslogic;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;

import org.eclipse.paho.client.mqttv3.MqttException;

import it.unibo.connector.IConnector;
import it.unibo.connector.UnityConnector;
import it.unibo.guimanager.Guimanager;
import it.unibo.guimanager.interfaces.IGuiManagerBL;
import it.unibo.robot.utility.MqttUtils;

public class UnityBL implements IGuiManagerBL{

	private IConnector connector;
	private Guimanager actor;
	
	private String actorName;
	private int startX, startY;
	
	private MqttUtils mqtt;
	private final String MQTT_SERVER = "tcp://m2m.eclipse.org:1883";
	private String topic = "unibo/mqtt/unityRobot";
	private String mqttClientID = "";
	
	public UnityBL(Guimanager actor){
		
		this.actor = actor;
		actorName = actor.getName().replace("_ctrl", "");
		
		connector = new UnityConnector(6000, actor);
		connector.connect();
		connector.setupActorSimulatorName();
		
		mqtt = new MqttUtils();
		try
		{
			mqttClientID = mqtt.connect(actor, MQTT_SERVER, topic);
		} 
		catch (MqttException e)
		{
			e.printStackTrace();
		}
		
		connector.send("subscribe("+topic+")");		
		actor.println("connected");		
	}
	
	
	@Override
	public void showMap(int startX, int startY, String filename) {

		String file = readMap(filename);		

		try
		{
			mqtt.publish(actor, mqttClientID, MQTT_SERVER, topic, file, 0, true);
		} 
		catch (MqttException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void createActor(){

		String create = "createActor(" + actorName + "," + startX + "," + startY +")";
		connector.send(create);	
	}
	
	@Override
	public void updateState(int x, int y, String direction) {
		
		String move = "move(" + actorName + "," + x + "," + y + "," + direction + ")";
		connector.send(move);		
	}
	
	private String readMap(String filename)
	{
		String data = "";						
		try
		{
			InputStream fs = new FileInputStream(filename);
			InputStreamReader inpsr = new InputStreamReader(fs);
			BufferedReader br       = new BufferedReader(inpsr);
			Iterator<String> lsit   = br.lines().iterator();

			while(lsit.hasNext())
			{
				data += lsit.next()+"\n";
			}
			br.close();
			
		} catch (Exception e)
		{
			System.out.println("QActor  ERROR " + e.getMessage());
		}
		
		return data;
	}

}
