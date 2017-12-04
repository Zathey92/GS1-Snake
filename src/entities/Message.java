package entities;

import main.ALIGN;
import main.DisplayManager;


import java.awt.*;


public class Message extends Entity{
    protected int width, height;
    protected int textHeight,textWidth;
    protected int acent , decent;
    public String text;
    protected Font font;
    public Color rectColor;
    public boolean isVisible;
    public Color textColor;
    private ALIGN align;
    protected ALIGN textAling;

    public Message(int x, int y, int width, int height, String text) {
        super(x,y);
        rectColor = new Color(200,200,200);
        textColor = Color.black;
        align = ALIGN.CENTER;
        this.width = width;
        this.height = height;
        this.text = text;
        this.font = new Font("TimesRoman", Font.PLAIN, 12);
        this.isVisible = true;
        this.textAling = ALIGN.CENTER;
        drawText();
        setLayout();
    }

    public Message(int x, int y, int width, int height, String text, ALIGN align) {
        super(x,y);
        rectColor = new Color(200,200,200);
        textColor = Color.black;
        this.align = align;
        this.width = width;
        this.height = height;
        this.text = text;
        this.font = new Font("TimesRoman", Font.PLAIN, 12);
        this.isVisible = true;
        this.textAling = ALIGN.CENTER;
        drawText();
        setLayout();
    }

    public Message(int x, int y, int width, int height, String text, Boolean isVisible) {
        super(x,y);
        rectColor = new Color(200,200,200);
        textColor = Color.black;
        align = ALIGN.CENTER;
        this.width = width;
        this.height = height;
        this.text = text;
        this.font = new Font("TimesRoman", Font.PLAIN, 24);
        this.isVisible = isVisible;
        this.textAling = ALIGN.TOP;
        drawText();
        setLayout();
    }



    public Message(int x, int y, int width, int height, String text, Boolean isVisible, Color rectColor, Color textColor) {
        super(x,y);
        this.rectColor = rectColor;
        this.textColor = textColor;
        align = ALIGN.CENTER;
        this.width = width;
        this.height = height;
        this.text = text;
        this.font = new Font("TimesRoman", Font.PLAIN, 12);
        this.isVisible = isVisible;
        this.textAling = ALIGN.LEFT;
        drawText();
        setLayout();

    }

    public Message(int x, int y, int width, int height, String text, Boolean isVisible, Color rectColor, Color textColor, ALIGN align) {
        super(x, y);
        this.align = align;
        this.rectColor = rectColor;
        this.textColor = textColor;
        this.width = width;
        this.height = height;
        this.text = text;
        this.font = new Font("TimesRoman", Font.PLAIN, 12);
        this.isVisible = isVisible;
        this.textAling = ALIGN.CENTER;
        drawText();
        setLayout();
    }

    public Message(int x, int y, int width, int height, String text, Boolean isVisible, Color rectColor, Color textColor, ALIGN align, ALIGN textAling) {
        super(x, y);
        this.align = align;
        this.rectColor = rectColor;
        this.textColor = textColor;
        this.width = width;
        this.height = height;
        this.text = text;
        this.font = new Font("TimesRoman", Font.PLAIN, 12);
        this.isVisible = isVisible;
        this.textAling = textAling;
        drawText();
        setLayout();
    }

    @Override
    public void update() {}

    public void drawText(){
        Graphics g = DisplayManager.getInstance().getCanvas().getGraphics();
        FontMetrics metrics = g.getFontMetrics(font);
        acent = metrics.getAscent();
        decent = metrics.getDescent();
        textWidth = metrics.stringWidth(text);
        textHeight = metrics.getHeight();
    }

    public void setLayout(){

        switch (align){
            case MIDDLE:
                this.x -= width/2;
            case CENTER:
                this.x -= width/2;
                this.y -= height /2;
                break;
            case RIGHT:
                this.x += width - textWidth;
                break;

        }
    }

    @Override
    public void render(Graphics g) {
        if(!isVisible) return;
        g.setColor(rectColor);
        g.fillRect(x,y,width, height);
        g.setColor(textColor);
        g.setFont(font);
        switch (textAling){
            case LEFT:
                g.drawString(text,x+10,y+(height -textHeight)/2 + acent);
                break;
            case TOP:
                g.drawString(text,x+(width-textWidth)/2,y + textHeight);
                break;
            default:
                g.drawString(text,x+(width-textWidth)/2,y+(height -textHeight)/2 + acent);
                break;
        }

    }

    public void setText(String text){
        this.text = text;
        drawText();
    }
}
