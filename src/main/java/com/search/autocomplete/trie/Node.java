package com.search.autocomplete.trie;

public interface Node {

    char getKey();

    Node getLeft();

    Node getMid();

    Node getRight();

    void setLeft(Node n);

    void setMid(Node n);

    void setRight(Node n);

    void addValue(String value);

    String[] getValues();

}
