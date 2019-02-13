package ConfigFiles;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ConfigMain {
    public ConfigMain(String name) {
        this.filename = name;
    }

    private String filename;

    private enum lexemes {
        SIZE("InputBlockSize"), NUMBER("ThreadsNumber"), PREFIX("ThreadConfigPrefix"), CONFIGALG("ConfigAlg");
        String name;
        String value;

        lexemes(String string) {
            this.name = string;
            this.value = " ";
        }
    }

    ;


    public void readConfigFile() throws FileNotFoundException {
        FileInputStream confFile = new FileInputStream(filename);
        Scanner scanner = new Scanner(confFile);
        while (scanner.hasNextLine()) {
            String[] str = scanner.nextLine().split("=");
            lexemes[] values = lexemes.values();

            for (lexemes value : values) {
                if (str[0].equals(value.name)) {
                    value.value = str[1];
                    System.out.println(value.name + '=' + value.value);
                }
            }

        }
    }

    public int getInputBlockSize() {
        String str = lexemes.SIZE.value;
        return Integer.parseInt(str);
    }

    public int getThreadsNumber() {
        String str = lexemes.NUMBER.value;
        return Integer.parseInt(str);
    }

    public String getConfigFilename() {
        return lexemes.CONFIGALG.value;
    }

    public String getThreadConfigPrefix() {
        return lexemes.PREFIX.value;
    }
}
