package main;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardOpenOption.CREATE;

public class FileManager {
    private Logger logger;
    public static FileManager instance = null;
    private String FILEPATH="LeaderBoard.txt";
    private File file;
    private Charset charset;


    private FileManager(){
        logger = Logger.getLogger(getClass().getName());
        charset = Charset.forName("US-ASCII");
    }


    public void loadFile(){
        file = new File(FILEPATH);
    }

    public List<LeaderBoardData> getData(){
        loadFile();
        List<LeaderBoardData> result = new ArrayList<>();
        try (BufferedReader reader = Files.newBufferedReader(file.toPath(), charset)) {
            String line = null;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\t");
                result.add(new LeaderBoardData(parts[0],
                        Integer.parseInt(parts[1]),
                        parts[2]));
            }
        } catch (IOException e) {
            logger.warning("No se pudo leer el archivo");
        }
        return result;
    }

    public static FileManager getInstance() {
        if(instance == null) {
            instance = new FileManager();
        }
        return instance;
    }

    public void saveScore(String s){
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        s = s + "\t" + dateFormat.format(date)+"\n";
        byte data[] = s.getBytes();
        try (OutputStream out = new BufferedOutputStream(Files.newOutputStream(file.toPath(), CREATE, APPEND))) {
            out.write(data, 0, data.length);
        } catch (IOException x) {
            logger.warning("No se puede guardar la puntuación");
        }
    }
}
