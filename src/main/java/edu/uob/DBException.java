package edu.uob;

public class DBException extends Exception {
    private static final long serialVersionUID = -2405736440969523511L;
    DBException(String message) {
        super("[ERROR]: " + message);
    }
}
