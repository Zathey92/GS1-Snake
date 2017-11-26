package states;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import entities.Button;
import main.InputManager;

public abstract class MenuState extends State {
    int currentButton;
    protected List<Button> buttons = new ArrayList<>();
    protected int KEYFRECUENCY = 1;
    protected InputManager input;

    @Override
    public void init() {
        input = InputManager.getInstance();
        input.addMapping("ENTER", KeyEvent.VK_ENTER, KEYFRECUENCY);
        input.addMapping("UP", KeyEvent.VK_UP, KEYFRECUENCY);
        input.addMapping("DOWN", KeyEvent.VK_DOWN, KEYFRECUENCY);
        super.init();
    }

    @Override
    public void update(){
        if(input.isFired("ENTER")){
            this.buttons.get(currentButton).action();
        }
        if(input.isFired("UP")){
            this.buttons.get(currentButton).setSelected(false);
            previousButton();
            this.buttons.get(currentButton).setSelected(true);
        }
        if(input.isFired("DOWN")){
            this.buttons.get(currentButton).setSelected(false);
            nextButton();
            this.buttons.get(currentButton).setSelected(true);
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

    private void nextButton() {
        if(currentButton < buttons.size() - 1){
            currentButton++;
        }else{
            currentButton = 0;
        }
    }
}
