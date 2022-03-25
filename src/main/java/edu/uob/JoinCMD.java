package edu.uob;

import java.util.LinkedList;

public class JoinCMD extends DBcmd{
    private Table tb1;
    private Table tb2;
    private Attribute column1;
    private Attribute column2;
    private Table resultTB;
    private LinkedList<Integer> indexList;
    private String result;

    public JoinCMD(DBController ctrl, String tbName1,
                   String tbName2, String aName1, String aName2) throws DBException {
        tb1 = (Table) ctrl.getCurrentDB().tables.get(tbName1);
        tb2 = (Table) ctrl.getCurrentDB().tables.get(tbName2);
        resultTB = tb1;
        resultTB.setName("join");
        column1 = setAttribute(tb1, aName1);
        column2 = setAttribute(tb2, aName2);
        indexList = new LinkedList<>();
        result = "";
        builtResult(aName1);
    }

    private Attribute setAttribute(Table tb, String attributeName) throws DBException {
        tb.setAttributesName();
        int colNum = tb.getColNum();
        Attribute a = null;
        boolean isFound = false;
        for (int i = 0; i < colNum; i++) {
            if(tb.valueList.get(i).name.equals(attributeName)) {
                isFound = true;
                a = tb.valueList.get(i);
            }
        }
        if (!isFound) {
            throw new DBException("Cannot find attribute named " + attributeName);
        }
        return a;
    }

    private void builtResult(String colNameForDel) throws DBException {
        matchValues();
        int colNum = tb2.getColNum();
        for (int i = 1; i < colNum; i++) {
            resultTB.valueList.add(copyAttribute(i));
        }
        deleteRepeatCol(resultTB, colNameForDel);
        result += resultTB.createNameRow();
        int resultNum = resultTB.valueList.get(0).col.size();
        for (int j = 0; j < resultNum; j++) {
            result += resultTB.createTableRow(j);
        }
    }

    private void matchValues() throws DBException {
        for (String value1 :
                column1.col) {
            int i = column2.col.indexOf(value1);
            if (i == -1) {
                throw new DBException("The two attributes cannot match");
            } else {
                indexList.add(i);
            }
        }
    }

    private void deleteRepeatCol(Table tb, String colName) throws DBException {
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

    private Attribute copyAttribute(int attributeNo) throws DBException {
        Attribute a = new Attribute();
        int colNum = tb2.getColNum();
        int size = tb2.valueList.get(0).col.size();
        if (attributeNo <= colNum) {
            for (int i = 0; i < size; i++) {
                int index = indexList.get(i);
                Attribute origin = tb2.valueList.get(attributeNo);
                a.name = origin.name;
                String value = origin.col.get(index);
                a.col.add(value);
            }
        } else throw new DBException("There is no more attribute in this table");
        return a;
    }

    @Override
    String query() {
        return result;
    }
}
