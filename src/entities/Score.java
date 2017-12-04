package entities;

import main.ALIGN;

import java.awt.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Score extends Message {
    public int value;

    public Score(int x, int y, int width, int height){
        super(x,y,width,height, "Score: 0");
    }

    public Score(int x, int y, int width, int height, ALIGN align){
        super(x,y,width,height, "Score: 0", align);
    }

    @Override
    public void init() {
        value = 0;
        setText("Score: " + value);
        super.init();
    }

    @Override
    public void render(Graphics g) {
        super.render(g);
    }

    @Override
    public void update() {
        super.update();

    }


    public void refreshScore(){
        value++;
        setText("Score: " + value);
    }
}
