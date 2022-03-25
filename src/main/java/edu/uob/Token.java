package edu.uob;

public class Token {
    String value;

    public Token(String clip) {
        value = clip;
    }
    
    public boolean isPlainText() {
        if (value == null) {return false;}
        char[] chars = value.toCharArray();
        for (char character :
                chars) {
            if (!Character.isLetterOrDigit(character)) {
                return false;
            }
        }
        return true;
    }

    public boolean isStringLiteral() {
        if (value == null) {return false;}
        String strForCheck = value;
        // ^['] means the first character is '
        // [\\s]* means there is 0 or more space in the string
        // [a-zA-Z]* means there is 0 or more letters in the string
        // [^\"\']* means there is 0 or more symbols in the string,
        // and these symbols won't contain " and '
        // [']$ means the string ends with '
        return strForCheck.matches("^['][\\s]*[a-zA-Z]*[^\"\']*[']$");
    }

    public boolean isBoolLiteral() {
        if (value == null) {return false;}
        String boolLiteral = value.toUpperCase();
        return boolLiteral.equalsIgnoreCase("TRUE")
                || boolLiteral.equalsIgnoreCase("FALSE");
    }

    public boolean isFloatLiberal() {
        if (value == null) {return false;}
        String strForCheck = value;
        return strForCheck.matches("^[+-]?[0-9]+\\.[0-9]+$");
    }

    public boolean isIntegerLiberal() {
        if (value == null) {return false;}
        String strForCheck = value;
        return strForCheck.matches("^[+-]?[0-9]+$");
    }

    public boolean isValue() {
        return isStringLiteral() || isBoolLiteral() || isFloatLiberal()
                || isIntegerLiberal() || value.equalsIgnoreCase("NULL");
    }

    public String removeQuoteInToken() {
        String strForRemove = value;
        if (this.isStringLiteral()) {
            int length = strForRemove.length();
            strForRemove = strForRemove.substring(1, length - 1);
        }
        return strForRemove;
    }

    public boolean isOperator() {
        if (value == null) return false;
        return value.equals("==") || value.equals(">") || value.equals("<")
                || value.equals(">=") || value.equals("<=") || value.equals("!=")
                || value.equalsIgnoreCase("LIKE");
    }


}
