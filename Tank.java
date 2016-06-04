import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.*;
import java.util.List;

public class Tank {
	public int getLife() {
		return life;// getter和setter方法实现类之间life的访问
	}

	public void setLife(int life) {
		this.life = life;
	}

	public void setGood(boolean good) {
		this.good = good;
	}

	public boolean isGood() {
		return good;// getter和setter方法实现类之间good的访问
	}

	public boolean isLive() {
		return live;
	}

	public void setLive(boolean live) {
		this.live = live;
	}

	private int x, y;// 声明坦克的初始位置
	boolean bL = false;
	boolean bU = false;
	boolean bR = false;
	boolean bD = false;// 将方向设为布尔型变量，初始化为false

	private Direction dir = Direction.STOP;// 初始化坦克的方向为停止
	private Direction ptDir = Direction.D;// 初始化炮筒的方向为向下

	public static final int XSPEED = 10;// 坦克移动的水平速度为5
	public static final int YSPEED = 10;// 坦克移动的垂直速度为5
	public static final int WIDTH = 200;// 坦克的宽度为200
	public static final int HEIGHT = 200;// 坦克的高度为200

	public int oldX, oldY;// 引入中间变量存储坐标
	private int life = 100;// 初始化己方坦克的生命值为100
	BloodBar bb = new BloodBar();// 新建一个血条bb
	TankClient tc;// 指向TankClient类的引用

	private boolean good;// 声明一个布尔型变量
	private boolean live = true;// 初始化表示坦克生命值的布尔型变量live,赋初值true
	private static Random r = new Random();// 建立随机数产生器r
	private int step = r.nextInt(12) + 4;// 设定坦克随机移动的步长为2

	private static Toolkit tk = Toolkit.getDefaultToolkit();
	private static Image[] images = null;
	private static Map<String, Image> imgs = new HashMap<String, Image>();
	// 类被加载至内存时，首先加载图片
	static {
		images = new Image[] {
				tk.getImage(Tank.class.getClassLoader().getResource(
						"imagesTank/坦克图片D.gif")),
				tk.getImage(Tank.class.getClassLoader().getResource(
						"imagesTank/坦克图片L.gif")),
				tk.getImage(Tank.class.getClassLoader().getResource(
						"imagesTank/坦克图片LD.gif")),
				tk.getImage(Tank.class.getClassLoader().getResource(
						"imagesTank/坦克图片LU.gif")),
				tk.getImage(Tank.class.getClassLoader().getResource(
						"imagesTank/坦克图片R.gif")),

				tk.getImage(Tank.class.getClassLoader().getResource(
						"imagesTank/坦克图片RD.gif")),
				tk.getImage(Tank.class.getClassLoader().getResource(
						"imagesTank/坦克图片RU.gif")),
				tk.getImage(Tank.class.getClassLoader().getResource(
						"imagesTank/坦克图片U.gif")) };
		imgs.put("U", images[7]);// 使用HashMap将坦克各方向images与方向对应
		imgs.put("RU", images[6]);// 使用HashMap将坦克各方向images与方向对应
		imgs.put("R", images[4]);
		imgs.put("RD", images[5]);
		imgs.put("D", images[0]);
		imgs.put("LD", images[2]);
		imgs.put("L", images[1]);
		imgs.put("LU", images[3]);

	}

	public Tank(int x, int y, boolean good) {// 定义一个方法实现坦克位置的确定
		this.x = x;
		this.y = y;
		this.oldX = x;
		this.oldY = y;
		this.good = good;
	}

	public void draw(Graphics g) {// 构造draw方法画出或消除坦克
		if (!live) {
			if (!good) {
				tc.tanks.remove(this);// 如果是敌方坦克并且中弹了，就将坦克移除
			}
			return;
		}

		if (good)
			bb.draw(g);
		move();
		switch (ptDir) {// 根据炮筒的方向画出炮筒
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
		this(x, y, good);// 构造方法传递坦克的位置，好坏（己方坦克还是敌方坦克），方向，并且传递TankClient类的引用
		this.tc = tc;
		this.dir = dir;
	}

	void move() {// 定义方法实现坦克的移动
		this.oldX = x;
		this.oldY = y;// 将新的坦克的位置储存到中间变量中
		switch (dir) {// 根据键盘上不同的键值实现坦克位置坐标x,y的变化
		case L:// 向左移动键
			x -= XSPEED;
			break;
		case LU:// 向左上移动键
			x -= XSPEED;
			y -= YSPEED;
			break;
		case U:// 向上移动键
			y -= YSPEED;
			break;
		case RU:// 向右上移动键
			x += XSPEED;
			y -= YSPEED;
			break;
		case R:// 向右移动键
			x += XSPEED;
			break;
		case RD:// 向右下移动键
			x += XSPEED;
			y += YSPEED;
			break;
		case D:// 向下移动键
			y += YSPEED;
			break;
		case LD:// 向左下移动键
			x -= XSPEED;
			y += YSPEED;
			break;
		case STOP:// 无任何键值，默认停止
			break;
		}
		if (this.dir != Direction.STOP)
			this.ptDir = this.dir;// 如果己方坦克方向不是停止（STOP），将炮筒的方向与坦克前进的方向保持一致
		if (x < 0)
			x = 0;
		if (y < 30)
			y = 30;// 坦克运动到边界后将坐标值作调整，横坐标置0，纵坐标调为30
		if (x + Tank.WIDTH > TankClient.GAME_WITH)
			x = TankClient.GAME_WITH - Tank.WIDTH;
		if (y + Tank.HEIGHT > TankClient.GAME_HEIGHT)
			y = TankClient.GAME_HEIGHT - Tank.HEIGHT;// 同样地，若是运动到右边界，也做出相应调整

		if (!good) {
			Direction[] dirs = Direction.values();// 将坦克运动的9个方向值（包括STOP）转化为数组进行处理
			if (step == 0) {
				step = r.nextInt(12) + 2;// ??
				int rn = r.nextInt(dirs.length);
				dir = dirs[rn];

			}
			step--;
			if (r.nextInt(45) > 35)
				this.fire();// 设置随机数，若随机数大于35，则调用fire()方法
		}

	}

	public void KeyPressed(KeyEvent e) {// 定义键值控制方向的方法
		int key = e.getKeyCode();
		switch (key) {
		case KeyEvent.VK_F2:// 按F2键己方坦克重新复活
			if (!this.live) {
				this.live = true;
				this.life = 100;
			}
			break;
		case KeyEvent.VK_CONTROL:// 按动Ctrl键发动攻击
			tc.missiles.add(fire());
			break;
		case KeyEvent.VK_LEFT:// 按动左键bL为true
			bL = true;
			break;
		case KeyEvent.VK_UP:// 按动上键bU为true
			bU = true;
			break;
		case KeyEvent.VK_RIGHT:// 按动右键bR为true
			bR = true;
			break;
		case KeyEvent.VK_DOWN:// 按动下键bD为true
			bD = true;
			break;
		}
		locatDirection();

	}

	void locatDirection() {// 根据bL、bU、bR、bD的与或运算赋予Direction对象dir 9个不同的方向值
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

	public void keyReleased(KeyEvent e) {// 定义按键抬起后的操作：所有方向值设为false

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
		case KeyEvent.VK_A:// 定义按下A键时，调用superfire()方法
			superFire();
			break;
		}
		locatDirection();
	}

	public Missile fire() {// 定义炮弹攻击的方法
		if (!live)// 如果敌方坦克的live值为0，不做任何处理
			return null;
		int x = this.x + Tank.WIDTH / 2;
		int y = this.y + Tank.HEIGHT / 2;// 定义炮弹的初始坐标
		Missile m = new Missile(x, y, good, ptDir, this.tc);// 实例化一个Missile类对象，该对象携带TankClient类的引用tc
		tc.missiles.add(m);// 添加炮弹实例到TankClient对象中
		return m;

	}

	public Rectangle getRect() {
		return new Rectangle(this.x, this.y, this.WIDTH, this.HEIGHT);// 定义处理边界的函数
	}

	public boolean collidesWithWall(Wall w) {// 定义返回值为布尔型变量的collidesWithWall（）函数，用于检测与墙的碰撞
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

	public boolean collidesWithTanks(java.util.List<Tank> tanks) {// 类似地定义一个函数描述坦克之间的碰撞
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

	public void superFire() {// 定义超级攻击的方法
		Direction[] dir = Direction.values();
		for (int i = 0; i < 8; i++) {
			fire(dir[i]);
		}
	}

	public Missile fire(Direction dir) {// 构造方法用于描述炮弹的运动
		if (!live)
			return null;
		int x = this.x + Tank.WIDTH / 2;
		int y = this.y + Tank.HEIGHT / 2;
		Missile m = new Missile(x, y, good, dir, this.tc);//
		tc.missiles.add(m);// 添加炮弹对象到TankClient对象
		return m;

	}

	private class BloodBar {// 定义内部类，描述血块
		public void draw(Graphics g) {// 定义draw方法画出血块
			Color c = g.getColor();
			g.setColor(Color.RED);
			g.drawRect(x, y - 10, WIDTH - 10, 10);// 对血块进行边界处理
			int w = (WIDTH - 10) * life / 100;// 设置血块的宽度
			g.fillRect(x, y - 10, w, 10);
			g.setColor(c);
		}
	}

	public boolean eat(Blood b) {// 定义己方坦克吃血块的方法
		if (this.live && b.isLive() && this.getRect().intersects((b.getRect()))) {
			this.life = 100;// 将己方坦克生命值重置为100，条件：血块live为true，己方坦克live为true，接触到血块边界
			b.setLive(false);// 将血块生命值设为false
			return true;
		}
		return false;
	}
}
