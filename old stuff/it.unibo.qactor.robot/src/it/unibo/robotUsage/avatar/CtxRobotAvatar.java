/* By AN DISI Unibo */ 
/*
 * ------------ SEMANTICS --------------------------------
 * A Robot is a QActor that can execute commands 
 * A Robot is also able to receive commands via HTTP (another Mind)
 * A Mind is a QActor able to send commands to the robot 
 * The commands can be send from the Mind via a keyboard (QActor RobotTerminal)
 */  
package it.unibo.robotUsage.avatar;
import it.unibo.qactor.robot.RobotActor;
import it.unibo.qactor.robot.RobotSysKb;
import it.unibo.qactor.robot.web.RobotHttpServer;
import it.unibo.qactors.ActorContext;

import java.io.InputStream;
import java.io.FileInputStream;

import it.unibo.iot.configurator.Configurator;
import it.unibo.iot.executors.baseRobot.IBaseRobot;
import it.unibo.iot.models.sensorData.ISensorData;
import it.unibo.iot.models.sensorData.distance.IDistanceSensorData;
import it.unibo.iot.models.sensorData.line.ILineSensorData;
import it.unibo.iot.models.sensorData.magnetometer.IMagnetometerSensorData;
import it.unibo.iot.models.sensorData.rotation.IRotationSensorData;
import it.unibo.iot.sensors.ISensor;
import it.unibo.iot.sensors.ISensorObserver;
import it.unibo.iot.sensors.distanceSensor.DistanceSensor;
import it.unibo.iot.sensors.lineSensor.LineSensor;
import it.unibo.iot.sensors.magnetometerSensor.MagnetometerSensor;
import it.unibo.iot.sensors.rotationSensor.RotationSensor;
import it.unibo.is.interfaces.IOutputEnvView;
import it.unibo.contactEvent.platform.EventPlatformKb;
//import it.unibo.robotUsage.sensors.SensorStreamSetter;
//import it.unibo.robotUsage.usb.USBTetherClientStreamer;
 

public class CtxRobotAvatar extends ActorContext{
//private USBTetherClientStreamer utUsb ;
private Configurator configurator;

	public CtxRobotAvatar(String name, IOutputEnvView outView,
			InputStream sysKbStream, InputStream sysRulesStream) throws Exception {
		super(name, outView, sysKbStream, sysRulesStream);
 	}
	
	protected void explain(){
		println( "%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%"    );
		println( "CtxRobotAvatar in project it.unibo.qactor.robot (package it.unibo.robotUsage.avatar)"    );
		println( "----------------------------------------------------------------------------------------"    );
		println( "GOAL: show the usage of platforms"    );
		println( "USE: base robot (labbaseRobotSam.jar)"    );
		println( "USE: QActors without ddr  (qactors18.jar)"    );
		println( "DEFINES: RobotHttpServer on 8080 in NAIVE/NMESSAGE/EVENT mode"    );
		println( "DEFINES: RobotAvatar as a RobotActor that waits for messages"    );
		println( "DEFINES: SensorEventHandler that handles 'usercmd' events raised by  RobotHttpServer\n"
				+ "	by adding a fact in robot WorldTheory "    );
		println( "%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%"    );
	}
	@Override
	public void configure() {
		explain();
		try {
			IBaseRobot baseRobot = RobotSysKb.setRobotBase(this, "rbase");
			/*
			 * Create a RobotAvatar as a RobotActor that includes robotBase
			 */
			RobotActor robot= new RobotAvatar("robotavatar", this,outEnvView, baseRobot );
 			/*
 			 * Include a HTTP server on the port 8080
 			 */
 			new RobotHttpServer(outEnvView, "./srcWeb", 8080, robot, RobotHttpServer.Mode.MESSAGE ).start();
//			new RobotHttpServer(outEnvView, "./srcWeb", 8080, robot, RobotHttpServer.Mode.EVENT ).start();
			/*
			 * Add to sensors an observer that emits the event raspsensor
			 */
//  			addSensorObservers();
 			/*
 			 * Create a event handler for the event raspsensor
 			 */
			new SensorEventHandler("sensEvHan", this,  outEnvView, new String[]{"raspsensor"} );
 			/*
 			 * Create a event handler for the event raspsensor
 			 */
			new UsercmdHandlerExecutor("usercmdEvHan", this,  "usercmd", outEnvView  );
		} catch (Exception e) {
 			e.printStackTrace();
		} 		
	}
	protected void addSensorObservers(){
		configurator = Configurator.getInstance();
//		println( "CtxRobotAvatar addSensorObservers "    );
		for (ISensor<?> sensor : configurator.getSensors()) {
//			println( "CtxRobotAvatar sensor= "  + sensor.getDefStringRep() );
// 			println( "CtxRobotAvatar sensor class= "  + sensor.getClass().getName() );
			ISensorObserver<?> obs = new SensorObserverToEvent<ISensorData>(outEnvView);		
 			
  			if( sensor instanceof DistanceSensor){
 				DistanceSensor sensorDistance = (DistanceSensor) sensor;
				println( "CtxRobotAvatar add observer to  "  + sensor.getDefStringRep() );
				sensorDistance.addObserver(  (ISensorObserver<IDistanceSensorData>)obs  ) ;
  			}
  			else if( sensor instanceof LineSensor){
  				LineSensor sensorLine = (LineSensor) sensor;
				println( "CtxRobotAvatar add observer to  "  + sensor.getDefStringRep() );
				sensorLine.addObserver( (ISensorObserver<ILineSensorData>) obs  ) ;
  			}
  			else if( sensor instanceof MagnetometerSensor){
  				MagnetometerSensor sensorMagneto = (MagnetometerSensor) sensor;
				println( "CtxRobotAvatar add observer to  "  + sensor.getDefStringRep() );
				sensorMagneto.addObserver( (ISensorObserver<IMagnetometerSensorData>) obs  ) ;
  			}
  			
  			//else sensor.addObserver(  (ISensorObserver<?>) obs  ) ;
		}		
	}	
	
  	
 
	
/*
* ----------------------------------------------
* MAIN
* ----------------------------------------------
*/
	public static void main(String[] args) throws Exception{
		InputStream sysKbStream = new FileInputStream("robotavatar.pl");
		InputStream sysRulesStream = new FileInputStream("sysRules.pl");
		new CtxRobotAvatar("ctxrobotavatar", EventPlatformKb.standardOutEnvView, sysKbStream, sysRulesStream ).configure();
 	}
	
 
}//CtxRobot1
