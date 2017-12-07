package entities;

import java.awt.*;

public class Bullet extends Entity{
    public double owner,x1,y1,x2,y2;
    public double angle;
    public int size = 5;
    public boolean shooted;
    private int updateCounter;

    public Bullet(int x, int y) {
        super(x, y);
        this.shooted=false;
        this.angle=0;
        this.updateCounter=0;
    }

    @Override
    public void update() {
        if(shooted){
            double dx=Math.cos(angle)*4;
            double dy=Math.sin(angle)*4;
            this.x1 +=dx;
            this.y1-=dy;
            this.x2+=dx;
            this.y2-=dy;
        }
    }

    @Override
    public void render(Graphics g) {
        if(shooted) {
            g.setColor(Color.WHITE);
            g.drawLine((int)x1, (int)y1, (int)x2, (int)y2);
        }
    }

    public void shoot(int playerOwner,double angle,int x,int y){
        this.owner = playerOwner;
        this.x1 = x;
        this.y1 = y;
        this.angle=angle;
        updateFarEnd();
        shooted=true;
    }

    public void updateFarEnd(){
        this.x2 = x1+(int) (Math.cos(angle)*8);
        this.y2 = y1-(int) (Math.sin(angle)*8);
        System.out.println(angle);
    }
}
