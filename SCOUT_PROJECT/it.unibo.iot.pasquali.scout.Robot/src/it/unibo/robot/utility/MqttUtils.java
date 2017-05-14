package it.unibo.robot.utility;

import it.unibo.qactors.akka.QActor;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class MqttUtils implements MqttCallback{
	
private static MqttUtils myself = null;	
	protected String clientid = null;
	protected String eventId = "mqtt";
	protected String eventMsg = "";
	protected  QActor actor = null;
	protected  MqttClient client = null;
	
	public static MqttUtils getMqttSupport( ){
		return myself  ;
	}
	public MqttUtils(){
		try {
			myself   = this;
			println("			%%% MqttUtils created "+ myself );
		} catch (Exception e) {
			println("			%%% MqttUtils WARNING: "+ e.getMessage() );
		} 
	}
 
 	public String connect(QActor actor, String brokerAddr, String topic ) throws MqttException{
 		println("			%%% MqttUtils connect/3 " );
 		clientid = MqttClient.generateClientId();
 		connect(actor,   clientid,   brokerAddr,   topic);
 		
 		return clientid;
	}
 	
	public void connect(QActor actor, String clientid, String brokerAddr, String topic ) throws MqttException{
		println("			%%% MqttUtils connect/4 "+ clientid );
		this.actor = actor;
		client = new MqttClient(brokerAddr, clientid);
		MqttConnectOptions options = new MqttConnectOptions();
		options.setWill("unibo/clienterrors", "crashed".getBytes(), 2, true);
		client.connect(options);		
	}
	
	public void disconnect( ) throws MqttException{
		println("			%%% MqttUtils disconnect "+ client );
		if( client != null ) client.disconnect();
	}	
	
	public void publish(QActor actor, String clientid, String brokerAddr, String topic, String msg, int qos, boolean retain) throws MqttException{
 		MqttMessage message = new MqttMessage();
		message.setRetained(retain);
		if( qos == 0 || qos == 1 || qos == 2){//qos=0 fire and forget; qos=1 at least once(default);qos=2 exactly once
			message.setQos(0);
		}
		message.setPayload(msg.getBytes());
		println("			%%% MqttUtils publish "+ message );
		client.publish(topic, message);
	}	

	public void subscribe(QActor actor, String  clientid, String brokerAddr, String topic) throws Exception {
		try{
			this.actor = actor;
			client.setCallback(this);
 			client.subscribe(topic);  
 		}catch(Exception e){
				println("			%%% MqttUtils subscribe error "+  e.getMessage() );
				eventMsg = "mqtt(" + eventId +", failure)";
				println("			%%% MqttUtils subscribe error "+  eventMsg );
 				if( actor != null ) actor.sendMsg("mqttmsg", actor.getName(), "dispatch", "error");
	 			throw e;
		}
	}
	@Override
	public   void connectionLost(Throwable cause) {
		println("			%%% MqttUtils connectionLost  = "+ cause.getMessage() );
	}
	@Override
	public   void deliveryComplete(IMqttDeliveryToken token) {
		println("			%%% MqttUtils deliveryComplete token= "+ token );
	}
	@Override
	public void messageArrived(String topic, MqttMessage msg) throws Exception {
		println("messageArrived on "+ topic + "="+msg.toString());
		String mqttmsg = "mqttmsg(" + topic +"," + msg.toString() +")";
		println("			%%% MqttUtils messageArrived mqttmsg "+ mqttmsg);
 		if( actor != null ) actor.sendMsg("mqttmsg", actor.getName(), "dispatch", mqttmsg);
	}	
	
	public void println(String msg){
		if( actor != null ) actor.println(msg);
		else System.out.println(msg);
	}
	
	/*
	 * =================================================================
	 * TESTING	
	 * =================================================================
	 */
		
		public void test() throws Exception{
			println("			%%% MqttUtils test " );
			connect(null,"qapublisher_mqtt", "tcp://m2m.eclipse.org:1883", "unibo/mqtt/qa");
			publish(null,"qapublisher_mqtt","tcp://m2m.eclipse.org:1883", "unibo/mqtt/qa", "sensordata(aaa)", 1, true);
			
			connect(  null,"observer_mqtt", "tcp://m2m.eclipse.org:1883", "unibo/mqtt/qa");
			subscribe(null,"observer_mqtt", "tcp://m2m.eclipse.org:1883", "unibo/mqtt/qa");
			for(int i=1; i<=3; i++)
			publish(null,"qapublisher_mqtt","tcp://m2m.eclipse.org:1883", "unibo/mqtt/qa", "distance("+ i +")", 1, true);
			
		}
		
		public static void main(String[] args) throws Exception{
			new MqttUtils().test();
		}	
}
