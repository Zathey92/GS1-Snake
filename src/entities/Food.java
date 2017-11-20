package entities;

import main.DisplayManager;

import java.awt.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Food extends Entity {
    private Logger logger;
    private int width;
    private int height;
    private Color color;

    public Food(int x, int y){
        super(x,y);
        logger = Logger.getLogger(getClass().getName());

        height = 25;
        width = 25;
    }

    @Override
    public void init(){
        color = Color.GREEN;
        logger.log(Level.INFO, String.valueOf(x) + " " + String.valueOf(y));
    }

    @Override
    public void update(){
        if(false){
            x = ((int)(Math.random()* DisplayManager.getInstance().getHeight() - 25));
            y = ((int)(Math.random()* DisplayManager.getInstance().getWidth() - 25));
        }
    }

    @Override
    public void render(Graphics g) {
        g.setColor(color);
        g.fillRect(x,y,width,height);
    }
}
