package states;


import entities.LeaderBoard;
import entities.Message;
import main.DisplayManager;
import main.FileManager;
import main.LeaderBoardData;
import main.StateManager;
import java.util.ArrayList;
import java.util.List;

public class LeaderBoardState extends State {
    private FileManager fileManager;
    private ArrayList<LeaderBoardData> dataList = new ArrayList<>();
    private Message infoMessage1;

    public LeaderBoardState(){
        fileManager = FileManager.getInstance();
        infoMessage1 = new Message( DisplayManager.getInstance().getCanvas().getWidth() - 100, DisplayManager.getInstance().getCanvas().getHeight() - 50,150,25,"ESC: Volver al menu");
    }

    @Override
    public void update() {
        if(enter){
            this.init();
            enter = false;
        }
        if(input.isFired("ESCAPE2")){
            if(StateManager.lastState != -1){
                StateManager.getInstance().setState(StateManager.lastState);
            }else{
                logger.warning("El estado a volver no existe");
            }
        }
        super.update();
    }

    @Override
    public void init() {
        super.init();
        entities.add(new LeaderBoard(100, 20, 400,500, getText(fileManager.getData())));
        entities.add(infoMessage1);
    }

    public ArrayList<String[]> getText(List<LeaderBoardData> data){
        ArrayList<String[]> text = new ArrayList<>();
        text.add(new String[]{"Jugador","Puntos", "Fecha"});
        for(LeaderBoardData score : data) {
            text.add(new String[]{score.getPlayerName(),String.valueOf(score.getPoints()),score.getTime()});
        }
        return text;
    }

}