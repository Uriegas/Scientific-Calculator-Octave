package com.spolancom;
/**
 * Node Class
 * Represent information about the tokens
 * In a no-gay way
 */
public class Node{
    Token info;
    Node left;
    Node right;
    /**
     * Instantiate with data
     * @param data
     */
    public Node(Token data){
        this.info = data;
        this.right = null;
        this.left = null;
    }
    /**
     * Copy node
     * @param t another node
     */
    public Node(Node t){
        this.info = t.info;
        this.right = t.right;
        this.left = t.left;
    }
    /**
     * Set Data aka. Token
     * @param info
     */
    public void setData(Token info){
        this.info = info;
    }
    /**
     * Get token object
     * @return
     */
    public Token getData(){
        return info;
    }
    /**
     * Set Node in right side of the node
     * @param n
     */
    public void setRight(Node n){
        this.right = n;
    }
    /**
     * Set Node in left side of the node
     * @param n
     */
    public void setLeft(Node n){
        this.left = n;
    }
    /**
     * Get right side of the node
     */
    public Node visitLeft(){
        return left;
    }
    /**
     * Get left side of the node
     */
    public Node visitRigth(){
        return right;
    }
}
