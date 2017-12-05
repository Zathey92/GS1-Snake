package entities;

import main.Application;
import main.InputManager;

import javax.swing.text.Segment;
import java.awt.*;
import java.awt.geom.Point2D;
import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;
import java.util.Vector;

public class CircularSnake extends Entity{

    private int radius;
    private double angle;
    private double speed,turningSpeed;
    private double freq;
    private double updateCounter;
    private double[][] segments;
    private LinkedList<double[]> path;
    private double[] head;

    public CircularSnake(int x,int y, int radius) {
        super(x, y);

        this.speed = 2.0;
        this.turningSpeed = .2;
        this.radius = radius;
        this.angle = 0;
    }

    @Override
    public void init(){
        freq =  Application.amountOfTicks/5;
        path = new LinkedList<>();
        head = new double[]{x,y};
        double[] body = new double[]{x-10,y};
        double[] tail = new double[]{x-20,y};
        segments=new double[][]{head,body,tail};
        updateCounter = 0;
    }

    @Override
    public void update() {
        updateCounter++;
        if(updateCounter>freq){
            getUserInput();
            updateCounter = 0;
            double delta = Application.delta;
            head[0] +=speed*Math.cos(angle)*2;
            head[1] -=speed*Math.sin(angle)*2;
            path.addFirst(head.clone());
            followHead();
        }

    }

    private void getUserInput() {
        InputManager input = InputManager.getInstance();
        if(input.isPressed("LEFT")){
            angle+=turningSpeed;
            if(angle>=Math.PI*2) angle = 0;
        }
        if(input.isPressed("RIGHT")){
            angle-=turningSpeed;
            if(angle<0) angle = Math.PI*2;
        }

    }

    private void followHead() {
        if(segments.length==0) return;
        if(path.size()<3) return;
        double[] pathNodeStart = path.getFirst();
        double[] pathNodeEnd = path.get(1);
        int j=2;
        for(int i=1; i<segments.length;i++) {
            double dist = 0;
            while(j<path.size()){
                dist += getDistance(pathNodeEnd,pathNodeStart);
                if(dist>=radius*2-8){
                    segments[i] = pathNodeEnd;
                    break;
                }
                pathNodeStart=pathNodeEnd.clone();
                pathNodeEnd = path.get(j).clone();
                j++;
            }
        }

    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.pink);
        g.fillOval((int)(head[0]-radius),(int)(head[1]-radius),radius*2,radius*2);
        for(double[] point: segments ){
            g.fillOval((int)point[0]-radius,(int)point[1]-radius,radius*2,radius*2);
        }

    }

    public double getDistance(double[] pointFrom, double[] pointTo ){
        double disX,disY;
        disX=pointTo[0]-pointFrom[0];
        disY=pointTo[1]-pointFrom[1];
        return Math.sqrt((Math.pow(disX,2.0) + Math.pow(disY,2.0)));
    }
}
