package edu.uob;

import java.util.ArrayList;

public class CreateCMD {
    public CreateCMD(DBController ctrl, String name, ArrayList<String> attributeNames, Boolean isDB) {
        // if isDB is true, then create a database. Otherwise, create a table.
        if (isDB) {createDB(ctrl, name);}
        else {createTB(ctrl, name, attributeNames);}
    }

    private void createDB(DBController ctrl, String name) {
        Database newDB = new Database();
        newDB.name = name;
        ctrl.addNewDB(name, newDB);
    }

    private void createTB(DBController ctrl, String name, ArrayList<String> attributeNames) {
        Database db = ctrl.getCurrentDB();
        Table newTB;
        if (attributeNames != null && attributeNames.size() > 0) {
            newTB = new Table(attributeNames.size());
            for (int i = 0; i < attributeNames.size(); i++) {
                newTB.valueList.get(i).name = attributeNames.get(i);
            }
        } else {
            newTB = new Table(0);
        }
        newTB.setName(name);
        db.tables.put(name, newTB);
    }
}
