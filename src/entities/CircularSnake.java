package entities;

import main.Application;
import main.InputManager;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;

public class CircularSnake extends Entity{

    private int radius;
    private Point head;
    private List<Point> partsList;
    private LinkedList<Point> path;
    private int angle;
    private int speed;
    private double freq;
    private int updateCounter;

    public CircularSnake(int x,int y, int radius){
        super(x,y);
        this.speed = 4;
        this.radius = radius;
        this.angle = 0;
        partsList = new ArrayList<>();
        path = new LinkedList<>();
        head =new Point(x,y);
    }

    @Override
    public void init(){
        freq =  Application.amountOfTicks*3;
        updateCounter = 0;
    }

    @Override
    public void update() {
        updateCounter++;
        if(updateCounter>freq) {
            updateCounter = 0;
            getUserInput();
            path.addFirst(new Point(head.x,head.y));
            head.x +=speed*Math.cos(angle);
            head.y -=speed*Math.sin(angle);
            System.out.println(head.x+" "+head.y);
            followHead();
        }
    }

    private void getUserInput() {
        InputManager input = InputManager.getInstance();
        if(input.isPressed("LEFT")){
            angle++;
        }
        if(input.isPressed("RIGHT")){
            angle--;
        }

    }

    private void followHead() {
        if(partsList.size()==0) return;
        int dist=0,j=0;
        for(int i=0; i<partsList.size();i++){
            Point part = partsList.get(i);
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
        g.fillOval(head.x-radius,head.y-radius,radius*2,radius*2);
        for(Point point: partsList ){
            g.fillOval(point.x-radius,point.y-radius,radius*2,radius*2);
        }

    }

    public int getDistance(Point pointFrom, Point pointTo ){
        int disX,disY;
        disX=pointTo.x-pointFrom.x;
        disY=pointTo.y-pointFrom.y;
        return (int)Math.sqrt((disX^2 + disY^2));
    }
}
