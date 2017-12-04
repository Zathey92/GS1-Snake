package states;

import entities.CircularSnake;
import main.*;

import java.awt.*;

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
        super.init();
        logger.log(Level.INFO," Iniciando Mappeo");

    }

    @Override
    public void render(Graphics g){
        super.render(g);
    }

    @Override
    public void update(){
        if(input.isFired("ESCAPE")){
            StateManager.getInstance().lastState = StateManager.GAME_MENU;
        }
        super.update();
    }

}
