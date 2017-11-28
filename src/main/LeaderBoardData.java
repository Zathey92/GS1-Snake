package main;

public class LeaderBoardData {
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
