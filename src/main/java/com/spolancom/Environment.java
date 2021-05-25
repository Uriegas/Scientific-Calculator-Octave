package com.spolancom;

import java.util.*;

/**
 * Here we store variables and functions
 * Actually in the map, this is just a wrapper
 */
public class Environment {
    private HashMap<String, Object> values;
    /**
     * Constructor: init the map
     */
    public Environment(){
        values = new HashMap<String, Object>();
    }
    /**
     * Add a new variable or function to the envmnt
     * @param s the key
     * @param o the actual value
     */
    public void define(String s, Object o){
        values.put(s, o);
    }
    public Object get(String s){
        if(values.containsKey(s)){
            return values.get(s);
        }
        throw new EnvironmentException("The variable or function: " + s + " doesn't exist in the environment");
    }
}
