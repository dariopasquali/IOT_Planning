package it.unibo.qactor.robot.action;
import it.unibo.iot.executors.ExecutorType;
import it.unibo.iot.models.commands.baseRobot.IBaseRobotAngle;
import it.unibo.iot.models.commands.baseRobot.IBaseRobotCommand;
import it.unibo.iot.models.commands.baseRobot.IBaseRobotSpeed;
import it.unibo.iot.models.commands.baseRobot.MovementValue;
import it.unibo.iot.models.sensorData.IPosition;
import it.unibo.qactor.robot.IRobotTimedCommand;

import org.json.JSONObject;
 

public class RobotTimedCommand implements IRobotTimedCommand{
private IBaseRobotCommand command;
private String endEvId;
private long duration;

	public RobotTimedCommand(IBaseRobotCommand command, long duration, String endEvId ){
		this.command 	= command;
		this.endEvId	= endEvId;
		this.duration	= duration;
		System.out.println("RobotTimedCommand CREATED ");
	}
	@Override
	public IBaseRobotCommand getRobotBaseCommand() {
 		return command;
	}
	@Override
	public long getDuration() {
 		return duration;
	}
	@Override
	public String getCompletionEventId() {
 		return endEvId;
	}
	@Override
	public IBaseRobotSpeed getSpeed() {
 		return command.getSpeed();
	}
	@Override
	public IBaseRobotAngle getAngle() {
 		return command.getAngle();
	}
	@Override
	public String getDefStringRep() {
		return "robotTimedCommand("+command.getDefStringRep() + ", duration(" +  duration + "),endEvId(" + endEvId + "))";
	}
	@Override
	public JSONObject getJsonRep() {
		System.out.println("RobotMoveAction RobotTimedCommand TODO ");
 		return null;
	}
	@Override
	public IPosition getPosition() {
		System.out.println("RobotMoveAction getPosition TODO ");
		return null;
	}
	@Override
	public void setPosition(IPosition position) {
		System.out.println("setPosition getPosition TODO ");
	}
	@Override
	public ExecutorType getType() {
		System.out.println("setPosition getType TODO ");
		return null;
	}
	@Override
	public MovementValue geMovement() {
		System.out.println("setPosition geMovement TODO ");
		return null;
	}
 }
