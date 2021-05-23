package com.spolancom;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.Stack;

import org.junit.Test;

/**
 * Test for the calcuator
 * passing string and comparing with result
 */
public class CalculatorTest {
    Calculator c = new Calculator();
    Stack<Double> result = new Stack<>();
    Stack<String> input = new Stack<>();
    /**
     * Init inputs(string) and results(double)
     */
    public CalculatorTest(){//Initialize values to evaluate
        input.push("3*5+2");
        result.push((double)3*5+2);
        input.push("3*4 + sin(2) - 2^2 + sqrt(4)");
        result.push( 3 * 4 + Math.sin(2) - Math.pow(2, 2) + Math.sqrt(4) );
        input.push("2*5+5+0-4");//Doesnt supported by ShuntingYardMethod
        result.push((double)2*5+5+(-4));
    }
    /**
     * Evaluation of String using Expression tree
     */
    @Test
    public void TreeMethod() throws IOException {
        while(!result.empty() && !input.empty())
            assertEquals(result.pop(), c.compute(input.pop()), 0.01);
    }
    /**
     * Evaluation of String using Shunting Yard algorithm
     */
    @Test
    public void ShuntingYardMethod() throws IOException {
        while(!result.empty() && !input.empty())
            assertEquals(result.pop(), c.eval(c.toRPN(c.Tokenizer(input.pop()))).getNumber(), 0.01);
    }

    /**
     * Test for the multiple variable expression
     */
    @Test
    public void testTokenizer() throws TokenizerException{
        //Adding multivariable expression to input stack
        input.add("2*x_1+15*x_2");
        Tokenizer tokenizer = new Tokenizer();
        for(String in : input ){
            tokenizer.tokenize(in);
            System.out.println( tokenizer.toString() );
        }
    }
}
