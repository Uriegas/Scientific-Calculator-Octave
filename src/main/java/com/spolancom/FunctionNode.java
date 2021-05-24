package com.spolancom;

import java.util.LinkedList;

public class FunctionNode implements ExpressionNode {
    //Define Variable Types
    public static final int SIN = 1;
    public static final int COS = 2;
    public static final int TAN = 3;
    public static final int POW = 4;
    public static final int SQRT = 5;
    public static final int READ = 6;

    private int func_type;
    private LinkedList<ExpressionNode> arguments;

    /**
     * Constructor of the Function Node
     * @param func_type Type of function
     * @param arg Expression to evaluate
     */
    public FunctionNode(int func_type, ExpressionNode arg){
        this.func_type = func_type;
        this.arguments = new LinkedList<ExpressionNode>();
        this.arguments.add(arg);
    }
    public FunctionNode(int func_type, LinkedList<ExpressionNode> args){
        this.func_type = func_type;
        this.arguments = new LinkedList<ExpressionNode>();
        this.arguments = (LinkedList<ExpressionNode>)args.clone();
    }
    /**
     * @return Type of Node
     */
    public int getType(){
        return FUNC_NODE;
    }
    /**
     * Get the type of function
     * @return Function type
     */
    public int getFuncType(){
        return func_type;
    }
    /**
     * Evaluate the function given an argument aka expression
     */
    public double getValue(){
        switch(func_type){
            case SIN: return Math.sin(arguments.getLast().getValue());
            case COS: return Math.cos(arguments.getLast().getValue());
            case TAN: return Math.tan(arguments.getLast().getValue());
            case POW: return Math.pow(arguments.getFirst().getValue(), arguments.getLast().getValue());
            case SQRT: return Math.sqrt(arguments.getLast().getValue());
            case READ: return 0;//Implement function to read an xlsx
            default: throw new EvaluationException("Function " + func_type + "not valid");
        }
    }
    /**
     * Recursive toString method
     */
    public String toString(){
        String s = "";
        switch(func_type){
            case SIN: s += "sin( " + arguments.getFirst().toString(); break;
            case COS: s += "cos( " + arguments.getFirst().toString(); break;
            case TAN: s += "tan( " + arguments.getFirst().toString(); break;
            case POW: s += "pow( " + arguments.getFirst().toString() + ", " + arguments.getLast().toString(); break;
            case SQRT: s += "sqrt( " + arguments.getFirst().toString(); break;
            case READ: s += "read( " + arguments.getFirst().toString(); break;
            default: s += " ";
        }
        return s += " )";
    }
}
