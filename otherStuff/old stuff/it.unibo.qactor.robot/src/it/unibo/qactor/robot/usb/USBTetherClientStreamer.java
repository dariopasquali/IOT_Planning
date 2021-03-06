/* Generated by AN DISI Unibo */ 
package it.unibo.qactor.robot.usb;
import it.unibo.contactEvent.interfaces.IContactEventPlatform;
import it.unibo.contactEvent.platform.ContactEventPlatform;
import it.unibo.is.interfaces.IOutputView;
import it.unibo.system.SituatedActiveObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import rx.Observable;
 

public class USBTetherClientStreamer  extends SituatedActiveObject{
 	private  int port  = 38800;
	private  Socket socket;
	private  PrintWriter output;
	private  BufferedReader in; 
    private String ipAndroid = "192.168.42.129";	// stable
    private  IContactEventPlatform platform;
    private  Observable<String> sensorStream;
    private boolean onLinux = false; 
    
	public USBTetherClientStreamer(  IOutputView outView )   {
		super(outView, "");
		try {
			platform=ContactEventPlatform.getPlatform();
		} catch (Exception e) {
 			e.printStackTrace();
		}  	
	}	
	@Override
	protected void startWork() throws Exception {
  	}
	@Override
	protected void endWork() throws Exception {
 	}
	@Override
	protected void doJob() throws Exception {
		println(" *** USBTheterClientSender starts *** ");
		/*
		* ==== ON RASPBERRY (linux) ==== 
			*/
		try{
			Runtime.getRuntime().exec("sudo dhclient usb0");
			onLinux = true;
		}catch(Exception e){
			println("We are working outside Linux");
		}
		setUSBConnection();
      }
  	private synchronized void setUSBConnection()  throws Exception {
 		do{
			try{
 				if( socket != null ) socket.close();
//				println("Connection setting  (remember tethering on ANDROID) ... ");
//				println("Connection setting on 192.168.42.129... ");
				//socket = new Socket(ipSamsungUSB, port);
				socket = new Socket(ipAndroid, port);
				output = new PrintWriter(socket.getOutputStream(), true);
				in     = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				println("USB CONNECTED  ");
				break;
 			}catch(Exception e){
 		 		println("USB CONNECTION BROKEN: I WILL RESTART IN 8 sec!");
				if( in != null) in.close();
				Thread.sleep(8000);		
			}
		}while( true );
 		sensorStream = new USBTetherStream(outView,in).getStream();
 		notifyAll();
 		// receiveMsgUSB( );
 	}
	
  	public PrintWriter getPrintWriter(){
		return output;
	}	
  	public synchronized Observable<String> getSensoStream() throws InterruptedException{
  		while( sensorStream == null ) wait();
		return sensorStream;
	}	
  	public void sendMsgUSB(String msg){
		if( output != null ){
 			output.println(msg);
			//platform.raiseEvent("usb", RobotSysKb.eventAndroidSensor, msg);
		}
 	}
	public void receiveMsgUSB( ) throws IOException{
		if( in == null ) return;
		while(true){
			String msg = in.readLine();
			println( msg );
 		}
	}
 
}
