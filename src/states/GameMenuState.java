package states;

import entities.Button;
import main.ActionManager;
import main.InputManager;
import main.StateManager;

import java.awt.event.KeyEvent;

public class GameMenuState extends MenuState {
    @Override
    public void init() {
        buttons.add(new Button(0,0,100, 75, ActionManager.CHANGE_STATE, new int[]{StateManager.ORIGINALGAME}, "Iniciar Juego"));
        buttons.add(new Button(200,0,100, 75, ActionManager.EXIT, "Exit"));
        for (Button button: buttons) {
            this.entities.add(button);
        }
        InputManager.getInstance().addMapping("ENTER", KeyEvent.VK_ENTER);
        currentButton = 0;
        super.init();
    }


}
