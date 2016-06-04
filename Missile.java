import java.awt.*;
import java.awt.List;
import java.awt.event.KeyEvent;
import java.util.*;

public class Missile {
	public boolean isbLive() {
		return bLive;
	}

	public static final int XSPEED = 15;
	public static final int YSPEED = 15;// ������̹��x,y������н��ٶ�
	public static final int WIDTH = 40;
	public static final int HEIGHT = 40;// �������ڵ��Ŀ��

	int x, y;// �����ڵ���λ��
	private boolean bLive = true;// �趨һ��Boolean�ͱ������missiles������
	Direction dir;// ����һ��Direction������ʾ����
	private TankClient tc;// ����һ��TankClient����
	private boolean good;

	public Missile(int x, int y, Direction dir) {// ����Misssile��������λ�ò���
		this.x = x;
		this.y = y;
		this.dir = dir;
	}

	public Missile(int x, int y, boolean good, Direction dir, TankClient tc) {// ���ݼ���̹�˵�λ�ü����������
		this(x, y, dir);
		this.tc = tc;
		this.good = good;
	}

	public void draw(Graphics g) {// ���ڵ����������󣬽�ͼ���Ƴ�
		if (!bLive) {
			tc.missiles.remove(this);
			return;
		}
		Color c = g.getColor();
		if (good)
			g.setColor(Color.ORANGE);// ����̹�˵��ڵ���ɫΪ�ٻ�ɫ
		else
			g.setColor(Color.RED);// �з�̹���ڵ�����ɫΪ��ɫ
		g.fillOval(x, y, 40, 40);
		g.setColor(c);
		move();// ����move����ʹ���ڵ��˶�
	}

	public void move() {
		switch (dir) {// �ڵ����˶�����̹���˶��ķ�����仯
		case L:
			x -= XSPEED;
			break;
		case LU:
			x -= XSPEED;
			y -= YSPEED;
			break;
		case U:
			y -= YSPEED;
			break;
		case RU:
			x += XSPEED;
			y -= YSPEED;
			break;
		case R:
			x += XSPEED;
			break;
		case RD:
			x += XSPEED;
			y += YSPEED;
			break;
		case D:
			y += YSPEED;
			break;
		case LD:
			x -= XSPEED;
			y += YSPEED;
			break;
		case STOP:
			break;

		}
		if (x < 0 || y < 0 || x > TankClient.GAME_WITH
				|| y > TankClient.GAME_HEIGHT) {// ����Խ����Ϸ���ڱ߽���ڵ�����Ϊ��������
			bLive = false;
		}

	}

	public Rectangle getRect() {// �ڵ��ı�Ե���
		return new Rectangle(this.x, this.y, this.WIDTH, this.HEIGHT);
	}

	public boolean hitTank(Tank t) {// �ڵ���̹���໥���ã�����������������ֵ��2
		if (this.bLive && this.getRect().intersects(t.getRect()) && t.isLive()
				&& this.good != t.isGood()) {
			if (t.isGood()) {
				t.setLife(t.getLife() - 2);
				if (t.getLife() <= 0)
					t.setLive(false);
			} else {
				t.setLive(false);// �ڵ��������з�̹�ˣ��з�̹��ֱ������
			}
			this.bLive = false;// ͬʱ�����ö���bLive��ֵ��Ϊfalse
			explode e = new explode(x, y, tc);
			tc.explodes.add(e);
			return true;
		}
		return false;
	}

	public boolean hitTanks(java.util.List<Tank> tanks) {
		for (int i = 0; i < tanks.size(); i++) {
			if (hitTank(tanks.get(i)))// �����õ���ǰ���󣨵з�̹�˵�����������trueֵ
				return true;
		}
		return false;
	}

	public boolean hitWall(Wall w) {//
		if (this.bLive && this.getRect().intersects(w.getRect())) {// ��ǽ�໥���ã���������̹�˺�ǽ����������
			this.bLive = false;
			return true;
		}
		return false;

	}

}