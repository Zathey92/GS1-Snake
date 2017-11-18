package entities;

import entities.Entity;
import main.ActionManager;
import main.DisplayManager;
import main.InputManager;
import states.MenuState;

import java.awt.*;

public class Button extends Entity {
    private int width, heigth;
    private int textHeight,textWidth;
    private int acent , decent;
    private int action;
    private int[] param;
    private String text;
    private Font font;
    private boolean hasBorder;


    public Button(int x, int y, int width, int heigth, int action, int[] param, String text, Font font) {
        super(x, y);
        this.width = width;
        this.heigth = heigth;
        this.text = text;
        this.font = font;
        this.action = action;
        this.param = param;
    }
    public Button(int x, int y, int width, int heigth, int action, int[] param, String text) {
        super(x, y);
        this.width = width;
        this.heigth = heigth;
        this.action = action;
        this.param = param;
        this.text = text;
        this.font = new Font("TimesRoman", Font.PLAIN, 12);
    }

    public Button(int x, int y, int width, int heigth, int action, String text) {
        super(x, y);
        this.width = width;
        this.heigth = heigth;
        this.text = text;
        this.font = new Font("TimesRoman", Font.PLAIN, 12);
        this.action = action;
        this.param = new int[]{};
    }

    @Override
    public void update() {

    }

    @Override
    public void init() {
        Graphics g = DisplayManager.getInstance().getCanvas().getGraphics();
        FontMetrics metrics = g.getFontMetrics(font);
        hasBorder = false;
        acent = metrics.getAscent();
        decent = metrics.getDescent();
        textWidth = metrics.stringWidth(text);
        textHeight = metrics.getHeight();
    }

    @Override
    public void render(Graphics g) {
        if(hasBorder){
            g.setColor(Color.black);
            g.fillRect(x-3,y-3,width+6,heigth+6);
        }
        g.setColor(Color.BLUE);
        g.fillRect(x,y,width,heigth);
        g.setColor(Color.WHITE);
        g.setFont(font);
        g.drawString(text,x+(width-textWidth)/2,y+(heigth-textHeight)/2 + acent);
    }

    public void setBorder(boolean hasBorder) {
        this.hasBorder = hasBorder;
    }

    public void action(){
        ActionManager.getInstance().action(action, param);
    }

}
