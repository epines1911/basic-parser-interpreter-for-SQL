package edu.uob;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class Parser {
    private Tokenizer tokenizer;
    private DBController ctrl;
    //todo 主要是为了返回【ok】和调用server里的database。我搞了个controller替代这些东西
    private String message;

    public Parser(DBController controller, String command) {
        if (controller != null && command != null) {
            tokenizer = new Tokenizer(command);
            ctrl = controller;
        }
        message = "";
    }

    public void parse() throws DBException, IOException {
        if (!Objects.equals(tokenizer.lastToken().value, ";")) {
            throw new DBException("Missing semicolon");
        }
        //todo???
        String commandType = tokenizer.firstToken().value;
        switch (commandType) {
            case "USE":
                parseUseCmd(tokenizer.nextToken());
                break;
            case "CREATE":
                parseCreateCmd(tokenizer.nextToken());
                break;
            case "DROP":
                parseDropCmd(tokenizer.nextToken());
                break;
            case "ALTER":
                parseAlterCmd(tokenizer.nextToken());
                break;
            case "INSERT":
                parseInsertCmd(tokenizer.nextToken());
                break;
            case "SELECT":
                parseSelectCmd(tokenizer.nextToken());
                break;
                //todo
            case "UPDATE":
                parseUpdateCmd(tokenizer.nextToken());
                break;
            case "DELETE":
                parseDeleteCmd(tokenizer.nextToken());
                break;
//            case "JOIN":
//                new JoinCMD();
            default:
                throw new DBException("The first word in command is wrong");
        }
    }

    private void parseUseCmd(Token t) throws DBException {
        // t should be <DatabaseName>
        if (t.isPlainText()) {
            new UseCMD(ctrl, t);
        } else throw new DBException("Database name should be plain text");
    }

    private void parseCreateCmd(Token t) throws DBException, IOException {
        // t should be "DATABASE" or "TABLE"
        if (t.value.equalsIgnoreCase("DATABASE")) {
            t = tokenizer.nextToken(); // t should be <DatabaseName>
            if (t.isPlainText()) {
                new CreateCMD(ctrl, t.value, null, true);
            } else throw new DBException("Database name is invalid");
        } else if (t.value.equalsIgnoreCase("TABLE")) {
            t = tokenizer.nextToken(); // t should be <TableName>
            if (t.isPlainText()) {
                parseTableName(t);
            } else throw new DBException("Table name is invalid");
        } else throw new DBException("No 'DATABASE' or 'TABLE'?");
    }

    private void parseTableName(Token t) throws DBException, IOException {
        // t should be <TableName>
        String tableName = t.value;
        t = tokenizer.nextToken(); // t should be ";" or "("
        if (t.value.equals("(")) {
            t = tokenizer.nextToken(); // t should be <AttributeList>
            ArrayList<String> colList = new ArrayList<String>();
            parseAttributeList(t, colList, tableName);
        } else if (t.value.equals(";")) {
            new CreateCMD(ctrl, tableName, null, false);
        } else throw new DBException("Missing '('?");
    }

    private void parseAttributeList(Token t, ArrayList<String> attribute, String tableName) throws DBException, IOException {
        // t should be <AttributeList> -- <AttributeName>
        if (t.isPlainText()) {
            attribute.add(t.value);
            t = tokenizer.nextToken(); // t should be "," or ")"
            if (t.value.equals(",")) {
                t = tokenizer.nextToken(); // t should be <AttributeName>
                parseAttributeList(t, attribute, tableName);
            } else if (t.value.equals(")")) {
                new CreateCMD(ctrl, tableName, attribute, false);
            } else throw new DBException("No symbol ',' or ')' ?");
        } else throw new DBException("Attribute name should be plain text");
    }

    private void parseDropCmd(Token t) throws DBException {
        // t should be "DATABASE" or "TABLE"
        if (t.value.equalsIgnoreCase("DATABASE")) {
            t = tokenizer.nextToken(); // t should be <DatabaseName>
            if (t.isPlainText()) {
                new DropCMD(ctrl, t.value, true);
            } else throw new DBException("Database name is invalid");
        } else if (t.value.equalsIgnoreCase("TABLE")) {
            t = tokenizer.nextToken(); // t should be <TableName>
            if (t.isPlainText()) {
                new DropCMD(ctrl, t.value, false);
            } else throw new DBException("Table name is invalid");
        } else throw new DBException("No 'DATABASE' or 'TABLE'?");
    }

    private void parseAlterCmd(Token t) throws DBException {
        // t should be "TABLE"
        if (t.value.equalsIgnoreCase("TABLE")) {
            t = tokenizer.nextToken(); // t should be <TableName>
            if (t.isPlainText()) {
                String tableName = t.value;
                t = tokenizer.nextToken(); // t should be <AlterationType>
                if (t.value.equalsIgnoreCase("ADD")) {
                    t = tokenizer.nextToken(); // t should be <AttributeName>
                    if (t.isPlainText()) {
                        new AlterCMD(ctrl, tableName, t, true);
                    } else throw new DBException("Attribute name is invalid");
                } else if (t.value.equalsIgnoreCase("DROP")) {
                    t = tokenizer.nextToken(); // t should be <AttributeName>
                    if (t.isPlainText()) {
                        new AlterCMD(ctrl, tableName, t, false);
                    } else throw new DBException("Attribute name is invalid");
                } else throw new DBException("No 'ADD' or 'DROP'?");
            } else throw new DBException("Table name is invalid");
        } else throw new DBException("No 'TABLE'?");
    }

    private void parseInsertCmd(Token t) throws DBException {
        // t should be "INTO"
        if (t.value.equalsIgnoreCase("INTO")) {
            t = tokenizer.nextToken(); // t should be <TableName>
            if (t.isPlainText()) {
                String tableName = t.value;
                t = tokenizer.nextToken(); // t should be "VALUES"
                if (t.isPlainText() && t.value.equalsIgnoreCase("VALUES")) {
                    t = tokenizer.nextToken(); // t should be "("
                    if (t.value.equals("(")) {
                        t = tokenizer.nextToken(); // t should be <ValueList>
                        InsertCMD insertCMD = new InsertCMD(ctrl, tableName);
                        parseValueList(t, insertCMD);
                    } else throw new DBException("No '('?");
                } else throw new DBException("No 'VALUES('?");
            } else throw new DBException("Table name is invalid");
        } else throw new DBException("No 'INTO'?");
    }

    private void parseValueList(Token t, InsertCMD cmd) throws DBException {
//        t should be <ValueList> -- <Value>
        if (t.isValue()) {
            cmd.addValue(t.removeQuoteInToken());
            t = tokenizer.nextToken(); // t should be "," or ")"
            if (t.value.equals(",")) {
                t = tokenizer.nextToken(); // t should be <ValueList>
                parseValueList(t, cmd);
            } else if (t.value.equals(")")) {
            } else throw new DBException("No ','?");
        } else throw new DBException("The format of value is wrong, please check it");
    }

    private void parseSelectCmd(Token t) throws DBException {
        // t should be <WildAttribList> -- <AttributeList> or "*"
        ArrayList<String> attribNames = new ArrayList<>();
        String tableName;
        boolean isADD = false;
        if (t.isPlainText()) {
            collectAttribName(attribNames, t);
            // t should be 'FROM' when it goes back
            t = tokenizer.nextToken(); // t should be TableName
            if (t.isPlainText()) {
                tableName = t.value;
                t = tokenizer.nextToken(); // t should be 'WHERE' or ';'
                if (t.value.equalsIgnoreCase("WHERE")) {
                    t = tokenizer.nextToken(); // t should be <Condition>
                    if (t.isPlainText() || t.value.equals("(")) {
                        parseCondition(t, tableName, attribNames, isADD, null);
                    } else throw new DBException("The format of condition is wrong");
                } else if (t.value.equals(";")) {
                    message = new SelectCMD(ctrl, tableName, attribNames,
                            null, false).query();
                } else throw new DBException("No 'WHERE' or ';'?");
            } else throw new DBException("Table name is invalid");
        } else if (t.value.equals("*")) {
            t = tokenizer.nextToken(); // t should be 'FROM'
            if (t.value.equalsIgnoreCase("FROM")) {
                t = tokenizer.nextToken(); // t should be TableName
                if (t.isPlainText()) {
                    tableName = t.value;
                    t = tokenizer.nextToken(); // t should be 'WHERE' or ';'
                    if (t.value.equalsIgnoreCase("WHERE")) {
                        t = tokenizer.nextToken(); // t should be <Condition>
                        if (t.isPlainText() || t.value.equals("(")) {
                            parseCondition(t, tableName, attribNames, isADD, null);
                        } else throw new DBException("The format of condition is wrong");
                    } else if (t.value.equals(";")) {
                        message = new SelectCMD(ctrl, tableName, attribNames,
                                null, false).query();
                    } else throw new DBException("No 'WHERE' or ';'?");
                }
            } else throw new DBException("No 'FROM'?");
        } else throw new DBException("No Attribute name or '*'?");
    }

    private void collectAttribName(ArrayList<String> attributeNames, Token t) throws DBException {
        // t should be AttributeName
        attributeNames.add(t.value);
        t = tokenizer.nextToken(); // t should be ',' or 'FROM'
        if (t.value.equals(",")) {
            t = tokenizer.nextToken(); // t should be <AttributeName>
            if (t.isPlainText()) {
                collectAttribName(attributeNames, t);
            } else throw new DBException("Attribute name is invalid");
        } else if (t.value.equalsIgnoreCase("FROM")) return;
        else throw new DBException("No ',' or 'FROM'?");
    }

    private void parseCondition(Token t, String tbName,
                                ArrayList<String> names, boolean isADD,
                                ArrayList<NameValuePair> pairs) throws DBException {
        ArrayList<Condition> conditions = new ArrayList<>();
        // t should be ( or AttributeName
        if (t.value.equals("(")) {
            t = tokenizer.nextToken(); // t should be AttributeName
            if(t.isPlainText()) {
                parseCondition(t, tbName, names, isADD, null);
            } else throw new DBException("Attribute name is invalid");
        } else if (t.isPlainText()) {
            Condition c = new Condition(t.value, null, null);
            // Now t should be Attribute Name
            t = tokenizer.nextToken(); // t should be Operator
            if (t.isOperator()) {
                c.setOperator(t.value);
                t = tokenizer.nextToken(); // t should be value
                if (t.isValue()) {
                    c.setValue(t.value);
                    conditions.add(c);
                    t = tokenizer.nextToken(); // t should be ) or ;
                    if (t.value.equals(")")) {
                        t = tokenizer.nextToken(); // t should be AND or OR or ;
                        if (t.value.equalsIgnoreCase("AND")) {
                            parseCondition(tokenizer.nextToken(), tbName, names, true, null);
                        } else if (t.value.equalsIgnoreCase("OR")) {
                            parseCondition(tokenizer.nextToken(), tbName, names, false, null);
                        } else if (t.value.equals(";")) {
                            if (names != null && pairs == null) {
                                message = new SelectCMD(ctrl, tbName, names, conditions, isADD).query();
                            } else if (pairs != null){
                                new UpdateCMD(ctrl, pairs, tbName, conditions, isADD);
                            } else {
                                //delete
                            }
                        } else throw new DBException("No AND or OR?");
                    } else if (t.value.equals(";")) {
                        if (names != null && pairs == null) {
                            message = new SelectCMD(ctrl, tbName, names, conditions, isADD).query();
                        } else if (pairs != null){
                            new UpdateCMD(ctrl, pairs, tbName, conditions, isADD);
                        } else {
                            //delete
                        }
                    } else throw new DBException("No ')'?");
                } else throw new DBException("Value is invalid");
            } else throw new DBException("Operator is invalid");
        } else throw new DBException("No ( or Attribute name?");

    }

    private void parseUpdateCmd(Token t) throws DBException {
        // t should be TableName
        if (t.isPlainText()) {
            String tbName = t.value;
            t = tokenizer.nextToken(); // t should be SET
            if (t.value.equalsIgnoreCase("SET")) {
                t = tokenizer.nextToken(); // t should be NameValueList
                parseNameValuePair(t, tbName);
            } else throw new DBException("No 'SET'?");
        } else throw new DBException("Table name is invalid");
    }

    private void parseNameValuePair(Token t, String tbName) throws DBException {
        // t should be NameValueList -- NameValuePair -- AttributeName
        if (t.isPlainText()) {
            ArrayList<NameValuePair> pairs = new ArrayList<>();
            String attribName = t.value;
            t = tokenizer.nextToken(); // t should be "="
            if (t.value.equals("=")) {
                t = tokenizer.nextToken(); // t should be Value
                if (t.isValue()) {
                    NameValuePair pair = new NameValuePair(attribName, t.value);
                    pairs.add(pair);
                    t = tokenizer.nextToken(); // t should be ',' or 'WHERE'
                    if (t.value.equals(",")) {
                        parseNameValuePair(tokenizer.nextToken(), tbName);
                    } else if (t.value.equalsIgnoreCase("WHERE")) {
                        parseCondition(tokenizer.nextToken(), tbName,
                                null, false, pairs);
                    } else throw new DBException("No ','?");
                } else throw new DBException("The format of Value is wrong");
            } else throw new DBException("No '='?");
        } else throw new DBException("Attribute name is invalid");
    }

    private void parseDeleteCmd(Token t) throws DBException {
        // t should be 'FROM'
        if (t.value.equalsIgnoreCase("FROM")) {
            t = tokenizer.nextToken(); // t should be TableName
            if (t.isPlainText()) {
                String tbName = t.value;
                t = tokenizer.nextToken(); // t should be WHERE
                if (t.value.equalsIgnoreCase("WHERE")) {
                    parseCondition(tokenizer.nextToken(), tbName, null, false, null);
                } else throw new DBException("No 'WHERE'?");
            } else throw new DBException("Table name is invalid");
        } else throw new DBException("No 'FROM'?");
    }

    public String getMessage() {
//        System.out.println(message); //todo for test
        return message;
    }
}
