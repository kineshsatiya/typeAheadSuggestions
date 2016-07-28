package com.netflix.search.autocomplete;

import com.netflix.search.autocomplete.trie.TST;
import com.netflix.search.autocomplete.trie.Trie;

import java.util.Scanner;

public class TypeAheadSuggestions {

    private static final String PROCESS_FILE = "process-file";
    private static final String QUERY = "query";
    private static final String QUIT = "quit";

    private static final String LINE_VALUE_SEPARATOR = "\t";
    private static final String MOVIE_NAME_SEPARATOR = " ";

    private Trie trie;

    public TypeAheadSuggestions(Trie trie) {
        this.trie = trie;
    }

    private void processFile(String[] args) {
        String[] filenames = new String[args.length];
        int index = 0;
        StringBuilder builder = new StringBuilder();
        for (int i = 1; i < args.length; i++) {
            filenames[index] = args[i];
            index++;
            builder.append(args[i] + " ");
        }
        System.out.println(PROCESS_FILE + " " + builder.toString());
        (new Thread(new TrieIngestion(trie, filenames, LINE_VALUE_SEPARATOR, MOVIE_NAME_SEPARATOR))).start();
    }

    private void query(String[] args) {
        for (int i = 1; i < args.length; i++) {
            System.out.println(QUERY + " " + args[i]);
            trie.query(args[i]).stream().forEach(System.out::println);
        }
    }

    private void start() {
        Scanner scanner = new Scanner(System.in);
        String command = scanner.nextLine();
        while (!command.equals(QUIT)) {
            String[] args = command.split(" ");
            String directive = args[0];

            if (directive.equals(PROCESS_FILE)) processFile(args);
            else if (directive.equals(QUERY)) query(args);
            else throw new IllegalArgumentException("Invalid directive, exiting");

            command = scanner.nextLine();
        }
    }

    public static void main(String[] args) {

        Trie trie = new TST(10);
        TypeAheadSuggestions autocomplete = new TypeAheadSuggestions(trie);
        autocomplete.start();
    }
}
