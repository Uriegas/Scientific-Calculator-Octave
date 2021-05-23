package com.spolancom;

import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * Simple tokenizer implementing regex
 * It takes a string and divides it into tokens
 * It works with multiple variables
 */
public class Tokenizer {
    private LinkedList<Token> tokens;
    private LinkedList<TknMeaning> tknmeaning;
    public Tokenizer(){
        tokens = new LinkedList<Token>();
        tknmeaning = new LinkedList<TknMeaning>();
        //Defining tokens meaning (Tokens grammar)
        tknmeaning.add(new TknMeaning(1, "\\+|-"));
        tknmeaning.add(new TknMeaning(2, "\\*|/"));
        tknmeaning.add(new TknMeaning(3, "\\("));
        tknmeaning.add(new TknMeaning(4, "\\)"));
        tknmeaning.add(new TknMeaning(5, "sin|cos|tan|sqrt|\\^"));
        tknmeaning.add(new TknMeaning(6, "[0-9]+"));//Warning: maybe wrong
        tknmeaning.add(new TknMeaning(7, "[a-zA-Z][a-zA-Z0-9_]*"));
    }
    /**
     * Algorithm to convert a strin into tokens
     * Implements regex analysis
     * @param s
     */
    public void tokenize(String st){
        String s = st; //Because of internal mods
        //Init tokens
        tokens.clear();
        //Remove white spaces
        s = s.trim();
        //Removes implicit multiplication converting to explicit
        //Ex. [2x+xy] --> [2 * x + x * y]
        //s = s.replaceAll("(\\d+|\\))\\w", "$1*\\w"); //\\w not working
        while(!s.isEmpty()){
            boolean match = false;
            for(TknMeaning tknm : tknmeaning){//Search for every token defined
                Matcher m = Pattern.compile(tknm.getRegex()).matcher(s);//Ugly syntax
                if(m.find()){//If we found the token
                    match = true;
                    String tok = m.group().trim();
                    s = m.replaceFirst("").trim();
                    Token t = new Token(tknm.getTokenType(), tok);
                    tokens.add(t);
                    break;
                }
            }
            if(!match) throw new TokenizerException();
        }
    }
    /**
     * Get the list of tokens
     * @return tokens
     */
    public LinkedList<Token> getTokens(){
        return tokens;
    }
    /**
     * Converts to string
     * @return List of tokens in string
     */
    public String toString(){
        String s = "[\n";
        for(Token t : tokens){
            s += t.toString() + '\n';
        }
        s += "]\n";
        return s;
    }
}
