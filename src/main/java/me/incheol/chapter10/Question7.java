package me.incheol.chapter10;

import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

// ConcurrentHashMap<String,Long>에서 최댓값과 연관된 키를 찾아라 (값이 같으면 임의로 선택한다.) (힌트: reduce Entries를 이용한다.)
public class Question7 {

    ConcurrentHashMap<String, Long> concurrentHashMap = new ConcurrentHashMap<>();

    public static void main(String[] args) {
        Question7 question7 = new Question7();
        question7.putRandomValues();

        // ConcurrentHashMap에 무슨 값이 들어갔는지 출력
        question7.concurrentHashMap.forEachEntry(1, stringLongEntry -> {
            System.out.println(stringLongEntry.getKey() + " :: " + stringLongEntry.getValue());
        });

        String keyForMaxValue = question7.findMaxValueInMap();
        System.out.println("max - key, value: " + keyForMaxValue + ", " + question7.concurrentHashMap.get(keyForMaxValue));
    }


    private void putRandomValues() {
        Random random = new Random();

        for (int i = 0; i < 2; i++) {
            concurrentHashMap.putIfAbsent(String.valueOf(i), random.nextLong());
        }
    }

    private String findMaxValueInMap() {
        return concurrentHashMap
                .reduceEntries(1,
                        (stringLongEntry, stringLongEntry2)
                                -> stringLongEntry.getValue() > stringLongEntry2.getValue() ? stringLongEntry : stringLongEntry2)
                .getKey()
                ;
    }


}
