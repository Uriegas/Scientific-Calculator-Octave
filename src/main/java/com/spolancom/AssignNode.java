package com.spolancom;

public class AssignNode implements ExpressionNode {
    private String var_name;
    private ExpressionNode expression;

    /**
     * Simple Constructor
     * @param name Variable name (aka callable function)
     * @param e this function is equals to expression "e"
     */
    public AssignNode(String name, ExpressionNode e){
        var_name = name;
        expression = e;
    }
    /**
     * @return Type of Node
     */
    public int getType(){
        return ASSIGN_NODE;
    }
    /**
     * @return Actually succesful or not
     */
    public double getValue(){
        return 0;
    }
    /**
     * Save to a map<String, ExpressionNode>
     */
    public void saveToEnvironment(){
    }
    /**
     * Recursive toString method
     */
    public String toString(){
        return var_name + " = " + expression.toString();
    }
}
