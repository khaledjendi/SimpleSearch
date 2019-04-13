/**
 * @author Khaled Jendi
 */

package com.texty;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    static Logger log = Logger.getLogger(Main.class.getName());
    static List<TxtFile> txtFiles = new ArrayList<>();

    public static void main(String[] args) {
        PropertyConfigurator.configure("log4j.properties");

        List<String> filesPaths = null;

        StringBuilder sbFiles = new StringBuilder();

        try (Stream<Path> txtsPath = Files.walk(Paths.get(args[0]))) {
            filesPaths = txtsPath.map(x -> x.getFileName().toString()).filter(f -> f.endsWith(".txt")).collect(Collectors.toList());

            filesPaths.forEach(fileName -> {
                txtFiles.add(new TxtFile(fileName, retrieveContents((args[0].endsWith("/") ? args[0] : args[0].concat("/")) + fileName)));
            });
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
            log.error("at Main::main, full stack trace:", ex);
            System.out.println("Exiting...");
            return;
        }
        log.info(sbFiles.append(txtFiles.size()).append(" file(s) read in directory: ").append(args[0]).toString());
    }

    private static String retrieveContents(String txtPath) {
        StringBuilder sb = new StringBuilder();
        try (Stream<String> stream = Files.lines(Paths.get(txtPath), StandardCharsets.UTF_8)) {
            stream.forEach(s -> sb.append(s).append("\n"));
        } catch (IOException ex) {
            log.error("at Main::retrieveContents::txtPath, full stack trace:", ex);
        }
        return sb.toString();
    }
}
