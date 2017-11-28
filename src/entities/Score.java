package entities;

import java.awt.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Score extends Entity {
    private int value;
    private final String PATH = "LeaderBoard.txt";
    private final String TEXT = "Puntuación: ";
    private Button button;
    private Boolean eat;

    public Score(int x, int y){
        super(0,0);
        button = new Button(x,y,150,20,10,TEXT + value);
    }

    @Override
    public void init() {
        value = 0;
        eat = false;
        button.init();
    }

    @Override
    public void render(Graphics g) {
        button.render(g);
    }

    @Override
    public void update() {
        if(eat){
            value++;
            button.setText(TEXT + value);
            eat = false;
        }
    }

    public void saveScore(){
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        try (Writer writer = new BufferedWriter(new FileWriter(PATH, true))) {
            System.out.println("escribiendo");
            writer.append(System.getProperty("line.separator") + "default" + "\t" + value + "\t" + dateFormat.format(date));
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setHasCollide(boolean b) {eat = b; }
}
