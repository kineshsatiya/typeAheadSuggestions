package com.search.autocomplete.trie;

import java.util.TreeSet;

public class MovieNameNode implements Node {

    private char key;
    private MovieNameNode left;
    private MovieNameNode mid;
    private MovieNameNode right;

    /**
     * Using tree set for storing 10 smallest movie names, with default comparator
     */
    private TreeSet<String> values;
    private int maxSize;

    public MovieNameNode(char key, int maxSize) {
        this.key = key;
        this.maxSize = maxSize;
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
        if (values == null) values = new TreeSet<>();
        if (value == null) throw new IllegalArgumentException("Value is null");
        values.add(value);
        if (values.size() > maxSize) values.remove(values.last());
    }

    @Override
    public String[] getValues() {
        if (values == null) values = new TreeSet<>();
        return values.toArray(new String[values.size()]);
    }
}
