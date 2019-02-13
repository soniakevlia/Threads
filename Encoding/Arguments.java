package Encoding;

import java.util.HashMap;

public class Arguments {

    public enum Id {
        IN("in:"), OUT("out:"), CONF("conf:");

        String name;

        Id(String s) {
            this.name = s;
        }

        public String getName() {
            return name;
        }
    }

    private static final int numberOfArguments = 6;
    private HashMap<Id, String> fileNameMap = new HashMap<>();

    /**
     * Get command arguments
     *
     * @return
     */
    public String getInputFilename() {
        return fileNameMap.get(Id.IN);
    }

    public String getOutputFilename() {
        return fileNameMap.get(Id.OUT);
    }

    public String getConfFilename() {
        return fileNameMap.get(Id.CONF);
    }


    /**
     * Check number of command arguments and append it into the map
     *
     * @param args
     * @throws ArgException
     */
    public Arguments(String[] args) throws ArgException {
        Id[] arr = Id.values();
        if (args.length != numberOfArguments) throw new ArgException("Invalid number of arguments", args.length);
        int i = 0;
        while (i < args.length) {
            for (Id el : arr) {
                if (args[i].equals(el.getName()))
                    fileNameMap.put(el, args[i + 1]);
            }
            if (!fileNameMap.values().contains(args[i + 1]))
                throw new ArgException("Invalid lexemes in command line", 0);
            i += 2;
        }
    }
}
