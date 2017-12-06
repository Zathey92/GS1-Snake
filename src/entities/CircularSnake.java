package entities;

import main.Application;
import main.InputManager;

import javax.swing.text.Segment;
import java.awt.*;
import java.awt.geom.Point2D;
import java.util.*;
import java.util.List;

public class CircularSnake extends Entity{

    public int radius;
    private double angle;
    private double speed,turningSpeed;
    public double freq;
    private double updateCounter;
    private double[][] segments;
    private LinkedList<double[]> path;
    public double[] head,eyePosition;
    public int player;
    public boolean collision;

    public CircularSnake(int x,int y, int radius, int player) {
        super(x-radius, y+radius);

        this.player = player;
        this.speed = 2.0;
        this.turningSpeed = .2;
        this.radius = radius;

    }

    @Override
    public void init(){
        this.angle = 0;
        collision=false;
        freq =  Application.amountOfTicks/3;
        path = new LinkedList<>();
        head = new double[]{x,y};
        eyePosition = new double[] {x,y};
        double[] body = new double[]{x-10,y};
        double[] tail = new double[]{x-20,y};
        segments=new double[][]{head,body,tail};
        updateCounter = 0;
    }

    @Override
    public void update() {
        updateCounter++;
        if(updateCounter>freq&&!collision){
            getUserInput();
            updateCounter = 0;
            double delta = Application.delta;
            head[0] +=speed*Math.cos(angle)*2;
            head[1] -=speed*Math.sin(angle)*2;
            segments[0]=head;
            path.addFirst(head.clone());
            calcEyePosition();
            followHead();
        }

    }

    private void getUserInput() {
        InputManager input = InputManager.getInstance();
        if(input.isPressed("LEFT"+player)){
            angle+=turningSpeed;
            if(angle>=Math.PI*2) angle = 0;
        }
        if(input.isPressed("RIGHT"+player)){
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
        drawEye(g);
        //g.setColor(Color.white);
        //g.fillOval((int)head[0],(int)head[1],5,5);
    }

    private void calcEyePosition(){
        double[] pos = new double[2];
        pos[0] = 5*Math.cos(angle);
        pos[1] = 5*Math.sin(angle);
        eyePosition[0] =pos[0] + head[0];
        eyePosition[1] =head[1] - pos[1];
    }

    private void drawEye(Graphics g){
        int eyeradius = radius-4;
        g.setColor(Color.yellow);
        g.fillOval((int)eyePosition[0]-eyeradius/2,(int)eyePosition[1]-eyeradius/2,eyeradius,eyeradius);
    }

    public double getDistance(double[] pointFrom, double[] pointTo ){
        double disX,disY;
        disX=pointTo[0]-pointFrom[0];
        disY=pointTo[1]-pointFrom[1];
        return Math.sqrt((Math.pow(disX,2.0) + Math.pow(disY,2.0)));
    }

    public void addSegment(){
        double[] segment = segments[segments.length-1];
        segments  = Arrays.copyOf(segments, segments.length + 1);
        segments[segments.length - 1] = segment;
      }
}
