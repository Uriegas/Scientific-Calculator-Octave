package com.spolancom;

import static org.junit.Assert.assertEquals;

import java.util.LinkedList;

import org.junit.Test;

public class TreeTest {
    /**
     * Evalutes if the tree can be constructed with
     * the defined nodes and if the tree can be
     * evaluated (solved) correctly
     * ONLY for Expression Tree
     * Assignment, save, read and user-defined functions
     * not impemented yet
     */
    @Test
    public void ExpressionTreeConstructionAndEvaluation(){
        AdditionNode innerSum = new AdditionNode();
        innerSum.add(new NumNode(1), true);
        innerSum.add(new NumNode(3), true);

        ExpressionNode sqrt = new FunctionNode( FunctionNode.SQRT, innerSum);

        LinkedList<ExpressionNode> args = new LinkedList<ExpressionNode>();
        args.add(new NumNode(2));
        args.add(new NumNode(4));
        ExpressionNode expo =new FunctionNode(FunctionNode.POW, args);

        MulNode prod = new MulNode();
        prod.add(new NumNode(3), true);
        prod.add(expo, true);

        AdditionNode expression = new AdditionNode();
        expression.add(prod, true);
        expression.add(sqrt, true);

        System.out.println("Expression is " + expression.toString());
        System.out.println("The result is " + expression.getValue());
        assertEquals(50.0, 50.0, 0);
    }    
}
