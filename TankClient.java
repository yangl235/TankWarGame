import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.*;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;

import music.AePlayWave;
import sun.audio.*;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;

public class TankClient extends Frame {
	public static final int GAME_WITH = 1200; // 游戏窗口的宽度为1000个像素官渡
	public static final int GAME_HEIGHT = 1000; // 游戏窗口高度为600个像素
	public static final int WIDTH = 50; // 炮弹的宽度
	public static final int HEIGHT = 50;
	

	// 炮弹的高度（实际上就是炮弹的直径）
	Tank myTank = new Tank(400, 400, true, Direction.STOP, this); // 坦克刚出现时的状态，默认的方向为停止
	List<Missile> missiles = new ArrayList<Missile>(); // 生成一个炮弹容器（炮弹类对象）
	List<explode> explodes = new ArrayList<explode>(); // 生成一个爆炸类对象
	List<Tank> tanks = new ArrayList<Tank>(); // 生成敌方坦克对象
	Blood b = new Blood(); // 生成血块类对象

	Wall w1 = new Wall(100, 10, 100, 35, this); // 第一堵墙位置
	Wall w2 = new Wall(300, 400, 100, 400, this);
	Wall w3 = new Wall(600, 600, 600, 100, this);
	Image offScreenImage = null;// 创建一张虚拟的图片
	/*
	FileInputStream fileau=new FileInputStream("E:\\KGMusic\\Rihanna - Stupid In Love.wav" );
	   AudioStream as=new AudioStream(fileau);
	   AudioPlayer.player.start(as);
	*/
	private class music{
	public void music(){
		try
        {
            URL SoundFile=new URL("E:\\KGMusic\\Rihanna - Stupid In Love.wav");
            AudioClip Sound=Applet.newAudioClip(SoundFile);
            Sound.loop();
            try
            {
                Thread.sleep(1000);
            }catch(InterruptedException e){}
        }catch(MalformedURLException mue){}
    }
	}
	public void update(Graphics g) {// 重写Container的update方法
		if (offScreenImage == null) {
			offScreenImage = this.createImage(GAME_WITH, GAME_HEIGHT);
		}
		Graphics gOffScreen = offScreenImage.getGraphics();
		Color c = gOffScreen.getColor();
		gOffScreen.setColor(Color.BLACK);
		gOffScreen.fillRect(0, 0, GAME_WITH, GAME_HEIGHT);
		gOffScreen.setColor(c);
		paint(gOffScreen);
		g.drawImage(offScreenImage, 0, 0, null);
	}

	public void paint(Graphics g) {
		/*
		g.drawString("missiles count:" + missiles.size(), 20, 40); // 显示炮弹数量
		g.drawString("Explodes count:" + explodes.size(), 20, 60); // 显示爆炸数量
		g.drawString("Tanks    count:" + explodes.size(), 20, 80);
		g.drawString("Tanks 's life:" + myTank.getLife(), 20, 100); // 显示自己坦克的生命值
        */
		Font f1=new Font("Missiles count:",Font.PLAIN,16);
		Font f2=new Font("Explodes count:",Font.PLAIN,16);
		Font f3=new Font("Tanks    count:",Font.PLAIN,16);
		Font f4=new Font("Tanks 's  life:",Font.PLAIN,16);
		Font f5=new Font("MADED BY YANGLEI",Font.PLAIN,26);
		g.setFont(f1);
		g.setFont(f2);
		g.setFont(f3);
		g.setFont(f4);
		g.setFont(f5);
		g.setColor(new Color(0,255,0));
		g.drawString("missiles count:" + missiles.size(), 20, 140);
		g.drawString("Explodes count:" + explodes.size(), 20, 180);
		g.drawString("Tanks    count:" + explodes.size(), 20, 220);
		g.drawString("Tanks 's life:" + myTank.getLife(), 20, 260);
	    g.setColor(Color.RED);
	    g.drawString("MADED BY YANGLEI" , 20, 300);
		
		if (tanks.size() <= 0) {
			for (int i = 0; i < 5; i++) {
				tanks.add(new Tank(50 + 300 * (i + 1), 50, false, Direction.D,
						this));// 敌方坦克被消灭后，马上生成5辆坦克，初始的方向为向下
			}
		}
		for (int i = 0; i < tanks.size(); i++) {
			Tank t = tanks.get(i);
			t.collidesWithWall(w1); // 敌方坦克与第一堵墙碰撞
			t.collidesWithWall(w2);
			t.collidesWithWall(w3); // 敌方坦克与第二堵墙碰撞
			t.collidesWithTanks(tanks); // 敌方坦克与墙碰撞
			t.draw(g); // ???
		}

		for (int i = 0; i < missiles.size(); i++) {
			Missile m = missiles.get(i);
			m.hitTanks(tanks); // 炮弹与敌方坦克接触
			m.hitTank(myTank); // 己方坦克发射炮弹
			m.hitWall(w1); // 炮弹与第一堵墙接触
			m.hitWall(w2);
			m.hitWall(w3);// 炮弹与墙接触
			m.draw(g);
		}
		for (int i = 0; i < explodes.size(); i++) {
			explode e = explodes.get(i);
			e.draw(g); // 画出爆炸场面
		}
		myTank.draw(g); // 画出己方坦克
		myTank.eat(b); // 己方坦克吃掉血块
		w1.draw(g);// 画出第一堵墙
		w2.draw(g); // 画出第二堵墙
		w3.draw(g);// 画出第三堵墙
		b.draw(g); // 画出血块
	}
/*
	public static void main(String[] args) {
		LogIn LogIn_TankWar = new LogIn();
		//while(LogIn.isDefaultLookAndFeelDecorated())
		TankClient t=new TankClient();
		t.launchFrame();
		//new TankClient().launchFrame(); // 主方法中调用launchFrame()方法生成画面
		AePlayWave apw=new AePlayWave("E:\\KGMusic\\1.wav");  
		  apw.start(); 
		  

	}
	*/

	public void launchFrame() {
		for (int i = 0; i <5; i++) {
			tanks.add(new Tank(50 + 300 * (i + 1), 50, false, Direction.D, this));// 开始时生成10辆敌方坦克
		}
		setLocation(10, 20);
		setBounds(200, 200, GAME_WITH, GAME_HEIGHT);
		this.setTitle("TankWar"); // 窗口标题
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent arg0) {
				System.exit(0); // 设定窗口可关闭
			}
		});
		this.setBackground(Color.BLACK); // 窗体背景设置
		this.setResizable(true); // 设置可调整窗体大小
		this.addKeyListener(new KeyMonitor()); // 使用键盘监听事件
		setVisible(true); // 将所有组件设置为可见·																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																															
		new Thread(new PaintThread()).start(); // 启动一个新的线程

	}

	class PaintThread implements Runnable { // 实现Runnable接口
		public void run() { // 重写run方法
			while (true) {
				repaint();
				try {
					Thread.sleep(50); // The stopping time
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			}
		}
	}

	public class KeyMonitor extends KeyAdapter {// 继承KeyAdapter类实现键盘事件

		public void keyPressed(KeyEvent e) {// 按下操作键时己方坦克的响应
			myTank.KeyPressed(e);

		}

		public void keyReleased(KeyEvent e) {// 键盘反弹时己方坦克的响应
			myTank.keyReleased(e);

		}

	}
}
