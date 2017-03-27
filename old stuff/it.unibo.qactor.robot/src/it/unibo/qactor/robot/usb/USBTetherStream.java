package it.unibo.qactor.robot.usb;
import java.io.BufferedReader;
import it.unibo.is.interfaces.IOutputView;
import it.unibo.rx.sensor.sources.SensSrcBufReaderGeneric;	//in qevstreams.jar

public class USBTetherStream extends SensSrcBufReaderGeneric<String, String>{

	public USBTetherStream(IOutputView outView,  BufferedReader in ){
		super(outView, in);
	}
	@Override
	protected String convertData(String s) {
		return s;
	}
	@Override
	protected void initTheVal() {
 	}
	@Override
	protected void genTheVal() {
		setVal( data ); //val = data;
 	}
}
