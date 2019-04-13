/**
 * @author Khaled Jendi
 */

package com.texty;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class SearchImpl implements Search {
    private List<TxtFile> txtFiles;
    private List<String> words;
    private Logger log = Logger.getLogger(SearchImpl.class.getName());
    private final int FILESTOPRINT;

    public List<String> getWords() {
        return words;
    }

    public List<TxtFile> getTxtFiles() {
        return txtFiles;
    }

    @Override
    public void setWordsToMatch(String _words) {
        try {
            if (_words.contains(" ")) {
                words = Arrays.asList(_words.split(" "));
            } else {
                words = new ArrayList<>();
                words.add(_words);
            }
        } catch (Exception ex) {
            log.error("at SearchImpl::setWordsToMatch, full stack trace:", ex);
        }

    }

    public void setTxtFiles(List<TxtFile> txtFiles) {
        this.txtFiles = txtFiles;
    }

    public SearchImpl() {
        FILESTOPRINT = 10;
    }

    public SearchImpl(String _words, List<TxtFile> txtFiles, int filesToPrint) {
        PropertyConfigurator.configure("log4j.properties");
        this.FILESTOPRINT = filesToPrint;
        setTxtFiles(txtFiles);
        setWordsToMatch(_words);
    }

    @Override
    public void searchWords() {
        txtFiles.forEach(file -> {
            int containerCount = 0;
            containerCount = words.stream()
                    .filter((word) -> (file.getTxtFileContent().contains(word)))
                    .map((word) -> 1)
                    .reduce(containerCount, Integer::sum);
            int score = (int) ((containerCount * 1.0 / words.size()) * 100);
            file.setScore(score);
        });

        txtFiles.sort(Comparator.comparing(TxtFile::getScore).reversed());
        if (txtFiles.get(0).getScore() == 0) {
            System.out.println("no match found");
        } else {
            txtFiles.stream().limit(FILESTOPRINT).filter(x -> x.getScore() > 0).forEach(file -> System.out.printf("%1s - %s : %s%%%n", "", file.getTxtFileName(), file.getScore()));
        }
    }
}
