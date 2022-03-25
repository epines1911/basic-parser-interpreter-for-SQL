package edu.uob;

public class AlterCMD {
    // if isADD is true, then add a column in a table. Otherwise, delete a column in a table
    public AlterCMD(DBController ctrl, String tbName, Token attributeName, Boolean isADD) throws DBException {
        Table tb = (Table) ctrl.getCurrentDB().tables.get(tbName);
        if (isADD) { addCol(tb, attributeName.value);}
        else { deleteCol(tb, attributeName.value);}
    }

    private void addCol(Table tb, String colName) {
        Attribute newAttribute = new Attribute();
        int recordsNum = tb.valueList.get(0).col.size();
        for (int i = 0; i < recordsNum; i++) {
            newAttribute.col.add("");
        }
        newAttribute.name = colName;
        tb.valueList.add(newAttribute);
        tb.setColNum();
        tb.setAttributesName();
    }

    private void deleteCol(Table tb, String colName) throws DBException {
        Boolean isFound = false;
        for (int i = 0; i < tb.valueList.size(); i++) {
            String name = tb.valueList.get(i).name;
            if (name.equals(colName)) {
                isFound = true;
                tb.valueList.remove(i);
                tb.setColNum();
                tb.deleteAttributeName(colName);
            }
        }
        if (!isFound) {
            throw new DBException("There is no Attribute named " + colName);
        }

    }
}
