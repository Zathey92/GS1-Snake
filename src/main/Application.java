package main;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Application implements Runnable {
    private Thread thread;
    public static boolean isRunning;
    public final static double amountOfTicks = 30.0; //Numero de updates por segundo
    public double targetTime = 1000 / amountOfTicks;
    public long lastTime, totalTime = 0, waitTime, time;
    private int frameCount=0;
    private Logger logger;
    private Canvas canvas;
    private StateManager stateManager;
    private SoundManager soundManager;
    private double averageFPS = 0;


    public Application(){
        //Iniciamos las clases Manager
        logger = Logger.getLogger(getClass().getName());
        stateManager = StateManager.getInstance();
        DisplayManager frame = DisplayManager.getInstance();
        canvas = frame.getCanvas();
        canvas.addKeyListener(InputManager.getInstance());
        frame.addKeyListener(InputManager.getInstance());
        soundManager = SoundManager.getInstance();
    }

    public void run() {
        logger.log(Level.INFO," Aplication Running");
        lastTime = System.nanoTime();
        stateManager.init();
        while(isRunning){
            long now = System.nanoTime();
            lastTime=now;

            stateManager.update();
            render();

            time=(now-lastTime) /100000000;
            double waitTime = targetTime-time;
            try {
                Thread.sleep((long)waitTime);
            }catch (Exception e){
            }
            totalTime += System.nanoTime()-lastTime;
            frameCount++;
            if(frameCount==amountOfTicks){
                averageFPS = 1000.0/((totalTime/frameCount)/1000000);
                frameCount=0;
                totalTime=0;
            }
        }
        logger.log(Level.INFO," Stopping");
    }

    private void start(){
        thread = new Thread(this);
        isRunning=true;
        thread.start();
    }

    public void stop(){
        try {
            thread.join();
        } catch (InterruptedException e) {
            logger.log(Level.SEVERE," Thread Closing Error", e);
            e.printStackTrace();
        }
        System.exit(1);
    }

    private void render(){
        //Iniciamos los buffer de dibujo
        BufferStrategy bs = canvas.getBufferStrategy();
        if(bs==null){
            canvas.createBufferStrategy(2);
            bs = canvas.getBufferStrategy();
            logger.log(Level.WARNING," No existe BufferStrategy");
        }
        Graphics g = bs.getDrawGraphics();
        g.clearRect(0,0,canvas.getWidth(),canvas.getHeight());  //Limpiamos el canvas

        stateManager.render(g); //Renderizamos el estado actual

        bs.show();        //Mostramos el buffer en pantalla

        g.dispose();        //Nos cargamos el componente gráfico
    }

    public static void main (String [ ] args) {
        Logger.getGlobal().log(Level.INFO," Aplication Starting");//Ejemplo a borrar de uso global del logger
        Application app = new Application();
        app.start();
        app.stop();
    }

}

