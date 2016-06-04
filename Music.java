package music;

import java.applet.Applet;
import java.applet.AudioClip;
import java.net.MalformedURLException;
import java.net.URL;

public class Music {  
	  
	   
	public static void main(String[] args) {  
	  // TODO Auto-generated method stub  
	                //修改你的音乐文件路径就OK了  
	  AePlayWave apw=new AePlayWave("E:\\music\\1.mp3");
	  apw.start();  
	}  
	  
	}  
