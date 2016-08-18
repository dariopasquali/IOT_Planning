package it.unibo.robotUsage.servo;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import it.unibo.iot.configurator.Configurator;
import it.unibo.iot.executors.ExecutorType;
import it.unibo.iot.executors.IExecutor;
import it.unibo.iot.executors.servo.Servo;
import it.unibo.iot.models.commands.servo.ServoCommand;
import it.unibo.is.interfaces.IOutputEnvView;
import it.unibo.system.SituatedPlainObject;
import it.unibo.system.SituatedSysKb;
 
public class Servo360MotorTest extends SituatedPlainObject{
	protected Configurator configurator;
	
	public Servo360MotorTest(IOutputEnvView outEnvView){
		super(outEnvView);
	}
	protected void explain(){
		println( "%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%"    );
		println( "Servo360MotorTest in project it.unibo.qactor.robot (package it.unibo.robotUsage.servo)"    );
		println( "----------------------------------------------------------------------------------------"    );
		println( "GOAL: show the usage of servo motor SS09"    );
		println( "USE: robot bbblike (labbaseRobotSam.jar)"    );
//		println( "USE: QActors without ddr  (qactors18.jar)"    );
//		println( "DEFINES: RobotHttpServer on 8080 in NAIVE/NMESSAGE/EVENT mode"    );
//		println( "DEFINES: RobotAvatar as a RobotActor that waits for messages"    );
//		println( "DEFINES: SensorEventHandler that handles 'usercmd' events raised by  RobotHttpServer\n"
//				+ "	by adding a fact in robot WorldTheory "    );
		println( "%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%"    );
	}

	protected void init(){
		try{
			configurator = Configurator.getInstance();
			System.out.println( "ACTIVATING pi-blaster: sudo /home/pi/pi-blaster/pi-blaster");
			String startPiBlasterCmd = "sudo /home/pi/pi-blaster/pi-blaster";		
			Runtime.getRuntime().exec(startPiBlasterCmd).waitFor();
			System.out.println( "Working with pi-blaster");
		}catch(Exception e){
			System.out.println( "Working without pi-blaster");
		};
		
	}
	
	protected void work() {
		try{
		BufferedReader br = new BufferedReader( new InputStreamReader(System.in) );
		for (IExecutor<?> executor : configurator.getExecutors(ExecutorType.SERVO)) {
//			for( int i = 1; i<=5; i++ ){
//				String cmd = "";
//				int angle = 0;
//					System.out.println(i+ ") ANGLE (0-180)>");
//					cmd = br.readLine();
//					System.out.println("cmd="+cmd);
//					angle = Integer.parseInt(cmd) ;
//				((Servo) executor).execute(new ServoCommand(angle));
//			}		
			println( "FOUND Servo " + executor.getDefStringRep() );
			println( "execute 0 "  );
			((Servo) executor).execute(new ServoCommand(0));
			Thread.sleep(2000);
			println( "execute 180 "  );
			((Servo) executor).execute(new ServoCommand(180));
			Thread.sleep(1000);
		}//for executor
		}catch(Exception e){
			println( "ERROR " + e.getMessage());
		};
	}
	
	protected void terminate(){
		try{
			System.out.println( "Bye bye with pi-blaster");
			Runtime.getRuntime().exec("sudo killall pi-blaster").waitFor();
			System.exit(1);
		}catch(Exception e){
			System.out.println( "Bye bye without pi-blaster");
		};
		
	}
	
	public void doJob(){
		explain();
		init();
		work();
		terminate();
	}
	
	
	public static void main(String[] args) throws Exception {
		new Servo360MotorTest( SituatedSysKb.standardOutEnvView).doJob();
		
				
	}//main
}
