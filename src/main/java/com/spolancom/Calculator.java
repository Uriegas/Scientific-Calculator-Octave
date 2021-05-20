package com.spolancom;

import java.io.*;
import java.util.*;

/**
 * <h1>Calculator<h1/>
 * A scientific calculator from terminal
 * programmed in Java
 * @author Eduardo Uriegas
 * @version 1.0
 * @since 2021-06-06
 */
public class Calculator{
    /**
     * User input
     */
    private String input;
    /**
     * Expression Tree to store the user input abstractely
     * It is useful for making notation convertions and evaluation
     */
    private ExpressionTree tree = new ExpressionTree();
    private Token result;

    /**
     * Dummy constructor
     */
    public Calculator(){
        input = "";
        tree = null;
        result = null;
    }
    /**
     * Usage for Shunting Yard algorithm used for comparation with binary tree.
     * @param a number 1
     * @param b number 2
     * @param operation
     * @return Token representing the result of the operation
     */
    public Token evaluate(Token a, Token b, Token operation){
        switch (operation.getType()) {
            case ADD: return new Token( a.getNumber() + b.getNumber() );
            case SUB: return new Token( a.getNumber() - b.getNumber() );
            case MUL: return new Token( a.getNumber() * b.getNumber() );
            case DIV: return new Token( a.getNumber() / b.getNumber() );
            case POW: return new Token( Math.pow(a.getNumber(), b.getNumber()) );
            case SQRT: return new Token( Math.sqrt(a.getNumber()) );
            case SIN: return new Token( Math.sin(a.getNumber()) );
            case COS: return new Token( Math.cos(a.getNumber()) );
            case TAN: return new Token( Math.tan(a.getNumber()) );
            default: return new Token("ERROR"); //Return token error
        }
    }
    /**
     * Tokenizer, takes a string and divides it into tokens
     * @param s representing string to evaluate
     * @return Array of tokens
     */
    public ArrayList<Token> Tokenizer(String s) throws IOException{
        s = s.toLowerCase();
        s = s.replaceAll(" ","");
        StreamTokenizer tk = new StreamTokenizer(new StringReader(s));
        tk.ordinaryChar('-');
        tk.ordinaryChar('/');
        ArrayList<Token> out = new ArrayList<Token>();

        while (tk.nextToken() != StreamTokenizer.TT_EOF) {
            switch(tk.ttype) {
                case StreamTokenizer.TT_NUMBER:
                    out.add( new Token(Double.valueOf(tk.nval)) );
                    break;
                case StreamTokenizer.TT_WORD:
                    out.add(new Token(tk.sval) );
                    break;
                default:  // operator
                    out.add( new Token(String.valueOf((char) tk.ttype)) );
            }
        }
        return out; 
    }

    /**
     * toRPN function
     * Converts an array of tokens to a stack in Reverse Polish Notation
     * This function implements the Shunting Yard algorithm
     * @param ArrayList<Token> array of tokens
     * @return Queue of tokens in RPN notation
     */
    public Queue<Token> toRPN(ArrayList<Token> input){
        Stack<Token> operators = new Stack<Token>();
        Queue<Token> output = new LinkedList<Token>();

        for(int i = 0; i < input.size(); i++){//Iterate over tokens input
            if(input.get(i).isOperand())//If it is a number
                output.add(input.get(i));
            else if(input.get(i).getType() == Token_Type.ASSOCIATIVE_LEFT )//If '('
                operators.push(input.get(i));
            else if(input.get(i).getType() == Token_Type.ASSOCIATIVE_RIGTH ){//If ')'
                while(operators.peek().getType() != Token_Type.ASSOCIATIVE_LEFT)
                    output.add(operators.pop());
                operators.pop();
                if(operators.peek().isFunction())
                    output.add(operators.pop());
            }
            else if(input.get(i).isFunction())//If it is a function
                operators.add(input.get(i));
            else if(input.get(i).isOperator()){//If it is an operator
                if(input.get(i).getType() == Token_Type.SUB && input.get(i-1).isOperator()){
                    operators.push(input.get(i));
                    continue;
                }
                else if(input.get(i).leftAssociative())
                    while( !operators.isEmpty() && operators.peek().precedence() >= input.get(i).precedence() )
                        output.add(operators.pop());
                else if(input.get(i).rigthAssociative())
                    while( !operators.isEmpty() && operators.peek().precedence() > input.get(i).precedence() )
                        output.add(operators.pop());
                operators.push(input.get(i));
            }
        }
        while(!operators.isEmpty())//While the stack has operator push them to the output
            output.add(operators.pop());
        return output;
    }

    /**
     * Converts a array in RPN notation to an expression tree
     * @param RPN notation array
     * @return ExpressionTree
     */
    public ExpressionTree toTree(Queue<Token> RPN){
        return new ExpressionTree(RPN);
    }
    /**
     * Defines variables into numbers(double)
     * In this case just of one variable aka 'x'
     * @param t array of tokens
     * @return array with instantiated variables
     */
    public ArrayList<Token> instantiateVariables(ArrayList<Token> t){
        Scanner s = new Scanner(System.in);
        for(int i = 0; i < t.size(); i++){
            if(t.get(i).getType() == Token_Type.VARIABLE){
                System.out.print(String.format("Introduzca el valor de %s: ",t.get(i).getValue()));
                Token tmp = new Token(Double.parseDouble(s.nextLine()));
                for(int j = i; j < t.size(); j++){
                    if(t.get(j).equals(t.get(i)))
                        t.set(j, tmp);
                }
                break;//Remove break and we can implement multiple variables(ex. x, y, z)
            } 
        }
        return t;
    }
    /**
     * Function to evaluate the RPN expression
     * using the shunting yard algorithm.
     * Thanks Edsger Dijkstra
     * @param RPN queue of tokens
     * @return Token representing the result
     */
    public Token eval(Queue<Token> RPN){
        Stack<Token> result = new Stack<>();
        while(!RPN.isEmpty()){
            if(RPN.peek().isOperand())
                result.push(RPN.remove());
            else if(RPN.peek().isOperator() || RPN.peek().isFunction())
                if(RPN.peek().isBinary()){
                    Token B = result.pop();
                    Token A = result.pop();
                    result.push( this.evaluate(A, B, RPN.remove()) );
                }
                else if(RPN.peek().isUnary())
                    result.push( this.evaluate(result.pop(), null, RPN.remove()) );
        }
        return result.pop();//Last element aka Result
    }
    /**
     * Using all the functions, evaluates from String to result of expression
     * @param s the string
     * @throws IOException
     * @return double result
     */
    public double compute(String s) throws IOException{
        tree = toTree( toRPN( instantiateVariables( Tokenizer(s) ) ) );
        return tree.compute();
    }
    /**
     * Run function.
     * Just runs the calculator
     * @return Exit state
     */
    public int run(){
        Scanner s = new Scanner(System.in);
        System.out.println("Scientific Calculator");

        System.out.print("> ");
        //Save user input to in
        input = s.nextLine();
        while(!input.equals("!exit")){
            try{
                System.out.println(compute(input));
            }
            catch(Exception e){
                System.out.println("Syntax error");
            }
            System.out.print("> ");
            //Save user input to in
            input = s.nextLine();
        }
        s.close();
        return 0;
    }
}