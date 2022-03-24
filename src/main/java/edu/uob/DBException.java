package edu.uob;

public class DBException extends Exception {
    DBException(String message) {
        super("[ERROR]: " + message);
    }
}
