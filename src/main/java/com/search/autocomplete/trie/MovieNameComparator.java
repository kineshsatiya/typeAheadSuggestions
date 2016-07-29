package com.search.autocomplete.trie;

import java.util.Comparator;

public class MovieNameComparator implements Comparator<String> {

    private String valueLineSeparator;

    public MovieNameComparator(String valueLineSeparator) {
        this.valueLineSeparator = valueLineSeparator;
    }

    @Override
    public int compare(String o1, String o2) {
        String[] args1 = o1.split(valueLineSeparator);
        String[] args2 = o2.split(valueLineSeparator);

        return args1[2].compareTo(args2[2]);
    }
}
