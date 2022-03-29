package edu.uob;

import java.util.ArrayList;

public class DeleteCMD {
    private Table aimTB;

    public DeleteCMD(DBController ctrl, String tbName,
                     ArrayList<Condition> conditions, boolean isADD) throws DBException {
        aimTB = ctrl.getCurrentDB().tables.get(tbName);
        matchValue(conditions, isADD);
    }

    private void matchValue(ArrayList<Condition> conditions, boolean isADD) throws DBException {
        int colNum = aimTB.getColNum();
        for (Condition condition :
                conditions) {
            boolean isFound = false;
            String colName = condition.getAttributeName();
            for (int i = 0; i < colNum; i++) {
                Attribute a = aimTB.valueList.get(i);
                if (a.name.equals(colName)) {
                    isFound = true;
                    // todo
                }
            }
            if (!isFound) {
                throw new DBException("No attribute in this table named" + colName);
            }
        }
    }
}
