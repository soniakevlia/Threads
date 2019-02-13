package Encoding;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

public class Table {
    private String filename;
    private static final String split = " ";

    public Table(String fName) {
        filename = fName;
    }

    /**
     * Fill map with data from table
     *
     * @param mode
     * @return map <String, String> with data from file with table
     * @throws FileNotFoundException
     */
    public HashMap<Integer, byte[]> readTable(boolean mode) throws FileNotFoundException, UnsupportedEncodingException {
        HashMap<Integer, byte[]> map = new HashMap<>();
        File inputFile = new File(filename);
        Scanner scan = new Scanner(inputFile);
        while (scan.hasNextLine()) {
            String line = scan.nextLine();
            String[] string = line.split(split);
            byte[] buff0 = string[0].getBytes("Cp1251");
            byte[] buff1 = string[1].getBytes("Cp1251");
            map.put(Arrays.hashCode(buff0), buff1);
            map.put(Arrays.hashCode(buff1), buff0);
        }
        return map;
    }
}

