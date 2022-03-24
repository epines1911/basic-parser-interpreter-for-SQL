package edu.uob;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Tokenizer {
    private LinkedList<Token> tokenList;
    private int curIndex = 0;

    public Tokenizer(String command) {
        tokenList = new LinkedList<Token>();
        convertCmdToToken(command);
    }

    public void convertCmdToToken(String command) {
        int i = command.indexOf(";"); //todo maybe could delete? I forget why I set this int.
        String[] tokens = command.split("\\s+");
        //todo add new feature: value( -> value (
        //todo 记得还有其他类似的情况，并且把这部分拆出去单独做个function。
        ArrayList<String> tokenArrList = new ArrayList<>(List.of(tokens));
        for (int j = 0; j < tokenArrList.size() - 1; j++) {
            String clip = tokenArrList.get(j);
            if (clip.matches("[VALUE\\(]")) {
                String[] clips = clip.split("\\(");
                //todo 把当前的VALUE(set成（，然后再在这个位置添加VALUE，那么（将自动延后一个。顺序就对了。没test过记得test
                tokenArrList.set(j, clips[1]);
                tokenArrList.add(j, clips[0]);
            }
        }
        // todo this is original version:
        for (String clip : tokenArrList) {
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
