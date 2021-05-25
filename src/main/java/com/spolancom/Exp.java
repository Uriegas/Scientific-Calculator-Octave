package com.spolancom;

import java.util.List;

/**
 * Handle Nodes and tree therefore
 */
public abstract class Exp {
    //Implements the visitor design pattern
    interface Visitor<R> {
        R visitAssignExpr(AssignNode expr);
        R visitCallExpr(CallNode expr);
        R visitGroupingExpr(GroupingNode expr);
        R visitNumberExpr(NumberNode expr);
        R visitVariableExpr(Variable expr);
        R visitBinaryExpr(BinaryNode expr);
    }
    /**
     * Abstract function to evaluate a node
     * Each node has his own implementation method thats
     * why we use the visitor patter design.
     * @param visitor an interface with bunch of functions
     * @return  the returns a type R defined latter by the user of the interface
     */
    public abstract <R> R accept(Visitor<R> visitor);

    /**
     * Node that handles assignment operations
     */
    static class AssignNode extends Exp{
        public String name;
        public Exp value;
        public AssignNode(Token t, Exp child){
            this.name = t.getValue();
            this.value = child;
        }
        @Override
        public <R> R accept(Visitor<R> visitor) {
            return visitor.visitAssignExpr(this);
        }
    }
    /**
     * Node that handles SUM RES MUL DIV
     */
    static class BinaryNode extends Exp {
        public Exp left;
        public Token operator;
        public Exp right;
        BinaryNode(Exp left, Token operator, Exp right) {
            this.left = left;
            this.operator = operator;
            this.right = right;
        }

        @Override
        public <R> R accept(Visitor<R> visitor) {
            return visitor.visitBinaryExpr(this);
        }
    }
    /**
     * Node that handles function calls
     */
    static class CallNode extends Exp {
        public String name;
        public List<Exp> arguments;

        public CallNode(Token callee, List<Exp> arguments) {
            this.name = callee.getValue();
            this.arguments = arguments;
        }

        @Override
        public <R> R accept(Visitor<R> visitor) {
            return visitor.visitCallExpr(this);
        }
    }
    /**
     * Node that handles parenthesis
     */
    static class GroupingNode extends Exp {
        public Exp expression;
        GroupingNode(Exp expression) {
            this.expression = expression;
        }

        @Override
        public <R> R accept(Visitor<R> visitor) {
            return visitor.visitGroupingExpr(this);
        }
    }
    /**
     * Node that handles a Number
     */
    static class NumberNode extends Exp {
        public String value;
        NumberNode(String value) {
        this.value = value;
        }

        @Override
        public <R> R accept(Visitor<R> visitor) {
            return visitor.visitNumberExpr(this);
        }
    }
    /**
     * Node that handles variables
     */
    static class Variable extends Exp {
        public String name;
        Variable(Token t) {
            this.name = t.getValue();
        }

        @Override
        public <R> R accept(Visitor<R> visitor) {
            return visitor.visitVariableExpr(this);
        }

    }

}
