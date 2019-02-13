package ThreadsControl;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

class Consumer {
    private static FileOutputStream outputStream;
    private static TreeMap<Integer, byte[]> map = new TreeMap<>();
    Consumer(FileOutputStream outputFileStream) throws FileNotFoundException {
        Consumer.outputStream = outputFileStream;
    }

    /*synchronized void writeOutput(int currentPos, byte[] bytes) throws IOException {

        outputStream.write(Integer.toString(currentPos).getBytes());
        outputStream.write(" : ".getBytes());
        outputStream.write(bytes);
        outputStream.write('\n');
    }*/

    void put(int currentPos, byte[] bytes) throws IOException {
        map.put(currentPos, bytes);
    }

    void printResult() throws IOException {
        SortedSet<Integer> keys = new TreeSet<>(map.keySet());
        for (Integer key : keys){
            outputStream.write(map.get(key));
            outputStream.write(':');
        }
    }
}
