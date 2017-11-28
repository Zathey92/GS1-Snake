package states;

import entities.Button;
import main.ActionManager;
import main.DisplayManager;
import main.StateManager;

import java.awt.event.KeyEvent;

public class GameMenuState extends MenuState {


    @Override
    public void init() {
        buttons.add(new Button((DisplayManager.getInstance().getCanvas().getWidth()/2)-50,(DisplayManager.getInstance().getCanvas().getHeight()/4),108, 63, ActionManager.CHANGE_STATE, new int[]{StateManager.SELECTGAME}, "Iniciar Juego"));
        buttons.add(new Button((DisplayManager.getInstance().getCanvas().getWidth()/2)-50,(DisplayManager.getInstance().getCanvas().getHeight()/4+100),108, 63, ActionManager.LEADERBOARD, new int[]{StateManager.LEADERBOARD}, "Puntuaciones"));
        buttons.add(new Button((DisplayManager.getInstance().getCanvas().getWidth()/2)-50,(DisplayManager.getInstance().getCanvas().getHeight()/4)+200,108, 63, ActionManager.EXIT, "Exit"));
        this.entities.addAll(buttons);
        currentButton = 0;

        super.init();
        input.addMapping("ESCAPE2", KeyEvent.VK_ESCAPE,1);
    }

    @Override
    public void update(){
        if(input.isFired("ESCAPE2")){
            if(StateManager.lastState != -1){
                StateManager.getInstance().setState(StateManager.lastState);
            }else{
                logger.warning("El estado a volver no existe");
            }
        }
        super.update();
    }

}
