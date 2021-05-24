package com.spolancom;
/**
 * Save Node, reserve calculator function
 * Saves an expression into a file
 */
public class SaveNode implements ExpressionNode {
    private ExpressionNode expression;
    private String file_name;
    /**
     * Constructor with basic child nodes
     * @param e expression to evaluate and save
     * @param f_name name of file to save expression
     */
    public SaveNode(ExpressionNode e, String f_name){
        expression = e;
        file_name = f_name;
    }
    /**
     * @return Type of node
     */
    public int getType(){
        return SAVE_NODE;
    }
    /**
     * It actually doesnt return a value,
     * just performs the operation
     * if there is an error it throws it
     * @deprecated Not implemented yet
     */
    public double getValue(){
        //Here instantiate save class and perform operation
        return 0;
    }
    /**
     * Recursive toString method
     */
    public String toString(){
        return "save( " + expression.toString() + ", '" + file_name + "')";
    }
}
