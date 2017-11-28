package states;

import entities.*;
import main.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.logging.Level;

public class OriginalGameState extends State {
    private static double cellWidth = 0.0;
    private Food food;
    private Snake snake;
    private Score score;
    private static final int cellNumber = 20 ;
    Canvas canvas;
    private SoundManager soundManager;
    private int state;

    public OriginalGameState(){
        canvas = DisplayManager.getInstance().getCanvas();
        cellWidth = (canvas.getWidth()-200)/cellNumber;
        if((cellWidth % 1) != 0) {
            logger.severe("BOP");
        }
        snake = new Snake((int)(200+(cellNumber/2)*cellWidth), (int) ((cellNumber/2)*cellWidth),(int)cellWidth);
        addEntity(snake);
        Point location = generatePosition();
        food = new Food(location.x,location.y,(int)cellWidth);
        addEntity(food);
        score = new Score(25,canvas.getHeight()/2);
        addEntity(score);
    }

    @Override
    public void init() {
        super.init();
        canvas.setBackground(new Color(50,50,50));
        logger.log(Level.INFO," Iniciando Mappeo");
        input.addMapping("UP", KeyEvent.VK_UP);
        input.addMapping("DOWN", KeyEvent.VK_DOWN);
        input.addMapping("RIGHT", KeyEvent.VK_RIGHT);
        input.addMapping("LEFT", KeyEvent.VK_LEFT);
        input.addMapping("ESCAPE", KeyEvent.VK_ESCAPE, 1);
        soundManager = SoundManager.getInstance();
        soundManager.add("eat","eat1.wav");
        soundManager.add("lose","lose.wav");
    }

    @Override
    public void render(Graphics g){
        drawGrid(g);
        super.render(g);
    }

    @Override
    public void update(){

        if(input.isFired("ESCAPE")){
            StateManager.getInstance().setState(StateManager.GAME_MENU);
        }
        super.update();
        int snakeX = snake.getHeadPosition().x;
        int snakeY = snake.getHeadPosition().y;
        snakeCollision(snakeX, snakeY);
        snakeFoodCollision(snakeX,snakeY);
    }

    private void drawGrid(Graphics g) {
        g.setColor(new Color(160,160,160));
        g.fillRect(200,0,canvas.getWidth()-200,canvas.getHeight());
        g.setColor(Color.black);

        for (int i = cellNumber-1; i > 0; i--) {
            int y =  (i*(int)cellWidth);
            g.drawLine(200,y,canvas.getWidth(),y);

        }
        for (int i = cellNumber; i > 0; i--) {
            int x =  (i*(int)cellWidth);
            g.drawLine(x+200,0,x+200,canvas.getHeight());
        }
        g.fillRect(196,0,4,canvas.getHeight());
    }

    private void snakeFoodCollision(int x, int y) {
        if(x == food.x && y == food.y){
            soundManager.play("eat");
            snake.setGrow(true);
            Point location = generatePosition();
            while(snake.hasSnake(location.x,location.y)){
                location = generatePosition();
            }
            if(snake.freq > 0){
                snake.freq -= 5;
            }else{
                logger.fine("WINNER");
            }
            ActionManager.getInstance().action(3,null);
            score.setHasCollide(true);
            food.setHasCollide(location);
        }
    }

    private void snakeCollision(int snakeX, int snakeY) {
        if(snakeX >= (canvas.getWidth()) || snakeX < 200 || snakeY >= (canvas.getHeight()) || snakeY < 0) {
            gameOver();
        }
        if(snake.checkSelfCollision())gameOver();
    }

    public static Point generatePosition(){
        int xaux = ((int) (Math.random() * cellNumber));
        int yaux = ((int) (Math.random() * cellNumber));
        return new Point(xaux*(int)cellWidth+200,yaux*(int)cellWidth);
    }

    private void gameOver() {
        soundManager.play("lose");
        JOptionPane.showMessageDialog(DisplayManager.getInstance(), "YOU LOSE");
        score.saveScore();
        init();
        StateManager.getInstance().setState(StateManager.GAME_MENU);
    }

}
