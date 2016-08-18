package it.unibo.robotUsage.arduino;
import it.unibo.iot.models.sensorData.ISensorData;
import it.unibo.iot.sensors.ISensorObserver;
import it.unibo.is.interfaces.IOutputEnvView;
import it.unibo.system.SituatedPlainObject;

public class SensorObserver<T extends ISensorData> 
	extends SituatedPlainObject implements ISensorObserver<T>{

	public SensorObserver(IOutputEnvView outView) { 
		super(outView);
	}
	@Override
	public void notify(T data) {
		println("	SensorObserver: " +data.getDefStringRep());
	}

}
