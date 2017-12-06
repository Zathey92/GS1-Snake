package entities;

import java.awt.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CircularFood extends Entity {
    private Logger logger;
    private Color color;
    private int radius;
    private boolean hasCollide;



    private Point nextPosition;

    public CircularFood(int x, int y,int radius){
        super(x,y);
        logger = Logger.getLogger(getClass().getName());
        this.radius = radius;
    }

    @Override
    public void init(){
        hasCollide=false;
        color = Color.GREEN;
        logger.log(Level.INFO, String.valueOf(x) + " " + String.valueOf(y));
    }

    @Override
    public void update(){
        if(hasCollide) {
            Point p = nextPosition;
            x = p.x; y=p.y;
            hasCollide = false;
        }
    }
    @Override
    public void render(Graphics g) {
        g.setColor(Color.green);
        g.fillOval((int)(x-radius),(int)(y-radius),radius*2,radius*2);
    }

    public void setHasCollide(Point nextPosition){
        hasCollide = true;
        this.nextPosition = nextPosition;
    }


}
