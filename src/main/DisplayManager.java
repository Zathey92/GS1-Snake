package main;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DisplayManager extends JFrame {
    private static DisplayManager instance = null;
    private Canvas canvas;
    private BufferStrategy bs;
    private Logger logger;
    protected DisplayManager(){
        super();
        logger = Logger.getLogger(getClass().getName());
        canvas = new Canvas();
        init();
    }
    public void init(){

        canvas.setPreferredSize(new Dimension(800,600));
        logger.log(Level.FINE," Configurando Pantalla"); //ejemplo a borrar
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        add(canvas);
        Frame frame = this;
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                logger.log(Level.INFO," Global Pause");
                StateManager.getInstance().isPaused=true;
                if (JOptionPane.showConfirmDialog(frame,
                        "Are you sure to close this window?", "Really Closing?",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
                    Application.isRunning=false;

                }else{
                    StateManager.getInstance().isPaused=false;
                }
            }
        });
        setResizable(false);
        pack();
        setVisible(true);
        this.requestFocus();
    }

    public Canvas getCanvas(){
        return canvas;
    }
    public static DisplayManager getInstance() {
        if(instance == null) {
            instance = new DisplayManager();
        }
        return instance;
    }

}