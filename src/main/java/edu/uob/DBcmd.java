package edu.uob;

import java.util.List;
import java.util.concurrent.locks.Condition;

public abstract class DBcmd {
    List<Condition> conditions;
    List<String> colNames;
    List<String> tableNames;
    String DBname;
    String commandType;

    public DBcmd() {}

    abstract String query();

}
