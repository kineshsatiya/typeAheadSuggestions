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
    private String valueLineSeparator;
    private Object lock;

    public TST(int maxResultSize, String valueLineSeparator) {
        this.maxResultSize = maxResultSize;
        this.valueLineSeparator = valueLineSeparator;
        this.lock = new Object();
    }

    @Override
    public void insert(String key, String value) {
        if (key == null) throw new NullPointerException("Input is null");
        if (key.length() == 0) throw new IllegalArgumentException("Input length is 0");
        if (value == null) throw new NullPointerException("value is null");
        if (value.split(valueLineSeparator).length != 3) throw new IllegalArgumentException("incorrect value");

        synchronized (lock) {
            root = insert(key.toLowerCase(), value, 0, root);
        }
    }

    private Node insert(String key, String value, int ptr, Node n) {
        char ch = key.charAt(ptr);
        if (n == null) {
            n = getNewNode(ch);
        }

        if (ch < n.getKey()) {
            n.setLeft(insert(key, value, ptr, n.getLeft()));
        } else if (ch > n.getKey()) {
            n.setRight(insert(key, value, ptr, n.getRight()));
        } else {
            n.addValue(value);
            if ((ptr + 1) < key.length()) {
                n.setMid(insert(key, value, ptr + 1, n.getMid()));
            }
        }
        return n;
    }

    @Override
    public List<String> query(String prefix) {
        if (prefix == null) throw new NullPointerException("Query prefix is null");
        prefix = prefix.toLowerCase();
        List<String> result = new ArrayList<>();

        if (prefix.length() == 0) {
            return result;
        }

        synchronized (lock) {
            Node current = root;
            int inputSize = prefix.length();
            int ptr = 0;
            while (true) {

                if (current == null) {
                    break;
                }
                char ch = prefix.charAt(ptr);
                if (ch < current.getKey()) current = current.getLeft();
                else if (ch > current.getKey()) current = current.getRight();
                else {
                    if (ptr == (inputSize - 1)) {
                        result = Arrays.asList(current.getValues());
                        break;
                    }
                    ptr++;
                    current = current.getMid();
                }
            }
        }

        return result;
    }

    private Node getNewNode(char key) {
        return new MovieNameNode(key, maxResultSize, new MovieNameComparator(valueLineSeparator));
    }
}
