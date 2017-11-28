package main;

import states.*;

import javax.net.ssl.KeyManager;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StateManager {
    public static final int GAME_MENU = 0;
    public static final int ORIGINALGAME = 1;
    public static final int LEADERBOARD = 2;
    public static final int SELECTGAME= 3;

    public static int lastState;
    private static StateManager instance = null;
    private int currentState;
    public List<State> states= new ArrayList<>();
    Logger logger;
    public boolean isPaused;

    /* Añadir nuevos Estados aqui */
    protected StateManager(){
        logger = Logger.getLogger(getClass().getName());
        GameMenuState gameMenu = new GameMenuState();
        lastState = -1;
        states.add(gameMenu);
        states.add(new OriginalGameState());
        states.add(new LeaderBoardState());
        states.add(new SelectGameState());
    }
    /* No Tocar */
    public void init() {
        for (State state: states){
            state.init();
        }
        currentState = GAME_MENU;
    }
    public void update(){
        if(!isPaused) {
            states.get(currentState).update();
        }
    }
    public void render(Graphics g){
        states.get(currentState).render(g);
    }

    public void setState(int state){
        InputManager.getInstance().clearKeyMappings();
        SoundManager.getInstance().stopAll();
        if(states.size()>state) {
            lastState = currentState;
            currentState = state;
            logger.log(Level.INFO,"Cambiando el estado ("+state+")");
        }else{
            logger.log(Level.SEVERE,"No se ha encontrado ningún estado con la id ("+state+")");
        }
        states.get(currentState).enter = true;
    }

    public static StateManager getInstance() {
        if(instance == null) {
            instance = new StateManager();
        }
        return instance;
    }
}
