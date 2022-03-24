package edu.uob;

public class UseCMD extends DBcmd{
    public UseCMD(DBController ctrl, Token t) {
        super();
        setCurDatabase(ctrl, t);
    }

    private void setCurDatabase(DBController ctrl, Token t) {
        // t should be <DatabaseName>
        String dbName = t.value;
        Database db = ctrl.getDBByName(dbName);
        ctrl.setCurrentDB(db);
        //todo how to tell server that the task has a result?
    }

    // <USE> commandType don't need to execute query method
    @Override
    String query() {
        return null;
    }
}
