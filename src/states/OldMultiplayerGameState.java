package states;

import entities.*;
import main.*;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.logging.Level;

public class OldMultiplayerGameState extends State {
    private static double cellWidth = 0.0;
    private Food food;
    private Snake snake,snake2;
    private Score score,score2;
    private Input inputMessage;
    private static final int cellNumber = 20 ;
    Canvas canvas;
    private SoundManager soundManager;
    private int state;
    private Message winMessage,drawMessage,nameMessage;

    public OldMultiplayerGameState(){
        canvas = DisplayManager.getInstance().getCanvas();
        cellWidth = (canvas.getWidth()-200)/cellNumber;
        int middle = (int)((cellNumber/2)*cellWidth);
        if((cellWidth % 1) != 0) {
            logger.severe("BOP");
        }
        snake = new Snake( 200 + (int)cellWidth * 3, (int)cellWidth * 2,(int)cellWidth, 0);
        snake2 = new Snake(200 + (int)cellWidth * 15, (int)cellWidth * 18,(int)cellWidth, 1);
        Point location = generatePosition();
        food = new Food(location.x,location.y,(int)cellWidth);
        location = generatePosition();
        score = new Score(100,middle, 150,20, ALIGN.CENTER);
        score2 = new Score(100,middle + 60, 150,20, ALIGN.CENTER);
        Message infoMessage1 = new Message( 100, middle - 125,150,25,"ESC: Pausa/Reanudar");
        Message infoMessage2 = new Message( 100, middle - 100,150,25,"Flechas: Moverse jugador 1");
        Message infoMessage3 = new Message( 100, middle - 75,150,25,"AWSD: Moverse jugador 2");
        Message player1 = new Message( 100, middle - 20,150,20,"Jugador 1:");
        Message player2 = new Message( 100, middle + 40,150,20,"jugador 2:");
        winMessage = new Message(middle+200,middle,200,110 ,"Rojo gano!", false);
        drawMessage = new Message(middle+200,middle,200,110 ,"Empate 0.o", false);
        nameMessage = new Message(middle+100,middle+8,85,25 ,"Nombre:", false , new Color (200,200,200), Color.BLACK, ALIGN.LEFT, ALIGN.LEFT);
        inputMessage = new Input(middle+225,middle+20, 115,25);
        addEntity(snake);
        addEntity(snake2);
        addEntity(food);
        addEntity(score);
        addEntity(score2);
        addEntity(infoMessage1);
        addEntity(infoMessage2);
        addEntity(infoMessage3);
        addEntity(player1);
        addEntity(player2);
        addEntity(winMessage);
        addEntity(drawMessage);
        addEntity(nameMessage);
        addEntity(inputMessage);
    }

    @Override
    public void init() {
        super.init();
        canvas.setBackground(new Color(50,50,50));
        logger.log(Level.INFO," Iniciando OldMultiplayerGameState");
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
                showMessage(winMessage);
                break;
            case 2:
                showMessage(drawMessage);
                break;
            default:
                logger.warning("El estado " + state + "no existe");
        }
        super.update();
    }

    private void showMessage(Message message){
        snake.isMoving = false;
        snake2.isMoving = false;
        if(snake.collision && state == 1){
            message.setText("Azul gano!");
        }
        message.isVisible = true;
        if(state == 1){
            nameMessage.isVisible = true;
            inputMessage.isVisible = true;
        }
        if(input.isPressed("ENTER" )){
            message.isVisible = false;
            inputMessage.isVisible = false;
            nameMessage.isVisible = false;
            if(inputMessage.text == null){
                if(snake.collision && state == 1){
                    FileManager.getInstance().saveScore("Annonymous" + "\t" + score2.value);
                }else{
                    FileManager.getInstance().saveScore("Annonymous" + "\t" + score.value);
                }
            }else{
                if(snake.collision && state == 1){
                    FileManager.getInstance().saveScore(inputMessage.text.trim() + "\t" + score2.value);
                }else{
                    FileManager.getInstance().saveScore(inputMessage.text.trim() + "\t" + score.value);
                }
            }
            init();
            state = 0;
        }
    }

    private void play(){
        int snakeX = snake.getHeadPosition().x;
        int snakeY = snake.getHeadPosition().y;
        int snakeX2 = snake2.getHeadPosition().x;
        int snakeY2 = snake2.getHeadPosition().y;
        snake.collided(snake2);
        snake2.collided(snake);
        if (snake.collision && snake2.collision){
            input.clearBuffer();
            soundManager.play("lose");
            state = 2;
        }else if(snake.collision || snake2.collision){
            input.clearBuffer();
            soundManager.play("win");
            state = 1;
        }
        snakeFoodCollision(snakeX,snakeY,snakeX2,snakeY2);
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

    private void snakeFoodCollision(int x, int y, int x2, int y2) {
        if(x == food.x && y == food.y){
            eat(snake, snake2);
        }else if(x2 == food.x && y2 == food.y){
            eat(snake2, snake);
        }
    }

    private void eat(Snake snake, Snake snake2){
        soundManager.play("eat");
        snake.setGrow(true);
        Point location = generatePosition();
        while(snake.hasSnake(location.x,location.y) && snake2.hasSnake(location.x,location.y)){
            location = generatePosition();
        }
        snake.freq -= 5;
        ActionManager.getInstance().action(3,null);
        if(snake.player == 0){
            score.refreshScore();
        }else{
            score2.refreshScore();
        }
        food.setHasCollide(location);
    }

    public static Point generatePosition() {
        int xaux = ((int) (Math.random() * cellNumber));
        int yaux = ((int) (Math.random() * cellNumber));
        return new Point(xaux * (int) cellWidth + 200, yaux * (int) cellWidth);
    }

    @Override
    public void initMapping() {
        input.addMapping("UP0", KeyEvent.VK_UP);
        input.addMapping("DOWN0", KeyEvent.VK_DOWN);
        input.addMapping("RIGHT0", KeyEvent.VK_RIGHT);
        input.addMapping("LEFT0", KeyEvent.VK_LEFT);
        input.addMapping("UP1", KeyEvent.VK_W);
        input.addMapping("DOWN1", KeyEvent.VK_S);
        input.addMapping("RIGHT1", KeyEvent.VK_D);
        input.addMapping("LEFT1", KeyEvent.VK_A);
        input.addMapping("ENTER", KeyEvent.VK_ENTER);
        super.initMapping();
    }
}