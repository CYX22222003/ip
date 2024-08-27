package storage;

import exception.ParseException;
import task.KorolevTask;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

import parser.EventParser;

public class KorolevStorage {
    private static final String home = System.getProperty("user.dir");

    private static final java.nio.file.Path dir = java.nio.file.Paths.get(
            home, "src", "main", "java", "data");

    private static final java.nio.file.Path path = java.nio.file.Paths.get(
            home, "src", "main", "java", "data", "korolev.txt");

    private void createNewFile() {
        if (!java.nio.file.Files.exists(path)) {
            try {
                java.nio.file.Files.createDirectories(KorolevStorage.dir);
                java.nio.file.Files.createFile(path);
                File record = new File(String.valueOf(path));
                boolean test = record.createNewFile();
            } catch (IOException e) {
                System.out.println("IOException: " + e.getMessage());
            }
        } else {
            return;
        }
    }

    /**
     * Writes new information to the specific file in data folder.
     *
     * @param msg the message being written to the file
     */
    public void writeToFile(String msg) {
        createNewFile();

        try {
            FileWriter writer = new FileWriter(String.valueOf(path));
            writer.write(msg);
            writer.close();
        } catch (IOException e) {
            System.out.println("IOException: " + e.getMessage());
        }
    }

    /**
     * Reads records from data file and parse them into KorolevTask.
     *
     * @param events the list of KorolevTask to store the tasks based on stored information
     */
    public void readLines(ArrayList<KorolevTask> events) {
        createNewFile();
        try {
            BufferedReader bfr = new BufferedReader(new FileReader(String.valueOf(path)));
            String line = bfr.readLine();
            while(line != null) {
                events.add(EventParser.parseLoadedRecord(line));
                line = bfr.readLine();
            }
        } catch (IOException e) {
            System.out.println("IOException: " + e.getMessage());
        } catch (ParseException e) {
            System.out.println(e.getMessage());
        }
    }

}
