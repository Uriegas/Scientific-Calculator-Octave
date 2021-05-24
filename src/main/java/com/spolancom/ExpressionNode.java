package com.spolancom;
/**
 * Every node should have this constant variables defined
 * Aslo this functions defined
 * Note: Here we could add user-defined functions
 */
public interface ExpressionNode {
    //Types of nodes
    public static final int VAR_NODE = 1;
    public static final int NUM_NODE = 2;
    public static final int ADD_NODE = 3;
    public static final int MUL_NODE = 4;
    public static final int FUNC_NODE = 5;
    public static final int SAVE_NODE = 6;
    public static final int ASSIGN_NODE = 7;
    
    public int getType();
    public double getValue();
}
