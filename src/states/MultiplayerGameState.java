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
    public static Message winMessage;
    private Logger logger;
    private CircularFood food;
    private CircularSnake player1,player2;
    public static int state;
    private SoundManager soundManager;
    public static List<Bullet> bulletsPool = new ArrayList<Bullet>();
    private static List<Bullet> activeBullets = new ArrayList<Bullet>();


    public MultiplayerGameState(){
        soundManager = SoundManager.getInstance();
        logger= Logger.getLogger(getClass().getName());
        Point pos = generatePosition();
        food = new CircularFood(pos.x,pos.y,5);
        entities.add(food);
        player1= new CircularSnake(50,100,25,0);
        entities.add(player1);
        player2= new CircularSnake(50,300,25,1);
        entities.add(player2);
        int middle = DisplayManager.getInstance().getCanvas().getWidth()/2;
        winMessage = new Message(middle,middle-180,250,60,"Player X ha ganado!", false);
        entities.add(winMessage);

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
                checkWallCollision(player1);
                checkWallCollision(player2);
                checkFoodCollision(player1);
                checkFoodCollision(player2);
                checkBulletCollision();
                checkSnakesCollision();
                break;
            case 1:
                player1.collision=true;
                player2.collision=true;
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
    private static Point generatePosition(){
        Canvas canvas = DisplayManager.getInstance().getCanvas();
        int xaux = ((int) (Math.random() *(canvas.getWidth()-50)));
        int yaux = ((int) (Math.random() *(canvas.getHeight()-50)));
        return new Point(25+xaux,25+yaux);
    }

    private void checkFoodCollision(CircularSnake player){
        double dx = food.x- player.head[0];
        double dy = food.y- player.head[1];
        double[] foodPos = new double[]{food.x,food.y};

        if( player.getDistance(player.head,foodPos)<player.radius){
            soundManager.play("eat");
            Point location = generatePosition();
            player.score+=5;
            if(player.score>=20){
                player.score-=20;
                player.addSegment();
                soundManager.play("win");
                if(player.freq > 0){
                    player.freq -= 5;
                }
            }
            ActionManager.getInstance().action(3,null);
            food.setHasCollide(location);

        }

    }
    private void checkWallCollision(CircularSnake player){
        double px =player.head[0];
        double py =player.head[1];
        if (px+player.radius>canvas.getWidth()|| px<player.radius || py <player.radius || py+player.radius>canvas.getHeight()){
            winMessage.text= "Player "+Integer.toString(player.player+1)+" ha ganado!";
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
        activeBullets.add(bullet);

    }

    private void checkSnakesCollision() {
        if(player1.checkSnakeSegmentCollision(player2.head)) win(player1.player);
        if(player2.checkSnakeSegmentCollision(player1.head)) win(player2.player);
    }

    private void checkBulletCollision(){
        for(int i =0; i<activeBullets.size();i++){
            Bullet bullet = activeBullets.get(i);
            double[] bulletPos = new double[]{bullet.x2,bullet.y2};
            double dist=0;

            if(bullet.x2>canvas.getWidth()||bullet.x2<0||bullet.y2>canvas.getHeight()||bullet.y2<0){
                activeBullets.remove(bullet);
                bulletsPool.add(bullet);
            }else{
                if(bullet.owner==0){
                    dist = player2.getDistance(player2.head,bulletPos);
                    if(dist<player2.radius) {
                        player2.score -=10;
                        activeBullets.remove(bullet);
                        bulletsPool.add(bullet);
                    }

                }else{
                    dist = player1.getDistance(player1.head,bulletPos);
                    if(dist<player1.radius){
                        player1.score -=10;
                        activeBullets.remove(bullet);
                        bulletsPool.add(bullet);
                    }
                }
            }
        }
    }

    public static void win(int player){
        winMessage.text= "Player "+Integer.toString(player+1)+" ha ganado!";
        state=1;
    }

}
