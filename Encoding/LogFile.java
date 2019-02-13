package Encoding;

import java.io.FileOutputStream;
import java.io.IOException;

public class LogFile {
    static private String fileName = "src/encodingAlg/logfile.txt";

    /**
     * Write to  Log file
     *
     * @param str
     */
    public static void write(String str) {
        try {
            FileOutputStream logFile = new FileOutputStream(fileName);
            logFile.write(str.getBytes());
        } catch (IOException e) {
            System.out.println("error: " + e.getMessage());
        }
    }
}
