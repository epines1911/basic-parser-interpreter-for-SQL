package edu.uob;

import java.util.*;

public class Tokenizer {
    private LinkedList<Token> tokenList;
    private int curIndex = 0;

    public Tokenizer(String command) {
        tokenList = new LinkedList<Token>();
        convertCmdToToken(command);
    }

    public void convertCmdToToken(String command) {
        String modifiedCmd = modifySymbolInStr(command);
        String[] tokens = modifiedCmd.split("\\s+");
        for (String clip : tokens) {
            Token newToken = new Token(clip);
            tokenList.add(newToken);
        }
        if (tokenList.size() > 0) {
            tokenList.getFirst().value = tokenList.getFirst().value.toUpperCase();
        }
        //todo token value for test. delete:
//        for (int i = 0; i < tokenList.size(); i++) {
//            System.out.println("token value: " + tokenList.get(i).value);
//        }
    }

    private String modifySymbolInStr(String aimString) {
        char[] chars = aimString.toCharArray();
        ArrayList<Character> copyChars = new ArrayList<>();
        for (char character : chars) {
            copyChars.add(character);
        }
        LinkedList<Integer> singleQuotaNum = new LinkedList<>();
        for (int j = 0; j < copyChars.size(); j++) {
            char currentChar = copyChars.get(j);
            if (currentChar == '\'') {
                singleQuotaNum.add(j);
                if (singleQuotaNum.size() == 2) {
                    singleQuotaNum.clear();
                }
            }
            if (singleQuotaNum.size() == 0 &&
                    (currentChar == ',' || currentChar == '(' || currentChar == ')'
                    || currentChar == ';')) {
                copyChars.add(j, ' ');
                copyChars.add(j+2, ' ');
                j += 1;
            } else if (singleQuotaNum.size() == 0 &&
                    (currentChar == '<' || currentChar == '>' || currentChar == '='
                            || currentChar == '!')) {
                if (j+1 < copyChars.size() && copyChars.get(j+1) == '=') {
                    // There is a '>=' or '<='
                    copyChars.add(j, ' ');
                    copyChars.add(j+3, ' ');
                } else {
                    // There is a single operator < or >
                    copyChars.add(j, ' ');
                    copyChars.add(j+2, ' ');
                }
                j += 2;
            }
        }
        String result = "";
        for (char newCharacter : copyChars) result += newCharacter;
        return result;
    }

    private void modifyOperator() {
        //
    }

    public Token nextToken() {
        curIndex += 1; //todo 这个每次调用就+1+1好像有个class的属性或者method能实现？
        if (curIndex < tokenList.size()) {return tokenList.get(curIndex);}
        else {curIndex = tokenList.size() - 1;}
        return null;
    }

    public Token firstToken() {
        return tokenList.getFirst();
    }

    public Token lastToken() {
        return tokenList.getLast();
    }
}
