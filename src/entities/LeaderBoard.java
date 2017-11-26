package entities;

import java.awt.*;

public class LeaderBoard extends Entity{
    private int width, height;
    private String text;
    private Font font;

    public LeaderBoard(int x, int y, int width, int height, String text) {
        super(x, y);
        this.width = width;
        this.height = height;
        this.text = text;
        this.font = new Font("TimesRoman", Font.PLAIN, 12);
    }

    @Override
    public void update() {

    }
    public void init(){

    }
    void drawString(Graphics g, String text, int x, int y) {
        for (String line : text.split("\n"))
            g.drawString(line, x, y += g.getFontMetrics().getHeight());
    }
    @Override
    public void render(Graphics g) {
        g.setColor(Color.GRAY);
        g.fillRect(x,y,width,height);
        g.setColor(Color.WHITE);
        g.setFont(font);

        drawString(g, text, 50, 50);

    }
}
