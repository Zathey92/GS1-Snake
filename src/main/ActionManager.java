package main;

public class ActionManager {
    private static ActionManager instance = null;
    public static final int CHANGE_STATE = 0;
    public static final int EXIT = 1;
    public static final int LEADERBOARD = 2;
    public static final int CLOSEWIN = 3;
    public static final int CLOSELOSE = 4;


    public void changeState(int state){
        StateManager.getInstance().setState(state);
    }

    public void action(int action, int[] param){
        switch (action){
            case CHANGE_STATE:
                changeState(param[0]);
                break;
            case LEADERBOARD:
                changeState(param[0]);
                break;
            case EXIT:
                Application.isRunning = false;
                break;
            case CLOSEWIN:

                break;
            case CLOSELOSE:
                break;
        }
    }

    public static ActionManager getInstance() {
        if(instance == null) {
            instance = new ActionManager();
        }
        return instance;
    }
}
