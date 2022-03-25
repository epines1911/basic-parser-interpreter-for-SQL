package edu.uob;

import java.util.HashMap;
import java.util.Map;

public class Database {
    String name;
    Map tables;

    public Database() {
        tables = new HashMap();
    }

    public String getName() {
        return name;
    }

    public void setName(String DBName) {
        name = DBName;
    }
}
