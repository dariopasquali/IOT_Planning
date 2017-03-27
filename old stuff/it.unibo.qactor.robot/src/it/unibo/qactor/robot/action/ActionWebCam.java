package it.unibo.qactor.robot.action;
import it.unibo.is.interfaces.IOutputEnvView;
import it.unibo.qactor.robot.RobotSysKb;
import it.unibo.qactor.robot.devices.RobotWebCam;
import it.unibo.qactors.action.ActorAction;
 

public class ActionWebCam extends ActorAction{
protected String fName;
protected RobotWebCam webCam  = null ;
protected boolean photo;
 
	public ActionWebCam(String name, boolean cancompensate, 
			String terminationEvId, String answerEvId, IOutputEnvView outView, long maxduration, 
			boolean photo, String fName) throws Exception {
		super(name, cancompensate, terminationEvId, answerEvId, outView, maxduration);
		this.photo = photo;
		this.fName = fName.replaceAll("'","").trim();
 	}
	@Override
	public void execAction() throws Exception {
  			webCam    = RobotSysKb.getRobotwebCam();
  			if( webCam != null ) 
  				if(photo) 
  					execPhoto(fName);
  				else
  					execVideo(fName );		
 	}
	protected void execVideo( String fName  ) throws Exception {
//		println("		%%% Action execVideo to " +  fName );
		int nFrames = 300;
 		webCam.setForVideo();
		webCam.captureVideo(nFrames,fName);
		Thread.sleep( maxduration );
    } 
    protected void execPhoto(String fName) throws Exception{
    	println("		%%% Action execPhoto to " +  fName );
	    webCam.setForImage(2592,1944);//width=2592,height=1944
		webCam.captureImg(fName);
		Thread.sleep( maxduration );
    }
	@Override
	public void suspendAction(){
 		if( webCam != null ) webCam.reset();
  		super.suspendAction();
	}
	@Override
	protected void execTheAction() throws Exception {
		println("ActionWebCam execTheAction TODO ");
 	}
	@Override
	protected String getApplicationResult() throws Exception {
		println("ActionWebCam getApplicationResult TODO ");
		return "TODO";
	}
}
