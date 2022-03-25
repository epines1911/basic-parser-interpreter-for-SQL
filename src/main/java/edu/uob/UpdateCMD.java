package edu.uob;

import java.util.ArrayList;

public class UpdateCMD {
    Table aimTB;
    public UpdateCMD(DBController ctrl, ArrayList<NameValuePair> pairs,
                     String tbName, ArrayList<Condition> conditions,
                     boolean isADD) {
        aimTB = (Table) ctrl.getCurrentDB().tables.get(tbName);
        if (conditions == null) {
            // update data without conditions
            updateNoConditions();
        }
    }

    private void updateNoConditions() {
        // todo
    }
}
