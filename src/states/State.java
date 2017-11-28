package states;


import entities.Entity;
import main.InputManager;
import main.StateManager;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public abstract class State {

    protected List<Entity> entities= new ArrayList<Entity>();
    protected Logger logger;
    protected InputManager input;
    public boolean enter;

    public void addEntity(Entity e){
        this.entities.add(e);
    }

    public void update(){
        if(input.isFired("ESCAPE2")){
            if(StateManager.lastState != -1){
                StateManager.getInstance().setState(StateManager.lastState);
            }else{
                logger.warning("El estado a volver no existe");
            }
        }
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
        enter = false;
        logger = java.util.logging.Logger.getLogger(getClass().getName());
        input = InputManager.getInstance();
        for(Entity entity: entities){
            entity.init();
        }
    }


}
