package com.netflix.search.autocomplete.trie;

import java.util.List;

/**
 * Interface for trie, where key and value are assumed to be String.
 */
public interface Trie {

    /**
     * Insert the given key into Trie with given value.
     *
     * @param key
     * @param value
     */
    void insert(String key, String value);

    /**
     * Get list of all values that have start with the given prefix.
     *
     * @param prefix
     * @return
     */
    List<String> query(String prefix);

}
