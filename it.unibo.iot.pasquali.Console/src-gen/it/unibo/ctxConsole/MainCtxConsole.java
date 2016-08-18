/* Generated by AN DISI Unibo */ 
package it.unibo.ctxConsole;
import it.unibo.qactors.ActorContext;
import java.io.InputStream;
import it.unibo.is.interfaces.IOutputEnvView;
import it.unibo.system.SituatedSysKb;
public class MainCtxConsole extends ActorContext{
//private IBasicEnvAwt env; 
 
	public MainCtxConsole(String name, IOutputEnvView outEnvView,
			InputStream sysKbStream, InputStream sysRulesStream) throws Exception {
		super(name, outEnvView, sysKbStream, sysRulesStream);
		this.outEnvView = outEnvView;
		env = outEnvView.getEnv();
 	}
	@Override
	public void configure() {
		try {
		SituatedSysKb.init();	//Init the schedulers
		println("Starting the actors .... ");	
new it.unibo.console.Console("console", this, outEnvView);
		
 		} catch (Exception e) {
 		  e.printStackTrace();
		} 		
	}
 	
/*
* ----------------------------------------------
* MAIN
* ----------------------------------------------
*/
 	
public static void main(String[] args) throws Exception{
	IOutputEnvView outEnvView = SituatedSysKb.standardOutEnvView;
 		InputStream sysKbStream    = //MainCtxConsole.class.getResourceAsStream("exploreandgo.pl");
 			new java.io.FileInputStream("./srcMore/it/unibo/ctxConsole/exploreandgo.pl");
	InputStream sysRulesStream = MainCtxConsole.class.getResourceAsStream("sysRules.pl");
	new MainCtxConsole("ctxConsole", outEnvView, sysKbStream, sysRulesStream ).configure();
 	} 	
}
