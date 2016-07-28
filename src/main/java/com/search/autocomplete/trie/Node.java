package com.search.autocomplete.trie;

public interface Node {

    char getKey();

    Node getLeft();

    Node getMid();

    Node getRight();

    void addValue(String value);

    String[] getValues();

}
