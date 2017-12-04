package states;

import entities.CircularSnake;
import main.*;

import java.awt.*;

import java.awt.event.KeyEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MultiplayerGameState extends State {
    private Logger logger;



    public MultiplayerGameState(){
        logger= Logger.getLogger(getClass().getName());
        entities.add(new CircularSnake(50,100,25));
    }

    @Override
    public void init() {
        logger.log(Level.INFO," Iniciando Multijugador");
        super.init();
    }

    @Override
    public void render(Graphics g){
        super.render(g);
    }

    @Override
    public void update(){
        if(enter){
            initMapping();
            enter=false;
        }
        if(input.isFired("ESCAPE")){
            logger.log(Level.INFO," Saliendo al Menu");
            StateManager.getInstance().lastState = StateManager.GAME_MENU;
        }
        super.update();
    }
    @Override
    public void initMapping() {
        input.addMapping("LEFT", KeyEvent.VK_LEFT,1);
        input.addMapping("RIGHT", KeyEvent.VK_RIGHT,1);
    }

}
