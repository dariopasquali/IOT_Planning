package it.unibo.robotUsage.avatar;
import alice.tuprolog.Struct;
import alice.tuprolog.Term;
import it.unibo.iot.executors.baseRobot.IBaseRobot;
import it.unibo.qactor.robot.RobotSysKb;
import it.unibo.system.SituatedPlainObject;

public class CmdUilInterpreter extends SituatedPlainObject{
	protected IBaseRobot robot; 
	public CmdUilInterpreter(  ){
  		robot = RobotSysKb.robotBase;
	}
	public void execute(String cmd){
		//cmd=usercmd("w-low")
//		println("CmdUilInterpreter execute " +cmd );
		Struct cmdT = (Struct) Term.createTerm(cmd);
//		println("CmdUilInterpreter execute " + cmdT.getArg(0).toString() );
		char c = cmdT.getArg(0).toString().replace("'", "").charAt(0);
		execute(c);
	}
	public void  execute(char b){
//		println("CmdUilInterpreter execute char " + b );
		if (b == 'q' || b=='h' ) {
			robot.execute(RobotSysKb.STOP);
		} else if (b == 'w') {
			robot.execute(RobotSysKb.FORWARD);
		} else if (b == 's') {
			robot.execute(RobotSysKb.BACKWARD);
	 	} else if (b == 'a') {
	  		robot.execute(RobotSysKb.LEFT);
	 	} else if (b == 'd') {
	 		robot.execute(RobotSysKb.RIGHT);
	 	}
	}
 
}
