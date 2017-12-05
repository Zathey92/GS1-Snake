package main;


public class Key {

    public String name;
    public int keyCode, pressCount, limit;
    public boolean pressed, fired;

    public Key(String name, int keyCode){
        this.name = name;
        this.keyCode = keyCode;
        this.pressed=false;
        this.limit = -1;
    }

    public Key(String name, int keyCode, int limit){
        this.name = name;
        this.keyCode = keyCode;
        this.pressed = false;
        this.fired = false;
        this.pressCount = 0;
        this.limit = limit;
    }

    public void toggle(boolean toggle){
        if(pressed != toggle){
            pressed = toggle;
        }
        if(limit < 0)return;

        if(pressed) {
            pressCount++;
            if (pressCount >= limit){
                pressCount = 0;
                fired = true;
            }
        }
    }
}
