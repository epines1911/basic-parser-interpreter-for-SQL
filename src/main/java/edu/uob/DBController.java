package edu.uob;

import java.util.HashMap;
import java.util.Map;

public class DBController {
    private Map dbMap;
    private Database currentDB;

    public DBController() {
        dbMap = new HashMap();
        currentDB = null;
    }

    public void setCurrentDB(Database newDB) {
        currentDB = newDB;
    }

    public Database getCurrentDB() {
        return currentDB;
    }

    public void addNewDB(String name, Database newDB) {
        dbMap.put(name, newDB);
    }

    public Database getDBByName(String name) {
        return (Database) dbMap.get(name);
    }

    public void removeDB(String name) {
        dbMap.remove(name);
    }
}
