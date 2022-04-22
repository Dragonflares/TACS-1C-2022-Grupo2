package com.probasteReiniciando.TPTACS.functions;

import com.probasteReiniciando.TPTACS.domain.Language;
import com.probasteReiniciando.TPTACS.dto.HelpDto;
import com.probasteReiniciando.TPTACS.dto.WordDto;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

@Component
@NoArgsConstructor
public class WordFileSearcher {

    public  final String WORD_FILE_ENGLSIH = "words-english.list";
    public  final String WORD_FILE_SPANISH = "words-spanish.list";


    public  List<String> readWordsFromFile(Language language){

        List<String> words = new ArrayList<>();

        String path = Language.ENGLISH.equals(language) ? WORD_FILE_ENGLSIH : WORD_FILE_SPANISH;

        try {
            File file = new File(path);
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                for (String word : line.split("\\s")) {
                    if (!word.isEmpty()) {
                        words.add(word);
                    }
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
        }
        return words;
    }

    public List<WordDto> findWords(HelpDto helpDto, List<String> words) {
        List<WordDto> wordDtosResult = new ArrayList<>();
        words.forEach(word -> {
            if(validatesGreyWords(word,helpDto.getGreyWords())  && validatesYellowWords(word,helpDto.getYellowWords())
                    && validatesGreenWords(word,helpDto.getGreenWords()))
                wordDtosResult.add(WordDto.builder().phrase(word.toLowerCase()).build());

        });
        return wordDtosResult;
    }


    /** Validates the word given has the characters in the exact position given in the hashmap
     *
     * @param word
     * @param greenWords
     * @return
     */
    private boolean validatesGreenWords(String word, HashMap<Integer, String> greenWords) {

        Boolean condition = true;

        if(!greenWords.isEmpty()) {
            Set<Integer> keys = greenWords.keySet();

            for (Integer key : keys) {
                if (word.length() > key) {
                    condition = greenWords.get(key).equalsIgnoreCase(String.valueOf(word.charAt(key))) && condition;
                }
            }
        }
        return condition;
    }

    /** Validates that the word given does  contain the letter passed in the second parameter
     *
     * @param word
     * @param yellowWords
     * @return
     */
    private boolean validatesYellowWords(String word, String yellowWords) {
        char[] array = yellowWords.toCharArray();
        Boolean condition = true;
        for (int i = 0; i < array.length && condition; i++) {

            char y = array[i];
            if(!word.toLowerCase().chars().anyMatch(x -> x == y))
                condition = false;
        }

        return condition;

    }


    /**Validates that the word given does not contain the letter passed in the second parameter
     *
     * @param word
     * @param greyWords
     * @return
     */
    private boolean validatesGreyWords(String word, String greyWords) {
        char[] array = greyWords.toCharArray();
        Boolean condition= true;
        for (int i = 0; i < array.length && condition; i++) {

            char y = array[i];
            if(word.toLowerCase().chars().anyMatch(x -> x == y))
                condition = false;
        }

        return condition;
    }
}
