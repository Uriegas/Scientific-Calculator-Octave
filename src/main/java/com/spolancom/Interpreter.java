package com.spolancom;

import java.util.ArrayList;

public class Interpreter implements Exp.Visitor<Double>{
    //We dont implement function scope, just global variables
    public Environment envmnt;
    /**
     * Just initialize the environment (map of values)
     * Here we can load all previous functions
     * Or variables that we want. Ex. PI.
     */
    public Interpreter(){
        envmnt = new Environment();
        envmnt.define("x", "4.0");//Test variable
        //Defin a print function
        envmnt.define("print", new FuncCallable(){
            @Override
            public int arity(){return 1;}
            @Override
            public Object call(Interpreter interpreter, ArrayList<String> arguments){
                try{
                    System.out.println(arguments.get(0));
                }
                catch(Exception e){
                    throw new EnvironmentException("Cannot use: " + arguments.get(0) + "in print function");
                }
                return 0.0;
            }
        });
        /**
         * Define sin function
         */
        envmnt.define("sin", new FuncCallable(){
            @Override
            public int arity(){return 1;}
            @Override
            public Object call(Interpreter interpreter, ArrayList<String> arguments){
                return Math.sin(Double.parseDouble(arguments.get(0)));
            }
        });
        /**
         * Define cos function
         */
        envmnt.define("cos", new FuncCallable(){
            @Override
            public int arity(){return 1;}
            @Override
            public Object call(Interpreter interpreter, ArrayList<String> arguments){
                return Math.cos(Double.parseDouble(arguments.get(0)));
            }
        });
        /**
         * Define tan function
         */
        envmnt.define("tan", new FuncCallable(){
            @Override
            public int arity(){return 1;}
            @Override
            public Object call(Interpreter interpreter, ArrayList<String> arguments){
                return Math.tan(Double.parseDouble(arguments.get(0)));
            }
        });
    }
    /**
     * Add the value and expression to the environment
     * Here we don't execute the expression, just save it
     * We only execute it when we call a function
     */
    @Override
    public Double visitAssignExpr(Exp.AssignNode expr){
        envmnt.define(expr.name, expr.value);
        return evaluate(expr.value);
    }
    /**
     * Search for the function in the environment
     * and execute the expression, it will search
     * for the parameters of the expression recursevly
     */
    @Override
    public Double visitCallExpr(Exp.CallNode expr){
        try{
            Object func = envmnt.get(expr.name);
            if(func instanceof FuncCallable){
                FuncCallable f = (FuncCallable)func;
                if(f.arity() != expr.arguments.size())
                    throw new EnvironmentException("Incorrect nomber of parameters for function" + expr.name);
                else{
                    ArrayList<String> args = new ArrayList<String>();
                    for(Exp arg : expr.arguments){
                        args.add(String.valueOf(evaluate(arg)));
                    }
                    return (Double)f.call(this, args);
                }
            }
            else{
                throw new EnvironmentException("Incorrect nomber of parameters for function" + expr.name);
            }
            
        }catch(Exception e){
            throw new EnvironmentException("This function doesn't exist");
        }
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
    public Double visitVariableExpr(Exp.Variable expr){//We are searching for variables when their value is just their string, this are strings we can change the parser to accept arguments in '' as strings its var_name = value
        String var_name = expr.name;//Get the name of the token
        Object value = envmnt.get(var_name);
        if(value instanceof String){
            try{
                return Double.parseDouble(value.toString());
            }catch(Exception e){
                throw new EnvironmentException("Cannot convert " + var_name + " to number");
            }
        }
        else if(value instanceof Exp){
            return evaluate((Exp) value);
        }
//        if(value instanceof ArrayList){
//            try{
//                for(Object v : value){
//                    
//                }
//            }catch(Exception e){
//                throw new EnvironmentException("Not valid data for variable" + var_name);
//            }
//        }
        throw new EnvironmentException("No match type for the variable " + var_name);
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
