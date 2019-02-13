package ThreadsControl;

import ConfigFiles.ConfigMain;
import ConfigFiles.ConfigThread;
import Encoding.Encoding;
import Encoding.MyException;
import java.io.IOException;

public class MyThread extends Thread {
    private int size;
    private int offSet;
    private int incrementOffSet;
    private static ConfigMain configMain;
    private Provider provider;
    private Consumer consumer;

    MyThread(ConfigThread configThread, ConfigMain configMain, Provider provider, Consumer consumer) throws IOException, MyException {
        this.size = configThread.getSize();
        this.offSet = configThread.getOffSet();
        this.incrementOffSet = configThread.getIncrementOffSet();
        this.provider = provider;
        this.consumer = consumer;

        MyThread.configMain = configMain;
    }


    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " started");
        byte[] result;
        Encoding encoding = new Encoding();
        int currentPos = offSet;

        while (!provider.getIsInputFileFinished()) {

            byte[] input = new byte[0];

            try {
                input = provider.getThreadCurrentInput(size, currentPos);
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
            if (input == null){
                try {
                    MyThread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(MyThread.currentThread().getName() + " was sleeping for 1000 millis");
            }
            else{
                result = encoding.runAlgorithm(input, configMain);
                try {
                    consumer.put(currentPos, result);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                currentPos += size + incrementOffSet;
            }
        }
        System.out.println(Thread.currentThread().getName() + " finished");
    }
}
