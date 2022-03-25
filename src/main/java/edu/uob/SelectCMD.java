package edu.uob;

import java.util.ArrayList;

public class SelectCMD extends DBcmd{
    String result;
    Table aimTB;
    Table modifiedTB;
    public SelectCMD(DBController ctrl, String tableName,
                     ArrayList<String> attributeName, ArrayList<Condition> conditions, boolean isADD) {
        aimTB = (Table) ctrl.getCurrentDB().tables.get(tableName);
        result = "";
        if (attributeName.size() == 0 || attributeName == null) {
            if (conditions == null) {
                // that means SELECT * FROM table
                selectAll();
            } else if (conditions.size() > 0) {
                // that means SELECT * FROM table WHERE conditions
            }
        }
        if (attributeName.size() > 0) {
            if (conditions == null) {
                // that means SELECT attributes FROM table
                //
            } else if (conditions.size() > 0) {
                // that means SELECT attributes FROM table WHERE conditions
            }
        }
    }

    private void selectAllWithCond() {
        // return all columns with conditions

    }

    private void selectAll() {
        // return all attributes
        result += aimTB.createNameRow();
        int num = aimTB.valueList.get(0).col.size();
        for (int i = 0; i < num; i++) {
            result += aimTB.createTableRow(i);
        }
        System.out.println(result); //todo delete
    }

    @Override
    String query() {
        return result;
    }
}
