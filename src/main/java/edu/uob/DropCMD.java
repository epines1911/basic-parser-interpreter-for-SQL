package edu.uob;

public class DropCMD {
    public DropCMD(DBController ctrl, String name, Boolean isDB) throws DBException {
        // if isDB is true, then create a database. Otherwise, create a table.
        if (isDB) {dropDB(ctrl, name);}
        else {dropTB(ctrl, name);}
    }

    private void dropDB(DBController ctrl, String name) throws DBException {
        ctrl.removeDB(name);
    }

    private void dropTB(DBController ctrl, String name) throws DBException {
        ctrl.getCurrentDB().tables.remove(name);
        ctrl.deleteTableFile(name);
    }
}
