package states;

import entities.Entity;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public abstract class State {

    protected List<Entity> entities= new ArrayList<>();

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
        for(Entity entity: entities){
            entity.init();
        }
    }


}
