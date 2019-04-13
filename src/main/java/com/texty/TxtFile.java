/**
 * @author Khaled Jendi
 */

package com.texty;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class TxtFile {
    private static Logger log = Logger.getLogger(TxtFile.class.getName());
    private String txtFileName;
    private String txtFileContent;
    private int score;

    public String getTxtFileName() {
        return txtFileName;
    }

    public void setTxtFileName(String txtFileName) {
        this.txtFileName = txtFileName;
    }

    public String getTxtFileContent() {
        return txtFileContent;
    }

    public void setTxtFileContent(String txtFileContent) {
        this.txtFileContent = txtFileContent;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public TxtFile(String txtFileName, String txtFileContent) {
        PropertyConfigurator.configure("log4j.properties");
        score = 0;
        this.txtFileName = txtFileName;
        this.txtFileContent = txtFileContent;
    }

    public static void addFiles(String _filesPaths, List<TxtFile> txtFiles) {
        List<String> filesPaths = getListOfFiles(_filesPaths);

        filesPaths.forEach(filePath -> {
            try (Stream<String> stream = Files.lines(Paths.get(filePath), StandardCharsets.UTF_8)) {
                StringBuilder sb = new StringBuilder();
                stream.forEach(s -> sb.append(s).append("\n"));

                TxtFile matchTxtFile = txtFiles.stream()
                        .filter(txtFile -> new File(filePath).getName().equals(txtFile.getTxtFileName()))
                        .findAny()
                        .orElse(null);

                if (matchTxtFile != null) {
                    matchTxtFile.setTxtFileContent(sb.toString());
                } else {
                    txtFiles.add(new TxtFile(new File(filePath).getName(), sb.toString()));
                }
            } catch (IOException ex) {
                log.error("at TxtFile::addFiles, full stack trace:", ex);
            }
        });
        System.out.printf("%s file(s) added/updated.%n", filesPaths.size());
    }

    public static void removeFiles(String _filesPaths, List<TxtFile> txtFiles) {

        List<String> filesPaths = getListOfFiles(_filesPaths);;

        filesPaths.forEach(file -> txtFiles.removeIf(t -> t.getTxtFileName().equals(file)));
        System.out.printf("file(s) removed.%n");
    }

    private static List<String> getListOfFiles(String _filesPaths) {
        List<String> filesPaths;
        if (_filesPaths.contains(" ")) {
            filesPaths = Arrays.asList(_filesPaths.split(" "));
        } else {
            filesPaths = new ArrayList<>();
            filesPaths.add(_filesPaths);
        }
        return filesPaths;
    }
}
