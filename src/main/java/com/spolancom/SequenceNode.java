package com.spolancom;

import java.util.LinkedList;
/**
 * Sequence of sums and substractions
 */
public abstract class SequenceNode implements ExpressionNode {
    /**
     * Inner class that holds a sum or substraction
     */
    public class Term{
        public boolean positive;
        public ExpressionNode node;

        public Term(boolean p, ExpressionNode n){
            super();
            positive = p;
            node = n;
        }
    }
    public LinkedList<Term> terms;
    /**
     * Empty sequence
     */
    public SequenceNode() {
        this.terms = new LinkedList<Term>();
    }
    /**
     * Initialize with node and operation type
     * @param a expression node to evaluate
     * @param positive positive for + *, negative for - /
     */
    public SequenceNode(ExpressionNode a, boolean positive) {
        this.terms = new LinkedList<Term>();
        this.add(a, positive);
        //this.terms.add(new Term(positive, a));
    }
    /**
     * Add another term to evaluate
     * @param a child expression node
     * @param positive type of operation
     */
    public void add(ExpressionNode a, boolean positive) {
        this.terms.add(new Term(positive, a));
    }
}
