package states;

import java.util.logging.Logger;

public class LeaderBoardState extends State {

    private Logger logger;

    @Override
    public void update() {
        System.out.println("HOLA MUNDO");
        super.init();
    }

    public LeaderBoardState() {

    }
}
