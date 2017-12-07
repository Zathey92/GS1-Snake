package main;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Application implements Runnable {
    private Thread thread;
    public static boolean isRunning;
    public final static double amountOfTicks = 60.0; //Numero de updates por segundo

    private Logger logger;
    private Canvas canvas;
    private StateManager stateManager;
    private SoundManager soundManager;


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
        long initialTime = System.nanoTime();
        final double timeU = 1000000000 / amountOfTicks;
        final double timeF = 1000000000 / amountOfTicks;
        double deltaU = 0, deltaF = 0;
        int frames = 0, ticks = 0;
        long timer = System.currentTimeMillis();
        stateManager.init();
        while(isRunning){

            long currentTime = System.nanoTime();
            deltaU += (currentTime - initialTime) / timeU;
            deltaF += (currentTime - initialTime) / timeF;
            initialTime = currentTime;


            if (deltaU >= 1) {
                stateManager.update();
                ticks++;
                deltaU--;
            }

            if (deltaF >= 1) {
                render();
                frames++;
                deltaF--;
            }
            if (System.currentTimeMillis() - timer > 1000) {
                if (false) {
                    System.out.println(String.format("UPS: %s, FPS: %s", ticks, frames));
                }
                frames = 0;
                ticks = 0;
                timer += 1000;
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

