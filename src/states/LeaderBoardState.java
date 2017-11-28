package states;

import entities.Entity;
import entities.LeaderBoard;
import main.FileManager;
import main.LeaderBoardData;
import main.StateManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;

public class LeaderBoardState extends State {
    private FileManager fileManager;
    private ArrayList<LeaderBoardData> dataList = new ArrayList<>();

    public LeaderBoardState(){
        fileManager = FileManager.getInstance();
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
        entities.add(new LeaderBoard(20, 20, 500,500, getText(fileManager.getData())));
    }

    public String getText(List<LeaderBoardData> data){
        String text = "Jugador   Puntos   Fecha" + "\n";
        for(LeaderBoardData score : data) {
            text+= score.getPlayerName() + "       "+
                    score.getPoints() + "   " +
                    score.getTime() + "\n";
        }
        //System.out.println(text);
        return text;
    }



}