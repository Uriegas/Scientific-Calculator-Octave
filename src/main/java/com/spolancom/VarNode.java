package com.spolancom;
/**
 * Variable Leaf Node, no childs
 */
public class VarNode implements ExpressionNode {
    private String var_name;
    private Double var_value;

    public VarNode(String str){
        var_name = str;
    }
    public int getType(){
        return VAR_NODE;
    }
    public double getValue() throws EvaluationException{
        if(var_value == null)
            throw new EvaluationException("Variable " + var_name + "not initialized");
        else
            return var_value;
    }
}
