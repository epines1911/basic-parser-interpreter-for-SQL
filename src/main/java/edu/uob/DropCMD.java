package edu.uob;

import java.awt.*;

public class DropCMD {
    public DropCMD(DBController ctrl, String name, Boolean isDB) {
        // if isDB is true, then create a database. Otherwise, create a table.
        if (isDB) {dropDB(ctrl, name);}
        else {dropTB(ctrl, name);}
    }

    private void dropDB(DBController ctrl, String name) {
        ctrl.removeDB(name);
    }

    private void dropTB(DBController ctrl, String name) {
        ctrl.getCurrentDB().tables.remove(name);
    }
}
