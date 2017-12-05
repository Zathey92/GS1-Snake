package states;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import entities.Button;
import main.DisplayManager;
import main.InputManager;
import main.SoundManager;

public abstract class MenuState extends State {

    int currentButton;
    protected List<Button> buttons = new ArrayList<>();
    protected int KEYFRECUENCY = 1;

    protected SoundManager soundManager;

    @Override
    public void init() {
        DisplayManager.getInstance().getCanvas().setBackground(new Color(50,50,50));
        super.init();
        soundManager = SoundManager.getInstance();
        soundManager.add("menuSelect","select.wav");
        this.buttons.get(currentButton).setSelected(true);

    }

    @Override
    public void update(){
        if(enter){
            initMapping();
            enter=false;
        }
        checkEscape();
        if(input.isFired("ENTER")){
            this.buttons.get(currentButton).action();
        }
        if(input.isFired("UP")){
            System.out.println("going up");
            this.buttons.get(currentButton).setSelected(false);
            soundManager.play("menuSelect");
            previousButton();
            this.buttons.get(currentButton).setSelected(true);
        }
        if(input.isFired("DOWN")){
            this.buttons.get(currentButton).setSelected(false);
            soundManager.play("menuSelect");
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

    @Override
    public void initMapping(){
        input.addMapping("ENTER", KeyEvent.VK_ENTER, KEYFRECUENCY);
        input.addMapping("UP", KeyEvent.VK_UP, KEYFRECUENCY);
        input.addMapping("DOWN", KeyEvent.VK_DOWN, KEYFRECUENCY);
        super.initMapping();
    }
}
