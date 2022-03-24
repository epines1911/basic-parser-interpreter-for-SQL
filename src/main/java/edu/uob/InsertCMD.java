package edu.uob;

public class InsertCMD {
    String tbName;
    Table aimTB;
    int valueCounter;
    public InsertCMD(DBController ctrl, String tableName) {
        tbName = tableName;
        aimTB = (Table) ctrl.getCurrentDB().tables.get(tableName);
        valueCounter = 0;
    }

    public void addValue(String value) throws DBException {
        if (aimTB == null) {
            throw new DBException("There is no table named "+tbName);
        }
        int colNum = aimTB.getColNum();
        if (valueCounter < colNum) {
            aimTB.valueList.get(valueCounter).col.add(value);
            valueCounter += 1;
        } else throw new DBException("The number of value is larger than the number of attributes");
    }
}
