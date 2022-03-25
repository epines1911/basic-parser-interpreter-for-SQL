package edu.uob;

import java.util.ArrayList;

public class UpdateCMD {
    Object aimTB;
    public UpdateCMD(DBController ctrl, ArrayList<NameValuePair> pairs, String tbName) {
        aimTB = ctrl.getCurrentDB().tables.get(tbName);
    }
}
