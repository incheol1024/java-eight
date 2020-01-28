package me.incheol.chapter10;

import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.*;

// 블로킹큐로 디렉터리에 있는 파일을 처리하라. 한 스레드는 파일 트리를 순회하여 큐에 파일을 삽입한다.
// 다른 여러 스레드는 파일을 제거하고 주어진 키워드에 해당하는 파일을 검색하며, 일치하는 파일을 출력한다.
// 생산자는 작업을 마칠 때 큐에 더미 파일을 집어넣어야 한다.
public class Question10 {

    private static Path path = Paths.get("D:/app");

    private static ConcurrentLinkedQueue<Path> linkedQueue = new ConcurrentLinkedQueue();

    private static BlockingQueue<Path> blockingQueue = new LinkedBlockingQueue();


    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.submit(supportFiles());

        Future<Optional<Path>> submit;
        while (true) {
            submit = executorService.submit(findPath("test.png"));
            Optional<Path> path = submit.get();

            path.ifPresent(System.out::println);
        }

    }

    private static Callable<Optional<Path>> findPath(String fileName) {
        return () -> {
            Path pollPath = blockingQueue.take();
            if (fileName.equals(pollPath.getFileName().toString()))
                return Optional.of(pollPath);

            return Optional.empty();
        };
    }

    private static Runnable supportFiles() {
        return () -> {
            try {
                Files.walk(path, 1, FileVisitOption.FOLLOW_LINKS)
                        .forEach(blockingQueue::add)
                ;
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
    }


}
