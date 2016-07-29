package com.search.autocomplete;

import com.search.autocomplete.trie.TST;
import com.search.autocomplete.trie.Trie;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Exchanger;

public class TypeAheadSuggestions {

    private static final String PROCESS_FILE = "process-file";
    private static final String QUERY = "query";
    private static final String QUIT = "quit";

    private static final String LINE_VALUE_SEPARATOR = "\t";
    private static final String MOVIE_NAME_SEPARATOR = " ";

    private Trie trie;
    private List<Thread> ingestionThreads;

    public TypeAheadSuggestions(Trie trie) {
        this.trie = trie;
        ingestionThreads = new ArrayList<>();
    }

    private void processFile(String[] args) {
        String[] filenames = new String[args.length - 1];
        int index = 0;
        StringBuilder builder = new StringBuilder();
        for (int i = 1; i < args.length; i++) {
            filenames[index] = args[i];
            index++;
            builder.append(args[i] + " ");
        }
        System.out.println(PROCESS_FILE + " " + builder.toString());
        Thread t = new Thread(new TrieIngestion(trie, filenames, LINE_VALUE_SEPARATOR, MOVIE_NAME_SEPARATOR));
        ingestionThreads.add(t);
        t.start();
    }

    private void stopAll() {
        ingestionThreads.stream().forEach(t -> {
            try {
                if (t.isAlive()) {
                    t.join();// if ingestion thread is alive, then wait for it to complete
                }
            } catch (Exception e) {
                System.out.println("Exception when waiting for ingestion thread msg=" + e.getMessage());
            }
        });
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
        System.out.println(QUIT);
        stopAll();
    }

    public static void main(String[] args) {

        Trie trie = new TST(10, LINE_VALUE_SEPARATOR);
        TypeAheadSuggestions autocomplete = new TypeAheadSuggestions(trie);
        autocomplete.start();
    }
}
