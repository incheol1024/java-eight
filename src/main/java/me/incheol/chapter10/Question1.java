package me.incheol.chapter10;


import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class Question1 {


    public static void main(String[] args) throws IOException {

        findFilesIncludingWord("D:/app", "");
    }


    public static File[] findFilesIncludingWord(String pathDir, String word) throws IOException {

        Path path = Paths.get(pathDir);
        Stream<Path> walk = Files.walk(path, FileVisitOption.FOLLOW_LINKS);
        walk.forEach(System.out::println);

        return null;
// gkgkgk
    }




}
