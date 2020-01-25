package me.incheol.chapter10;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.stream.Collectors;

// 문제5번에서 computeIfAbsent를 이용
public class Question6 {

    ConcurrentHashMap<String, Set<File>> concurrentHashMap = new ConcurrentHashMap<>();

    public static void main(String[] args) {
        Question5 question6 = new Question5();
        Collection<File> files = question6.getFiles(Paths.get("D:\\app\\"));

        question6.setWordsInFilesToMap(files);
        question6.concurrentHashMap.forEachEntry(10, stringSetEntry -> System.out.println(stringSetEntry.getKey() + "::" + stringSetEntry.getValue()));
    }


    public void setWordsInFilesToMap(Collection<File> files) {
        files.stream()
                .parallel()
                .forEach(file -> processForFile(file));
    }

    private void processForFile(File file) {
        try {
            Files.lines(file.toPath(), Charset.forName("ISO-8859-1"))
                    .map(s -> s.split(" "))// 단어를 찾는 로직을 스페이스로 구분했음.
                    .forEach(strings -> setWordsOnMap(file, strings))
            ;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setWordsOnMap(File file, String[] strings) {
        Arrays.stream(strings).forEach(s -> {
            ConcurrentSkipListSet<File> files = new ConcurrentSkipListSet<>();
            files.add(file);
            concurrentHashMap.computeIfAbsent(s, s1 -> files);
        });
    }

    public Collection<File> getFiles(Path path) {
        List<File> collect = null;

        try {
            collect = Files.walk(path, 1, FileVisitOption.FOLLOW_LINKS)
                    .filter(path1 -> !Files.isDirectory(path1, LinkOption.NOFOLLOW_LINKS))
                    .peek(System.out::println)
                    .map(path1 -> path1.toFile())
                    .collect(Collectors.toList());

        } catch (IOException e) {
            e.printStackTrace();
        }
        return collect;
    }


}
