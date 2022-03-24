package edu.uob;

import java.util.ArrayList;

public class Token {
    String value;
    private ArrayList<Integer> ASCIIList;
    private TokenType type; // todo maybe delete

    public Token(String clip) {
        //todo
        value = clip;
    }

    private void setASCIIList() {
        if (value != null) {
            ASCIIList = new ArrayList<Integer>();
            char[] chars = value.toCharArray();
            for (char character : chars) {
                ASCIIList.add((int) character);
            }
        }
    }
    // todo maybe delete
    public TokenType getType() {
        return type;
    }
    // todo maybe delete
    public void setType() {
        // todo analyze the type of token using ASCII, and change the value of this.type
    }
    
    public boolean isPlainText() {
        if (value != null) {
            char[] chars = value.toCharArray();
            for (char character :
                    chars) {
                if (!Character.isLetterOrDigit(character)) {
                    return false;
                }
            }
        } else {return false;}
        return true;
    }

    public boolean isUpperCase() { //todo 似乎是create这种commandType不需要强制全大写，create database这俩也不需要强制全大写。所以这个功能可能用不到了
        if (value != null) {
            char[] chars = value.toCharArray();
            for (char character :
                    chars) {
                if (!Character.isUpperCase(character)) {
                    return false;
                }
            }
        } else {return false;}
        return true;
    }

    public boolean isStringLiteral() {
        //todo
        return false;
    }

    public boolean isBoolLiteral() {
        String boolLiteral = value.toUpperCase();
        return boolLiteral.equals("TRUE") || boolLiteral.equals("FALSE");
    }

    public boolean

}
