package it.unibo.robotUsage.naive;
import it.unibo.contactEvent.interfaces.IContactEventPlatform;
import it.unibo.contactEvent.platform.ContactEventPlatform;
import it.unibo.iot.models.sensorData.distance.IDistanceSensorData;
import it.unibo.is.interfaces.IOutputEnvView;
import it.unibo.qactors.web.GuiUiKb;

public class DistanceSensorObserver  extends SensorObserver<IDistanceSensorData>{
 	public DistanceSensorObserver(IOutputEnvView outView) { 
		super(outView);
	}
	 
	public void notify(IDistanceSensorData data) {
 		System.out.println("	DistanceSensorObserver: " +data.getDefStringRep());
 	}

 	
}
