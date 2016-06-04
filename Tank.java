import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.*;
import java.util.List;

public class Tank {
	public int getLife() {
		return life;// getter��setter����ʵ����֮��life�ķ���
	}

	public void setLife(int life) {
		this.life = life;
	}

	public void setGood(boolean good) {
		this.good = good;
	}

	public boolean isGood() {
		return good;// getter��setter����ʵ����֮��good�ķ���
	}

	public boolean isLive() {
		return live;
	}

	public void setLive(boolean live) {
		this.live = live;
	}

	private int x, y;// ����̹�˵ĳ�ʼλ��
	boolean bL = false;
	boolean bU = false;
	boolean bR = false;
	boolean bD = false;// ��������Ϊ�����ͱ�������ʼ��Ϊfalse

	private Direction dir = Direction.STOP;// ��ʼ��̹�˵ķ���Ϊֹͣ
	private Direction ptDir = Direction.D;// ��ʼ����Ͳ�ķ���Ϊ����

	public static final int XSPEED = 10;// ̹���ƶ���ˮƽ�ٶ�Ϊ5
	public static final int YSPEED = 10;// ̹���ƶ��Ĵ�ֱ�ٶ�Ϊ5
	public static final int WIDTH = 200;// ̹�˵Ŀ��Ϊ200
	public static final int HEIGHT = 200;// ̹�˵ĸ߶�Ϊ200

	public int oldX, oldY;// �����м�����洢����
	private int life = 100;// ��ʼ������̹�˵�����ֵΪ100
	BloodBar bb = new BloodBar();// �½�һ��Ѫ��bb
	TankClient tc;// ָ��TankClient�������

	private boolean good;// ����һ�������ͱ���
	private boolean live = true;// ��ʼ����ʾ̹������ֵ�Ĳ����ͱ���live,����ֵtrue
	private static Random r = new Random();// ���������������r
	private int step = r.nextInt(12) + 4;// �趨̹������ƶ��Ĳ���Ϊ2

	private static Toolkit tk = Toolkit.getDefaultToolkit();
	private static Image[] images = null;
	private static Map<String, Image> imgs = new HashMap<String, Image>();
	// �౻�������ڴ�ʱ�����ȼ���ͼƬ
	static {
		images = new Image[] {
				tk.getImage(Tank.class.getClassLoader().getResource(
						"imagesTank/̹��ͼƬD.gif")),
				tk.getImage(Tank.class.getClassLoader().getResource(
						"imagesTank/̹��ͼƬL.gif")),
				tk.getImage(Tank.class.getClassLoader().getResource(
						"imagesTank/̹��ͼƬLD.gif")),
				tk.getImage(Tank.class.getClassLoader().getResource(
						"imagesTank/̹��ͼƬLU.gif")),
				tk.getImage(Tank.class.getClassLoader().getResource(
						"imagesTank/̹��ͼƬR.gif")),

				tk.getImage(Tank.class.getClassLoader().getResource(
						"imagesTank/̹��ͼƬRD.gif")),
				tk.getImage(Tank.class.getClassLoader().getResource(
						"imagesTank/̹��ͼƬRU.gif")),
				tk.getImage(Tank.class.getClassLoader().getResource(
						"imagesTank/̹��ͼƬU.gif")) };
		imgs.put("U", images[7]);// ʹ��HashMap��̹�˸�����images�뷽���Ӧ
		imgs.put("RU", images[6]);// ʹ��HashMap��̹�˸�����images�뷽���Ӧ
		imgs.put("R", images[4]);
		imgs.put("RD", images[5]);
		imgs.put("D", images[0]);
		imgs.put("LD", images[2]);
		imgs.put("L", images[1]);
		imgs.put("LU", images[3]);

	}

	public Tank(int x, int y, boolean good) {// ����һ������ʵ��̹��λ�õ�ȷ��
		this.x = x;
		this.y = y;
		this.oldX = x;
		this.oldY = y;
		this.good = good;
	}

	public void draw(Graphics g) {// ����draw��������������̹��
		if (!live) {
			if (!good) {
				tc.tanks.remove(this);// ����ǵз�̹�˲����е��ˣ��ͽ�̹���Ƴ�
			}
			return;
		}

		if (good)
			bb.draw(g);
		move();
		switch (ptDir) {// ������Ͳ�ķ��򻭳���Ͳ
		case L:
			g.drawImage(imgs.get("L"), x, y, null);
			break;
		case LU:
			g.drawImage(imgs.get("LU"), x, y, null);
			break;
		case U:
			g.drawImage(imgs.get("U"), x, y, null);
			break;
		case RU:
			g.drawImage(imgs.get("RU"), x, y, null);
			break;
		case R:
			g.drawImage(imgs.get("R"), x, y, null);
			break;
		case RD:
			g.drawImage(imgs.get("RD"), x, y, null);
			break;
		case D:
			g.drawImage(imgs.get("D"), x, y, null);
			break;
		case LD:
			g.drawImage(imgs.get("LD"), x, y, null);
			break;
		}
	}

	public Tank(int x, int y, boolean good, Direction dir, TankClient tc) {
		this(x, y, good);// ���췽������̹�˵�λ�ã��û�������̹�˻��ǵз�̹�ˣ������򣬲��Ҵ���TankClient�������
		this.tc = tc;
		this.dir = dir;
	}

	void move() {// ���巽��ʵ��̹�˵��ƶ�
		this.oldX = x;
		this.oldY = y;// ���µ�̹�˵�λ�ô��浽�м������
		switch (dir) {// ���ݼ����ϲ�ͬ�ļ�ֵʵ��̹��λ������x,y�ı仯
		case L:// �����ƶ���
			x -= XSPEED;
			break;
		case LU:// �������ƶ���
			x -= XSPEED;
			y -= YSPEED;
			break;
		case U:// �����ƶ���
			y -= YSPEED;
			break;
		case RU:// �������ƶ���
			x += XSPEED;
			y -= YSPEED;
			break;
		case R:// �����ƶ���
			x += XSPEED;
			break;
		case RD:// �������ƶ���
			x += XSPEED;
			y += YSPEED;
			break;
		case D:// �����ƶ���
			y += YSPEED;
			break;
		case LD:// �������ƶ���
			x -= XSPEED;
			y += YSPEED;
			break;
		case STOP:// ���κμ�ֵ��Ĭ��ֹͣ
			break;
		}
		if (this.dir != Direction.STOP)
			this.ptDir = this.dir;// �������̹�˷�����ֹͣ��STOP��������Ͳ�ķ�����̹��ǰ���ķ��򱣳�һ��
		if (x < 0)
			x = 0;
		if (y < 30)
			y = 30;// ̹���˶����߽������ֵ����������������0���������Ϊ30
		if (x + Tank.WIDTH > TankClient.GAME_WITH)
			x = TankClient.GAME_WITH - Tank.WIDTH;
		if (y + Tank.HEIGHT > TankClient.GAME_HEIGHT)
			y = TankClient.GAME_HEIGHT - Tank.HEIGHT;// ͬ���أ������˶����ұ߽磬Ҳ������Ӧ����

		if (!good) {
			Direction[] dirs = Direction.values();// ��̹���˶���9������ֵ������STOP��ת��Ϊ������д���
			if (step == 0) {
				step = r.nextInt(12) + 2;// ??
				int rn = r.nextInt(dirs.length);
				dir = dirs[rn];

			}
			step--;
			if (r.nextInt(45) > 35)
				this.fire();// ����������������������35�������fire()����
		}

	}

	public void KeyPressed(KeyEvent e) {// �����ֵ���Ʒ���ķ���
		int key = e.getKeyCode();
		switch (key) {
		case KeyEvent.VK_F2:// ��F2������̹�����¸���
			if (!this.live) {
				this.live = true;
				this.life = 100;
			}
			break;
		case KeyEvent.VK_CONTROL:// ����Ctrl����������
			tc.missiles.add(fire());
			break;
		case KeyEvent.VK_LEFT:// �������bLΪtrue
			bL = true;
			break;
		case KeyEvent.VK_UP:// �����ϼ�bUΪtrue
			bU = true;
			break;
		case KeyEvent.VK_RIGHT:// �����Ҽ�bRΪtrue
			bR = true;
			break;
		case KeyEvent.VK_DOWN:// �����¼�bDΪtrue
			bD = true;
			break;
		}
		locatDirection();

	}

	void locatDirection() {// ����bL��bU��bR��bD��������㸳��Direction����dir 9����ͬ�ķ���ֵ
		if (bL && !bU && !bR && !bD)
			dir = Direction.L;
		else if (bL && bU && !bR && !bD)
			dir = Direction.LU;
		else if (!bL && bU && !bR && !bD)
			dir = Direction.U;
		else if (!bL && bU && bR && !bD)
			dir = Direction.RU;
		else if (!bL && !bU && bR && !bD)
			dir = Direction.R;
		else if (!bL && !bU && bR && bD)
			dir = Direction.RD;
		else if (!bL && !bU && !bR && bD)
			dir = Direction.D;
		else if (bL && !bU && !bR && bD)
			dir = Direction.LD;
		else if (!bL && !bU && !bR && !bD)
			dir = Direction.STOP;
	}

	public void keyReleased(KeyEvent e) {// ���尴��̧���Ĳ��������з���ֵ��Ϊfalse

		int key = e.getKeyCode();
		switch (key) {
		case KeyEvent.VK_LEFT:
			bL = false;
			break;
		case KeyEvent.VK_UP:
			bU = false;
			break;
		case KeyEvent.VK_RIGHT:
			bR = false;
			break;
		case KeyEvent.VK_DOWN:
			bD = false;
			break;
		case KeyEvent.VK_A:// ���尴��A��ʱ������superfire()����
			superFire();
			break;
		}
		locatDirection();
	}

	public Missile fire() {// �����ڵ������ķ���
		if (!live)// ����з�̹�˵�liveֵΪ0�������κδ���
			return null;
		int x = this.x + Tank.WIDTH / 2;
		int y = this.y + Tank.HEIGHT / 2;// �����ڵ��ĳ�ʼ����
		Missile m = new Missile(x, y, good, ptDir, this.tc);// ʵ����һ��Missile����󣬸ö���Я��TankClient�������tc
		tc.missiles.add(m);// ����ڵ�ʵ����TankClient������
		return m;

	}

	public Rectangle getRect() {
		return new Rectangle(this.x, this.y, this.WIDTH, this.HEIGHT);// ���崦��߽�ĺ���
	}

	public boolean collidesWithWall(Wall w) {// ���巵��ֵΪ�����ͱ�����collidesWithWall�������������ڼ����ǽ����ײ
		if (this.live && this.getRect().intersects(w.getRect())) {
			this.stay();
			return true;
		}
		return false;
	}

	public void stay() {
		x = oldX;
		y = oldY;
	}

	public boolean collidesWithTanks(java.util.List<Tank> tanks) {// ���Ƶض���һ����������̹��֮�����ײ
		for (int i = 0; i < tanks.size(); i++) {
			Tank t = tanks.get(i);
			if (this != t && this.live && t.live
					&& this.getRect().intersects(t.getRect())) {
				this.stay();
				t.stay();
			}
			return true;
		}
		return false;
	}

	public void superFire() {// ���峬�������ķ���
		Direction[] dir = Direction.values();
		for (int i = 0; i < 8; i++) {
			fire(dir[i]);
		}
	}

	public Missile fire(Direction dir) {// ���췽�����������ڵ����˶�
		if (!live)
			return null;
		int x = this.x + Tank.WIDTH / 2;
		int y = this.y + Tank.HEIGHT / 2;
		Missile m = new Missile(x, y, good, dir, this.tc);//
		tc.missiles.add(m);// ����ڵ�����TankClient����
		return m;

	}

	private class BloodBar {// �����ڲ��࣬����Ѫ��
		public void draw(Graphics g) {// ����draw��������Ѫ��
			Color c = g.getColor();
			g.setColor(Color.RED);
			g.drawRect(x, y - 10, WIDTH - 10, 10);// ��Ѫ����б߽紦��
			int w = (WIDTH - 10) * life / 100;// ����Ѫ��Ŀ��
			g.fillRect(x, y - 10, w, 10);
			g.setColor(c);
		}
	}

	public boolean eat(Blood b) {// ���强��̹�˳�Ѫ��ķ���
		if (this.live && b.isLive() && this.getRect().intersects((b.getRect()))) {
			this.life = 100;// ������̹������ֵ����Ϊ100��������Ѫ��liveΪtrue������̹��liveΪtrue���Ӵ���Ѫ��߽�
			b.setLive(false);// ��Ѫ������ֵ��Ϊfalse
			return true;
		}
		return false;
	}
}
