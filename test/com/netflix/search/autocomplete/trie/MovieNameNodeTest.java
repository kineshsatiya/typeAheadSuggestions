package com.netflix.search.autocomplete.trie;

import org.junit.Assert;
import org.junit.Test;

public class MovieNameNodeTest {

    @Test
    public void testAddWord() {
        MovieNameNode movieNameNode = new MovieNameNode('s', 2);
        //check empty node
        Assert.assertArrayEquals(new String[]{}, movieNameNode.getValues());

        // check 1 value
        movieNameNode.addValue("2007\tUS\tStar Wars");
        String[] values = movieNameNode.getValues();
        Assert.assertArrayEquals(new String[]{"2007\tUS\tStar Wars"}, values);

        // add another and check order
        movieNameNode.addValue("2007\tUS\tA Star Wars");
        values = movieNameNode.getValues();
        Assert.assertArrayEquals(new String[]{"2007\tUS\tA Star Wars", "2007\tUS\tStar Wars"}, values);

        // add another and check size
        movieNameNode.addValue("2007\tUS\tB Star Wars");
        values = movieNameNode.getValues();
        Assert.assertArrayEquals(new String[]{"2007\tUS\tA Star Wars", "2007\tUS\tB Star Wars"}, values);
    }
}
