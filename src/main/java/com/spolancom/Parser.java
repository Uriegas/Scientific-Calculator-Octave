package com.spolancom;

import java.util.LinkedList;

/**
 * Parser implementing a recursive descendent algorithm.
 * It parses = tokens too.
 */
public class Parser {
    LinkedList<Token> tokens;
    Token currentToken;
    /**
     * Create the object with the tokens
     * @param t
     */
    public Parser(LinkedList<Token> t){
        tokens = (LinkedList<Token>)t.clone();
        currentToken = tokens.getFirst();
    }
    /**
     * Tokenize the string before parsing it
     * @param str The expression to tokenize
     */
    public Parser(String str){
        Tokenizer t = new Tokenizer();
        t.tokenize(str);
        tokens = t.getTokens();
    }
    /**
     * Method that pops current token and makes
     * the variable currentToken point to the
     * newer first token.
     * @return The newer token
     */
    public Token nextToken(){
        tokens.pop();
        if(!tokens.isEmpty())
            return currentToken = tokens.getFirst();
        else
            return currentToken = new Token(Token.EPSILON, null);//EOT:END OF TOKEN
    }
    /**
     * Actual method that parses the tokens
     * Implements recursive descendent parsing
     * @return
     */
    public void parse(LinkedList<Token> t){
        tokens = (LinkedList<Token>) t.clone();
        currentToken = tokens.getFirst();
        //Recursive descendent parsing implementation
        expression();
        //Error if we havent get EOT at the end of parsing
        if(currentToken.getToken() != Token.EPSILON)
            throw new ParserException();
    }
    /**
     * Equality method, everything entered into the
     * calculator is an equality, defined as:
     * equality -> VARIABLE EQUALS expression
     * equality -> expression
     */
    private void equality(){
        if(currentToken.getToken() == Token.VARIABLE){
            Token previousToken = currentToken;
            nextToken();
            if(currentToken.getToken() == Token.EQUALS){
                nextToken();
                expression();
            }
            else{
                tokens.addFirst(previousToken);
                expression();//Doesnt know how to implement
            }
        }
        else
            expression();
    }
    /**
     * Evaluates a grammar rule aka Expression
     * An expression is an math equality
     * Example: f1 = x*3+14
     * If we dont find the = sign, we need to
     * make an internal variable anyway
     */
    private void expression(){
        signed_term();
        sum_op();
    }
    private void sum_op(){
        if(currentToken.getToken() == Token.PLUSMINUS){
            nextToken();
            term();
            sum_op();
        }
        //else
            //return epsilon
    }
    private void signed_term(){
        if(currentToken.getToken() == Token.PLUSMINUS){
            nextToken();
            term();
        }
        else
            term();
    }
    private void term(){
        factor();
        term_op();
    }
    private void term_op(){
        if(currentToken.getToken() == Token.MULTDIV){
            nextToken();
            signed_factor();
            term_op();
        }
        //else epsilon
    }
    private void signed_factor(){
        if(currentToken.getToken() == Token.PLUSMINUS){
            nextToken();
            factor();
        }
        else
            factor();
    }
    private void factor(){
        argument();
        factor_op();
    }
    private void factor_op(){
        if(currentToken.getToken() == Token.POW){
            nextToken();
            signed_factor();
        }
        //else epsilon
    }
    private void argument(){
        if(currentToken.getToken() == Token.OPEN_PARENTHESIS){
            nextToken();
            expression();
            if(currentToken.getToken() != Token.CLOSE_PARENTHESIS)
                throw new ParserException();
            nextToken();
        }
        else if(currentToken.getToken() == Token.FUNCTION){
            nextToken();
            value();
        }
        else{
            value();
        }
    }
    private void value(){
        if(currentToken.getToken() == Token.VARIABLE){
            nextToken();
        }
        else if(currentToken.getToken() == Token.NUMBER){
            nextToken();
        }
        else
            throw new ParserException();
    }
    /**
     * Sum or minus operation for non-terminal tokens
     */
    private void non_terminal_sum(){
        if(currentToken.getToken() == Token.PLUSMINUS)
            nextToken();//Pop this token
    }
}
