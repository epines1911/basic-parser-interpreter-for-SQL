package edu.uob;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class DBController {
    private Map dbMap;
    private Database currentDB;
    private String topDirectory;
    private String currentDir;

    public DBController(String directory) {
        dbMap = new HashMap();
        currentDB = null;
        topDirectory = directory;
        currentDir = directory;
    }

    public void setCurrentDB(Database newDB) {
        currentDB = newDB;
        currentDir = generatePath(newDB.name, true);
    }

    public Database getCurrentDB() {
        return currentDB;
    }

    public void addNewDB(String name, Database newDB) throws DBException {
        dbMap.put(name, newDB);
        generateFolder(name);
    }

    public Database getDBByName(String name) {
        return (Database) dbMap.get(name);
    }

    public void removeDB(String name) throws DBException {
        dbMap.remove(name);
        deleteDBFile(name);
        if (Objects.equals(currentDir, generatePath(name, true))) {
            currentDir = topDirectory;
        }
    }

    private String generatePath(String fileName, Boolean isFolder) {
        // if isFolder is ture, means that this function will generate a path for a folder
        // if isFolder is false, means that this function will generate a path for a .tab file
        String result = "";
        if (!isFolder) {
            String type = ".tab";
            result = currentDir + File.separator + fileName + type;
        } else {
            result += topDirectory + File.separator + fileName;
        }
        return result;
    }

    public void generateTabFile(String fileName) throws DBException, IOException {
        String path = generatePath(fileName, false);
        File tabFile = new File(path);
        if (tabFile.exists()) {
            throw new DBException("This table already exists");
        } else {
            boolean result = tabFile.createNewFile();
            if (!result) {
                throw new DBException("Failed to Set up a new table");
            }
        }
    }

    public void generateFolder(String folderName) throws DBException {
        String path = topDirectory + File.separator + folderName;
        File folderFile = new File(path);
        if (folderFile.exists()) {
            throw new DBException("This database already exists");
        } else {
            boolean result = folderFile.mkdir();
            if (!result) {
                throw new DBException("Failed to Set up a new database");
            }
        }
    }

    public void deleteTableFile(String tableName) throws DBException {
        String path = generatePath(tableName, false);
        File fileToDelete = new File(path);
        if (fileToDelete.exists()) {
            boolean result = fileToDelete.delete();
            if (!result) {
                throw new DBException("Failed to delete this table");
            }
        } else {
            throw new DBException("Cannot find this table in current database");
        }
    }

    public void deleteDBFile(String dbName) throws DBException {
        String path = generatePath(dbName, true);
        File fileToDelete = new File(path);
        if(!fileToDelete.isDirectory()) {
            throw new DBException("Cannot find this database");
        }
        File[] listFiles = fileToDelete.listFiles();
        assert listFiles != null;
        for(File file : listFiles){
            file.delete();
        }
        boolean result = fileToDelete.delete();
        if (!result) {
            throw new DBException("Failed to delete this database");
        }
    }
}
