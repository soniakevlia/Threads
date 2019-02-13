package Encoding;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

public class ConfigurationFile {
    public class Settings {
        public String tableName;
        public Boolean mode;//enum
        public int bytesNumber;
    }

    private Settings set = new Settings();
    private String filename;

    private enum id {CODE, DECODE}

    ;
    HashMap<id, String> lexemeMap = new HashMap<>();

    public ConfigurationFile(String name) {
        filename = name;
        fillLexemeTable();
    }

    /**
     * Get configuration params
     *
     * @return
     */
    public Settings getSettings() {
        return set;
    }

    /**
     * Fill table with lexemes
     */
    private void fillLexemeTable() {
        lexemeMap.put(id.CODE, "code");
        //System.out.println(id.CODE);
        lexemeMap.put(id.DECODE, "decode");
    }

    /**
     * Read configuration file
     *
     * @throws IOException
     * @throws MyException
     */
    public void readFile() throws IOException, MyException {
        File f_configuration = new File(filename);
        if (!f_configuration.exists())
            f_configuration.createNewFile();
        Scanner scan = new Scanner(f_configuration);
        try {
            settings(scan);
        } catch (ConfException e) {
            LogFile.write("Configuration data error");
            throw new MyException(e);
        }
        scan.close();
    }

    /**
     * Check configuration file data
     *
     * @param scan
     * @throws ConfException
     */
    void settings(Scanner scan) throws ConfException {
        set.tableName = scan.nextLine();
        String str = scan.nextLine();
        if ((!str.equals(lexemeMap.get(id.CODE))) && !str.equals(lexemeMap.get(id.DECODE)))
            throw new ConfException("Can't recognize the mode, it must be equals to 'code' or 'decode'\n", 0);
        if (str.equals(lexemeMap.get(id.CODE)))
            set.mode = true;
        else
            set.mode = false;
        set.bytesNumber = Integer.parseInt(scan.nextLine());
        if (set.bytesNumber <= 0)
            throw new ConfException("Number of bytes must be strictly greater than zero\nNumber of bytes = ",
                    set.bytesNumber);
    }
}
