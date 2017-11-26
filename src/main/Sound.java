package main;


import java.applet.Applet;
import java.applet.AudioClip;
import java.net.URL;
import java.util.logging.Logger;

import static sun.management.snmp.jvminstr.JvmThreadInstanceEntryImpl.ThreadStateMap.Byte0.runnable;

public class Sound implements Runnable {

    public String name;
    public AudioClip sound;
    public Logger logger;
    private static Sound staticSound = new Sound();
    public boolean loopFlag;

    public Sound(){}

    public Sound(String name, URL url){
        loopFlag = false;
        logger = Logger.getLogger(getClass().getName());
        this.name = name;
        try{
            sound= Applet.newAudioClip(url);
        }catch(Exception e){
            logger.warning("Sonido en '"+url+"' no pudo ser abierto. Exception: "+e);
        }
    }

    public void stop(){
        if(sound!=null) sound.stop();
    }

    public static URL getURL(String fileName){
        return staticSound.getClass().getResource("../sounds/"+fileName);
    }


    @Override
    public void run() {
        System.out.println(" soy un sonido " + Thread.activeCount());
        if(loopFlag){
            sound.loop();
        }else {
            sound.play();
        }
    }
}
