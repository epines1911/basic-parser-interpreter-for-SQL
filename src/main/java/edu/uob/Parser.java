package edu.uob;

import java.util.ArrayList;
import java.util.Objects;

public class Parser {
    private Tokenizer tokenizer;
    private DBController ctrl;
    //todo 我看图是要传server进来用，但我其实不理解为啥要传这个进来。也没想明白传进来怎么用它。
    //主要是为了返回【ok】和调用server里的database。我搞了个controller替代这些东西
    private String message;

    public Parser(DBController controller, String command) {
        tokenizer = new Tokenizer(command);
        ctrl = controller; //todo 我看图是要传server进来用，但我其实不理解为啥要传这个进来。也没想明白传进来怎么用它。
        message = "";
    }

    public void parse() throws DBException {
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
                System.out.println("???");
//                new SelectCMD();
            case "UPDATE":
                new UpdateCMD();
            case "DELETE":
                new DeleteCMD();
            case "JOIN":
                new JoinCMD();
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

    private void parseCreateCmd(Token t) throws DBException {
        // t should be "DATABASE" or "TABLE"
        t.value.toUpperCase();
        if (t.value.equals("DATABASE")) {
            t = tokenizer.nextToken(); // t should be <DatabaseName>
            if (t.isPlainText()) {
                new CreateCMD(ctrl, t.value, null, true);
            } else throw new DBException("Database name is invalid");
        } else if (t.value.equals("TABLE")) {
            t = tokenizer.nextToken(); // t should be <TableName>
            if (t.isPlainText()) {
                parseTableName(t);
            } else throw new DBException("Table name is invalid");
        } else throw new DBException("No 'DATABASE' or 'TABLE'?");
    }

    private void parseTableName(Token t) throws DBException {
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

    private void parseAttributeList(Token t, ArrayList attribute, String tableName) throws DBException {
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
        t.value.toUpperCase();
        if (t.value.equals("DATABASE")) {
            t = tokenizer.nextToken(); // t should be <DatabaseName>
            if (t.isPlainText()) {
                new DropCMD(ctrl, t.value, true);
            } else throw new DBException("Database name is invalid");
        } else if (t.value.equals("TABLE")) {
            t = tokenizer.nextToken(); // t should be <TableName>
            if (t.isPlainText()) {
                new DropCMD(ctrl, t.value, false);
            } else throw new DBException("Table name is invalid");
        } else throw new DBException("No 'DATABASE' or 'TABLE'?");
    }

    private void parseAlterCmd(Token t) throws DBException {
        // t should be "TABLE"
        t.value.toUpperCase();
        if (t.value.equals("TABLE")) {
            t = tokenizer.nextToken(); // t should be <TableName>
            if (t.isPlainText()) {
                String tableName = t.value;
                t = tokenizer.nextToken(); // t should be <AlterationType>
                t.value.toUpperCase();
                if (t.value.equals("ADD")) {
                    t = tokenizer.nextToken(); // t should be <AttributeName>
                    if (t.isPlainText()) {
                        new AlterCMD(ctrl, tableName, t, true);
                    } else throw new DBException("Attribute name is invalid");
                } else if (t.value.equals("DROP")) {
                    if (t.isPlainText()) {
                        new AlterCMD(ctrl, tableName, t, false);
                    } else throw new DBException("Attribute name is invalid");
                } else throw new DBException("No 'ADD' or 'DROP'?");
            } else throw new DBException("Table name is invalid");
        } else throw new DBException("No 'TABLE'?");
    }

    private void parseInsertCmd(Token t) throws DBException {
        // t should be "INTO"
        t.value.toUpperCase();
        if (t.value.equals("INTO")) {
            t = tokenizer.nextToken(); // t should be <TableName>
            if (t.isPlainText()) {
                t = tokenizer.nextToken(); // t should be "VALUES"
                t.value.toUpperCase();
                // todo 我没有成功在tokenizer里搞定"VALUE("这个东西。或许变成两个token也没关系。先按两个token写了。
                if (t.isPlainText() && t.value.equals("VALUES")) {
                    t = tokenizer.nextToken(); // t should be "("
                    if (t.value.equals("(")) {
                        t = tokenizer.nextToken(); // t should be <ValueList>
                        parseValueList(t);
                    } else throw new DBException("No '('?");
                } else throw new DBException("No 'VALUES('?");
            } else throw new DBException("Table name is invalid");
        } else throw new DBException("No 'INTO'?");
    }

    private void parseValueList(Token t) {
//        t should be <ValueList>
// todo <ValueList> ::=  <Value> | <Value> "," <ValueList>
// todo <Value> ::= "'" <StringLiteral> "'" | <BooleanLiteral> | <FloatLiteral> | <IntegerLiteral> | "NULL"
    }

    public String getMessage() {
        return message;
    }
}
