package entities;

import main.*;

import java.awt.*;
import java.util.LinkedList;
import java.util.logging.Logger;

public class Snake extends Entity {
    private Logger logger;
    public int cellWidth;
    public boolean isMoving;
    public Color color;
    private InputManager input;
    private String direction;
    private Boolean isGrowing;
    public final int player;

    public double freq;

    private LinkedList<Point> queue;
    private double updateCounter;
    public boolean collision;

    public Snake(int x, int y,int cellWidth, int player) {
        super(x, y);
        logger = Logger.getLogger(getClass().getName());
        this.cellWidth=cellWidth;
        this.player = player;
    }

    @Override
    public void init(){
        freq =  Application.amountOfTicks/2;
        updateCounter = 0;
        collision = false;
        isGrowing = false;
        queue = new LinkedList<>();
        queue.addFirst(new Point(x,y));
        switch (player){
            case 0:
                queue.addLast(new Point(x-cellWidth,y));
                queue.addLast(new Point(x-2*cellWidth,y));
                color = Color.RED;
                direction = "RIGHT";
                break;
            case 1:
                queue.addLast(new Point(x+cellWidth,y));
                queue.addLast(new Point(x+2*cellWidth,y));
                color = Color.BLUE;
                direction = "LEFT";
                break;
        }
        input = InputManager.getInstance();
        isMoving = true;
    }

    @Override
    public void update() {
        getInput(); //sets direction
        updateCounter++;
        if(updateCounter>freq) {
            updateCounter = 0;
            move();
        }
    }

    @Override
    public void render(Graphics g) {
        g.setColor(color);
        for (Point p: queue) {
            g.fillRect((int)p.getX()+1,(int)p.getY()+1,cellWidth-1,cellWidth-1);
        }

    }

    public void setGrow(Boolean b){
        isGrowing = b;
    }

    private void move() {
        Point p = getHeadPosition();
        Point newP = p;
        if(!isMoving) return ;

        switch(direction){
            case "LEFT":
                newP = new Point((int)p.getX() - cellWidth,(int) p.getY());
                break;
            case "RIGHT":
                newP = new Point((int)p.getX() + cellWidth,(int) p.getY());
                break;
            case "UP":
                newP = new Point((int)p.getX(),(int) p.getY() - cellWidth);
                break;
            case "DOWN":
                newP = new Point((int)p.getX(),(int) p.getY() + cellWidth);
                break;
            default:
        }
        if(snakeCollision(newP)){
            collision = true;
            return;
        }
        if (newP == p) {
            logger.warning("Snake not moving");
            return;
        }
        queue.addFirst(newP);
        if(isGrowing) {
            isGrowing = false;
            return;
        }
        queue.removeLast();
        return;
    }



    private void getInput() {
        if(collision)return;
        if(input.isPressed("LEFT") && !direction.equals("RIGHT")){
            setDirection("LEFT");
        }
        if(input.isPressed("RIGHT") && !direction.equals("LEFT")){
            setDirection("RIGHT");
        }
        if(input.isPressed("UP") && !direction.equals("DOWN")){
            setDirection("UP");
        }
        if(input.isPressed("DOWN") && !direction.equals("UP")){
            setDirection("DOWN");
        }
    }

    private void setDirection(String st) {
        direction = st;
    }


    public Point getHeadPosition(){
        return new Point(queue.getFirst());
    }


    public LinkedList<Point> getQueue() {
        LinkedList<Point> result = (LinkedList<Point>) queue.clone();
        return result;
    }

    public boolean hasSnake(int x, int y){
        for(Point Snake : queue){
            if(Snake.x == x && Snake.y == y){
                return true;
            }
        }
        return false;
    }

    public boolean checkSelfCollision() {
        LinkedList<Point> temp = getQueue();
        temp.removeFirst();
        for(Point Snake : temp){
            if (Snake.x == getHeadPosition().x && Snake.y == getHeadPosition().y){
                return true;
            }
        }
        return false;
    }

    public boolean snakeCollision(Point newP) {
        Canvas canvas = DisplayManager.getInstance().getCanvas();
        if(newP.x >= (canvas.getWidth()) || newP.x < 200 || newP.y >= (canvas.getHeight()) || newP.y < 0) {
            collision = true;
            return true;
        }
        return checkSelfCollision();
    }

    public void collided(Snake snake2) {
        if(snake2.hasSnake(getHeadPosition().x,getHeadPosition().y)) collision = true;
    }
}
