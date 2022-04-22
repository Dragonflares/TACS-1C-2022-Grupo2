package com.probasteReiniciando.TPTACS.functions;

import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Component
public class WordFileReader {

    public  final String WORD_FILE = "words-english.txt";

    public  List<String> find(){
        List<String> words = new ArrayList<>();

        try {

            File file = new File(WORD_FILE);
            Scanner scanner = new Scanner(file);

            int lineNumber = 0;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                int positionNumber = 0;
                for (String word : line.split("\\s")) {
                    if (!word.isEmpty()) {
                        words.add(word);
                        System.out.println(word + ": line " + (lineNumber + 1) + ", position " + (positionNumber + 1));
                    }
                    positionNumber += word.length() + 1;
                }
                lineNumber++;
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
        }
        return words;
    }
}
