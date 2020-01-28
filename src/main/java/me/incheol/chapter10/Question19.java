package me.incheol.chapter10;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Question19 {


    public static void main(String[] args) throws InterruptedException {
        Question19 question19 = new Question19();
        question19.runTest();
    }


    private void runTest() throws InterruptedException {

        Stack stack = new Stack();

        ExecutorService executorService = Executors.newFixedThreadPool(10);

        for(int i = 0; i < 100; i++) {
            executorService.submit(() -> {
               stack.push(new Object());
            });
        }

        Thread.sleep(1000);

        int count = 0;
        for(int i = 0; i < 100; i++) {
            System.out.println(stack.pop());
            ++count;
        }
        // null 값이 출력 되면 안된다.
        System.out.println(count);

    }

    public class Stack{
        class Node{ Object value; Object next; }
        private Node top;

        public void push(Object newValue) {
            Node n = new Node();
            n.value = newValue;
            n.next = top;
            top = n;
        }

        public Object pop() {
            if(top == null) return null;
            Node n = top;
            top = (Node) n.next;
            return n.value;
        }
    }
}
