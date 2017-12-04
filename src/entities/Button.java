package entities;

import main.ActionManager;
import java.awt.*;

public class Button extends Message {
    protected int action;
    protected int[] param;
    protected boolean selected;


    public Button(int x, int y, int width, int height, int action, int[] param, String text, Font font) {
        super(x, y, width, height, text);
        this.action = action;
        this.param = param;
    }
    public Button(int x, int y, int width, int height, int action, int[] param, String text) {
        super(x, y, width, height, text);
        this.action = action;
        this.param = param;
    }

    public Button(int x, int y, int width, int height, int action, String text) {
        super(x, y, width, height, text);
        this.action = action;
        this.param = new int[]{};
    }



    @Override
    public void init() {
        super.init();
        selected = false;
    }

    @Override
    public void update() {
        super.update();
    }

    @Override
    public void render(Graphics g) {
        rectColor = new Color(160,160,160);
        if(selected){
           rectColor = new Color(200,200,200);
        }
        super.render(g);
    }

    public void setSelected(boolean isSelected) {
        this.selected = isSelected;
    }

    public void action(){
        ActionManager.getInstance().action(action, param);
    }



}
