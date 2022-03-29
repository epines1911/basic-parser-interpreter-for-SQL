package edu.uob;

import java.util.ArrayList;

public class InsertCMD {
    private String tbName;
    private Table aimTB;
    private int valueCounter;
    private ArrayList<String> values = new ArrayList<>();
    public InsertCMD(DBController ctrl, String tableName) {
        tbName = tableName;
        aimTB = ctrl.getCurrentDB().tables.get(tableName);
        valueCounter = 0;
    }

    public void addValue(String value) throws DBException {
        if (aimTB == null) {
            throw new DBException("There is no table named "+tbName);
        }
        values.add(value);
        int insertColNum = aimTB.getColNum() - 1;
        if (values.size() == insertColNum) {
            aimTB.idCounter();
            for (String eachValue :
                    values) {
                aimTB.valueList.get(valueCounter + 1).col.add(eachValue);
                valueCounter += 1;
            }
        }
        if (valueCounter > insertColNum) {
            throw new DBException("The number of value is larger than the number of attributes");
        } //todo value counter less than insert col num
    }
}
