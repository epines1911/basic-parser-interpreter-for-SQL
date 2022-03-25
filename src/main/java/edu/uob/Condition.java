package edu.uob;

public class Condition {
    private String attributeName;
    private String operator;
    private String value;

    public Condition(String name, String op, String attributeValue) {
        attributeName = name;
        operator = op;
        value = attributeValue;
    }

    public void setAttributeName(String name){
        attributeName = name;
    }

    public String getAttributeName(){
        return attributeName;
    }

    public void setOperator(String operatorSymbol){
        operator = operatorSymbol;
    }

    public String getOperator(){
        return operator;
    }

    public void setValue(String attributeValue){
        value = attributeValue;
    }

    public String getValue(){
        return value;
    }
}
