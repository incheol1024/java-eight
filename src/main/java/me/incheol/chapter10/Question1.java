package me.incheol.chapter10;


import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class Question1 {

    private final Path path;

    private final String word;

    private Question1(Path path, String word) {
        this.path = path;
        this.word = word;
    }


    //Find all matched Files
    public List<Path> findFilesIncludingWord() throws IOException {
        return Files
                .walk(path, 1, FileVisitOption.FOLLOW_LINKS)
                .filter(path1 -> !Files.isDirectory(path1))
                .filter(this::inspectFile)
                .collect(Collectors.toList());
    }

    private boolean inspectFile(Path path) {
        boolean isContain = false;

        try {
            isContain = Files.lines(path, StandardCharsets.ISO_8859_1)
                    .anyMatch(s -> s.contains(word));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return isContain;
    }


    public static void main(String[] args) throws IOException {
        Question1 question1 = new Question1(Paths.get("D:\\app-test"), "test");
        List<Path> filesIncludingWord = question1.findFilesIncludingWord();
        filesIncludingWord.forEach(System.out::println);
    }


}
