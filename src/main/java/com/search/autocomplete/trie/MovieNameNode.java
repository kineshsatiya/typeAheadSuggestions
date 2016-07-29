package com.search.autocomplete.trie;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MovieNameNode implements Node {

    private char key;
    private Node left;
    private Node mid;
    private Node right;

    /**
     * Using tree set for storing 10 smallest movie names, with default comparator
     */
    private List<String> values;
    private int maxSize;
    private MovieNameComparator movieNameComparator;

    public MovieNameNode(char key, int maxSize, MovieNameComparator movieNameComparator) {
        this.key = key;
        this.maxSize = maxSize;
        this.movieNameComparator = movieNameComparator;
        this.left = null;
        this.right = null;
        this.mid = null;
    }

    @Override
    public char getKey() {
        return key;
    }

    @Override
    public Node getLeft() {
        return left;
    }


    @Override
    public Node getMid() {
        return mid;
    }

    @Override
    public Node getRight() {
        return right;
    }

    @Override
    public void addValue(String value) {
        if (values == null) values = new ArrayList<>();
        if (value == null) throw new IllegalArgumentException("Value is null");
        values.add(value);
        Collections.sort(values, movieNameComparator);
        if (values.size() > maxSize) values.remove(values.size() - 1);
    }

    @Override
    public String[] getValues() {
        if (values == null) values = new ArrayList<>();
        return values.toArray(new String[values.size()]);
    }

    @Override
    public void setLeft(Node left) {
        this.left = left;
    }

    @Override
    public void setMid(Node mid) {
        this.mid = mid;
    }

    @Override
    public void setRight(Node right) {
        this.right = right;
    }
}
