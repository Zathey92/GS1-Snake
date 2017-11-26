package main;

import java.util.ArrayList;
import java.util.List;

public class SoundManager {
    private List<Sound> sounds = new ArrayList<Sound>();
    public static SoundManager instance;

    public void add(String s, String fileName){
        for(Sound sound: sounds){
            if(s.equals(sound.name)){
                return;
            }
        }
        Sound sound = new Sound(s,Sound.getURL(fileName));
        sounds.add(sound);
    }

    public void remove(String s){
        for(Sound sound: sounds) {
            if(sound.name.equals(s)) sounds.remove(sound);
        }
    }

    public void play(String s){
        for(Sound sound: sounds) {
            if(sound.name.equals(s)){
                sound.loopFlag = false;
                new Thread(sound).start();
            }
        }
    }

    public void start(String s){
        for(Sound sound: sounds) {
            if(sound.name.equals(s)){
                sound.loopFlag = true;
                new Thread(sound).start();
            }
        }
    }

    public void stop(String s){
        for(Sound sound: sounds) {
            if(sound.name.equals(s)) sound.stop();
        }
    }

    public void stopAll(){
        for(Sound sound: sounds) {
            sound.stop();
        }
    }

    public static SoundManager getInstance() {
        if(instance == null) {
            instance = new SoundManager();
        }
        return instance;
    }
}
