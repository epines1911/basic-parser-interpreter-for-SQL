package edu.uob;

import java.util.LinkedList;

public class Tokenizer {
    private LinkedList<Token> tokenList;
    private int curIndex = 0;

    public Tokenizer(String command) {
        tokenList = new LinkedList<Token>();
        convertCmdToToken(command);
    }

    public void convertCmdToToken(String command) {
        int i = command.indexOf(";");
        String[] tokens = command.split("\\s+");
        for (String clip : tokens) {
            Token newToken = new Token(clip);
            tokenList.add(newToken);
        }
        tokenList.getFirst().value = tokenList.getFirst().value.toUpperCase();
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
