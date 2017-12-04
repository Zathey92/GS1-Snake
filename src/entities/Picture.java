package entities;

import main.DisplayManager;

import java.awt.*;

public class Picture extends Entity {
    protected Image picture;
    protected int width, height;
    protected String text;

    public Picture(int x, int y,int width,int height, String text) {
        super(x, y-height/2);
        this.height = height;
        this.width = width;
        this.text = text;
    }
    @Override
    public void init(){
        super.init();
        this.picture = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/menu/" + text));
    }

    @Override
    public void update() { }

    @Override
    public void render(Graphics g) {
        g.drawImage(picture,x,y,width,height, DisplayManager.getInstance().getRootPane());
    }
}
