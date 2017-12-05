package states;

import entities.Picture;
import entities.StyledButton;
import main.ActionManager;
import main.DisplayManager;
import main.StateManager;

import java.awt.*;

public class SelectGameState extends MenuState {

    private Picture picture;

    @Override
    public void init() {
        Canvas canvas = DisplayManager.getInstance().getCanvas();
        picture = new Picture(25,canvas.getHeight()/2,400,150 , "logo.png");
        buttons.add(new StyledButton((canvas.getWidth()/2)+150,(canvas.getHeight()/2 - 150),210, 104, ActionManager.CHANGE_STATE, new int[]{StateManager.ORIGINALGAME}, "", "S_boton1.png", "boton1.png"));
        buttons.add(new StyledButton((canvas.getWidth()/2)+150,(canvas.getHeight()/2 ),210, 104, ActionManager.CHANGE_STATE, new int[]{StateManager.MULTIPLAYERGAME}, "","S_boton2.png","boton2.png"));
        buttons.add(new StyledButton((canvas.getWidth()/2)+150,(canvas.getHeight()/2 + 150),210, 104, ActionManager.CHANGE_STATE, new int[]{StateManager.OLDMULTIPLAYERGAME}, "", "S_boton3.png", "boton3.png"));
        this.entities.add(picture);
        this.entities.addAll(buttons);
        currentButton = 0;
        super.init();

    }

    @Override
    public void update() {
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
