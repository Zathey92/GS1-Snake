package main;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Application implements Runnable {
    private Thread thread;
    public static Boolean isRunning;
    public final static double amountOfTicks = 60.0; //Numero de updates por segundo

    private Logger logger;
    private Canvas canvas;
    private StateManager stateManager;
    private SoundManager soundManager;



    public Application(){
        //Iniciamos las clases Manager
        logger = Logger.getLogger(getClass().getName());
        stateManager = StateManager.getInstance();
        canvas = DisplayManager.getInstance().getCanvas();
        canvas.addKeyListener(InputManager.getInstance());
        soundManager = SoundManager.getInstance();
    }

    public void run() {
        logger.log(Level.INFO," Aplication Running");
        long lastTime = System.nanoTime();

        double ns = 100000000 / amountOfTicks;
        double delta = 0;
        int updates = 0;
        int frames =0;
        long timer =System.currentTimeMillis();
        while(isRunning){
            long now = System.nanoTime();
            delta += (double)(now-lastTime) / (long)ns; //diferencia de tiempo en cada loop
            lastTime=now;

            if(delta>=1){
                stateManager.update();
                updates++;
                delta--;
            }
            render();
            frames++;
            if(System.currentTimeMillis() - timer > 1000){
                timer += 100;
                logger.log(Level.FINE,updates + " Ticks, Fps " + frames);
                updates=0;
                frames=0;
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

