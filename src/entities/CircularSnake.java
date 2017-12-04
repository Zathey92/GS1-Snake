package entities;

import main.Application;
import main.InputManager;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;

public class CircularSnake extends Entity{

    private int radius;
    private double[] head;
    private List<double[]> partsList;
    private LinkedList<double[]> path;
    private double angle;
    private double speed,turningSpeed;
    private double freq;
    private double updateCounter;

    public CircularSnake(int x,int y, int radius){
        super(x,y);
        this.speed = 2.0;
        this.turningSpeed=.2;
        this.radius = radius;
        this.angle = 0;
        partsList = new ArrayList<>();
        path = new LinkedList<>();
        head = new double[]{(double) x, (double) y};
    }

    @Override
    public void init(){
        freq =  Application.amountOfTicks;
        updateCounter = 0;
    }

    @Override
    public void update() {
        updateCounter++;
        if(updateCounter>freq) updateCounter = 0;

        if(updateCounter%freq/3==0){
            System.out.println("pepe");
            updateCounter = 0;
            getUserInput();
        }
        if(updateCounter%(freq/4)==0){
            System.out.println("pepa");
            path.addFirst(head);
            head[0] +=speed*Math.cos(angle);
            head[1] +=speed*Math.sin(angle);
            followHead();
        }

    }

    private void getUserInput() {
        InputManager input = InputManager.getInstance();
        if(input.isPressed("LEFT")){
            angle-=turningSpeed;
            if(angle<=0) angle = Math.PI*2;
        }else if(input.isPressed("RIGHT")){
            angle+=turningSpeed;
            if(angle>=Math.PI*2) angle = 0;
        }

    }

    private void followHead() {
        if(partsList.size()==0) return;
        double dist;
        int j=0;
        for(int i=0; i<partsList.size();i++){
            double[] part = partsList.get(i);
            dist = getDistance(part,path.get(j));
            while(dist<(radius*2+speed)){
                j++;
                if(j>path.size()) return;
                dist = getDistance(part,path.get(j));
            }
            partsList.add(i,path.get(j-1));
        }
        while(path.size()>j){
            path.removeLast();
        }

    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.pink);
        g.fillOval((int)(head[0]-radius),(int)(head[1]-radius),radius*2,radius*2);
        for(double[] point: partsList ){
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
