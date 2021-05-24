package com.spolancom;

import static org.junit.Assert.*;

import java.util.Stack;
import org.junit.Test;

public class TokenizerTest {
    /**
     * Test for the multiple variable expression
     * It only test that it doesn't throw an exception
     */
    @Test
    public void impetable() throws TokenizerException{
        Stack<String> input = new Stack<String>();
        //Adding multivariable expression to input stack
        input.add("2*x_1+15*x_2");
        input.add("data = read('archivo1.xlsx')");
        input.add("f1 = 2x_1^2 - x_2");
        input.add("save(f1(data), 'output.txt')");
        Tokenizer tokenizer = new Tokenizer();
        for(String in : input ){
            tokenizer.tokenize(in);
            System.out.println( tokenizer.toString() );
        }
        Stack<String> result = new Stack<String>();
        result.add("[\n7: save\n3: (\n7: f1\n3: (\n7: data\n4: )\n12: ,\n11: '\n13: output.txt\n11: '\n4: )\n0: null\n]\n");
        System.out.println(result.lastElement());
        assertEquals(tokenizer.toString(), result.lastElement());
    }
}
