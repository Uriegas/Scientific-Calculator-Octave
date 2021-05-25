package com.spolancom;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Grammar:
 * program -> expression* EOT
 * 
 * expression -> assignment
 * assignment -> IDENTIFIER "=" expression | term
 * 
 * term -> factor ( ( "-" | "+" ) factor )*
 * factor -> pow ( ( "/" | "*" ) pow )*
 * pow -> call ^ call | call
 * call -> IDENTIFIER "(" arguments? ")"
 * primary -> NUMBER | IDENTIFIER | "(" expression ")"
 * 
 * arguments -> expression | IDENTIFIER ( "," expression | IDENTIFIER )*
 * 
 */

/**
 * 
 * Grammar from
 */

/**
 * Parser implementing a recursive descendent algorithm. It parses = tokens too.
 */
public class Parser {
    LinkedList<Token> tokens;
    int current;
    Token currentToken;

    /**
     * Create the object with the tokens
     * 
     * @param t
     */
    public Parser(LinkedList<Token> t) {
        this();
        tokens = (LinkedList<Token>) t.clone();
        currentToken = tokens.getFirst();
    }

    /**
     * Tokenize the string before parsing it
     * 
     * @param str The expression to tokenize
     */
    public Parser(String str) {
        this();
        Tokenizer t = new Tokenizer();
        t.tokenize(str);
        tokens = t.getTokens();
    }

    /**
     * Dummy constructor
     */
    public Parser() {
        current = 0;
        tokens = new LinkedList<Token>();
    }

    /**
     * Method that pops current token and makes the variable currentToken point to
     * the newer first token.
     * 
     * @return The newer token
     */
    public Token nextToken() {
        try {
            return tokens.get(current + 1);
        } catch (IndexOutOfBoundsException e) {// Maybe just throw a new TokenizerException
            return currentToken = new Token(Token.EPSILON, null);// EOT:END OF TOKENS
        }
    }

    public Token previousToken() {
        try {
            return tokens.get(current - 1);
        } catch (IndexOutOfBoundsException e) {
            throw new TokenizerException("Yo, you tried access a non existen previous token");
        }
    }

    /**
     * Advance to the next
     * @return The previous token before advancing
     */
    public Token advance() {
        current++;
        currentToken = tokens.get(current);
        return previousToken();
    }

    /**
     * Actual method that parses the tokens Implements recursive descendent parsing
     * 
     * @return
     */
    public Exp parse(LinkedList<Token> t) {
        tokens = (LinkedList<Token>) t.clone();
        currentToken = tokens.getFirst();
        // Recursive descendent parsing implementation
        Exp e = expression();
        // Error if we havent get EOT at the end of parsing
        if (currentToken.getToken() != Token.EPSILON)
            throw new ParserException("Syntax Error");
        return e;
    }

    /**
     * Actual method that parses the tokens Implements recursive descendent parsing
     * 
     * @return expression node representing the tree
     */
    public Exp parse(String str) {
        // Reinit the current token just in case
        current = 0;
        Tokenizer tok = new Tokenizer();
        tokens = tok.tokenize(str);
        currentToken = tokens.get(current);
        // Recursive descendent parsing implementation
        Exp e = expression();
        // Error if we havent get EOT at the end of parsing
        // if(currentToken.getToken() != Token.EPSILON)
        // throw new ParserException("Syntax Error");
        return e;
    }

    /**
     * Grammar implementation of Crafting Interpreters
     * 
     * @return
     */

    private Exp expression() {
        return assignment();
    }

    private Exp assignment() {// assignment -> IDENTIFIER "=" expression | term
        if ((currentToken.getToken() == Token.VARIABLE) && (nextToken().getToken() == Token.EQUALS)) {
            Token name = currentToken;// Point to the name of the token
            advance();// The EQUAL sign "="
            advance();// Point to next token to parse expression
            return new Exp.AssignNode(name, expression());
        } else {
            return term();
        }
    }

    private Exp term() {// term -> factor ( ( "-" | "+" ) factor )*
        Exp exp = factor();
        while (currentToken.getToken() == Token.PLUSMINUS) {
            Token operator = currentToken;
            advance();
            Exp right = factor();
            exp = new Exp.BinaryNode(exp, operator, right);
        }
        return exp;
    }

    private Exp factor() {// factor -> pow ( ( "/" | "*" ) pow )*
        Exp exp = pow();
        while (currentToken.getToken() == Token.MULTDIV) {
            Token operator = currentToken;
            advance();
            Exp right = pow();// call or maybe we should redifine unary to extend to call
            exp = new Exp.BinaryNode(exp, operator, right);
        }
        return exp;
    }

    private Exp pow() {// pow -> primary "^" primary
        Exp exp = call();
        if (currentToken.getToken() == Token.POW) {
            Token operator = currentToken;
            advance();
            Exp right = call();
            exp = new Exp.BinaryNode(exp, operator, right);
        }
        return exp;
    }

    /**
     * Grammar rule for a function call
     * In the future we need to change the distintion between variable and function
     * To abstract it to only an identifier
     * @return
     */
    private Exp call(){//call -> primary ( "(" arguments? ")" )*
        Exp exp = primary();
        if((previousToken().getToken() == Token.FUNCTION || previousToken().getToken() == Token.VARIABLE) && currentToken.getToken() == Token.OPEN_PARENTHESIS){//If the previous token is not a number or expression and the current token is a prenthesis
            ArrayList<Exp> arguments = new ArrayList<>();
            while(currentToken.getToken() != Token.CLOSE_PARENTHESIS){
                do{
                    advance();
                    arguments.add(expression());
                }while(currentToken.getToken() == Token.COMMA);
                advance();
                return new Exp.CallNode(exp, previousToken(), arguments);
            }
        }
        return exp;
    }

    private Exp primary() {// primary -> NUMBER | IDENTIFIER | "(" expression ")"
        if (currentToken.getToken() == Token.NUMBER || currentToken.getToken() == Token.VARIABLE) {
            advance();
            return new Exp.NumberNode(previousToken().getValue());
        } else if (currentToken.getToken() == Token.OPEN_PARENTHESIS) {
            advance();
            Exp exp = expression();
            if (currentToken.getToken() != Token.CLOSE_PARENTHESIS) {
                throw new ParserException("Close parenthesis not found");
            }
            return new Exp.GroupingNode(exp);
        }
        else if(currentToken.getToken() == Token.FUNCTION){
            advance();
            return new Exp.NumberNode(previousToken().getValue());
        }
        throw new ParserException("Missing token");
    }

//    private Exp arguments(){
//        ArrayList<Exp> arguments = new ArrayList<>();
//        if(!)
//    }

    public boolean match(Token_Type... types) {
        for (Token_Type type : types)
            if (type.equals(currentToken.getType()))
                return true;
        return false;
    }

    /**
     * Grammar implementation of CogitoLearning
     */

    /**
     * Equality method, everything entered into the calculator is an equality,
     * defined as: equality -> VARIABLE EQUALS expression equality -> expression
     */
    // private void equality(){
    // if(currentToken.getToken() == Token.VARIABLE){
    // Token previousToken = currentToken;
    // nextToken();
    // if(currentToken.getToken() == Token.EQUALS){
    // nextToken();
    // expression();
    // }
    // else{
    // tokens.addFirst(previousToken);
    // expression();//Doesnt know how to implement
    // }
    // }
    // else
    // expression();
    // }
    /**
     * Evaluates a grammar rule aka Expression An expression is an math equality
     * Example: f1 = x*3+14 If we dont find the = sign, we need to make an internal
     * variable anyway
     */
    // private void expression(){
    // signed_term();
    // sum_op();
    // }
    // private void sum_op(){
    // if(currentToken.getToken() == Token.PLUSMINUS){
    // nextToken();
    // term();
    // sum_op();
    // }
    // //else
    // //return epsilon
    // }
    // private void signed_term(){
    // if(currentToken.getToken() == Token.PLUSMINUS){
    // nextToken();
    // term();
    // }
    // else
    // term();
    // }
    // private void term(){
    // factor();
    // term_op();
    // }
    // private void term_op(){
    // if(currentToken.getToken() == Token.MULTDIV){
    // nextToken();
    // signed_factor();
    // term_op();
    // }
    // //else epsilon
    // }
    // private void signed_factor(){
    // if(currentToken.getToken() == Token.PLUSMINUS){
    // nextToken();
    // factor();
    // }
    // else
    // factor();
    // }
    // private void factor(){
    // argument();
    // factor_op();
    // }
    // private void factor_op(){
    // if(currentToken.getToken() == Token.POW){
    // nextToken();
    // signed_factor();
    // }
    // //else epsilon
    // }
    // private void argument(){
    // if(currentToken.getToken() == Token.OPEN_PARENTHESIS){
    // nextToken();
    // expression();
    // if(currentToken.getToken() != Token.CLOSE_PARENTHESIS)
    // throw new ParserException();
    // nextToken();
    // }
    // else if(currentToken.getToken() == Token.FUNCTION){
    // nextToken();
    // value();
    // }
    // else{
    // value();
    // }
    // }
    // private void value(){
    // if(currentToken.getToken() == Token.VARIABLE){
    // nextToken();
    // }
    // else if(currentToken.getToken() == Token.NUMBER){
    // nextToken();
    // }
    // else
    // throw new ParserException();
    // }
    // /**
    // * Sum or minus operation for non-terminal tokens
    // */
    // private void non_terminal_sum(){
    // if(currentToken.getToken() == Token.PLUSMINUS)
    // nextToken();//Pop this token
    // }

}
