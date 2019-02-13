package Encoding;

public class ConfException extends Exception {
    ConfException(String message, int num) {
        super(message + num);
    }
}
