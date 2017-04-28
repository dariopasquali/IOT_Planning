package it.unibo.robotUsage.avatar;
import it.unibo.contactEvent.interfaces.IContactEventPlatform;
import it.unibo.contactEvent.platform.ContactEventPlatform;
import it.unibo.iot.models.sensorData.ISensorData;
import it.unibo.iot.sensors.ISensorObserver;
import it.unibo.is.interfaces.IOutputEnvView;
import it.unibo.system.SituatedPlainObject;
 
public class SensorObserverToEvent<T extends ISensorData> 
				extends SituatedPlainObject implements ISensorObserver<T>{
	protected IContactEventPlatform platform ;
	
	public SensorObserverToEvent(IOutputEnvView outEnvView) { 
		super(outEnvView);
		try {
			platform = ContactEventPlatform.getPlatform();
		} catch (Exception e) {
 			e.printStackTrace();
		}
	}
	
 
	@Override
	public void notify(T sensorData) {
		String data =  sensorData.getDefStringRep();
 		try {
//	 		outEnvView.addOutput("--- SensorObserverToEvent data:=" + data + " class=" + sensorData.getClass().getName() + " emits raspsensor"   );
			platform.raiseEvent("raspberry", "raspsensor",   data);
		} catch (Exception e) {
 			e.printStackTrace();
		}
	}

 	
}
