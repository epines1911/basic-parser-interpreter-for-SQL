package edu.uob;

import java.util.ArrayList;

public class SelectCMD extends DBcmd{
    private String result;
    private Table aimTB;
    private Table modifiedTB;
    private ArrayList<Attribute> valueList;

    public SelectCMD(DBController ctrl, String tableName,
                     ArrayList<String> attributeName, ArrayList<Condition> conditions,
                     boolean isADD) throws DBException {
        aimTB = ctrl.getCurrentDB().tables.get(tableName);
        result = "";
        valueList = new ArrayList<>();
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
                selectAttribute(attributeName);
            } else if (conditions.size() > 0) {
                // that means SELECT attributes FROM table WHERE conditions
            }
        }
    }

    private void selectAllWithCond() {
        // return all columns with conditions

    }

    private ArrayList<Attribute> selectRecords() {
        // select records which are matched conditions, then make a new table
        return new ArrayList<Attribute>();
    }

    private void selectAttribute(ArrayList<String> names) throws DBException {
        // return some attributes by attribute name
        for (String name:
             names) {
            result += name;
            result += "\t";
            valueList.add(aimTB.getAttributeByName(name));
        }
        result += "\n";
        int attributeNum = valueList.size();
        int recordNum = valueList.get(0).col.size();
        for (int i = 0; i < attributeNum; i++) {
            for (int j = 0; j < recordNum; j++) {
                result += valueList.get(j).col.get(i);
                result += "\t";
            }
            result += "\n";
        }
    }

    private void selectAll() {
        // return all attributes
        result += aimTB.createNameRow();
        int num = aimTB.valueList.get(0).col.size();
        for (int i = 0; i < num; i++) {
            result += aimTB.createTableRow(i);
        }
    }

    @Override
    String query() {
        return result;
    }
}
