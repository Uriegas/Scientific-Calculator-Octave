package com.spolancom;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
/**
 * Test the Parser
 */
public class ParserTest {
    @Test
    public void testAssignment(){

    }
    @Test
    public void testEvaluation(){
        Parser p = new Parser();
        Exp tree = p.parse("3*2^(4+3)");
        Interpreter evalVisitor = new Interpreter();
        Object result = tree.accept(evalVisitor);
        PrintTree visit = new PrintTree();
        System.out.println("Expression is: " + tree.accept(visit) + "\nValue is: " + String.valueOf(result));
    }
    @Test
    public void testVariables(){
        Parser p = new Parser();
        Exp tree = p.parse("3*2^(x+3)");
        Interpreter evalVisitor = new Interpreter();
        Object result = tree.accept(evalVisitor);
        PrintTree visit = new PrintTree();
        System.out.println("Expression is: " + tree.accept(visit) + "\nValue is: " + String.valueOf(result));
    }
    @Test
    public void testFunctionCall(){

    }
    @Test
    public void TestSAVE(){

    }
    @Test
    public void TestREAD(){

    }
}
