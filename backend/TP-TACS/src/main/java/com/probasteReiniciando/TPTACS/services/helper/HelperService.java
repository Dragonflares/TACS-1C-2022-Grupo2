package com.probasteReiniciando.TPTACS.services.helper;

import com.probasteReiniciando.TPTACS.domain.Language;
import com.probasteReiniciando.TPTACS.dto.HelpDto;
import com.probasteReiniciando.TPTACS.dto.WordDto;
import lombok.NoArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

@NoArgsConstructor
@Service
public class HelperService {

    public  final String WORD_FILE_ENGLSIH = "words-english.list";
    public  final String WORD_FILE_SPANISH = "words-spanish.list";
    public List<String> spanishWords = new ArrayList<>();
    public List<String> englishWords = new ArrayList<>();
    public  final Map<Language,String> files = Map.of(Language.ENGLISH, WORD_FILE_ENGLSIH,Language.SPANISH,WORD_FILE_SPANISH);
    public  Map<Language,List<String>> wordsInMemory = new HashMap<>();




    @PostConstruct
    public void initialize() {
        this.spanishWords = readWordsFromFile(Language.SPANISH);
        this.englishWords = readWordsFromFile(Language.ENGLISH);
        wordsInMemory = Map.of(Language.ENGLISH,englishWords ,Language.SPANISH,spanishWords);
    }


    public List<WordDto> wordSearch(HelpDto helpDto) {
        return findWords(helpDto,readWordsInMemory(helpDto.getLanguage()));
    }


    public List<String> readWordsInMemory(Language language) {
        return this.wordsInMemory.getOrDefault(language,this.englishWords);
    }

    public  List<String> readWordsFromFile(Language language){

        List<String> words = new ArrayList<>();


        String path = files.getOrDefault(language,WORD_FILE_ENGLSIH);

        try {

            InputStream file = new ClassPathResource(path).getInputStream();
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
        } catch (IOException e) {
            e.printStackTrace();
        }
        return words;
    }

    public List<WordDto> findWords(HelpDto helpDto, List<String> words) {
        List<WordDto> wordDtosResult = new ArrayList<>();
        HelpDto helpDtoModified = removeEmptyGreenWords(helpDto);
        words = words.stream()
                .filter(word -> validatesGreyWords(word,helpDtoModified.getGreyWords()))
                .filter(word -> validatesYellowWords(word,helpDtoModified.getYellowWords()))
                .filter(word -> validatesGreenWords(word,helpDtoModified.getGreenWords()))
                .collect(Collectors.toList());
        words.forEach(word -> wordDtosResult.add(WordDto.builder().phrase(word.toLowerCase()).build()));
        return wordDtosResult;
    }

    private HelpDto removeEmptyGreenWords(HelpDto helpDto) {
        helpDto.getGreenWords().values().removeIf(String::isEmpty);
        return helpDto;
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
        Boolean condition = true;
        if(yellowWords != null ) {
            char[] array = yellowWords.toCharArray();

            for (int i = 0; i < array.length && condition; i++) {

                char y = array[i];
                if (!word.toLowerCase().chars().anyMatch(x -> x == y))
                    condition = false;
            }
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
        Boolean condition= true;
        if(greyWords != null) {
            char[] array = greyWords.toCharArray();

            for (int i = 0; i < array.length && condition; i++) {

                char y = array[i];
                if (word.toLowerCase().chars().anyMatch(x -> x == y))
                    condition = false;
            }
        }
        return condition;
    }
}
