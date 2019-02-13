package ThreadsControl;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;

class Provider {

    private static int eof = -1;
    private int inputBlockSize;
    private FileInputStream inputStream;
    private static int currentFilePos = 0;
    private byte[] currentInputBlock;
    private static int threadsNumber;
    private int endPos;
    private HashMap<Integer, Integer> map = new HashMap<>();
    private int nextBlockStartPos;
    private boolean isInputFileFinished;

    Provider(int inputBlockSize, int threadsNumber, String inputFilename) throws IOException {
        this.inputBlockSize = inputBlockSize;
        this.inputStream = new FileInputStream(inputFilename);

        Provider.threadsNumber = threadsNumber;
    }

    synchronized void readNewInputBlock() throws IOException {
        int addSize = currentFilePos - nextBlockStartPos;

        byte[] block = new byte[inputBlockSize];
        int j;
        for (j = 0; j < addSize; ++j) {
            block[j] = currentInputBlock[getInputBlockSize() - addSize + j ];
        }
        int ch;
        for (int i = j; i < inputBlockSize; ++i) {
                ch = inputStream.read();
                if (ch!= eof)
                    block[i] = (byte) ch;
                else
                    isInputFileFinished = true;
                currentFilePos++;
        }
        nextBlockStartPos = currentFilePos;
        currentInputBlock = block;
    }

    synchronized byte[] getThreadCurrentInput(int size, int currentPos) throws IOException, InterruptedException {
        int inputBlockCurPos;

        if (isBlockFinished())
            readNewInputBlock();

        byte[] result = new byte[size];

        if (currentPos >= currentFilePos) {
            result = null;
        }
        else
            if (size >currentFilePos - currentPos){
                nextBlockStartPos = currentPos;
                result = null;
            }
            else {
                inputBlockCurPos = getInputBlockSize() - (currentFilePos - currentPos);
                System.arraycopy(currentInputBlock, inputBlockCurPos, result, 0, size);
                map.put(currentPos, size);
            }
        return result;
    }

    private int getInputBlockSize(){
        return currentInputBlock.length;
    }

    private boolean isBlockFinished(){
        int i = 0;
        while (map.containsKey(i)) {
            i+=map.get(i);
            endPos = i;
            if (endPos >= nextBlockStartPos)
                return true;
        }
        return false;
    }

    boolean getIsInputFileFinished(){
       return endPos >= nextBlockStartPos && isInputFileFinished;
    }
}
