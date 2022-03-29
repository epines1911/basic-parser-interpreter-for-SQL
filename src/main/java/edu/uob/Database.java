package edu.uob;

import java.util.HashMap;

public class Database {
    String name;
    HashMap<String, Table> tables;

    public Database() {
        tables = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String DBName) {
        name = DBName;
    }
}
