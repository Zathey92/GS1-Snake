package states;

import entities.Entity;
import entities.Food;
import entities.Snake;
import main.DisplayManager;
import main.InputManager;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OriginalGameState extends State {
    //private CollisionManager some;
    private Logger logger;
    private Snake temp;
    private Food temp1;
    private int temp2;
    private int temp3;

    public OriginalGameState(){
        temp2 = DisplayManager.getInstance().getCanvas().getHeight();
        temp3 = DisplayManager.getInstance().getCanvas().getWidth()-48;
        temp = new Snake(0,0,50,50);
        temp1 = new Food(((int)(Math.random()* temp2)),((int)(Math.random()*temp3)));
        logger = Logger.getLogger(getClass().getName());
        addEntity(temp);
        addEntity(temp1);
        //some = new CollisionManager();
    }

    @Override
    public void init() {
        logger.log(Level.INFO," Iniciando Mappeo");
        InputManager input = InputManager.getInstance();
        input.addMapping("UP", KeyEvent.VK_UP);
        input.addMapping("DOWN", KeyEvent.VK_DOWN);
        input.addMapping("LEFT", KeyEvent.VK_LEFT);
        input.addMapping("RIGHT", KeyEvent.VK_RIGHT);
        super.init();
        //some.init();
    }

    @Override
    public void update(){
        super.update();
        //some.update();
        Rectangle sr = new Rectangle(temp.x, temp.y, 50, 50);
        Rectangle fr = new Rectangle(temp1.x, temp1.y, 25, 25);
        if (sr.intersects(fr)) {


            System.out.println(temp2 + "    " + temp3);
            int temp4 = (int)(Math.random()* temp2);
            int temp5 = (int)(Math.random()* temp3);
            if(temp4 < 0){
                temp4 = 0;
            }else if(temp4 > temp2){
                temp4 = temp2;
            }
            if (temp3 < 0){
                temp5 = 0;
            }else if(temp5 > temp3){
                temp5 = temp3;
            }
            System.out.println(temp4 + "     " + temp5);
            temp1.x =  temp4;
            temp1.y = temp5;
        }
    }
}
