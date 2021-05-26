package com.spolancom;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Class to Read variables or functions
 */
public class ReadFunction {
    private ArrayList<String> expressions = new ArrayList<>();
    /**
     * Dummy constructor
     */
    public ReadFunction(){
    }
    /**
     * ReadFile method
     */
    public Environment ReadFile(String path, Interpreter i) throws Exception{
        if(!path.endsWith(".equ") || !path.endsWith(".xslx"))
            throw new EnvironmentException("Not valid file extension in path " + path);
        
        if(path.endsWith(".equ")){
            File f = new File(path);
            Scanner s = new Scanner(f);

            while(s.hasNextLine()){
                expressions.add(s.nextLine());
            }
            //Pass expressions to parser and put them into the environment
            Parser p = new Parser();
            Exp result;
            for(String exp : expressions){
                result = p.parse(exp);
                result.accept(i);
            }
            s.close();
        }
        else if(path.endsWith(".xlsx")){
        }
        return i.getEnv();
    }
}
