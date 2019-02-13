import ConfigFiles.ConfigMain;
import Encoding.*;
import ThreadsControl.ThreadController;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class Main {
    public static void main(String[] args) throws IOException, MyException, IllegalAccessException, ClassNotFoundException, InstantiationException, NoSuchMethodException, InvocationTargetException, InterruptedException {

        /**Catch filenames from command line**/
        Arguments arguments;
        try {
            arguments = new Arguments(args);
        } catch (ArgException e) {
            LogFile.write("Invalid arguments\n");
            throw new MyException(e);
        }

        ConfigMain configMain = new ConfigMain(arguments.getConfFilename());
        configMain.readConfigFile();

        ThreadController threadController = new ThreadController(configMain);
        threadController.start(arguments.getInputFilename(), arguments.getOutputFilename());
        //threadController.printResult();
    }

}