/**
 * @author Khaled Jendi
 */

package com.texty;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.apache.commons.text.similarity.LevenshteinDistance;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class Suggestion {
    static Logger log = Logger.getLogger(Main.class.getName());
    Map<String, Integer> suggestedWords = new LinkedHashMap<>();
    List<String> seenWords = new ArrayList<>();
    int suggestionsNo = 3;

    public Suggestion() {
        PropertyConfigurator.configure("log4j.properties");
    }

    public void suggest(String param, List<TxtFile> txtFiles) {
        if (!Pattern.compile("\\d [a-z]+").matcher(param).matches()) {
            log.warn("Suggestion::suggest Wong parameter!\nParameter must start with digit and followed by a word --> :suggest 5 techno");
            return;
        }
        String[] params = param.split(" ");
        suggestionsNo = Integer.parseInt(params[0]);
        String wordToMatch = params[1];

        txtFiles.forEach(txtFile -> {
            Arrays.asList(txtFile.getTxtFileContent().split("\\W+")).forEach(word -> {
                if (!seenWords.contains(word)) {
                    boolean isAdded = addToSuggestion(wordToMatch, word);
                    if (suggestedWords.size() > suggestionsNo) {
                        if (isAdded) {
                            List<String> list = new ArrayList<>(suggestedWords.keySet());
                            suggestedWords.remove(list.get(list.size() - 1));
                        }
                    }
                }
            });
        });

        if(suggestedWords.isEmpty()) {
            System.out.println("no suggestions found");
        }
        else {
            suggestedWords.forEach((k, v) -> System.out.printf("%1s - %s : %s%n", "", wordToMatch, k));
        }
    }

    private boolean addToSuggestion(String wordToMatch, String word) {
        int distance = LevenshteinDistance.getDefaultInstance().apply(wordToMatch, word);
        seenWords.add(word);
        List<Map.Entry<String,Integer>> entryList = new ArrayList<Map.Entry<String, Integer>>(suggestedWords.entrySet());
        int lastDistance = suggestedWords.isEmpty() || entryList.get(entryList.size() - 1) == null ? Integer.MAX_VALUE : (int) entryList.get(entryList.size()-1).getValue();
        if (suggestedWords.size() < suggestionsNo || distance < lastDistance) {
            suggestedWords.put(word, distance);
            suggestedWords = suggestedWords.entrySet()
                    .stream()
                    .sorted(Map.Entry.comparingByValue())
                    .collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue(), (e1, e2) -> e2, LinkedHashMap::new));
            return true;
        }
        return false;
    }
}
