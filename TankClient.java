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
	public static final int GAME_WITH = 1200; // ��Ϸ���ڵĿ��Ϊ1000�����عٶ�
	public static final int GAME_HEIGHT = 1000; // ��Ϸ���ڸ߶�Ϊ600������
	public static final int WIDTH = 50; // �ڵ��Ŀ��
	public static final int HEIGHT = 50;
	

	// �ڵ��ĸ߶ȣ�ʵ���Ͼ����ڵ���ֱ����
	Tank myTank = new Tank(400, 400, true, Direction.STOP, this); // ̹�˸ճ���ʱ��״̬��Ĭ�ϵķ���Ϊֹͣ
	List<Missile> missiles = new ArrayList<Missile>(); // ����һ���ڵ��������ڵ������
	List<explode> explodes = new ArrayList<explode>(); // ����һ����ը�����
	List<Tank> tanks = new ArrayList<Tank>(); // ���ɵз�̹�˶���
	Blood b = new Blood(); // ����Ѫ�������

	Wall w1 = new Wall(100, 10, 100, 35, this); // ��һ��ǽλ��
	Wall w2 = new Wall(300, 400, 100, 400, this);
	Wall w3 = new Wall(600, 600, 600, 100, this);
	Image offScreenImage = null;// ����һ�������ͼƬ
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
	public void update(Graphics g) {// ��дContainer��update����
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
		g.drawString("missiles count:" + missiles.size(), 20, 40); // ��ʾ�ڵ�����
		g.drawString("Explodes count:" + explodes.size(), 20, 60); // ��ʾ��ը����
		g.drawString("Tanks    count:" + explodes.size(), 20, 80);
		g.drawString("Tanks 's life:" + myTank.getLife(), 20, 100); // ��ʾ�Լ�̹�˵�����ֵ
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
						this));// �з�̹�˱��������������5��̹�ˣ���ʼ�ķ���Ϊ����
			}
		}
		for (int i = 0; i < tanks.size(); i++) {
			Tank t = tanks.get(i);
			t.collidesWithWall(w1); // �з�̹�����һ��ǽ��ײ
			t.collidesWithWall(w2);
			t.collidesWithWall(w3); // �з�̹����ڶ���ǽ��ײ
			t.collidesWithTanks(tanks); // �з�̹����ǽ��ײ
			t.draw(g); // ???
		}

		for (int i = 0; i < missiles.size(); i++) {
			Missile m = missiles.get(i);
			m.hitTanks(tanks); // �ڵ���з�̹�˽Ӵ�
			m.hitTank(myTank); // ����̹�˷����ڵ�
			m.hitWall(w1); // �ڵ����һ��ǽ�Ӵ�
			m.hitWall(w2);
			m.hitWall(w3);// �ڵ���ǽ�Ӵ�
			m.draw(g);
		}
		for (int i = 0; i < explodes.size(); i++) {
			explode e = explodes.get(i);
			e.draw(g); // ������ը����
		}
		myTank.draw(g); // ��������̹��
		myTank.eat(b); // ����̹�˳Ե�Ѫ��
		w1.draw(g);// ������һ��ǽ
		w2.draw(g); // �����ڶ���ǽ
		w3.draw(g);// ����������ǽ
		b.draw(g); // ����Ѫ��
	}
/*
	public static void main(String[] args) {
		LogIn LogIn_TankWar = new LogIn();
		//while(LogIn.isDefaultLookAndFeelDecorated())
		TankClient t=new TankClient();
		t.launchFrame();
		//new TankClient().launchFrame(); // �������е���launchFrame()�������ɻ���
		AePlayWave apw=new AePlayWave("E:\\KGMusic\\1.wav");  
		  apw.start(); 
		  

	}
	*/

	public void launchFrame() {
		for (int i = 0; i <5; i++) {
			tanks.add(new Tank(50 + 300 * (i + 1), 50, false, Direction.D, this));// ��ʼʱ����10���з�̹��
		}
		setLocation(10, 20);
		setBounds(200, 200, GAME_WITH, GAME_HEIGHT);
		this.setTitle("TankWar"); // ���ڱ���
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent arg0) {
				System.exit(0); // �趨���ڿɹر�
			}
		});
		this.setBackground(Color.BLACK); // ���屳������
		this.setResizable(true); // ���ÿɵ��������С
		this.addKeyListener(new KeyMonitor()); // ʹ�ü��̼����¼�
		setVisible(true); // �������������Ϊ�ɼ���																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																															
		new Thread(new PaintThread()).start(); // ����һ���µ��߳�

	}

	class PaintThread implements Runnable { // ʵ��Runnable�ӿ�
		public void run() { // ��дrun����
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

	public class KeyMonitor extends KeyAdapter {// �̳�KeyAdapter��ʵ�ּ����¼�

		public void keyPressed(KeyEvent e) {// ���²�����ʱ����̹�˵���Ӧ
			myTank.KeyPressed(e);

		}

		public void keyReleased(KeyEvent e) {// ���̷���ʱ����̹�˵���Ӧ
			myTank.keyReleased(e);

		}

	}
}
