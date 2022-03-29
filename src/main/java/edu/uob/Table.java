package edu.uob;

import java.util.ArrayList;

public class Table {
    private String name;
    int colNum;
    public ArrayList<Attribute> valueList = new ArrayList<>();
    private ArrayList<String> attributesName;
    private int id = 0;

    public Table(int attributeNum) {
        colNum = attributeNum;
        if (attributeNum > 0) {
            attributesName = new ArrayList<>();
            setIDCol();
            for (int i = 0; i < attributeNum; i++) {
                valueList.add(new Attribute());
            }
        }
    }

    private void setIDCol() {
        Attribute id = new Attribute();
        id.setName("id");
        valueList.add(0, id);
    }

    public void idCounter() {
        id += 1;
        if (valueList.get(0).getName().equalsIgnoreCase("id")) {
            valueList.get(0).col.add(String.valueOf(id));
        }
    }

    public int getID() {
        return id;
    }

    public void zeroID() {
        id = 0;
    }

    public void setFirstItemAsNull() {
        setColNum();
        for (int i = 0; i < colNum; i++) {
            valueList.get(i).col.set(0, null);
        }
    }

    public void setAttributesName() {
        setColNum();
        for (int i = 0; i < colNum; i++) {
            if (i < attributesName.size()) {
                attributesName.set(i, valueList.get(i).getName());
            } else {
                attributesName.add(i, valueList.get(i).getName());
            }
        }
    }

    public void deleteAttributeName(String name) {
        setColNum();
        attributesName.remove(name);
    }

    public void setFirstItemByName() {
        for (int i = 0; i < colNum; i++) {
            valueList.get(i).col.set(0, attributesName.get(i));
        }
    }

    public String createTableRow(int index) {
        setColNum();
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < colNum; i++) {
            if (valueList.get(i).col.get(index).isEmpty()) {
                result.append(" ");
            } else {
                result.append(valueList.get(i).col.get(index));
            }
            if (i == colNum - 1) {result.append("\n");}
            else {result.append("\t");}
        }
        return result.toString();
    }

    public String createNameRow() {
        StringBuilder result = new StringBuilder();
        setAttributesName();
        if (attributesName.size() > 0) {
            for (String name :
                    attributesName) {
                result.append(name);
                result.append("\t");
            }
        }
        result.append("\n");
        return result.toString();
    }
//todo
    public String newLimitRecord() {
        StringBuilder result = new StringBuilder();
        return result.toString();
    }

    public void newLimitAttrib() {
        // for selecting some columns and make a new table
    }

    public Attribute getAttributeByName(String name) throws DBException {
        setColNum();
        boolean isFound = false;
        for (int i = 0; i < colNum; i++) {
            Attribute a = valueList.get(i);
            if (a.getName().equals(name)) {
                isFound = true;
                return a;
            }
        }
        if (!isFound) {
            throw new DBException("No attribute in this table named " + name);
        }
        return null;
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
    }
}
