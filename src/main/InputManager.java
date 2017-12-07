package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

public class InputManager implements KeyListener {
    private static InputManager instance = null;
    public List<Key> keys = new ArrayList<>();
    public String inputText = "";

    public void clearKeyMappings(){
        keys = new ArrayList<>();
        System.out.println("clear Mappings");
    }

    public void clearBuffer(){
        inputText = "";
    }

    public void addMapping(String s, int keyCode, int limit){
        for(int i = 0; i<keys.size();i++){
            if(s.equals(keys.get(i).name)){
                keys.add(new Key(s,keyCode,limit));
                System.out.println("Adding Mapping 1 2 "+s);
                return;
            }
        }
        keys.add(new Key(s,keyCode,limit));
        System.out.println("Adding Mapping 1 1 "+s);
    }

    public void addMapping(String s, int keyCode){
        for(int i = 0; i<keys.size();i++){
            if(s.equals(keys.get(i).name)){
                keys.add(new Key(s,keyCode));
                System.out.println("Adding Mapping 2 2 "+s);
                return;
            }
        }
        keys.add(new Key(s,keyCode));
        System.out.println("Adding Mapping 2 1 "+s);
    }

    public boolean isPressed(String s){
        for(Key key: keys){
            if(s.equals(key.name)){
                if(key.pressed)System.out.println(s+" pressed");
                return key.pressed;
            }
        }
        return false;
    }


    public boolean isFired(String s) {
        boolean aux;
        for(Key key: keys){
            if(s.equals(key.name)){
                if(key.fired)System.out.println(s+" fired");
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
        if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE && !inputText.isEmpty()){
            inputText = inputText.substring(0,inputText.length()-1);
        }
        if(inputText.length() < 10 && Character.isLetterOrDigit(e.getKeyChar())){
            inputText += e.getKeyChar();
        }
        for(Key key: keys){
            if(e.getKeyCode()==key.keyCode){
                System.out.println(key.name+" released");
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
