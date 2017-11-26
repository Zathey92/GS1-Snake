package main;


import java.applet.Applet;
import java.applet.AudioClip;
import java.net.URL;
import java.util.logging.Logger;

public class Sound {

    public String name;
    public AudioClip sound;
    public Logger logger;
    private static Sound staticSound = new Sound();

    public Sound(){}

    public Sound(String name, URL url){
        logger = Logger.getLogger(getClass().getName());
        this.name = name;
        try{
            sound= Applet.newAudioClip(url);
        }catch(Exception e){
            logger.warning("Sonido en '"+url+"' no pudo ser abierto. Exception: "+e);
        }
    }

    public void play(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                if(sound!=null) sound.play();
            }
        }).start();
    }

    public void loop(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (sound != null) sound.loop();
            }
        }).start();
    }
    public void stop(){
        if(sound!=null) sound.stop();
    }

    public static URL getURL(String fileName){
        return staticSound.getClass().getResource("../sounds/"+fileName);
    }

}