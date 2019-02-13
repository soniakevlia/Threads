package ConfigFiles;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ConfigThread {
    private final String prefix;
    private final int number;

    public ConfigThread(String prefix, int number) {
        this.prefix = prefix;
        this.number = number;
    }

    private enum lexemes {
        SIZE("Size"), OFFSET("Offset"), INCREMENTOFFSET("IncrementOffset");
        String name;
        String value;

        lexemes(String string) {
            this.name = string;
            this.value = "";
        }
    }

    ;


    public void readThreadFile() throws FileNotFoundException {
        FileInputStream confFile = new FileInputStream(prefix + Integer.toString(number) + ".txt");
        Scanner scanner = new Scanner(confFile);
        while (scanner.hasNextLine()) {
            String[] str = scanner.nextLine().split("=");
            lexemes[] values = lexemes.values();
            for (lexemes value : values) {
                if (str[0].equals(value.name))
                    value.value = str[1];
            }
        }
    }

    public int getSize() {
        return Integer.parseInt(lexemes.SIZE.value);
    }

    public int getOffSet() {
        return Integer.parseInt(lexemes.OFFSET.value);
    }

    public int getIncrementOffSet() {
        return Integer.parseInt(lexemes.INCREMENTOFFSET.value);
    }
}
