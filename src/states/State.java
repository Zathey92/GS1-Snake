package states;


import entities.Entity;
import main.DisplayManager;
import main.InputManager;
import main.StateManager;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public abstract class State {

    protected List<Entity> entities= new ArrayList<Entity>();
    protected Logger logger;
    protected InputManager input;
    Canvas canvas;
    public boolean enter;

    public void addEntity(Entity e){
        this.entities.add(e);
    }

    public void update(){

        for(Entity entity: entities){
            entity.update();
        }
    }

    public void render(Graphics g){
        for(Entity entity: entities){
            entity.render(g);
        }
    }
    public void init(){
        canvas = DisplayManager.getInstance().getCanvas();
        enter = true;
        logger = java.util.logging.Logger.getLogger(getClass().getName());
        input = InputManager.getInstance();
        for(Entity entity: entities){
            entity.init();
        }
    }
    public void initMapping() {
        input.addMapping("ESCAPE", KeyEvent.VK_ESCAPE,1);
    }

    public void checkEscape(){
        if(input.isFired("ESCAPE")){
            escape();
        }
    }
    public void escape(){
        if(StateManager.lastState != -1){
            StateManager.getInstance().setState(StateManager.lastState);
        }else{
            logger.warning("El estado a volver no existe");
        }
    }
}
