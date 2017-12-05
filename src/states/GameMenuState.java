package states;

import entities.Button;
import entities.Picture;
import main.ActionManager;
import main.DisplayManager;
import main.StateManager;
import java.awt.*;
import java.awt.event.KeyEvent;


public class GameMenuState extends MenuState {
    private Picture picture;

    @Override
    public void init() {
        Canvas canvas = DisplayManager.getInstance().getCanvas();
        picture = new Picture(25,canvas.getHeight()/2,400,150 , "logo.png");
        buttons.add(new Button((canvas.getWidth()/2)+150,(canvas.getHeight()/2-150),210, 104, ActionManager.CHANGE_STATE, new int[]{StateManager.SELECTGAME}, "Iniciar Juego"));
        buttons.add(new Button((canvas.getWidth()/2)+150,(canvas.getHeight()/2),210, 104, ActionManager.LEADERBOARD, new int[]{StateManager.LEADERBOARD}, "Puntuaciones"));
        buttons.add(new Button((canvas.getWidth()/2)+150,(canvas.getHeight()/2+150),210, 104, ActionManager.EXIT, "Exit"));
        this.entities.addAll(buttons);
        currentButton = 0;
        this.entities.add(picture);
        super.init();
    }

    @Override
    public void update(){
        if(input.isFired("ESCAPE")){
            if(StateManager.lastState != -1){
                StateManager.getInstance().setState(StateManager.lastState);
            }else{
                logger.warning("El estado a volver no existe");
            }
        }
        super.update();
    }

}
