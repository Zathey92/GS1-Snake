package entities;

import java.awt.*;
import java.util.ArrayList;

public class LeaderBoard extends Entity{
    private int width, height;
    private ArrayList<String[]> text;
    private Font font;
    private int textWidth;

    public LeaderBoard(int x, int y, int width, int height, ArrayList<String[]> text) {
        super(x, y);
        this.width = width;
        this.height = height;
        this.text = text;
        this.font = new Font("TimesRoman", Font.PLAIN, 18);
    }

    @Override
    public void update() { }

    void drawString(Graphics g, ArrayList<String[]> text, int x, int y) {
        FontMetrics metrics = g.getFontMetrics(this.font);
        for(String[] line : text){
            int tempY = y += g.getFontMetrics().getHeight();
            textWidth = metrics.stringWidth(line[0]);
            g.drawString(line[0], x+(this.width-textWidth)/3 , tempY);
            textWidth = metrics.stringWidth(line[1]);
            g.drawString(line[1], x+(this.width-textWidth)*2/3, tempY);
            textWidth = metrics.stringWidth(line[2]);
            g.drawString(line[2], x+(this.width-textWidth), tempY);
        }
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.GRAY);
        g.fillRect((x+width)/3,y,width,height);
        g.setColor(Color.WHITE);
        g.setFont(font);

        drawString(g, text, 105, 20);

    }
}