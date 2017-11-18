package entities;

import main.InputManager;

import java.awt.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Snake extends Entity {
    private Logger logger;
    public int width;
    public int height;
    public boolean turn;
    public Color color;
    private InputManager input;
    private int movement;

    public Snake(int x, int y,int width,int height) {
        super(x, y);
        logger = Logger.getLogger(getClass().getName());

        this.height=height;
        this.width=width;
    }

    @Override
    public void init(){
        turn = false;
        color = Color.BLUE;
        input = InputManager.getInstance();
        movement = 0;
        logger.log(Level.WARNING, String.valueOf(movement));
    }

    @Override
    public void update() {

        if(input.isPressed("UP")){
            movement = 1;
        }
        if(input.isPressed("DOWN")){
            movement = 3;
        }
        if(input.isPressed("RIGHT")){
            movement = 0;
        }
        if(input.isPressed("LEFT")){
            movement = 2;
            logger.log(Level.WARNING, String.valueOf(movement));
        }
        switch (movement){
            case 0:
                x++;
                break;
            case 1:
                y--;
                break;
            case 2:
                x--;
                break;
            case 3:
                y++;
                break;

            default:
                logger.log(Level.WARNING, "Direccion erronea");
        }

    }

    @Override
    public void render(Graphics g) {
        g.setColor(color);
        g.fillRect(x,y,width,height);
    }
}
