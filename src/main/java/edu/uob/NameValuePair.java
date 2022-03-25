package edu.uob;

import java.util.LinkedList;

public class NameValuePair {
    private String attributeName;
    private String value;

    public NameValuePair(String name, String newName) {
        attributeName = name;
        value = newName;
    }

    public void setAttributeName(String name){
        attributeName = name;
    }

    public String getAttributeName(){
        return attributeName;
    }

    public void setValue(String newName){
        value = newName;
    }

    public String getValue(){
        return value;
    }
}
