import java.awt.*;
public class Blood {
	public boolean isLive() {
		return live;
	}
	public void setLive(boolean live) {
		this.live = live;
	}


	int x,y,w,h;
	TankClient tc;
	private boolean live=true;
	int step=0;
	//·ÅÖÃÑª¿é
	private int[][] pos={{100,290},{150,310},{590,368},{765,234},{678,213},{123,564},{324,657},{987,789},{787},{432,456},{654,456}};
	public Blood(){
		x=pos[0][0];
		y=pos[0][1];
		w=h=30;
	}
	public void draw(Graphics g){
		if(!live)return;
		
		Color c=g.getColor();
		g.setColor(Color.MAGENTA);
		g.fillRect(x, y, w, h);
		g.setColor(c);
		move();
		
	}
	private void move() {
		step++;
		if(step==pos.length){
			step=0;
		}
		x=pos[step][0];
		y=pos[step][1];
		
	}
	
	
	public Rectangle getRect(){
		return new Rectangle(x,y,w,h);
	}

}
