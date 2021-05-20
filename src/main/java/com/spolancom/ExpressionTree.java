package com.spolancom;

import java.util.Queue;
import java.util.Stack;

/**
 * Expression Tree
 * It has only a root node pointing to the rest of the tree
 */
public class ExpressionTree {

    private Node root;
    /**
     * Dummy initialization
     */
    public ExpressionTree() {
        root = null;
    }
    /**
     * Copy tree
     * @param t Another expressiontree
     */
    public ExpressionTree(Node t) {
        root = t;
    }
    /**
     * Initialize tree based in a array of tokens in RPN syntax
     * @param RPN Array of tokens
     */
    public ExpressionTree(Queue<Token> RPN){
        root = RPNtoTree(RPN);
    }
    /**
     * Converts array of tokens (RPN notation) to an abstract tree
     * @param RPN
     * @return Root node
     */
    public Node RPNtoTree(Queue<Token> RPN){
        Stack<Node> tree = new Stack<>();
        for(Token t : RPN)//Iterate over RPN
            Add(t, tree);
        return tree.pop();
    }
    /**
     * Add a new Node to the tree represented in the stack
     * @param n
     * @param tree
     */
    public void Add(Token n, Stack<Node> tree){
        if (n.isOperand())
            tree.push(new Node(n));
        else if(n.isBinary()){
            Node tmp = new Node(n);
            tmp.setLeft(tree.pop());
            try{tmp.setRight(tree.pop());}
            catch(Exception e){tmp.setRight(null);}
            tree.push(tmp);
        }
        else if(n.isUnary()){
            Node tmp = new Node(n);
            tmp.setRight(tree.pop());
            tree.push(tmp);
        }
    }
    /**
     * Compute the operations of the tree
     * @return double equal to result
     */
    public double compute(){
        return evaluate(root);
    }
    /**
     * Evaluate a tree using its root node
     * This is a well known algorithm implementation
     * Recursive call to evaluate child nodes
     * @param root node
     * @return double representing the result
     */
    public double evaluate(Node root){
        if(root == null)
            return (double)0.0;
        else if ( (root.visitLeft() == null) && (root.visitRigth() == null) )
            return root.getData().getNumber();
        double left = evaluate(root.visitLeft());
        double rigth = evaluate(root.visitRigth());

        return eval(left, rigth, root.getData());

    }
    /**
     * Computes the result of 2 numbers using an operand token
     * @param a double
     * @param b double
     * @param operation Token representing operator
     * @return The result of the operation
     */
    public Double eval(double a, double b, Token operation){
        switch (operation.getType()) {
            case ADD: return ( a + b );
            case SUB: return ( b - a );
            case MUL: return ( a * b );
            case DIV: return ( a / b );
            case POW: return ( Math.pow(a, b) );
            case SQRT: return ( Math.sqrt(b) );
            case SIN: return ( Math.sin(b) );
            case COS: return ( Math.cos(b) );
            case TAN: return ( Math.tan(b) );
            default: return null;
        }
    }
}
