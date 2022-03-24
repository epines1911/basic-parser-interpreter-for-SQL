package edu.uob;

import java.util.ArrayList;

public class Table {
    private String name;
    int colNum;
    ArrayList<Attribute> valueList = new ArrayList<Attribute>();
    String[] attributesName;

    public Table(int attributeNum) {
        colNum = attributeNum;
        if (attributeNum > 0) {
            attributesName = new String[colNum];
            for (int i = 0; i < attributeNum; i++) {
                valueList.add(new Attribute());
            }
        }
    }

    public void setFirstItemAsNull() {
        for (int i = 0; i < colNum; i++) {
            valueList.get(i).col.set(0, null);
        }
    }

    public void setAttributesName() {
        for (int i = 0; i < colNum; i++) {
            attributesName[i] = (String) valueList.get(i).col.get(0);
        }
    }

    public void setFirstItemByName() {
        for (int i = 0; i < colNum; i++) {
            valueList.get(i).col.set(0, attributesName[i]);
        }
    }

    public String createTableRow(int index) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < colNum; i++) {
            result.append(valueList.get(i).col.get(index));
            if (i == colNum - 1) {result.append("\n");}
            else {result.append("\t");}
        }
        return result.toString();
    }

    public String getName() {
        return name;
    }

    public void setName(String tbName) {
        name = tbName;
    }

    public int getColNum() {
        setColNum();
        return colNum;
    }

    public void setColNum() {
        if (colNum != valueList.size()) colNum = valueList.size();
        // todo 目前是只根据size赋值。要不要开放自定义数字赋值colNum呢？
    }
}
