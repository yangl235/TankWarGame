import java.awt.Graphics;
import java.awt.image.*;
import java.awt.*;
import java.util.*;
import java.awt.Toolkit;
public class explode {
  int x,y;
  public TankClient tc;
  private boolean live=true;
  
  private static Toolkit tk=Toolkit.getDefaultToolkit();//����getDefaultToolkit()����Ӳ������
  
  
  private static Image images[]={
	  
	  tk.getImage(explode.class.getClassLoader().getResource("imagesTank/Ч��ͼ1.gif")),
	  tk.getImage(explode.class.getClassLoader().getResource("imagesTank/Ч��ͼ2.gif")),
	  tk.getImage(explode.class.getClassLoader().getResource("imagesTank/Ч��ͼ3.gif")),
	  tk.getImage(explode.class.getClassLoader().getResource("imagesTank/Ч��ͼ4.gif")),
	  tk.getImage(explode.class.getClassLoader().getResource("imagesTank/Ч��ͼ5.gif"))

		  
  };//�����˷����ȡimages
  int step=0;
  private static boolean init=false;
  public explode(int x,int y,TankClient tc){
		this.x=x;
		this.y=y;
		this.tc=tc;
		
	}
  public void draw(Graphics g){
	  if(false==init){
		  
		  for (int i = 0; i < images.length; i++) {//Ϊ��ֹ��һ���ڵ���ͼƬЧ�����Ȼ���һ��ͼ
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