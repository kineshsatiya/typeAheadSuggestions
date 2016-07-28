package com.netflix.search.autocomplete;

import com.netflix.search.autocomplete.trie.Trie;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class TrieIngestion implements Runnable {

    private String lineValueSeparator;
    private String movieNameSeparator;
    private Trie trie;
    private String[] filenames;

    public TrieIngestion(Trie trie, String[] filenames, String lineValueSeparator, String movieNameSeparator) {
        this.trie = trie;
        this.filenames = filenames;
        this.lineValueSeparator = lineValueSeparator;
        this.movieNameSeparator = movieNameSeparator;
    }

    @Override
    public void run() {
        try {
            for (String file : filenames) {
                InputStream inputStream = new FileInputStream(file);
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String line = bufferedReader.readLine();
                while (line != null) {
                    String args[] = line.split(lineValueSeparator);
                    String movieName = args[2];
                    getMovieKeys(movieName).stream().forEach(key -> trie.insert(key, line));
                }
            }
        } catch (FileNotFoundException fileNotFoundException) {
            System.out.println("ERROR: File not found msg=" + fileNotFoundException.getMessage());
        } catch (IOException ioException) {
            System.out.println("ERROR: IOexception message=" + ioException.getMessage());
        }
    }

    /**
     * Utility method to generate all keys from a movie name.
     *
     * @param movieName
     * @return
     */
    private List<String> getMovieKeys(String movieName) {
        List<String> allKeys = new ArrayList<>();
        String[] args = movieName.split(movieNameSeparator);
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < args.length; i++) {
            for (int j = i; j < args.length; j++) {
                builder.append(args[j]);
            }
            allKeys.add(builder.toString());
            builder = new StringBuilder();
        }
        return allKeys;
    }
}
