package edu.uob;

import java.util.ArrayList;

public class UpdateCMD {
    Table aimTB;
    public UpdateCMD(DBController ctrl, ArrayList<NameValuePair> pairs,
                     String tbName, ArrayList<Condition> conditions,
                     boolean isADD) throws DBException {
        aimTB = (Table) ctrl.getCurrentDB().tables.get(tbName);
        if (conditions == null) {
            // update data without conditions
            updateNoConditions(pairs);
        }
    }

    private void updateNoConditions(ArrayList<NameValuePair> pairs) throws DBException {
        boolean isFound = false;
        int colNum = aimTB.getColNum();
        for (NameValuePair pair :
                pairs) {
            String colName = pair.getAttributeName();
            for (int i = 0; i < colNum; i++) {
                Attribute a = aimTB.valueList.get(i);
                if (a.name.equals(colName)) {
                    isFound = true;
                    a.name = pair.getValue();
                }
            }
            if (!isFound) {
                throw new DBException("No attribute in this table named" + colName);
            }
        }
    }
}
