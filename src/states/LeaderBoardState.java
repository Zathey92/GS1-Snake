package states;

import entities.LeaderBoard;
import main.StateManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LeaderBoardState extends State {
    private String FILEPATH="LeaderBoard.txt";
    private LeaderBoard leaderBoardEntity;
    private ArrayList<LeaderBoardData> dataList = new ArrayList<>();

    @Override
    public void update() {
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
        loadLeaderBoard();
        leaderBoardEntity = new LeaderBoard(20, 20, 500,500, getText());
        entities.add(leaderBoardEntity);
        //printLeaderBoard();

    }



    private void printLeaderBoard() {
        for(LeaderBoardData data: dataList){
            System.out.println("jugador: " + data.getPlayerName());
            System.out.println("Puntos: " + data.getPoints());
            System.out.println("Date: " + data.getTime());
        }
    }

    public String getText(){
        String text = "Jugador   Puntos   Fecha" + "\n";
        for(LeaderBoardData data: dataList) {
            text+= data.getPlayerName() + "       "+
                    data.getPoints() + "   " +
                    data.getTime() + "\n";
        }
        //System.out.println(text);
        return text;
    }

    public void loadLeaderBoard(){
        logger.log(Level.INFO,"Cargando fichero");
        File file = new File(FILEPATH);

        try {
            Scanner sc = new Scanner(file);
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] parts = line.split("\t");
                dataList.add(new LeaderBoardData(parts[0],
                        Integer.parseInt(parts[1]),
                        parts[2]));

            }
            sc.close();
        } catch (FileNotFoundException e) {}
    }

    public LeaderBoardState() {

    }

    class LeaderBoardData{
        private String playerName;
        private int points;
        private String time;

        public LeaderBoardData(String playerName, int points, String time) {
            this.playerName = playerName;
            this.points = points;
            this.time = time;
        }

        public String getPlayerName() {
            return playerName;
        }

        public int getPoints() {
            return points;
        }

        public String getTime() {
            return time;
        }
    }
}