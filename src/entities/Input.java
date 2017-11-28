package entities;

import main.InputManager;

import java.awt.*;

public class Input extends Message {
    private InputManager input;

    public Input(int x, int y, int width, int height) {
        super(x, y, width, height, "", false, new Color (240,240,240), Color.black);
        input = InputManager.getInstance();
    }

    @Override
    public void update(){
        super.update();
        text = input.inputText;
    }

}
