package states;

import entities.*;
import main.*;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.logging.Level;

public class OriginalGameState extends State {
    private static double cellWidth = 0.0;
    private Food food;
    private Snake snake;
    private Score score;
    private Input inputMessage;
    private static final int cellNumber = 20 ;
    Canvas canvas;
    private SoundManager soundManager;
    private int state;
    private Message winMessage,loseMessage,nameMessage,infoMessage1,infoMessage2;



    public OriginalGameState(){
        canvas = DisplayManager.getInstance().getCanvas();
        cellWidth = (canvas.getWidth()-200)/cellNumber;
        int middle = (int)((cellNumber/2)*cellWidth);
        if((cellWidth % 1) != 0) {
            logger.severe("BOP");
        }
        snake = new Snake(middle+200, middle,(int)cellWidth,0);
        Point location = generatePosition();
        food = new Food(location.x,location.y,(int)cellWidth);
        score = new Score(100,canvas.getHeight()/2, 150,20, ALIGN.CENTER);
        infoMessage1 = new Message( 100, middle - 100,150,25,"ESC: Pausa/Reanudar");
        infoMessage2 = new Message( 100, middle - 75,150,25,"Flechas: Moverse");
        winMessage = new Message(middle+200,middle,200,110,"Has ganado!", false);
        loseMessage = new Message(middle+200,middle,200,110 ,"Has perdido...", false);
        nameMessage = new Message(middle+100,middle+8,85,25 ,"Nombre:", false , new Color (200,200,200), Color.BLACK, ALIGN.LEFT, ALIGN.LEFT);
        inputMessage = new Input(middle+225,middle+20, 115,25);
        addEntity(snake);
        addEntity(food);
        addEntity(score);
        addEntity(infoMessage1);
        addEntity(infoMessage2);
        addEntity(winMessage);
        addEntity(loseMessage);
        addEntity(nameMessage);
        addEntity(inputMessage);

    }

    @Override
    public void init() {
        super.init();
        canvas.setBackground(new Color(50,50,50));
        logger.log(Level.INFO," Iniciando OriginalGameState");
        soundManager = SoundManager.getInstance();
        soundManager.add("eat","eat1.wav");
        soundManager.add("lose","lose.wav");
        soundManager.add("win","win.wav");
        state = 0;
    }

    @Override
    public void render(Graphics g){
        drawGrid(g);
        super.render(g);
    }

    @Override
    public void update(){
        if(enter){
            initMapping();
            enter=false;
        }
        if(input.isFired("ESCAPE")){
            StateManager.getInstance().lastState = StateManager.GAME_MENU;
            escape();
        }
        switch (state){
            case 0:
                play();
                break;
            case 1:
                showMessage(loseMessage);
                break;
            case 2:
                showMessage(winMessage);
                break;
            default:
                logger.warning("El estado " + state + "no existe");
        }
        super.update();

    }

    private void showMessage(Message message){
        nameMessage.isVisible = true;
        message.isVisible = true;
        inputMessage.isVisible = true;
        if(input.isPressed("ENTER" )){
            message.isVisible = false;
            inputMessage.isVisible = false;
            nameMessage.isVisible = false;
            if(inputMessage.text == null){
                FileManager.getInstance().saveScore("Annonymous" + "\t" + score.value);
            }else{
                FileManager.getInstance().saveScore(inputMessage.text.trim() + "\t" + score.value);
            }
            init();
            state = 0;
        }
    }

    private void play(){
        int snakeX = snake.getHeadPosition().x;
        int snakeY = snake.getHeadPosition().y;
        if(snake.collision){
            input.clearBuffer();
            soundManager.play("lose");
            state = 1;
        }
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
                input.clearBuffer();
                soundManager.play("win");
                state = 2;
            }
            ActionManager.getInstance().action(3,null);
            score.refreshScore();
            food.setHasCollide(location);
        }
    }

    public static Point generatePosition(){
        int xaux = ((int) (Math.random() * cellNumber));
        int yaux = ((int) (Math.random() * cellNumber));
        return new Point(xaux*(int)cellWidth+200,yaux*(int)cellWidth);
    }

    @Override
    public void initMapping(){
        input.addMapping("UP0", KeyEvent.VK_UP);
        input.addMapping("DOWN0", KeyEvent.VK_DOWN);
        input.addMapping("RIGHT0", KeyEvent.VK_RIGHT);
        input.addMapping("LEFT0", KeyEvent.VK_LEFT);
        input.addMapping("ENTER", KeyEvent.VK_ENTER);
        super.initMapping();
    }
}
