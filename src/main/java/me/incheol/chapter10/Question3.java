package me.incheol.chapter10;

import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;


public class Question3 {

    public static void main(String[] args) {
        Question3 question3 = new Question3();

        try {
            question3.allocateTasks(Executors.newCachedThreadPool(), Paths.get("D:/app"), "test");
        } catch (IOException | ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }


    // 파일 안에서 주어진 단어를  찾으려고 시도하면서, 모든 단어를 읽는 태스크를 돌려주는 메서드를 구현하라.
    // 이 태스크는 인터럽트되면 즉시 디버그 메시지를 출력해야 하며 종료해야 한다.
    public Callable<Integer> getWordFinderTask(Path path, String word) {
        return () -> {

            if (Thread.interrupted()) {
                System.out.println("Debug Message: current Thread is interrupted.");
                return null;
            }

            if (Files.isDirectory(path))
                throw new RuntimeException("This is not File.");

            int wordCount = 0;
            try {
                wordCount = Files.lines(path)
                        .mapToInt(value -> getNumberOfWord(word, value))
                        .sum()
                ;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return wordCount;
        };
    }

    // 해당 문자열에 원하는 단어가 몇번 나오는지 횟수를 리턴하는 메서드
    private int getNumberOfWord(String word, String value) {
        int position = 0;
        int count = 0;

        while (true) {
            position = value.indexOf(word, position);
            if (position != -1) {
                count++;
                position = position + word.length();
                continue;
            }
            break;
        }
        return count;
    }


    //디렉터리에 들어있는 모든 파일 각각에 태스크를 하나씩 스케줄링하라. 그중 하나가 성공하면 나머지 태스크를 모두 인터럽트해야 한다.
    public Integer allocateTasks(ExecutorService executorService, Path path, String word) throws IOException, ExecutionException, InterruptedException {
        List<Callable<Integer>> callableList = null;
        if (Files.isDirectory(path)) {
            callableList = Files.walk(path, 1, FileVisitOption.FOLLOW_LINKS)
                    .filter(path1 -> !Files.isDirectory(path1))
                    .map(path1 -> getWordFinderTask(path1, word))
                    .collect(Collectors.toList());

        }
        return executorService.invokeAny(callableList);
    }
}
