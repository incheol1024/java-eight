package me.incheol.chapter10;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;


// 각각 카운터를 100,000번 증가시키는 스레드 1,000개를 생성하라. AtomicLong을 이용할 때와 LongAdder를 이용할때의 퍼포먼스를 비교하라.
public class Question8 {

    private static AtomicLong atomicLong = new AtomicLong();

    private static LongAdder longAdder = new LongAdder();

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        Question8 question8 = new Question8();

        ExecutorService executorService = Executors.newFixedThreadPool(1000);
        Instant start = Instant.now();
        List<Future> futures = question8.getFutures(executorService ,question8.useAtomicLong());
        for (Future future : futures) {
            future.get();
        }
        Instant end = Instant.now();
        System.out.println("Time to when using AtomicLong: " + Duration.between(start, end).toMillis() + " ms");
        System.out.println("AtomicLong last value = " + atomicLong.get());

        start = Instant.now();
        List<Future> futures1 = question8.getFutures(executorService, question8.useLongAdder());
        for (Future future : futures1) {
            future.get();
        }
        end = Instant.now();
        System.out.println("Time to when using LongAdder:" + Duration.between(start, end).toMillis() + " ms");
        System.out.println("LongAdder last value = " + longAdder.longValue());

        executorService.shutdown();


        /*
        My test Result Value
        Time to when using AtomicLong: 2274 ms
        AtomicLong last value = 100000000
        Time to when using LongAdder:576 ms
        LongAdder last value = 100000000
         */


    }


    private List<Future> getFutures(ExecutorService executorService, Callable runnable) {
        List<Future> futures = new ArrayList<>();

        for (int i = 0; i < 1000; i++) {
            futures.add(executorService.submit(runnable));
        }

        return futures;
    }

    private Callable useAtomicLong() {
        return () -> {
            for (int i = 0; i < 100000; i++) {
                atomicLong.addAndGet(1);
            }
            return atomicLong.get();
        };
    }

    private Callable useLongAdder() {
        return () -> {
            for (int i = 0; i < 100000; i++) {
                longAdder.add(1);
            }
            return atomicLong.get();
        };
    }
}



