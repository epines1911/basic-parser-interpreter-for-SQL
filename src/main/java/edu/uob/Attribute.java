package edu.uob;

import java.util.ArrayList;

public class Attribute {
    private String name;
    public ArrayList<String> col;
    public Attribute() {
        col = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String newName) {
        name = newName;
    }
}
