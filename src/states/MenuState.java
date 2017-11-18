package states;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import entities.Button;
import entities.Entity;
import main.ActionManager;
import main.InputManager;
import main.StateManager;
import static java.lang.Thread.sleep;

public abstract class MenuState extends State {
    int currentButton;
    protected List<Button> buttons = new ArrayList<Button>();

    @Override
    public void update(){
        if(InputManager.getInstance().isPressed("ENTER")){
            this.buttons.get(currentButton).action();
        }
        if(InputManager.getInstance().isPressed("UP")){
            this.buttons.get(currentButton).action();
        }
        if(InputManager.getInstance().isPressed("DOWN")){
            this.buttons.get(currentButton).action();
        }
        super.update();
    }
}
