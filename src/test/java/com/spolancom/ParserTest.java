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
//        ExpressionNode tree = p.parse("3*2^4 + sqrt(1+3)");
//        double result = tree.getValue();
//        assertEquals(tree.getValue(), 50, 0);
        Exp tree = p.parse("3*2^4 + sqrt(1+3)");
        PrintTree visit = new PrintTree();
        System.out.println(tree.accept(visit));
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
