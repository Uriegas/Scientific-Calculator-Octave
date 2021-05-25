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
        Exp tree = p.parse("3*2^(y+3)");
        Interpreter evalVisitor = new Interpreter();
        Object result = tree.accept(evalVisitor);
        PrintTree visit = new PrintTree();
        System.out.println("Expression is: " + tree.accept(visit) + "\nValue is: " + String.valueOf(result));
    }
    @Test
    public void testFunctionCall(){
        Parser p = new Parser();
        Interpreter evalVisitor = new Interpreter();

       // Exp tree = p.parse("x = 10+10");
       // Object result = tree.accept(evalVisitor);
        Exp tree = p.parse("print(x)");
        Object result = tree.accept(evalVisitor);
        PrintTree visit = new PrintTree();
        System.out.println("Expression is: " + tree.accept(visit) + "\nReturn value is: " + String.valueOf(result));
        tree = p.parse("sin(10+cos(x+2)-70)");
        result = tree.accept(evalVisitor);
        
        System.out.println("Expression is: " + tree.accept(visit) + "\nReturn value is: " + String.valueOf(result));
        assertEquals(Math.sin((10.0+Math.cos(4+2))-70), Double.valueOf((Double)result), 0.01);
    }
    @Test
    public void TestSAVE(){

    }
    @Test
    public void TestREAD(){

    }
}
