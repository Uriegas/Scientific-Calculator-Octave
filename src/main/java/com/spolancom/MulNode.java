package com.spolancom;
/**
 * Multiplitaction or divsion node
 * It has inner nodes in which to perform operations
 */
public class MulNode extends SequenceNode {
    /**
     * Dummy Constructor
     */
    public MulNode(){
        super();
    }
    /**
     * Init node and operation
     * @param a Child node of this node
     * @param p Type of operation to perform over "a"
     */
    public MulNode(ExpressionNode a, boolean p){
        super(a, p);
    }
    /**
     * Return type of node
     */
    public int getType(){
        return MUL_NODE;
    }
    /**
     * Return the value of this node
     * Implements recursion
     */
    public double getValue(){
        double mul = 1.0;
        for(Term t : terms){//If positive return a summation, if negative a substraction
            mul = (t.positive) ? (mul * t.node.getValue()) : (mul / t.node.getValue());
        }
        return mul;
    }
    /**
     * Recursive toString method
     */
    public String toString(){
        String s = "( 0.0";
        for(Term t : terms)
            s += (t.positive) ? ( " * " + t.node.toString()) : (" / " + t.node.toString());
        return s += " )";
    }
}
