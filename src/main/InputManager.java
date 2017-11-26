package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

public class InputManager implements KeyListener {
    private static InputManager instance = null;
    public List<Key> keys = new ArrayList<>();


    public void addMapping(String s, int keyCode, int limit){
        keys.add(new Key(s,keyCode,limit));
    }

    public void addMapping(String s, int keyCode){
        keys.add(new Key(s,keyCode));
    }

    public boolean isPressed(String s){
        for(Key key: keys){
            if(s.equals(key.name)){
                return key.pressed;
            }
        }
        return false;
    }

    public boolean isFired(String s) {
        boolean aux;
        for(Key key: keys){
            if(s.equals(key.name)){
                aux = key.fired;
                key.fired = false;
                return aux;
            }
        }
        return false;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        for(Key key: keys){
            if(e.getKeyCode()==key.keyCode){
                key.toggle(true);
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        for(Key key: keys){
            if(e.getKeyCode()==key.keyCode){
                key.toggle(false);
            }
        }

    }
    public static InputManager getInstance() {
        if(instance == null) {
            instance = new InputManager();
        }
        return instance;
    }
}
