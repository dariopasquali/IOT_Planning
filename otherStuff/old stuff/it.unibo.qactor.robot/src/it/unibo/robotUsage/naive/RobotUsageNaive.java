/*
 * ======================================================================================
 * The file ./hardwareConfiguration.properties  must contain the robot name:
  		configuration=mock
 * The file ./configuration/mock/iotRobot.properties must contain the robot configuration:
 		baserobot.bottom=differentialdrive
		baserobot.bottom.name=mock
		baserobot.bottom.comp=actuators.bottom,rotation.front
 
		actuators.bottom=ddmotorbased
		actuators.bottom.comp=motor.left,motor.right
		actuators.bottom.private=true

		motor.right=mock
		motor.left=mock

		distance.front=mock
* ======================================================================================
*/
package it.unibo.robotUsage.naive;
import it.unibo.iot.configurator.Configurator;
import it.unibo.iot.executors.baseRobot.IBaseRobot;
import it.unibo.iot.models.sensorData.ISensorData;
import it.unibo.iot.models.sensorData.distance.IDistanceSensorData;
import it.unibo.iot.models.sensorData.line.ILineSensorData;
import it.unibo.iot.models.sensorData.magnetometer.IMagnetometerSensorData;
import it.unibo.iot.sensors.ISensor;
import it.unibo.iot.sensors.ISensorObserver;
import it.unibo.iot.sensors.distanceSensor.DistanceSensor;
import it.unibo.iot.sensors.lineSensor.LineSensor;
import it.unibo.iot.sensors.magnetometerSensor.MagnetometerSensor;
import it.unibo.qactor.robot.RobotSysKb;
import it.unibo.robotUsage.avatar.SensorObserverToEvent;
import it.unibo.system.SituatedPlainObject;
/*
 * ----------------------------------------------------------------
 * RobotUsageNaive:
 * a basic robot that executes some moves and handles a distance sensor
 * ----------------------------------------------------------------
 */
public class RobotUsageNaive extends SituatedPlainObject{
	private Configurator configurator;
	private IBaseRobot robot ;
	private int delay= 1000;

	protected void explain(){
		println( "%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%"    );
		println( "RobotUsageNaive in project it.unibo.qactor.robot (package it.unibo.robotUsage.naive)"    );
		println( "----------------------------------------------------------------------------------------"    );
		println( "GOAL: show the usage of the base robot (by Sam) with sensors"    );
		println( "USE: base robot (labbaseRobotSam.jar)"    );
		println( "USE: hardwareConfiguration.properties (and configuration/.../iotRobot.properties) if defined"    );
		println( "USE: piblaster (linux) to use the GY85 Magnetometer"    );
		println( "DEFINES: a generic SensorObserver and a DistanceSensorObserver to show the sensor data"    );
		println( "%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%"    );
	}

	protected void init(){
		try{
			explain();
			configurator = Configurator.getInstance();
			System.out.println( "ACTIVATING pi-blaster: sudo /home/pi/pi-blaster/pi-blaster");
			String startPiBlasterCmd = "sudo /home/pi/pi-blaster/pi-blaster";		
			Runtime.getRuntime().exec(startPiBlasterCmd).waitFor();
			System.out.println( "Working with pi-blaster");
		}catch(Exception e){
			System.out.println( "Working without pi-blaster");
		};
		
	}

	public void doJob() throws Exception{
		/*
		 * Acquire the robvt
		 */
		configurator = Configurator.getInstance();
		robot = configurator.getBaseRobot();		
		println( "RobotUsageNaive STARTS "  + robot.getDefStringRep() );
		
		init();
		
		/*
		 * Acquire some sensor observer
		 */
 		addSensorObservers();
		/*
		 * Move the robot
		 */
 		executePlan();
		println( "RobotUsageNaive SLEEPS for a while ... "    );
		Thread.sleep(5000);
		println( "RobotUsageNaive ENDS "  + robot.getJsonStringRep() );
	}	
	protected void addSensorObservers(){
		println( "RobotUsageNaive addSensorObservers= "    );
		SensorObserver<?> obs = new SensorObserver<ISensorData>(outEnvView);
		
		for (ISensor<?> sensor : configurator.getSensors()) {
			
			println( "RobotUsageNaive sensor="  + sensor.getDefStringRep() + 
					" class="  + sensor.getClass().getName() );
			if( sensor instanceof DistanceSensor){
				DistanceSensor sensorDistance = (DistanceSensor) sensor;
 				ISensorObserver<IDistanceSensorData> dobs = new DistanceSensorObserver(outEnvView);
				println( "RobotUsageNaive add observer to  "  + sensor.getDefStringRep() );
				sensorDistance.addObserver(  (ISensorObserver<IDistanceSensorData>)dobs  ) ;
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
		}		
	}	
	/*
	 * robot.execute executes 
	 * DifferentialDriveBaseRobot and this calls MotorBasedDDActuators
	 */
	protected void executePlan(){
 		try {
 	 		Thread.sleep(delay*3);
 	 		println( "RobotUsageNaive execute ... " +RobotSysKb.FORWARD.getDefStringRep()   );
	 		robot.execute(RobotSysKb.FORWARD);
			Thread.sleep(delay);
 	 		println( "RobotUsageNaive execute ... " +RobotSysKb.LEFT.getDefStringRep()   );
	 		robot.execute(RobotSysKb.LEFT);
			Thread.sleep(delay);
 	 		println( "RobotUsageNaive execute ... " +RobotSysKb.BACKWARD.getDefStringRep()   );
	 		robot.execute(RobotSysKb.BACKWARD);
			Thread.sleep(delay);
 	 		println( "RobotUsageNaive execute ... " +RobotSysKb.STOP.getDefStringRep()   );
 	 		robot.execute(RobotSysKb.STOP);
		} catch (InterruptedException e) {
 			e.printStackTrace();
		}
	}
	
/*
 * Just to test	
 */
	public static void main(String[] args) throws Exception{
 		new RobotUsageNaive().doJob();
 	}

}
