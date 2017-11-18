package states;

import entities.Button;
import main.ActionManager;
import main.DisplayManager;
import main.StateManager;

import java.awt.event.KeyEvent;

public class GameMenuState extends MenuState {
    @Override
    public void init() {
        //botenes de un jugador, multi y sandbox
        buttons.add(new Button((DisplayManager.getInstance().getCanvas().getWidth()/2)-50,(DisplayManager.getInstance().getCanvas().getHeight()/4),100, 76, ActionManager.CHANGE_STATE, new int[]{StateManager.ORIGINALGAME}, "Iniciar Juego"));
        buttons.add(new Button((DisplayManager.getInstance().getCanvas().getWidth()/2)-50,(DisplayManager.getInstance().getCanvas().getHeight()/4+100),100, 76, ActionManager.LEADERBOARD, new int[]{StateManager.LEADERBOARD}, "Puntuaciones"));
        buttons.add(new Button((DisplayManager.getInstance().getCanvas().getWidth()/2)-50,(DisplayManager.getInstance().getCanvas().getHeight()/4)+200,100, 75, ActionManager.EXIT, "Exit"));
        this.entities.addAll(buttons);
        currentButton = 0;
        super.init();
    }


}
