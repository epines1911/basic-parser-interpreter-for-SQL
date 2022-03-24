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
        newAttribute.name = colName;
        tb.valueList.add(newAttribute);
        tb.setColNum();
        tb.setFirstItemAsNull(); //todo 在这里有必要这么做吗？不这么做，那就需要把Attribute的第一个元素赋值为colName
    }

    private void deleteCol(Table tb, String colName) throws DBException {
        Boolean isFound = false;
        for (Attribute col:
             tb.valueList) {
            if (col.name.equals(colName)) {
                isFound = true;
                tb.valueList.remove(col);
                tb.setColNum();
            }
        }
        if (!isFound) {
            throw new DBException("There is no Attribute named " + colName);
        }

    }
}
