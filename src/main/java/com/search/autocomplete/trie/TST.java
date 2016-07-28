package com.search.autocomplete.trie;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Ternary search tree implementation for Trie. Space saving compared to regular prefix - tree.
 */
public class TST implements Trie {

    private Node root;
    private int maxResultSize;

    public TST(int maxResultSize) {
        this.maxResultSize = maxResultSize;
    }

    @Override
    public void insert(String key, String value) {
        if (key == null) throw new NullPointerException("Input is null");
        if (key.length() == 0) throw new IllegalArgumentException("Input length is 0");

        Node current = root;
        int inputSize = key.length();
        for (int i = 0; i < inputSize; i++) {
            char ch = key.charAt(i);
            if (current == null) {
                current = getNewNode(ch);
            } else {
                if (ch < current.getKey()) current = current.getLeft();
                else if (ch > current.getKey()) current = current.getRight();
                else current = current.getMid();
            }

            current.addValue(value);
        }
    }

    @Override
    public List<String> query(String prefix) {
        if (prefix == null) throw new NullPointerException("Query prefix is null");

        List<String> result = new ArrayList<>();
        if (root == null) return result;

        Node current = root;
        int inputSize = prefix.length();
        for (int i = 0; i < inputSize; i++) {
            char ch = prefix.charAt(i);

            if (ch < current.getKey()) current = current.getLeft();
            else if (ch > current.getKey()) current = current.getRight();
            else current = current.getMid();

            if (current == null) break;

            if (i == (inputSize - 1)) result = Arrays.asList(current.getValues());
        }
        return result;
    }

    private Node getNewNode(char key) {
        return new MovieNameNode(key, maxResultSize);
    }
}
