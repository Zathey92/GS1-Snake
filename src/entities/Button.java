package entities;

import main.ActionManager;
import main.DisplayManager;

import java.awt.*;

public class Button extends Entity {
    protected int width, heigth;
    protected int textHeight,textWidth;
    protected int acent , decent;
    protected int action;
    protected int[] param;
    protected String text;
    protected Font font;
    protected boolean selected;


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
        selected = false;
        acent = metrics.getAscent();
        decent = metrics.getDescent();
        textWidth = metrics.stringWidth(text);
        textHeight = metrics.getHeight();
    }

    @Override
    public void render(Graphics g) {
        g.setColor(new Color(160,160,160));
        if(selected){
            g.setColor(new Color(200,200,200));
            g.fillRect(x,y,width,heigth);
        }
        g.fillRect(x,y,width,heigth);
        g.setColor(Color.BLACK);
        g.setFont(font);
        g.drawString(text,x+(width-textWidth)/2,y+(heigth-textHeight)/2 + acent);
    }

    public void setSelected(boolean isSelected) {
        this.selected = isSelected;
    }

    public void action(){
        ActionManager.getInstance().action(action, param);
    }

}
