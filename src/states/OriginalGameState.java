package states;

import entities.Entity;
import entities.Food;
import entities.Snake;
import main.DisplayManager;
import main.InputManager;
import main.StateManager;

import javax.swing.*;
import javax.xml.stream.Location;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OriginalGameState extends State {

    private static double cellWidth = 0.0;
    private static Point location;
    private Food food;
    private Snake snake;
    private Logger logger;
    private static final int cellNumber = 20 ;
    Canvas canvas;

    public OriginalGameState(){
        logger = Logger.getLogger(getClass().getName());
        canvas = DisplayManager.getInstance().getCanvas();
        cellWidth = canvas.getWidth()/cellNumber;

        if((cellWidth % 1) != 0){
            logger.severe("BOP");
        }

        logger = Logger.getLogger(getClass().getName());
        snake = new Snake(0,0,(int)cellWidth);
        addEntity(snake);
        generatePosition();
        food = new Food(location.x,location.y,(int)cellWidth);
        addEntity(food);
    }

    @Override
    public void init() {
        canvas.setBackground(new Color(225,225,225));
        logger.log(Level.INFO," Iniciando Mappeo");
        InputManager input = InputManager.getInstance();
        input.addMapping("UP", KeyEvent.VK_UP);
        input.addMapping("DOWN", KeyEvent.VK_DOWN);
        input.addMapping("RIGHT", KeyEvent.VK_RIGHT);
        input.addMapping("LEFT", KeyEvent.VK_LEFT);
        super.init();
    }
    @Override
    public void render(Graphics g){
        drawGrid(g);
        super.render(g);

    }

    @Override
    public void update(){
        int snakeX = snake.getHeadPosition().x;
        int snakeY = snake.getHeadPosition().y;
        snakeCollision(snakeX, snakeY);
        snakeFoodCollision(snakeX,snakeY);
        super.update();
    }

    private void drawGrid(Graphics g) {
        for (int i = cellNumber-1; i > 0; i--) {
            int y =  (i*(int)cellWidth);
            g.drawLine(0,y,canvas.getWidth(),y);

        }
        for (int i = cellNumber-1; i > 0; i--) {
            int x =  (i*(int)cellWidth);
            g.drawLine(x,0,x,canvas.getHeight());
        }
    }

    public static Point getLocation(){return location;}

    private void snakeFoodCollision(int x, int y) {
        if(x == food.x && y == food.y){
            snake.setGrow(true);
            generatePosition();
            foodCollision(x, y);
            food.setHasCollide(true);
        }
    }

    private void snakeCollision(int x, int y) {
        if(x > DisplayManager.getInstance().getWidth() || x < 0
            || y > DisplayManager.getInstance().getHeight() || y < 0) {
            gameOver();
        }
        if(snake.checkSelfCollision())gameOver();
    }

    public void foodCollision(int x, int y){
        LinkedList<Point> temp = snake.getQueue();
        for(Point Snake : temp){
            if(Snake.x == location.x && Snake.y == location.y){
                generatePosition();
                foodCollision(x,y);
            }
        }
    }

    public static void generatePosition(){
        int xaux = ((int) (Math.random() * cellNumber));
        int yaux = ((int) (Math.random() * cellNumber));
        location = new Point(xaux*(int)cellWidth,yaux*(int)cellWidth);
    }

    private void gameOver() {
        JOptionPane.showMessageDialog(DisplayManager.getInstance(), "YOU LOSE");
        snake.resetSnake();
        StateManager.getInstance().setState(StateManager.GAME_MENU);
    }

}
