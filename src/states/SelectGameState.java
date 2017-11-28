package states;

import entities.StyledButton;
import main.ActionManager;
import main.DisplayManager;
import main.StateManager;

import java.awt.*;

public class SelectGameState extends MenuState {

    @Override
    public void init() {
        Canvas canvas = DisplayManager.getInstance().getCanvas();
        buttons.add(new StyledButton((canvas.getWidth()/2),(canvas.getHeight()/2 - 100),105, 56, ActionManager.CHANGE_STATE, new int[]{StateManager.ORIGINALGAME}, "", "S_boton1.png", "boton1.png"));
        buttons.add(new StyledButton((canvas.getWidth()/2),(canvas.getHeight()/2 ),105, 56, ActionManager.CHANGE_STATE, new int[]{StateManager.ORIGINALGAME}, "","S_boton2.png","boton2.png"));
        buttons.add(new StyledButton((canvas.getWidth()/2),(canvas.getHeight()/2 + 100),105, 56, ActionManager.CHANGE_STATE, new int[]{StateManager.ORIGINALGAME}, "", "S_boton3.png", "boton3.png"));

        this.entities.addAll(buttons);
        currentButton = 0;
        super.init();

    }

    @Override
    public void update() {
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
