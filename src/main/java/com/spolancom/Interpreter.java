package com.spolancom;

import java.util.ArrayList;

public class Interpreter implements Exp.Visitor<Double> {
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
    public Double visitAssignExpr(Exp.AssignNode expr){
        return evaluate(expr.value);
    }
    /**
     * Search for the function in the environment
     * and execute the expression, it will search
     * for the parameters of the expression recursevly
     */
    @Override
    public Double visitCallExpr(Exp.CallNode expr){
//        Object callee = evalaute(expr.callee);
//        ArrayList<Object> arguments = new ArrayList<>();
//        for(Exp arg : arguments)
//            arguments.add(arg.accept(arg));
        return 0.0;
    }
    /**
     * Evaluate inner expression an return result
     */
    @Override
    public Double visitGroupingExpr(Exp.GroupingNode expr){
        return evaluate(expr.expression);
    }
    /**
     * Just get the number inside this node
     */
    @Override
    public Double visitNumberExpr(Exp.NumberNode e){
        return Double.parseDouble(e.value);
    }
    /**
     * If we are executing an expression and found a variable
     * Example: expression -> 2x+ sqrt(13+x)
     * we want to find the actual value of that variable
     * Here we search it in the environment
     */
    @Override
    public Double visitVariableExpr(Exp.Variable expr){
        String var_name = expr.name.getValue();//Get the name of the token
        Object value = envmnt.get(var_name);
        //return lookupVariable(expr.name, expr);
        return Double.parseDouble(value);
    }
    /**
     * This evalutes most of the mathematical functions
     */
    @Override
    public Double visitBinaryExpr(Exp.BinaryNode expr){
        Double left = evaluate(expr.left);
        Double right = evaluate(expr.right);
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
                    return Math.pow(left, (Double)right);
                }
            default:
                throw new EnvironmentException("Not valid operation in " + left.toString() + " " + expr.operator.getValue() + " " + right.toString());
        }
    }

    /**
     * Evalute with the current environment
     * @param e node to evalute
     * @return Te result of the evaluation
     */
    private Double evaluate(Exp e){
        return e.accept(this);
    }
}
