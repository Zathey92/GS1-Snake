package entities;

import main.DisplayManager;

import javax.swing.*;
import java.awt.*;

public class Score extends Entity {
    private int score;
    private static boolean first;
    private boolean hasCollide;


    public Score(){
        super(0,0);
        score = 0;
        first = true;
    }

    @Override
    public void init() {

    }

    @Override
    public void render(Graphics g) {
        if(first){
            first = false;
        }
    }

    @Override
    public void update() {
        if(hasCollide){
            score++;
            hasCollide = false;
        }
    }

    public static void reset(){first = true;}

    public void setHasCollide(boolean b) {hasCollide = b;}
}
