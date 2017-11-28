package entities;

import java.awt.*;
import java.util.ArrayList;

public class LeaderBoard extends Entity{
    private int width, height;
    private ArrayList<String[]> text;
    private Font font;

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
        for(String[] line : text){
            int tempY = y += g.getFontMetrics().getHeight();
            g.drawString(line[0], x , tempY);
            g.drawString(line[1], x + 100, tempY);
            g.drawString(line[2], x + 200, tempY);
        }
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.GRAY);
        g.fillRect(x,y,width,height);
        g.setColor(Color.WHITE);
        g.setFont(font);

        drawString(g, text, 105, 20);

    }
}
