package com.spolancom;
/**
 * Addition or Substraction Node
 * Handles operations in a recursive way
 * Implements SequenceNode (the recursivity/inner nodes)
 */
public class AdditionNode extends SequenceNode {
    /**
     * Dummy constructor
     */
    public AdditionNode(){
        super();
    }
    /**
     * Add a node to recursivity and operation type
     * @param a
     * @param p operation type true = +, false = -
     */
    public AdditionNode(ExpressionNode a, boolean p){
        super(a, p);
    }
    /**
     * Return the type of Operation
     */
    public int getType(){
        return ADD_NODE;
    }
    /**
     * Recursive call to get the result of a iterative operation
     * that could be summation or substraction
     */
    public double getValue(){
        double sum = 0.0;
        for(Term t : terms){//If positive return a summation, if negative a substraction
            sum = (t.positive) ? (sum + t.node.getValue()) : (sum - t.node.getValue());
        }
        return sum;
    }
}
