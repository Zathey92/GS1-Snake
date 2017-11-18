package states;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import entities.Button;
import entities.Entity;
import main.ActionManager;
import main.DisplayManager;
import main.InputManager;
import main.StateManager;
import static java.lang.Thread.sleep;

public abstract class MenuState extends State {
    int currentButton;
    protected List<Button> buttons = new ArrayList<Button>();
    private int count;

    @Override
    public void init() {
        InputManager.getInstance().addMapping("ENTER", KeyEvent.VK_ENTER);
        InputManager.getInstance().addMapping("UP", KeyEvent.VK_UP);
        InputManager.getInstance().addMapping("DOWN", KeyEvent.VK_DOWN);
        super.init();
    }

    @Override
    public void update(){
        if(InputManager.getInstance().isPressed("ENTER")){
            this.buttons.get(currentButton).action();
        }
        if(InputManager.getInstance().isPressed("UP")){
            countKey(false);
        }
        if(InputManager.getInstance().isPressed("DOWN")){
            countKey(true);
        }
        super.update();
    }

    private void previousButton() {
        if(currentButton > 0){
            currentButton--;
        }else{
            currentButton = buttons.size() - 1;
        }
    }

    private void countKey(boolean direction){
        count++;
        if(count > 50){
            count = 0;
            System.out.println(currentButton);
            this.buttons.get(currentButton).setBorder(false);
            if (direction){
                nextButton();
            }else{
                previousButton();
            }
            this.buttons.get(currentButton).setBorder(true);
        }
    }

    private void nextButton() {
        if(currentButton < buttons.size() - 1){
            currentButton++;
        }else{
            currentButton = 0;
        }
    }
}
