package edu.uob;

public class NameValuePair {
    private String attributeName;
    private String value;

    public NameValuePair(String name, String recordValue) {
        attributeName = name;
        value = recordValue;
    }

    public void setAttributeName(String name){
        attributeName = name;
    }

    public String getAttributeName(){
        return attributeName;
    }

    public void setValue(String attributeValue){
        value = attributeValue;
    }

    public String getValue(){
        return value;
    }}
