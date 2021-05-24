package com.spolancom;
/**
 * Number Node is a leaf node, it has no child nodes
 */
public class NumNode implements ExpressionNode {
    private double value;
    /**
     * Instantiate with double
     * @param num double
     */
    public NumNode(double num){
        value = num;
    }
    /**
     * Instantiate with string
     * Parsing it to double
     * @param num string
     */
    public NumNode(String num) throws NumberFormatException{
        value = Double.parseDouble(num);
    }
    public double getValue(){
        return value;
    }
    public int getType(){
        return NUM_NODE;
    }
    /**
     * Recursive toString method
     */
    public String toString(){
        return String.valueOf(value);
    }
}
