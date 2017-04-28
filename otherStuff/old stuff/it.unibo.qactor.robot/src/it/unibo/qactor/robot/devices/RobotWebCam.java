package it.unibo.qactor.robot.devices;
import java.io.IOException;

import it.unibo.is.interfaces.IOutputView;
import it.unibo.system.SituatedPlainObject;

public class RobotWebCam extends SituatedPlainObject{
	public RobotWebCam(IOutputView outView) throws Exception {
		super(outView);
		setModProbe();		 
	}
	protected void setModProbe() throws IOException{
 			Runtime.getRuntime().exec("sudo modprobe bcm2835-v4l2");		
	}
	
	public void setForImage(int width, int height ){ //width=2592,height=1944
		/*
		 * sudo modprobe bcm2835-v4l2
		 * v4l2-ctl --set-fmt-video=width=2592,height=1944,pixelformat=3
		 * v4l2-ctl --stream-mmap=3 --stream-count=1 --stream-to=somefile.jpg
		 */
		try {
 			Runtime.getRuntime().exec(
 					"v4l2-ctl --set-fmt-video=width="+ width+",height="+ height+",pixelformat=3");
		} catch (IOException e) {
 			e.printStackTrace();
		}
		
	}
	
	public void setForVideo(){
		/*
		 * sudo modprobe bcm2835-v4l2
		 * v4l2-ctl --set-fmt-video=width=1920,height=1088,pixelformat=4
		 * v4l2-ctl --stream-mmap=3 --stream-count=1 --stream-to=somefile.jpg
		 */
		try {
 			Runtime.getRuntime().exec(
 					"v4l2-ctl --set-fmt-video=width=1920,height=1088,pixelformat=4");
		} catch (IOException e) {
 			e.printStackTrace();
		}
		
	}
	
	public void reset(){
		try {
 			this.println("reset a photo or video (TODO???)");
		} catch (Exception e) {
 			e.printStackTrace();
		}		
	}
	public void captureImg(String fName) throws Exception{
		Runtime.getRuntime().exec("v4l2-ctl --stream-mmap=3 --stream-count=1 --stream-to="+fName);
	}
	public void captureVideo(int nframes, String fName) throws Exception{ //somefile.h264
		/* TEST
		    
		    v4l2 stands for video4linux2	http://linuxtv.org/downloads/v4l-dvb-apis/
		    http://lwn.net/Articles/203924/ INTRUDUCTION
		    sudo modprobe bcm2835-v4l2
		    v4l2-ctl --list-devices
		    ffmpeg -f v4l2 -list_formats all -i /dev/video0		//Does not work
		    v4l2-ctl --list-formats-ext
		    v4l2-ctl -L
sudo modprobe bcm2835-v4l2
v4l2-ctl --set-fmt-video=width=1920,height=1088,pixelformat=4
v4l2-ctl --stream-mmap=3 --stream-count=100 --stream-to=videoTest.h264
VIDIOC_REQBUFS: Cannot allocate memory see https://groups.google.com/forum/#!topic/beagleboard/Yi1FJEr1R1Q
see http://elinux.org/RPiconfig for gpu-mem	= 		/boot/config.txt
			v4l2-ctl --set-fmt-video=width=1920,height=1088,pixelformat=4
			v4l2-ctl --stream-mmap=3 --stream-count=300 --stream-to=videoTest.h264
			
			dd if=/dev/video0 of=video.h264 bs=1M & pid=$! ; sleep 5; kill $pid
https://www.udacity.com/open-ed

 raspivid -t 2000 -o fname.h264

		*/
		Runtime.getRuntime().exec("v4l2-ctl --stream-mmap=3 --stream-count="+nframes+" --stream-to="+fName);
	}
}
