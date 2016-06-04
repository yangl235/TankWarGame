import java.awt.Graphics;
import java.awt.image.*;
import java.awt.*;
import java.util.*;
import java.awt.Toolkit;
public class explode {
  int x,y;
  public TankClient tc;
  private boolean live=true;
  
  private static Toolkit tk=Toolkit.getDefaultToolkit();//调用getDefaultToolkit()访问硬盘数据
  
  
  private static Image images[]={
	  
	  tk.getImage(explode.class.getClassLoader().getResource("imagesTank/效果图1.gif")),
	  tk.getImage(explode.class.getClassLoader().getResource("imagesTank/效果图2.gif")),
	  tk.getImage(explode.class.getClassLoader().getResource("imagesTank/效果图3.gif")),
	  tk.getImage(explode.class.getClassLoader().getResource("imagesTank/效果图4.gif")),
	  tk.getImage(explode.class.getClassLoader().getResource("imagesTank/效果图5.gif"))

		  
  };//采用了反射获取images
  int step=0;
  private static boolean init=false;
  public explode(int x,int y,TankClient tc){
		this.x=x;
		this.y=y;
		this.tc=tc;
		
	}
  public void draw(Graphics g){
	  if(false==init){
		  
		  for (int i = 0; i < images.length; i++) {//为防止第一发炮弹无图片效果，先画出一张图
			g.drawImage(images[i],x,y,null);
		}
		init=true;
	  }
	  
  if(!live){
	  tc.explodes.remove(this); 
          return;}
  if(step==images.length){
	  live=false;
	  step=0;
	  return;
  }
  g.drawImage(images[step],x,y,null);
  step++;
}
}