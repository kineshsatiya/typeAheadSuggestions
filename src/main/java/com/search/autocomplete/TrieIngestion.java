package com.search.autocomplete;

import com.search.autocomplete.trie.Trie;

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
            for (String file : this.filenames) {
                if (file != null) {
                    InputStream inputStream = new FileInputStream(new File(file));
                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                    String line = bufferedReader.readLine();
                    while (line != null) {
                        String args[] = line.split(lineValueSeparator);
                        if (args.length == 3) {
                            String movieName = args[2];
                            for (String key : getMovieNameKeys(movieName)) {
                                trie.insert(key, line);
                            }
                        }
                        line = bufferedReader.readLine();
                    }
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
     * "Star Troopers" -> ["Star Troopers", "Troopers"]
     *
     * @param movieName
     * @return
     */
    public List<String> getMovieNameKeys(String movieName) {
        List<String> allKeys = new ArrayList<>();
        if (movieName != null && movieName.length() > 0) {
            String[] args = movieName.split(movieNameSeparator);
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < args.length; i++) {
                for (int j = i; j < args.length; j++) {
                    if (j == (args.length - 1)) {
                        builder.append(args[j]);
                    } else {
                        builder.append(args[j] + movieNameSeparator);
                    }
                }
                allKeys.add(builder.toString());
                builder = new StringBuilder();
            }
        }
        return allKeys;
    }
}
