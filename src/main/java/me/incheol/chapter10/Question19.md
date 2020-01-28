
*다음 스택 구현이 있다고 하자.*

```java
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
            top = n.next;
            return n.value;
        }    
    }
```

* 이 자료구조에 올바른 요소가 포함되지 않게 하는 방법 두 가지를 설명하라.

1. Not Thread Safe 
    * 위 스택 클래스는 *스레드 세이프* 하지 않다. 
    * 따라서 스레드 10개를 생성하여 스레별로 한번 씩 push 메서드를 호출한 뒤
    * 해당 스택 객체에 데이터가 10개가 적재 되어 있는지 확인해보자.
    


```java
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

        //실행자를 이용하여 Object객체 100개를 넣었다. 과연 100개 모두 정상적으로 들어갔을까?
        for(int i = 0; i < 100; i++) {
            executorService.submit(() -> {
               stack.push(new Object());
            });
        }
        

        Thread.sleep(1000);
        
        // Stack 클래스에 들어간 데이터를 꺼내보자 100개를 넣었으니 100개를 출력하면
        // 모두 오브젝트 객체의 주소가 나와야 한다.
        // null 값이 출력되면 덜 들어갔다는 얘기이다.
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
```
     