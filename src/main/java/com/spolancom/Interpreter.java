package com.spolancom;

import java.util.ArrayList;

public class Interpreter implements Exp.Visitor<Object> {
    public Environment envmnt;
    /**
     * Just initialize the environment (map of values)
     */
    public Interpreter(){
        envmnt = new Environment();
    }
    /**
     * Add the value and expression to the environment
     * Here we don't execute the expression, just save it
     * We only execute it when we call a function
     */
    @Override
    public Object visitAssignExpr(Exp.AssignNode expr){
        return evaluate(expr.value);
    }
    /**
     * Search for the function in the environment
     * and execute the expression, it will search
     * for the parameters of the expression recursevly
     */
    @Override
    public Object visitCallExpr(Exp.CallNode expr){
//        Object callee = evalaute(expr.callee);
//        ArrayList<Object> arguments = new ArrayList<>();
//        for(Exp arg : arguments)
//            arguments.add(arg.accept(arg));
        return 0;
    }
    /**
     * Evaluate inner expression an return result
     */
    @Override
    public Object visitGroupingExpr(Exp.GroupingNode expr){
        return evaluate(expr.expression);
    }
    /**
     * Just get the number inside this node
     */
    @Override
    public Object visitNumberExpr(Exp.NumberNode e){
        return e.value;
    }
    /**
     * If we are executing an expression and found a variable
     * Example: expression -> 2x+ sqrt(13+x)
     * we want to find the actual value of that variable
     * Here we search it in the environment
     */
    @Override
    public Object visitVariableExpr(Exp.Variable expr){
        String var_name = expr.name.getValue();//Get the name of the token
        Object value = envmnt.get(var_name);
        //return lookupVariable(expr.name, expr);
        return value;
    }
    /**
     * This evalutes most of the mathematical functions
     */
    @Override
    public Object visitBinaryExpr(Exp.BinaryNode expr){
        Object left = evaluate(expr.left);
        Object right = evaluate(expr.right);
        switch(expr.operator.getToken()){
            case Token.PLUS:
                if (left instanceof Double && right instanceof Double) {
                    return (double)left + (double)right;
                }
            case Token.MINUS:
                if (left instanceof Double && right instanceof Double) {
                    return (double)left - (double)right;
                }
            case Token.MULT:
                if (left instanceof Double && right instanceof Double) {
                    return (double)left * (double)right;
                }
            case Token.DIV:
                if (left instanceof Double && right instanceof Double) {
                    return (double)left / (double)right;
                }
            case Token.POW:
                if (left instanceof Double && right instanceof Double) {
                    return Math.pow((Integer)left, (Double)right);
                }
            default:
                throw new EnvironmentException("Not valid operation in " + (String)left + " " + expr.operator.getValue() + " " + (String)right );
        }
    }

    /**
     * Evalute with the current environment
     * @param e node to evalute
     * @return Te result of the evaluation
     */
    private Object evaluate(Exp e){
        return e.accept(this);
    }
}
