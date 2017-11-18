package states;

import entities.Entity;
import entities.Snake;
import main.InputManager;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OriginalGameState extends State {

    private Logger logger;

    public OriginalGameState(){
        logger = Logger.getLogger(getClass().getName());
        addEntity(new Snake(0,0,150,150));
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
