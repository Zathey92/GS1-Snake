package states;

import entities.Entity;
import entities.Food;
import entities.Snake;
import main.DisplayManager;
import main.InputManager;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OriginalGameState extends State {

    private Logger logger;

    public OriginalGameState(){
        logger = Logger.getLogger(getClass().getName());
        addEntity(new Snake(0,0,50,50));
        addEntity(new Food(((int)(Math.random()* DisplayManager.getInstance().getHeight() - 25)),((int)(Math.random()*DisplayManager.getInstance().getWidth() - 25))));

    }

    @Override
    public void init() {
        logger.log(Level.INFO," Iniciando Mappeo");
        InputManager input = InputManager.getInstance();
        input.addMapping("UP", KeyEvent.VK_UP);
        input.addMapping("DOWN", KeyEvent.VK_DOWN);
        input.addMapping("LEFT", KeyEvent.VK_LEFT);
        input.addMapping("RIGHT", KeyEvent.VK_RIGHT);
        super.init();
    }
}
