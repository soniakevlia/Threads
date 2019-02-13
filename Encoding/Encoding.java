package Encoding;

import ConfigFiles.ConfigMain;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Encoding {

    public byte[] runAlgorithm(byte[] input, ConfigMain configMain) {
        ConfigurationFile configurationFile = new ConfigurationFile(configMain.getConfigFilename());
        try {
            configurationFile.readFile();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MyException e) {
            e.printStackTrace();
        }
        Table table = new Table(configurationFile.getSettings().tableName);
        HashMap<Integer, byte[]> tableMap = null;
        try {
            tableMap = table.readTable(configurationFile.getSettings().mode);
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        try {
            return encodeData(input, configurationFile.getSettings().bytesNumber, tableMap);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * put byteArray  "buff2" into list of bytes "Main"
     *
     * @param main
     * @param buff2
     */
    private void append(ArrayList<Byte> main, byte[] buff2) {
        for (int i = 0; i < buff2.length; ++i)
            main.add(buff2[i]);
    }

    /**
     * Split string by bytes
     *
     * @param bytesNumber, map
     * @throws IOException
     */
    private byte[] encodeData(byte[] inputFile, int bytesNumber, HashMap<Integer, byte[]> map) throws IOException {
        int stringSize = inputFile.length / bytesNumber;
        int rem = inputFile.length % bytesNumber;
        byte[] buff = new byte[bytesNumber];
        int currentPos = 0;
        ArrayList<Byte> outputFile = new ArrayList<>();

        for (int i = 0; i < stringSize; ++i) {
            for (int j = 0; j < bytesNumber; ++j) {
                buff[j] = inputFile[currentPos++];
            }
            if (map.get(Arrays.hashCode(buff)) != null)
                append(outputFile, map.get(Arrays.hashCode(buff)));
            else
                append(outputFile, buff);
        }
        if (rem != 0) {
            buff = new byte[rem];
            for (int j = 0; j < rem; ++j) {
                buff[j] = inputFile[currentPos++];
            }
            for (int j = 0; j < buff.length; ++j) {
                if (map.get(Arrays.hashCode(buff)) != null)
                    append(outputFile, map.get(Arrays.hashCode(buff)));
                else
                    append(outputFile, buff);
            }
        }
        return toByteArray(outputFile);
    }

    /**
     * convert list of bytes to byteArray
     *
     * @param outputFile
     * @return byteArray
     */
    private byte[] toByteArray(ArrayList<Byte> outputFile) {
        byte[] out = new byte[outputFile.size()];

        for (int i = 0; i < out.length; ++i)
            out[i] = outputFile.get(i);

        return out;
    }
}