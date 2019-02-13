package ThreadsControl;

import ConfigFiles.ConfigMain;
import ConfigFiles.ConfigThread;
import Encoding.ConfigurationFile;
import Encoding.MyException;

import java.io.FileOutputStream;
import java.io.IOException;

public class ThreadController {
    private String prefix;
    private static int threadsNumber;
    private ConfigMain configMain;
    private static MyThread thread[];

    public ThreadController(ConfigMain configMain) throws IOException, MyException {

        this.configMain = configMain;
        prefix = configMain.getThreadConfigPrefix();
        threadsNumber = configMain.getThreadsNumber();

        ConfigurationFile configurationFile = new ConfigurationFile(configMain.getConfigFilename());
        configurationFile.readFile();
        thread = new MyThread[threadsNumber];
    }

    public void start(String inputFilename, String outputFilename) throws IOException, MyException, InterruptedException {
        ConfigThread configThread;
        FileOutputStream fileOutputStream = new FileOutputStream(outputFilename);
        Provider provider = new Provider(configMain.getInputBlockSize(), threadsNumber, inputFilename);
        Consumer consumer = new Consumer(fileOutputStream);
        provider.readNewInputBlock();
        for (int i = 0; i < threadsNumber; i++) {
            configThread = new ConfigThread(prefix, i + 1);
            configThread.readThreadFile();
            thread[i] = new MyThread(configThread, configMain, provider, consumer);
            thread[i].start();
        }
       for (int i = 0; i < threadsNumber; i++)
           thread[i].join();
        consumer.printResult();
    }
}
