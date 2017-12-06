package states;

import entities.*;
import main.*;
import java.awt.*;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MultiplayerGameState extends State {
    private Message winMessage, loseMessage, nameMessage;
    private Logger logger;
    private CircularFood food;
    private CircularSnake player1,player2;
    private int state;
    private SoundManager soundManager;
    public static List<Bullet> bulletsPool = new ArrayList<Bullet>();
    public static List<Bullet> activeBullets = new ArrayList<Bullet>();


    public MultiplayerGameState(){
        soundManager = SoundManager.getInstance();
        logger= Logger.getLogger(getClass().getName());
        entities.add(new Score(25,25,50,50));
        Point pos = generatePosition();
        food = new CircularFood(pos.x,pos.y,5);
        entities.add(food);
        player1= new CircularSnake(50,100,25,0);
        entities.add(player1);
        int middle = DisplayManager.getInstance().getCanvas().getWidth()/2;
        winMessage = new Message(middle+200,middle,200,110,"Has ganado!", false);
        loseMessage = new Message(middle+200,middle,200,110 ,"Has perdido...", false);
        entities.add(winMessage);
        entities.add(loseMessage);
    }

    @Override
    public void init() {
        state=0;
        logger.log(Level.INFO," Iniciando Multijugador");

        super.init();
    }

    @Override
    public void render(Graphics g){
        super.render(g);
        for(Bullet bullet: activeBullets){
            bullet.render(g);
        }
    }

    @Override
    public void update(){
        if(enter){
            initMapping();
            enter=false;
        }
        if(input.isFired("ESCAPE")){
            StateManager.lastState = StateManager.GAME_MENU;
            escape();
        }
        switch (state){
            case 0:
                checkWallCollision();
                checkFoodCollision();
                break;
            case 1:
                player1.collision=true;
                showMessage(winMessage);
                break;
            default:
                logger.warning("El estado " + state + "no existe");
        }
        super.update();
        for(Bullet bullet: activeBullets){
            bullet.update();
        }

    }
    @Override
    public void initMapping() {
        input.addMapping("LEFT0", KeyEvent.VK_LEFT,1);
        input.addMapping("RIGHT0", KeyEvent.VK_RIGHT,1);
        input.addMapping("SHOOT0", KeyEvent.VK_UP,1);
        input.addMapping("LEFT1", KeyEvent.VK_A,1);
        input.addMapping("RIGHT1", KeyEvent.VK_D,1);
        input.addMapping("SHOOT1", KeyEvent.VK_W,1);
        input.addMapping("ENTER", KeyEvent.VK_ENTER,1);

        super.initMapping();
    }
    public static Point generatePosition(){
        Canvas canvas = DisplayManager.getInstance().getCanvas();
        int xaux = ((int) (Math.random() *canvas.getWidth()));
        int yaux = ((int) (Math.random() *canvas.getHeight()));
        return new Point(xaux,yaux);
    }

    private void checkFoodCollision(){
        double dx = food.x- player1.head[0];
        double dy = food.y- player1.head[1];
        double[] foodPos = new double[]{food.x,food.y};

        if( player1.getDistance(player1.head,foodPos)<player1.radius){
            soundManager.play("eat");
            Point location = generatePosition();
            player1.addSegment();
            if(player1.freq > 0){
                player1.freq -= 5;
            }else{
                input.clearBuffer();
                soundManager.play("win");
                state = 2;
            }
            ActionManager.getInstance().action(3,null);
            //score.refreshScore();
            food.setHasCollide(location);

        }

    }
    private void checkWallCollision(){
        double px =player1.head[0];
        double py =player1.head[1];
        if (px+player1.radius>canvas.getWidth()|| px<player1.radius || py <player1.radius || py+player1.radius>canvas.getHeight()){
            System.out.println("Colisión");
            state=1;
        }
    }

    private void showMessage(Message message){
        message.isVisible = true;
        if(input.isPressed("ENTER" )){
            message.isVisible = false;
            init();
            //food.setHasCollide(generatePosition()); No necesario en multiplayer
            state = 0;
        }
    }
    public static void addBullet(Bullet bullet){
        System.out.println("add");
        activeBullets.add(bullet);

    }

}
