import java.awt.*;
import java.awt.List;
import java.awt.event.KeyEvent;
import java.util.*;

public class Missile {
	public boolean isbLive() {
		return bLive;
	}

	public static final int XSPEED = 15;
	public static final int YSPEED = 15;// 定义了坦克x,y方向的行进速度
	public static final int WIDTH = 40;
	public static final int HEIGHT = 40;// 定义了炮弹的宽度

	int x, y;// 定义炮弹的位置
	private boolean bLive = true;// 设定一个Boolean型变量标记missiles的生命
	Direction dir;// 定义一个Direction类对象表示方向
	private TankClient tc;// 定义一个TankClient引用
	private boolean good;

	public Missile(int x, int y, Direction dir) {// 定义Misssile方法传递位置参数
		this.x = x;
		this.y = y;
		this.dir = dir;
	}

	public Missile(int x, int y, boolean good, Direction dir, TankClient tc) {// 传递己方坦克的位置及方向的引用
		this(x, y, dir);
		this.tc = tc;
		this.good = good;
	}

	public void draw(Graphics g) {// 当炮弹“死亡”后，将图像移除
		if (!bLive) {
			tc.missiles.remove(this);
			return;
		}
		Color c = g.getColor();
		if (good)
			g.setColor(Color.ORANGE);// 己方坦克的炮弹颜色为橘黄色
		else
			g.setColor(Color.RED);// 敌方坦克炮弹的颜色为红色
		g.fillOval(x, y, 40, 40);
		g.setColor(c);
		move();// 调用move方法使得炮弹运动
	}

	public void move() {
		switch (dir) {// 炮弹的运动根据坦克运动的方向而变化
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
				|| y > TankClient.GAME_HEIGHT) {// 对于越过游戏窗口边界的炮弹设置为“死亡”
			bLive = false;
		}

	}

	public Rectangle getRect() {// 炮弹的边缘检测
		return new Rectangle(this.x, this.y, this.WIDTH, this.HEIGHT);
	}

	public boolean hitTank(Tank t) {// 炮弹与坦克相互作用，己方被攻击，生命值减2
		if (this.bLive && this.getRect().intersects(t.getRect()) && t.isLive()
				&& this.good != t.isGood()) {
			if (t.isGood()) {
				t.setLife(t.getLife() - 2);
				if (t.getLife() <= 0)
					t.setLive(false);
			} else {
				t.setLive(false);// 炮弹攻击到敌方坦克，敌方坦克直接死亡
			}
			this.bLive = false;// 同时将调用对象bLive的值设为false
			explode e = new explode(x, y, tc);
			tc.explodes.add(e);
			return true;
		}
		return false;
	}

	public boolean hitTanks(java.util.List<Tank> tanks) {
		for (int i = 0; i < tanks.size(); i++) {
			if (hitTank(tanks.get(i)))// 函数拿到当前对象（敌方坦克的数量）返回true值
				return true;
		}
		return false;
	}

	public boolean hitWall(Wall w) {//
		if (this.bLive && this.getRect().intersects(w.getRect())) {// 与墙相互作用，处理方法与坦克和墙的作用类似
			this.bLive = false;
			return true;
		}
		return false;

	}

}